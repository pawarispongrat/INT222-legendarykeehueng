package sit.int221.announcement.utils.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sit.int221.announcement.enumeration.TokenType;
import sit.int221.announcement.utils.security.jwt.JwtTokenUtil;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class OtpUtil {

    private final JwtTokenUtil util;
    private final LoadingCache<String, String> cache; //key = email, value = hashOtp (Hash Token)

    @Autowired
    public OtpUtil(JwtTokenUtil util) {
        this.util = util;
        Integer EXPIRED_MIN = util.getExpiredFromType(TokenType.OTP_TOKEN);
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRED_MIN, TimeUnit.MINUTES).build(CacheLoader.from(this::loadCache));
    }

    public void removeOtp(String key){
        cache.invalidate(key);
    }

    private final static String OTP_CLAIM = "otp";
    public String generateHOTP(String email) {
        String otp = generateOtp();
        ClaimsMap map = new ClaimsMap(OTP_CLAIM,otp);
        String hashOtp = util.generateTokenWithClaims(email, TokenType.OTP_TOKEN, map);
        this.cache.put(email,hashOtp);
        return otp;
    }
    public boolean validateOtp(String validateEmail, String validateOtp) {
        String hashOtp = cache.asMap().get(validateEmail);
        if (hashOtp == null) return false;
        String storedEmail = util.getSubjectFromToken(hashOtp);
        String storedOtp = util.getClaimFromToken(hashOtp,claims -> claims.get(OTP_CLAIM).toString());
        boolean isValid = validateEmail.equals(storedEmail) && validateOtp.equals(storedOtp) && !util.isTokenExpired(hashOtp);
        if (isValid) removeOtp(validateEmail);
        return isValid;
    }

    private String generateOtp() {
        String DIGITS = "000000";
        int MAX_DIGITS = 999999;
        DecimalFormat format = new DecimalFormat(DIGITS);
        return format.format(new Random().nextInt(MAX_DIGITS));
    }
    private String loadCache(String s) {
        return "";
    }
}

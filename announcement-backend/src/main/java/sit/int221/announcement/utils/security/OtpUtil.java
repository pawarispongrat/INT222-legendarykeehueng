package sit.int221.announcement.utils.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sit.int221.announcement.enumeration.TokenType;
import sit.int221.announcement.utils.security.jwt.JwtTokenUtil;

import java.text.DecimalFormat;
import java.util.List;
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
    private final static String CATEGORY_IDS_CLAIM = "categoryIds";
    public String generateHOTP(String email, List<Integer> categoryIds) {
        String otp = generateOtp();
        ClaimsMap[] map = new ClaimsMap[] {
                new ClaimsMap(OTP_CLAIM,otp),
                new ClaimsMap(CATEGORY_IDS_CLAIM,categoryIds)
        };
        String hashOtp = util.generateTokenWithClaims(email, TokenType.OTP_TOKEN, map);
        this.cache.put(email,hashOtp);
        return otp;
    }
    public List<Integer> getCategoryIdsByOtp(String validateEmail, String validateOtp) {
        String hashOtp = cache.asMap().get(validateEmail);
        if (hashOtp == null) return null;
        String storedEmail = util.getSubjectFromToken(hashOtp);
        String storedOtp = util.getClaimFromToken(hashOtp,claims -> claims.get(OTP_CLAIM).toString());
        List<Integer> storedCategoryIds = util.getClaimFromToken(hashOtp, claims -> (List<Integer>) claims.get(CATEGORY_IDS_CLAIM,List.class));
        boolean isValid = validateEmail.equals(storedEmail) && validateOtp.equals(storedOtp) && !util.isTokenExpired(hashOtp);
        if (isValid) removeOtp(validateEmail);
        return isValid ? storedCategoryIds : null;
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

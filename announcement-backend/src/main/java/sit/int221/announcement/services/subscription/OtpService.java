package sit.int221.announcement.services.subscription;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.announcement.utils.modules.EmailModule;
import sit.int221.announcement.utils.properties.EmailProperties;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailProperties properties;

    private static final Integer EXPIRE_MIN = 5;
    private final LoadingCache<String, String> otps;
    public OtpService() {
        otps = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() { @Override public String load(String s) { return ""; }});
    }
    public void invalidateOtp(String key){
        otps.invalidate(key);
    }

    public String generateOtp() {
        String DIGITS = "000000";
        int MAX_DIGITS = 999999;
        DecimalFormat format = new DecimalFormat(DIGITS);
        return format.format(new Random().nextInt(MAX_DIGITS));
    }

    public boolean sendOtp(String email) {
        EmailModule module = new EmailModule(properties);
        String otp = generateOtp();
        otps.put(email,otp);
        return module.sendEmail(email,"HelloOtp", otp);
    }

    public boolean verifyOtp(String email,String otp) {
        String value = otps.asMap().get(email);
        if (value == null) return false;
        boolean isValid = value.equals(otp);
        if (isValid) invalidateOtp(email);
        return isValid;
    }
}

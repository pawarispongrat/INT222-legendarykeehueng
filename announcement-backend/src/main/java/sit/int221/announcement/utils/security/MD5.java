package sit.int221.announcement.utils.security;

import org.springframework.beans.factory.annotation.Value;

import java.security.MessageDigest;
import java.util.Objects;

public class MD5 {
    @Value("${spring.mail.secret}")
    private String secretKey;
    private final String plainText;
    private final String algorithm;

    public MD5(String plainText) {
        this.plainText = plainText;
        this.algorithm = "MD5";
    }

    public String encode() {
        return encode(this.plainText + "." + this.secretKey);
    }

    private String encode(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(this.algorithm);
            byte[] array = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) { return null; }
    }

    public boolean matches(String hash) {
        return Objects.equals(encode(this.plainText), hash);
    }
}
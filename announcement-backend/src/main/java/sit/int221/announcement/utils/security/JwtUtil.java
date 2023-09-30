package sit.int221.announcement.utils.security;

public class JwtUtil {

    public static boolean isNotBearer(String header) {
        return header == null || !header.startsWith("Bearer ");
    }

    public static String getTokenFromHeader(String header) {
        return header.substring(7).trim();
    }
}

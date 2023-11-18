package sit.int221.announcement.utils.security.jwt;

import sit.int221.announcement.exceptions.list.AuthorizedException;

public class JwtUtil {

    public static boolean isNotBearer(String header) {
        return header == null || !header.startsWith("Bearer ");
    }

    public static String getTokenFromHeader(String header) {
        if (isNotBearer(header)) return null;
        return header.substring(7).trim();
    }

}

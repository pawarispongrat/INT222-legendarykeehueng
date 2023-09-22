package sit.int221.announcement.utils;

import org.springframework.web.context.request.WebRequest;

public class WebUtils {


    public static String getUri(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}

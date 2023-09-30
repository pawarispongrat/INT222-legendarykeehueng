package sit.int221.announcement.utils;

import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {


    public static String getUri(WebRequest request) {
        return request.getDescription(false).substring(4);
    }

    public static Set<String> getEnumSet(Class<? extends Enum> enums) {
        return  Arrays.stream(enums.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
    public static boolean existsEnum(Class<? extends Enum> enums,String enumType) {
        if (enumType == null) return false;
        return getEnumSet(enums).contains(enumType);
    }
}

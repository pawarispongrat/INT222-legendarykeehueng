package sit.int221.announcement.utils.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

public class StringTrimModule extends StringDeserializer {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws java.io.IOException {
        final var value = super.deserialize(p, ctx);
        if (value == null) return null;
        final var trimmed = value.trim();
        if (trimmed.isEmpty()) return null;

        return trimmed;
    }

}

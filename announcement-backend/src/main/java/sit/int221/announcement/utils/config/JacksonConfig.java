package sit.int221.announcement.utils.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sit.int221.announcement.utils.modules.StringTrimModule;

@Configuration
public class JacksonConfig {

    @Autowired
    void mapper(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringTrimModule());
        mapper.registerModule(module);
    }
}
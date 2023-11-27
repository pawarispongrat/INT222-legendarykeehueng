package sit.int221.announcement;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sit.int221.announcement.services.FileService;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AnnouncementApplicationTests {

    public static void main(String[] args) throws Exception {
        RandomAccessFile f = new RandomAccessFile("morethan21mb", "rw");
        f.setLength(21 * 1024 * 1024);
    }


    @Test
    void contextLoads() {
    }


}

package ka.masato.sample.imageviewer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.jms.Queue;
import java.io.IOException;


@Slf4j
@RestController
public class ImageApiController {
    private static final String distinationName = "image.queue";
    private String filePath = "/static/test.jpg";
    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    public ImageApiController(JmsTemplate jmsTemplate, Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
    }

    @PostMapping("/api/v1/image")
    public void getImage(@RequestBody MultipartFile image) throws IOException {
        byte[] imageByte = image.getBytes();
        jmsTemplate.convertAndSend(distinationName, imageByte);
    }

}

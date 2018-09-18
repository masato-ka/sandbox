package ka.masato.sample.imageviewer.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Log4j2
@RestController
public class StompController {

    private static final String srcDestinationName = "image.queue";
    private static final String dstTopicName = "/topic/imagestream";
    private static final String imageHeader = "data:image/jpeg;base64,";

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public StompController(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @JmsListener(destination = srcDestinationName)
    public void destination(Message<byte[]> data){
        byte[] imageByte = data.getPayload();
        simpMessageSendingOperations.convertAndSend(dstTopicName, convertImageToString(imageByte));
    }


    public String convertImageToString(byte[] imageByte){
        StringBuffer sb = new StringBuffer();
        sb.append(imageHeader);
        sb.append(Base64.getEncoder().encodeToString(imageByte));
        return sb.toString();
    }



}

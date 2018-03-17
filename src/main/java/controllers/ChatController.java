package controllers;

import models.InputMessage;
import models.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by denze on 3/17/2018.
 */

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public OutputMessage sendOutputMessage(InputMessage inputMessage) throws Exception{
        return new OutputMessage(inputMessage.getMessage());
    }
}

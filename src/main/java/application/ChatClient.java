package application;

import configuration.ApplicationConfig;
import configuration.StompSessionHandlerConfig;
import models.InputMessage;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denze on 3/19/2018.
 */
public class ChatClient {

    public static void main(String[] args) throws Exception{

        //once websocket client is created and decoarted with sockJS
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);


        //decorate with stomp protocol
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(sockJsClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());


        //Initiate a session with the session handler
        StompSession session = webSocketStompClient.connect(ApplicationConfig.SERVER_HOST,new StompSessionHandlerConfig()).get();

        //Parse user input and send
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for(;;){
            String line = in.readLine();
            if(line == null) break;
            if(line.length() == 0) continue;;
            InputMessage message = new InputMessage(line);
            session.send("/app/chat/java",message);
        }
    }
}

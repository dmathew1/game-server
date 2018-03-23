package application;

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

        /**
         * Create a websocketclient
         * research about transport
         * research about sockJS and transport
         */
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);


        /**
         * After creating a websocket client....need to create websocketStompclient???
         */
        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(sockJsClient);
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());


        /**
         * What is a session handler and why do we need a stompSession handler...
         */
        String url = "ws://localhost:8080/chat";
        StompSessionHandler sessionHandler = new StompSessionHandler() {
            @Override
            public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {

                //after connected, subscribe the output channel from WS server
                stompSession.subscribe("/topic/messages", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return InputMessage.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders stompHeaders, @Nullable Object o) {
                        System.out.println(o.toString());
                    }
                });
            }

            @Override
            public void handleException(StompSession stompSession, @Nullable StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {

            }

            @Override
            public void handleTransportError(StompSession stompSession, Throwable throwable) {

            }

            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return null;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, @Nullable Object o) {

            }
        };

        /**
         * Then we need to initiate a session..
         */
        StompSession session = webSocketStompClient.connect(url,sessionHandler).get();
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

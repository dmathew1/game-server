package configuration;

import models.InputMessage;
import models.OutputMessage;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

/**
 * Created by denzel on 3/26/18.
 */
public class StompSessionHandlerConfig implements StompSessionHandler {

    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {

        //after connected, subscribe the output channel from WS server
        stompSession.subscribe(ApplicationConfig.OUTBOUND_CHANNEL, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return InputMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, @Nullable Object o) {
                System.out.println(stompHeaders.getId() + o.toString());
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

}

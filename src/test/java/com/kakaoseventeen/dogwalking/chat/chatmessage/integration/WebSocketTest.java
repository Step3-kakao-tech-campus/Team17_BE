//package com.kakaoseventeen.dogwalking.chat.chatmessage.integration;
//
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.messaging.converter.StringMessageConverter;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//import org.springframework.web.socket.sockjs.client.SockJsClient;
//import org.springframework.web.socket.sockjs.client.Transport;
//import org.springframework.web.socket.sockjs.client.WebSocketTransport;
//
//import java.lang.reflect.Type;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.TimeoutException;
//
//import static java.util.concurrent.TimeUnit.MILLISECONDS;
//import static java.util.concurrent.TimeUnit.SECONDS;
//import static org.awaitility.Awaitility.await;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // local Server Random port Setting
//public class WebSocketTest {
//
//    @Value("${local.server.port}")
//    private int port;
//
//    private BlockingQueue<String> receivedMessages;
//
//    private StompSession session;
//
//    private static final String SUBSCRIPTION_TOPIC = "/queue/chat-sub";
//    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketTest.class);
//
//    @Autowired
//    SimpMessagingTemplate messagingTemplate;
//
//    @BeforeEach
//    public void setup() throws InterruptedException, ExecutionException, TimeoutException {
//        final String URL = "ws://localhost:" + port + "/chat-connect";
//        LOGGER.info("port : {}",port);
//
//        final List<Transport> transportList = Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));
//        final WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(transportList));
//        stompClient.setMessageConverter(new StringMessageConverter());
//
//        receivedMessages = new LinkedBlockingDeque<>();
//        session = stompClient.connectAsync(URL, new MySessionHandler()).get(5, SECONDS);
//        LOGGER.info(" -----연결 성공!------");
//        await().until(this::isSubscribed);
//    }
//
//    @Test
//    public void stompTest() throws Exception {
//        final String message = "myMessage"; // payload에 담긴 메세지
//        messagingTemplate.convertAndSend(SUBSCRIPTION_TOPIC, message);
//        final String response = receivedMessages.poll(5, SECONDS);
//        Assertions.assertEquals(message, response);
//        LOGGER.info("-----테스트 성공------");
//    }
//
//    @AfterEach
//    public void reset() throws InterruptedException {
//        session.disconnect();
//        await().until(() -> !session.isConnected());
//        LOGGER.info("-----연결 종료!------");
//    }
//
//    private boolean isSubscribed() {
//        final String message = UUID.randomUUID().toString();
//
//        messagingTemplate.convertAndSend(SUBSCRIPTION_TOPIC, message);
//
//        String response = null;
//        try {
//            response = receivedMessages.poll(20, MILLISECONDS);
//
//            // drain the message queue before returning true
//            while(response!= null && !message.equals(response)) {
//                LOGGER.debug("메세지 대기열을 비웁니다.");
//                response = receivedMessages.poll(20, MILLISECONDS);
//            }
//
//        } catch (InterruptedException e) {
//            LOGGER.debug("수신된 메시지 폴링이 중단되었습니다.", e);
//        }
//
//        return response != null;
//    }
//
//    private class MySessionHandler extends StompSessionHandlerAdapter {
//        @Override
//        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//            session.subscribe(SUBSCRIPTION_TOPIC, this);
//        }
//
//        @Override
//        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
//            LOGGER.warn("Stomp 에러:", exception);
//        }
//
//        @Override
//        public void handleTransportError(StompSession session, Throwable exception) {
//            super.handleTransportError(session, exception);
//            LOGGER.warn("Stomp 전송 계층 Error:", exception);
//        }
//
//        @Override
//        public Type getPayloadType(StompHeaders headers) {
//            return String.class;
//        }
//
//        @Override
//        public void handleFrame(StompHeaders stompHeaders, Object o) {
//            LOGGER.info("payload가 있는 Handle Frame: {}", o);
//            try {
//                receivedMessages.offer((String) o, 500, MILLISECONDS);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
//

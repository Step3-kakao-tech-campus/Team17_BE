package com.kakaoseventeen.dogwalking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/connect")
                .setAllowedOriginPatterns("*") // TODO - CORS 정책 논의 후 수정할 것
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/api/topic"); // onetoone이기 때문에 queue를 받아준다.
        registry.setApplicationDestinationPrefixes("/api/app");// 받은 데이터를 컨트롤러에서 받고 브로커로 통하게 하고 싶으면 app prefix로 설정하고 브로커로 넘긴다.

    }

}

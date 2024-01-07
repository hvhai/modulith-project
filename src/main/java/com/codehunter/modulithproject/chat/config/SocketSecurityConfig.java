package com.codehunter.modulithproject.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

import static org.springframework.messaging.simp.SimpMessageType.MESSAGE;
import static org.springframework.messaging.simp.SimpMessageType.SUBSCRIBE;

@Configuration
@EnableWebSocketSecurity
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpDestMatchers("/chat/**").permitAll()
                .anyMessage().permitAll()
        ;
//        messages
//                .simpDestMatchers("/chat/**").authenticated()
//                .anyMessage().authenticated();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
//    @Bean
//    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        messages
//                .nullDestMatcher().permitAll()
////                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
////                .simpDestMatchers("/app/**").hasRole("USER")
////                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
////                .simpDestMatchers("/app/**").hasRole("USER")
////                .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
//                .simpTypeMatchers(MESSAGE, SUBSCRIBE).permitAll()
//                .anyMessage().permitAll();
////                .nullDestMatcher().authenticated()
////                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
////                .simpDestMatchers("/app/**").hasRole("USER")
////                .simpSubscribeDestMatchers("/user/queue/errors").permitAll()
////                .simpDestMatchers("/app/**").hasRole("USER")
////                .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER")
////                .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
////                .anyMessage().denyAll();
//
//        return messages.build();
//    }
}

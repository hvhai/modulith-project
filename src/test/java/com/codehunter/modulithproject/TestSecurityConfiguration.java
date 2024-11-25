package com.codehunter.modulithproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

@Lazy
@TestConfiguration
public class TestSecurityConfiguration {

    private final ClientRegistration clientRegistration;

    public TestSecurityConfiguration() {
        this.clientRegistration = clientRegistration().build();
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    private ClientRegistration.Builder clientRegistration() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("end_session_endpoint", "https://example.org/logout");

        return ClientRegistration.withRegistrationId("okta")
                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("read:user")
                .authorizationUri("https://example.org/login/oauth/authorize")
                .tokenUri("https://example.org/login/oauth/access_token")
                .jwkSetUri("https://example.org/oauth/jwk")
                .userInfoUri("https://api.example.org/user")
                .providerConfigurationMetadata(metadata)
                .userNameAttributeName("id")
                .clientName("Client Name")
                .clientId("client-id")
                .clientSecret("client-secret");
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return mock(JwtDecoder.class);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }


    @Bean
    public ReactiveClientRegistrationRepository testClientRegistrations(
            @Value("${app.wiremock.oauth2.url}") String tokenUri
//            @Value("${spring.security.oauth2.client.provider.okta.token-uri}") String tokenUri,
//            @Value("${spring.security.oauth2.client.registration.okta.client-id}") String clientId,
//            @Value("${spring.security.oauth2.client.registration.okta.client-secret}") String clientSecret,
//            @Value("${spring.security.oauth2.client.registration.okta.scope}") List<String> scopes,
//            @Value("${spring.security.oauth2.client.registration.okta.authorization-grant-type}") String grantType
    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("okta")
                .tokenUri(tokenUri + "/token")
                .clientId("client-id")
                .clientSecret("client-secret")
                .scope(List.of("read:user"))
                .authorizationGrantType(new AuthorizationGrantType("client_credentials"))
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public WebTestClient serviceTestClient(ReactiveClientRegistrationRepository testClientRegistrations,
                                           @LocalServerPort int port) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService
                = new InMemoryReactiveOAuth2AuthorizedClientService(testClientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager
                = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(testClientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth
                = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("okta");
        return WebTestClient
                .bindToServer().baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofMillis(30000))
                .filter(oauth)
                .build();
    }
}

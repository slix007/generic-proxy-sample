package org.slix.mproxy.sender;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component("payloadSenderHttp")
@Setter
public class PayloadSenderHttp implements PayloadSender {

    private WebClient client = WebClient
            .builder()
            .baseUrl("http://localhost:8088")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public void sendingForward(Object payload) {
        final WebClient.RequestBodyUriSpec post = client.post();
        final WebClient.RequestBodySpec uri = post.uri("/receiver/item");
        final WebClient.RequestHeadersSpec<?> body = uri.body(BodyInserters.fromValue(payload));
        //noinspection ConstantConditions
        String resp = post.exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        log.info("FinalBackend response: " + resp);
        if (resp == null || resp.equals("not ok")) {
            throw new IllegalStateException("FinalBackend did not accept theItem " + payload);
        }
    }

}

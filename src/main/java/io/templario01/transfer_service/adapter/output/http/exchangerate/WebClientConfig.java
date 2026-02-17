package io.templario01.transfer_service.adapter.output.http.exchangerate;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient exchangeRateWebClient(ExchangeRateProperties props) {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        props.getTimeouts().getConnect())
                .doOnConnected(conn -> conn
                        .addHandlerLast(
                                new ReadTimeoutHandler(props.getTimeouts().getRead()))
                        .addHandlerLast(
                                new WriteTimeoutHandler(props.getTimeouts().getWrite()))
                );

        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
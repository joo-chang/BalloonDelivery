package com.sparta.gateway.client;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Configuration
public class FeignConfig {

    @Bean
    public HttpMessageConverters messageConverters() {
        return new HttpMessageConverters();
    }

    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(this::messageConverters);
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(this::messageConverters);
    }
}
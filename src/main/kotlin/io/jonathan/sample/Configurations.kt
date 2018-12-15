package io.jonathan.sample

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(WebClientProperties::class)
class PropertiesConfiguration

@Configuration
class WebConfiguration {

    @Bean fun webClientBuilder() = WebClient.builder()
}
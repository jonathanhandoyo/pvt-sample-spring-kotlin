package io.jonathan.sample

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SpringAdapter(val webClientProperties: WebClientProperties,
                    val webClientBuilder: WebClient.Builder) {

    fun getActuatorInfo(): Mono<Info> {
        return this.webClientBuilder.baseUrl(this.webClientProperties.spring.url).build()
                .method(HttpMethod.GET).uri { it -> it.pathSegment("actuator", "info").build() }
                .exchange()
                .flatMap { it -> it.bodyToMono(Info::class.java) }
    }
}
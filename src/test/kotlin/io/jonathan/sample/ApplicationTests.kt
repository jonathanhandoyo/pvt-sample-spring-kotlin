package io.jonathan.sample

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier


@ExtendWith(SpringExtension::class)
@SpringBootTest
class ApplicationTests(@Autowired val springAdapter: SpringAdapter) {

    val response: String = """
        {
          "git": {
            "commit": {
              "time": "2018-12-13T08:46:54Z",
              "id": "bd31cf1"
            },
            "branch": "bd31cf1658a29ea3cab3dd2dfc9dd8f830f74b9e"
          },
          "build": {
            "version": "0.0.2-SNAPSHOT",
            "artifact": "start-site",
            "name": "start.spring.io website",
            "group": "io.spring.start",
            "time": "2018-12-13T08:49:29.257Z",
            "versions": {
              "spring-boot": "2.1.1.RELEASE"
            }
          }
        }
        """.trimIndent()
    val server: MockWebServer = MockWebServer().also { it.start(54321) }

    @Test
    fun contextLoads() {
        this.server.enqueue(MockResponse().setBody(response).addHeader("Content-Type", "application/json"))

        StepVerifier.create(this.springAdapter.getActuatorInfo())
                .expectSubscription()
                .consumeNextWith { it ->
                    assert(it.build.version == "0.0.2-SNAPSHOT")
                    assert(it.build.artifact == "start-site")
                    assert(it.git.branch == "bd31cf1658a29ea3cab3dd2dfc9dd8f830f74b9e")
                    println(it)
                }
                .verifyComplete()
    }
}
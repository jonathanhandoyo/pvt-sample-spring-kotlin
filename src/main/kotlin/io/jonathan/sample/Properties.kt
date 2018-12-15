package io.jonathan.sample

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "test.web-client")
@Configuration
class WebClientProperties {
    val spring: ConfigItem = ConfigItem()
}

class ConfigItem {
    lateinit var url: String
}
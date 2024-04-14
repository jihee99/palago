package com.ex.ticket.security.config;

import com.ex.ticket.security.jwt.JwtProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    JwtProperties.class
})
@Configuration
public class ConfigurationPropertiesConfig {}

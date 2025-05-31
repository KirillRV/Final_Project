package com.tms.casino;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@OpenAPIDefinition(info = @Info(
        title = "Online Casino API",
        description = "Backend system for online casino operations",
        contact = @Contact(
                name = "Casino Support",
                email = "support@tms-casino.com"
        )
))
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TmsCasinoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TmsCasinoApplication.class, args);
    }
}
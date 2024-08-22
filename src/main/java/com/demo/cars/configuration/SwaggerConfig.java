package com.demo.cars.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Car Booking Service",
        description = "API for managing users, cars, payments, places and booking history"))
public class SwaggerConfig {
}

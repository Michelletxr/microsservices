package com.br.cloudfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudFunctionApplication.class, args);
	}

	@Bean
	public Function<Flux<String>, Flux<String>> port() {
		return stringFlux -> stringFlux.map(value ->  "server in port: ".concat(value));
	}

}

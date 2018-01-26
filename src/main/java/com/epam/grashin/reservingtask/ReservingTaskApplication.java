package com.epam.grashin.reservingtask;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ReservingTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservingTaskApplication.class, args);
	}

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.apiInfo(new ApiInfoBuilder().title("Train ticker booking api").description("Ticket booking testing task").build())
				.select()
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				.build();
	}
}

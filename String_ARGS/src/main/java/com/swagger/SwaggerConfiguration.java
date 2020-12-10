package com.swagger;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	/**
	 * @author prayas
	 * @description Adding LAN API v4 in Swagger
	 * @since 03-11-2020
	 */
	@Bean
	@Conditional(SwaggerCondition.class)
	public Docket swaggerApiv4() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("LAN-api-v4.0").select()
				.apis(RequestHandlerSelectors.basePackage("com.controller")).paths(regex("/")).build()
				.apiInfo(new ApiInfoBuilder().version("4.0").title("LAN API").description("Documentation LAN API v4.0")
						.build());
	}

}

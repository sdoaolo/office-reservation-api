package com.lottehealthcare.officereservationsystem.configuration.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableWebMvc
class SwaggerConfig {

    @Bean
    fun swaggerApi(): Docket = Docket(DocumentationType.OAS_30)
        .consumes(getConsumeContentTypes())
        .produces(getProduceContentTypes())
        .apiInfo(swaggerInfo())
        .select()
        .paths(PathSelectors.ant("/error").negate())
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .useDefaultResponseMessages(false)

        private fun swaggerInfo() = ApiInfoBuilder()
            .title("Office Reservation System")
            .description("롯데헬스케어 과제 - 강지은")
            .version("1.0.0")
            .build()

        private fun getConsumeContentTypes(): Set<String> {
            val consumes = HashSet<String>()
            consumes.add("multipart/form-data")
            return consumes
        }

        private fun getProduceContentTypes(): Set<String> {
            val produces = HashSet<String>()
            produces.add("application/json;charset=UTF-8")
            return produces
        }
}
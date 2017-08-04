package com.sample.jms.ibmmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  @Value(value = "${swagger.spring.ibm.integration.title}")
  protected String title;
  @Value(value = "${swagger.spring.ibm.integration.description}")
  protected String description;
  @Value(value = "${swagger.spring.ibm.integration.version}")
  protected String version;
  @Value(value = "${swagger.spring.ibm.integration.license}")
  protected String license;
  @Value(value = "${swagger.spring.ibm.integration.licenseUrl}")
  protected String licenseUrl;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(new ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
                .license(license)
                .licenseUrl(licenseUrl)
                .contact(new springfox.documentation.service.Contact("Prasad Dasari","www.dasariprasad.com","dvsavprasad@gmail.com"))
            .build()

        );

  }

}

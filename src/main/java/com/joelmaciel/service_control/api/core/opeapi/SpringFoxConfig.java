package com.joelmaciel.service_control.api.core.opeapi;


import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.joelmaciel.service_control.api.exceptionhandler.Problem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("joelmaciel.service_control.api"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PATCH, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))

                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))

                .apiInfo(apiInfo())
                .tags(new Tag("Clients", "Client Registration"))
                .tags(new Tag("Authentication", "User Authentication"))
                .tags(new Tag("Service Provided", "Control of Services Provided"))
                .tags(new Tag("Users", "Listing and Updating Users"));



    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Internal Server Error")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Asset has no representation that can be accepted by the consumer")
                        .build()
        );
    }

    private HttpAuthenticationScheme authenticationScheme() {
        return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
    }
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }


    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }


    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey",
                authorizationScopes));
    }



    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.CONFLICT.value()))
                        .description("Request with duplicate CPF")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .description("Resource not found")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Invalid request (user error)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),

                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Internal Server Error")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build()


        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NO_CONTENT.value()))
                        .description("Successfully deleted.)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                        .description("Resource not found")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Invalid request (client error)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Internal server error")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build()
        );
    }

    private Consumer<RepresentationBuilder> getProblemModelReference() {
        return r -> r.model(m -> m.name("Problem")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problem").namespace("com.joel.clients.api.exceptionhandler")))));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("System Control")
                .description("Client control system")
                .version("1")
                .contact(new Contact("Joel Maciel",
                        "http://localhost:4200/clients-list", "jmviana37@gmail.com"))
                .build();
    }
}



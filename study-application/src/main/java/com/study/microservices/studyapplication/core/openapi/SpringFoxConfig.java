package com.study.microservices.studyapplication.core.openapi;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.study.microservices.studyapplication.core.openapi.model.PageableModelOpenApi;
import com.study.microservices.studyapplication.core.openapi.model.RestaurantsPageModelOpenApi;
import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import com.study.microservices.studyapplication.exceptionhandler.ErrorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
/* WebMvcConfigurer --> Allow access to our html swagger */
/* @EnableSwagger2 --> Habilitando o swagger */
/* BeanValidatorPluginsConfiguration --> showing to swagger the annotations of bean validations */
public class SpringFoxConfig implements WebMvcConfigurer {

    /* To replace every session of a controller to a new name and description, without show a controller name */
    private static final Tag[] TAGS = {
            new Tag("City", "Manage the cities"),
            new Tag("Restaurant", "Manage the restaurants")};

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(SWAGGER_2)
                .select()
                /* Any controller found in this package */
                .apis(RequestHandlerSelectors.basePackage("com.study.microservices.studyapplication.api"))
                /* It's possible pass more than one .apis(Predicates.and("RequestHandlerSelectors1", "RequestHandlerSelectors2")) */
                /* It's possible to apply another filter after .apis with .paths(PathSelectors.ant("/restaurant/*")) */
                .build()
                /* Not generate http codes just the response status of the resource in the controller */
                .useDefaultResponseMessages(false)
                .globalResponseMessage(GET, globalGetResponseMessages())
                .additionalModels(typeResolver.resolve(ErrorDto.class))
                /* ask to replace pageable spring request to other documented */
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                /* ask to replace page spring response to other documented */
                .alternateTypeRules(buildAlternateTypeRulesForPage(typeResolver, RestaurantDto.class, RestaurantsPageModelOpenApi.class))
                //.securitySchemes(Arrays.asList(securityScheme()))
                .apiInfo(apiInfo())
                .tags(TAGS[0], TAGS);
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder().name("Studyapplication")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
}

    private List<GrantType> grantTypes() {
        return singletonList(new ResourceOwnerPasswordCredentialsGrant("http://localhost:7070/auth/realms/studyapplication/protocol/openid-connect/token"));
    }

    private List<AuthorizationScope> scopes() {
        return Arrays.asList(new AuthorizationScope("email", "email"),
                new AuthorizationScope("profile", "profile"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return List.of(buildResponseMessage(INTERNAL_SERVER_ERROR, "Internal error server.", "Error"),
                buildResponseMessage(NOT_ACCEPTABLE, "Representation not acceptable."));
    }

    private AlternateTypeRule buildAlternateTypeRulesForPage(TypeResolver typeResolver, Class<?> dto, Class<?> newRepresentation) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(Page.class, dto), newRepresentation);
    }

    private ResponseMessage buildResponseMessage(HttpStatus status, String message, String modelRef) {
        if (isNull(status)) {
            return null;
        }

        return new ResponseMessageBuilder().code(status.value())
                .message(message)
                /* Add a class representation to the http code */
                .responseModel(new ModelRef(modelRef))
                .build();
    }

    private ResponseMessage buildResponseMessage(HttpStatus status, String message) {
        if (isNull(status)) {
            return null;
        }

        return new ResponseMessageBuilder().code(status.value())
                .message(message)
                /* Add a class representation to the http code */
                .build();
    }

    /**
     * This method will configure a initial page[header] of swagger html with information about the api.
     * @return
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Study Application")
                .description("Api to practice Spring Rest")
                .version("1")
                .contact(new Contact("StudyApplication", "https://", "rospielberg@gmail.com"))
                .build();
    }

    /* Showing when the spring can get the html page of swagger */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String locationResourcesSwagger = "classpath:/META-INF/resources/";

        /* Registry the name of swagger page */
        registry.addResourceHandler("swagger-ui.html")
                /* where we can find de files */
                .addResourceLocations(locationResourcesSwagger);
        /* where we can find files .css and .js */
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(locationResourcesSwagger.join("", "webjars/"));
    }
}

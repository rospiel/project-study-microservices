package com.study.microservices.studyapplication.api;

import com.study.microservices.studyapplication.domain.dto.KitchenDto;
import com.study.microservices.studyapplication.domain.service.KitchenService;
import com.study.microservices.studyapplication.domain.service.RestaurantService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.study.microservices.studyapplication.util.DatabaseCleaner;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/* to ensure all injections */
@RunWith(SpringRunner.class)
/* to use features from spring in our tests,
   in this case we are asking for a web environment, to request directly on endpoint*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
public class KitchenApiIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private KitchenService service;

    @Autowired
    private RestaurantService restaurantService;

    private List<KitchenDto> kitchens = new ArrayList<>();
    private static String TOKEN;

    @BeforeClass
    public static void setupToken() {
        getToken();
    }

    @Before
    public void setup() {
        /* print errors */
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        /* get the port from de server */
        RestAssured.port = this.port;
        /* RequestMapping - class level */
        RestAssured.basePath = "/kitchens";

        cleaner.clearTables();
        setupDataBase();
    }

    @Test
    public void testGetAllKitchenHttp200() {
        given().accept(ContentType.JSON)
                .auth().oauth2(TOKEN)
                .when()
                .get()
                .then()
                .statusCode(OK.value());
    }

    @Test
    public void testGetKitchenByIdHttp200() {
        given().pathParam("kitchenId", kitchens.get(0).getId())
                .accept(ContentType.JSON)
                .auth().oauth2(TOKEN)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(OK.value())
                .body("id", equalTo(kitchens.get(0).getId().intValue()))
                .body("name", equalTo(kitchens.get(0).getName()));
    }

    @Test
    public void testGetKitchenByIdHttp422() {
        long kitchenIdNonexistent = NumberUtils.toLong("9999999");

        given().pathParam("kitchenId", kitchenIdNonexistent)
                .accept(ContentType.JSON)
                .auth().oauth2(TOKEN)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(UNPROCESSABLE_ENTITY.value())
                .body("detail", equalTo(format("Kitchen of id %s not found.", kitchenIdNonexistent)))
                .body("title", equalTo("Entity cannot be processed"));
    }

    @Test
    public void testInsertKitchenHttp201() {
        /* body receive a entity and the process convert to json */
        given().body(new KitchenDto(null, "Indian"))
                .auth().oauth2(TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value());
    }

    private void setupDataBase() {
        kitchens.add(service.save(new KitchenDto(null, "American")));
        kitchens.add(service.save(new KitchenDto(null, "Arabic")));
    }

    private static void getToken() {
        TOKEN = given().contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("password", "rodrigo")
                .formParam("username", "rodrigo")
                .formParam("client_id", "studyapplication_password")
                .formParam("grant_type", "password")
                .when()
                .post("http://keycloak:8080/auth/realms/studyapplication/protocol/openid-connect/token")
                .then()
                .extract()
                .response()
                .jsonPath()
                .get("access_token");
    }
}

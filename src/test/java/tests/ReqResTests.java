package tests;

import io.restassured.http.ContentType;
import models.CreateUserReqBodyModel;
import models.CreateUserResBodyModel;
import org.junit.jupiter.api.Test;
import io.qameta.allure.restassured.AllureRestAssured;


import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReqResTests {

    @Test
    public void successUserCreationTest(){
        CreateUserReqBodyModel CreateUserReqBM = new CreateUserReqBodyModel();
        CreateUserReqBM.setName("morpheus");
        CreateUserReqBM.setJob("leader");

        CreateUserResBodyModel createUserResBM = given()
                .filter(withCustomTemplates())
                .body(CreateUserReqBM)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().headers()
                .log().body()
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateUserResBodyModel.class);

        assertEquals("morpheus", createUserResBM.getName());
        assertEquals("leader", createUserResBM.getJob());
        assertNotEquals("", createUserResBM.getId());
        assertNotEquals("", createUserResBM.getCreatedAt());
    }

    @Test
    public void successUserCreationFAILEDTest(){
        CreateUserReqBodyModel CreateUserReqBM = new CreateUserReqBodyModel();
        CreateUserReqBM.setName("morpheus");
        CreateUserReqBM.setJob("leader");

        CreateUserResBodyModel createUserResBM = given()
                .filter(withCustomTemplates())
                .body(CreateUserReqBM)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().headers()
                .log().body()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateUserResBodyModel.class);

        assertEquals("morpheus1", createUserResBM.getName());
        assertEquals("leader", createUserResBM.getJob());
        assertNotEquals("", createUserResBM.getId());
        assertNotEquals("", createUserResBM.getCreatedAt());
    }
//
//    @Test
//    public void successUserCreationWithoutJobTest(){
//        String createData = "{ \"name\": \"morpheus\"}";
//
//        given()
//                .body(createData)
//                .contentType(ContentType.JSON)
//                .log().uri()
//        .when()
//                .post("https://reqres.in/api/users")
//        .then()
//                .log().status()
//                .log().body()
//                .statusCode(201)
//                .body("name", is("morpheus"))
//                .body("job", is(nullValue()));
//    }
//
//    @Test
//    public void wrongBodyUserCreation400Test(){
//        String createData = "{ blah blah blah }";
//
//        given()
//                .body(createData)
//                .contentType(ContentType.JSON)
//                .log().uri()
//        .when()
//                .post("https://reqres.in/api/users")
//        .then()
//                .log().status()
//                .log().body()
//                .statusCode(400)
//                .body(containsString("Bad Request"));
//    }
//
//    @Test
//    public void noBodyUserCreation415Test(){
//        given()
//                .log().uri()
//        .when()
//                .post("https://reqres.in/api/users")
//        .then()
//                .log().status()
//                .log().body()
//                .statusCode(415)
//                .body(containsString("Unsupported Media Type"));
//    }
//
//    @Test
//    public void successUserDeleteTest() {
//        given()
//                .log().uri()
//        .when()
//                .delete("https://reqres.in/api/user/2")
//        .then()
//                .log().status()
//                .log().body()
//                .statusCode(204);
//    }
}

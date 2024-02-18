package tests;

import io.restassured.http.ContentType;
import models.CreateUserErrorResBodyModel;
import models.CreateUserReqBodyModel;
import models.CreateUserResBodyModel;
import models.CreateUserWrongFormatReqBodyModel;
import org.junit.jupiter.api.Test;
import io.qameta.allure.restassured.AllureRestAssured;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static specs.CreateUserSpec.createUserReqSpec;
import static specs.CreateUserSpec.createUserResSpec;

public class ReqResTests {

    @Test
    public void successUserCreationTest(){
        CreateUserReqBodyModel createUserReqBM = new CreateUserReqBodyModel();
        createUserReqBM.setName("morpheus");
        createUserReqBM.setJob("leader");

        CreateUserResBodyModel createUserResBM = step("Make request", ()->
            given(createUserReqSpec)
                .body(createUserReqBM)
            .when()
                .post()
            .then()
                .spec(createUserResSpec)
                .extract().as(CreateUserResBodyModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", createUserResBM.getName());
            assertEquals("leader", createUserResBM.getJob());
            assertNotEquals("", createUserResBM.getId());
            assertNotEquals("", createUserResBM.getCreatedAt());
        });
    }


    @Test
    public void successUserCreationWithoutJobTest(){
        CreateUserReqBodyModel createUserReqBM = new CreateUserReqBodyModel();
        createUserReqBM.setName("morpheus");

        CreateUserResBodyModel createUserResBM = step("Make request", ()->
            given(createUserReqSpec)
                .body(createUserReqBM)
            .when()
                .post()
            .then()
                .extract().as(CreateUserResBodyModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", createUserResBM.getName());
            assertNull(createUserResBM.getJob());
        });
    }

    @Test
    public void wrongBodyUserCreation400Test(){
        CreateUserWrongFormatReqBodyModel createUserWrongFormatReqBM = new CreateUserWrongFormatReqBodyModel();
        createUserWrongFormatReqBM.setBody("{blah blah blah}");

        CreateUserErrorResBodyModel createUserErrorResBM = step("Make request", ()->
        given()
                .body(createUserWrongFormatReqBM)
        .when()
                .post()
        .then()
                .extract().as(CreateUserErrorResBodyModel.class));


        //                .body(containsString("Bad Request"));
    }

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

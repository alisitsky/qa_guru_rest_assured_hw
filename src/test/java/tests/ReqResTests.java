package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.CreateUserSpec.*;

public class ReqResTests {

    @Test
    public void successUserCreationTest() {
        CreateUserReqBodyModel createUserReqBM = new CreateUserReqBodyModel();
        createUserReqBM.setName("morpheus");
        createUserReqBM.setJob("leader");

        CreateUserResBodyModel createUserResBM = step("Make request", () ->
                given(createUserReqSpec)
                        .body(createUserReqBM)
                .when()
                        .post()
                .then()
                        .spec(createUser201ResSpec)
                        .extract().as(CreateUserResBodyModel.class));

        step("Check response", () -> {
            assertEquals("morpheus", createUserResBM.getName());
            assertEquals("leader", createUserResBM.getJob());
            assertNotEquals("", createUserResBM.getId());
            assertNotEquals("", createUserResBM.getCreatedAt());
        });
    }

    @Test
    public void successUserCreationWithoutJobTest() {
        CreateUserReqBodyModel createUserReqBM = new CreateUserReqBodyModel();
        createUserReqBM.setName("morpheus");

        CreateUserResBodyModel createUserResBM = step("Make request", () ->
                given(createUserReqSpec)
                        .body(createUserReqBM)
                .when()
                        .post()
                .then()
                        .spec(createUser201ResSpec)
                        .extract().as(CreateUserResBodyModel.class));

        step("Check response", () -> {
            assertEquals("morpheus", createUserResBM.getName());
            assertNull(createUserResBM.getJob());
        });
    }

    @Test
    public void unsuccessfulRegister400Test() {
        UnsuccessfulRegisterReqBodyModel unsuccessfulRegisterReqBM = new UnsuccessfulRegisterReqBodyModel();
        unsuccessfulRegisterReqBM.setEmail("sydney@fife");

        UnsuccessfulRegisterResBodyModel unsuccessfulRegisterErrorResBM = step("Make request", () ->
                given(unsuccessfulRegisterReqSpec)
                        .body(unsuccessfulRegisterReqBM)
                .when()
                        .post()
                .then()
                        .spec(unsuccessfulRegister400ResSpec)
                        .extract().as(UnsuccessfulRegisterResBodyModel.class));

        step("Check response", () -> {
            assertEquals("Missing password", unsuccessfulRegisterErrorResBM.getError());
        });
    }

    @Test
    public void updateUserTest(){
        UpdateUserReqBodyModel updateUserReqBM = new UpdateUserReqBodyModel();
        updateUserReqBM.setName("morpheus");
        updateUserReqBM.setJob("zion resident");

        UpdateUserResBodyModel updateUserResBM =  step("Make request", () ->
        given(updateUserReqSpec)
                .body(updateUserReqBM)
        .when()
                .put()
        .then()
                .spec(updateUser200ResSpec)
                .extract().as(UpdateUserResBodyModel.class));

        step("Check response", () -> {
            assertEquals("morpheus", updateUserResBM.getName());
            assertEquals("zion resident", updateUserResBM.getJob());
            assertNotEquals("", updateUserResBM.getId());
            assertNotEquals("", updateUserResBM.getUpdatedAt());
        });
        }




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

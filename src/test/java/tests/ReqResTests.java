package tests;

import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.Test;

import static data.ReqResData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.CreateUserSpec.*;
import static specs.DeleteUserSpec.deleteUser200ResSpec;
import static specs.DeleteUserSpec.deleteUserReqSpec;
import static specs.RegisterUserSpec.unsuccessfulRegister400ResSpec;
import static specs.RegisterUserSpec.unsuccessfulRegisterReqSpec;
import static specs.UpdateUserSpec.updateUser200ResSpec;
import static specs.UpdateUserSpec.updateUserReqSpec;

public class ReqResTests extends TestBase {

    @Test
    public void successfulUserCreationTest() {
        CreateUserReqBodyModel createUserReqBM = new CreateUserReqBodyModel();
        createUserReqBM.setName("morpheus");
        createUserReqBM.setJob("leader");

        CreateUserResBodyModel createUserResBM = step("Make request", () ->
                given(createUserReqSpec)
                        .basePath(basePath + usersPath)
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
    public void successfulUserCreationWithoutJobTest() {
        CreateUserReqBodyModel createUserReqBM = new CreateUserReqBodyModel();
        createUserReqBM.setName("morpheus");

        CreateUserResBodyModel createUserResBM = step("Make request", () ->
                given(createUserReqSpec)
                        .basePath(basePath + usersPath)
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
                        .basePath(RestAssured.basePath + registerPath)
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
    public void successfulUserUpdateTest(){
        UpdateUserReqBodyModel updateUserReqBM = new UpdateUserReqBodyModel();
        updateUserReqBM.setName("morpheus");
        updateUserReqBM.setJob("zion resident");

        UpdateUserResBodyModel updateUserResBM =  step("Make request", () ->
        given(updateUserReqSpec)
                .basePath(RestAssured.basePath + userId2Path)
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

    @Test
    public void successfulUserDeleteTest() {
        given(deleteUserReqSpec)
                .basePath(RestAssured.basePath + userId2Path)
        .when()
                .delete()
        .then()
                .spec(deleteUser200ResSpec);
    }
}

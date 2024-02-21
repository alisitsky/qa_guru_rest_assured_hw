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
        CreateUserReqResBodyModel createUserReqResBM = new CreateUserReqResBodyModel();
        createUserReqResBM.setName("morpheus");
        createUserReqResBM.setJob("leader");

        CreateUserReqResBodyModel createUserResBM = step("Make request", () ->
                given(createUserReqSpec)
                        .basePath(basePath + usersPath)
                        .body(createUserReqResBM)
                .when()
                        .post()
                .then()
                        .spec(createUser201ResSpec)
                        .extract().as(CreateUserReqResBodyModel.class));

        step("Check response", () -> {
            assertEquals("morpheus", createUserResBM.getName());
            assertEquals("leader", createUserResBM.getJob());
            assertNotEquals("", createUserResBM.getId());
            assertNotEquals("", createUserResBM.getCreatedAt());
        });
    }

    @Test
    public void successfulUserCreationWithoutJobTest() {
        CreateUserReqResBodyModel createUserReqResBM = new CreateUserReqResBodyModel();
        createUserReqResBM.setName("morpheus");

        CreateUserReqResBodyModel createUserResBM = step("Make request", () ->
                given(createUserReqSpec)
                        .basePath(basePath + usersPath)
                        .body(createUserReqResBM)
                .when()
                        .post()
                .then()
                        .spec(createUser201ResSpec)
                        .extract().as(CreateUserReqResBodyModel.class));

        step("Check response", () -> {
            assertEquals("morpheus", createUserResBM.getName());
            assertNull(createUserResBM.getJob());
        });
    }

    @Test
    public void unsuccessfulRegister400Test() {
        UnsuccessfulRegisterReqResBodyModel unsuccessfulRegisterReqResBM = new UnsuccessfulRegisterReqResBodyModel();
        unsuccessfulRegisterReqResBM.setEmail("sydney@fife");

        UnsuccessfulRegisterReqResBodyModel unsuccessfulRegisterErrorReqResBM = step("Make request", () ->
                given(unsuccessfulRegisterReqSpec)
                        .basePath(RestAssured.basePath + registerPath)
                        .body(unsuccessfulRegisterReqResBM)
                .when()
                        .post()
                .then()
                        .spec(unsuccessfulRegister400ResSpec)
                        .extract().as(UnsuccessfulRegisterReqResBodyModel.class));

        step("Check response", () -> {
            assertEquals("Missing password", unsuccessfulRegisterErrorReqResBM.getError());
        });
    }

    @Test
    public void successfulUserUpdateTest(){
        UpdateUserReqResBodyModel updateUserReqResBM = new UpdateUserReqResBodyModel();
        updateUserReqResBM.setName("morpheus");
        updateUserReqResBM.setJob("zion resident");

        UpdateUserReqResBodyModel updateUserResBM =  step("Make request", () ->
        given(updateUserReqSpec)
                .basePath(RestAssured.basePath + userId2Path)
                .body(updateUserReqResBM)
        .when()
                .put()
        .then()
                .spec(updateUser200ResSpec)
                .extract().as(UpdateUserReqResBodyModel.class));

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

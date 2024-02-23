package tests;

import models.CreateUserReqResBodyModel;
import models.UnsuccessfulRegisterReqResBodyModel;
import models.UpdateUserReqResBodyModel;
import org.junit.jupiter.api.Test;

import static data.ReqResData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.CreateUserSpec.createUser201ResSpec;
import static specs.CreateUserSpec.createUserReqSpec;
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
                        .body(createUserReqResBM)
                .when()
                        .post(usersPath)
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
                        .body(createUserReqResBM)
                .when()
                        .post(usersPath)
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
                        .body(unsuccessfulRegisterReqResBM)
                .when()
                        .post(registerPath)
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
                .body(updateUserReqResBM)
        .when()
                .put(userId2Path)
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
        .when()
                .delete(userId2Path)
        .then()
                .spec(deleteUser200ResSpec);
    }
}

package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class DeleteUserSpec {

    public static RequestSpecification deleteUserReqSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().headers()
            .log().body();

    public static ResponseSpecification deleteUser200ResSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .log(BODY)
            .build();
}



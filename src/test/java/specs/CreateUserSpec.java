package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class CreateUserSpec {

    public static RequestSpecification createUserReqSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().uri()
            .log().headers()
            .log().body();

    public static ResponseSpecification createUser201ResSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();
}



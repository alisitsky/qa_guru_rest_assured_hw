package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class UpdateUserSpec {

    public static RequestSpecification updateUserReqSpec = with()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON)
            .log().uri()
            .log().headers()
            .log().body();

    public static ResponseSpecification updateUser200ResSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();
}



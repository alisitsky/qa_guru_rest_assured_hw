import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqResTests {

    @Test
    public void successUserCreationTest(){
        String createData = "{ \"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(createData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", not(emptyString()))
                .body("createdAt", not(emptyString()));
    }

    @Test
    public void successUserCreationWithoutJobTest(){
        String createData = "{ \"name\": \"morpheus\"}";

        given()
                .body(createData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is(nullValue()));
    }

    @Test
    public void wrongBodyUserCreation400Test(){
        String createData = "{ blah blah blah }";

        given()
                .body(createData)
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body(containsString("Bad Request"));
    }

    @Test
    public void noBodyUserCreation415Test(){
        given()
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(415)
                .body(containsString("Unsupported Media Type"));
    }

    @Test
    public void successUserDeleteTest() {
        given()
                .log().uri()
                .when()
                .delete("https://reqres.in/api/user/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}

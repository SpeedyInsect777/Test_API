package Test_01;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;


public class Test02_Spartans {

    Faker faker = new Faker();

    String name = faker.name().firstName();
    String gender = faker.options().option("Male", "Female");
    long phone = Long.parseLong(faker.number().digits(10));

    String apiSpartans = "/api/spartans/";

    String postNewSpartan = "{\n" +
            "\"gender\":\"" + gender + "\",\n" +
            "\"name\":\"" + name + "\",\n" +
            "\"phone\":" + phone + "\n" + "}";

    String putSpartan = "{\n" +
            "\"gender\":\"" + gender + "\",\n" +
            "\"name\":\"" + name + "\",\n" +
            "\"phone\":" + phone + "\n" + "}";

    String patchSpartan = "{\"name\":" + "\"" + name + "\"}";

    int newSpartanId = 0;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://3.82.233.195:8000";
    }

    @DisplayName("GET all Spartans")
    @Test
    public void test01() {
        given().accept(ContentType.JSON).get(apiSpartans);
    }

    @DisplayName("POST new Spartan")
    @Test
    public void test02() {
        JsonPath jsonPath = given().contentType(ContentType.JSON).body(postNewSpartan).
                post(apiSpartans).then().statusCode(HttpStatus.SC_CREATED).extract().jsonPath();

        assertThat(jsonPath.getString("success"),
                is(equalToIgnoringCase("A Spartan is Born!")));

    }

    @DisplayName("PUT new Spartan")
    @Test
    public void test03() {
        given().contentType(ContentType.JSON).
                pathParam("id", 152).
                body(putSpartan).when().put(apiSpartans + "{id}").prettyPeek();
    }

    @DisplayName("PATCH update Spartan")
    @Test
    public void test04() {
        given().contentType(ContentType.JSON).
                pathParam("id", 4).
                body(patchSpartan).when().patch(apiSpartans + "{id}").prettyPeek();
    }

    @DisplayName("DELETE a Spartan")
    @Test
    public void test05() {
        given().contentType(ContentType.JSON).
                pathParam("id", 153).when()
                .delete(apiSpartans + "{id}");
    }

    @DisplayName("SCHEMA automated Validation")
    @Test
    public void test06() {
        Response response = given().accept(ContentType.JSON).
                pathParam("id", 3).when().get(apiSpartans + "{id}").prettyPeek()
                .then().
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath
                        ("Schemas/SpartansSchema.json")).extract().response();

        System.out.println(response.statusCode());
    }
}

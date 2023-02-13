package Test_01;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

public class Test01_Spartans {

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

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://3.82.233.195:8000";
    }

    @DisplayName("JavaFaker Test")
    @Test
    public void test00() {
        System.out.println(name);
        System.out.println(gender);
        System.out.println(phone);
        System.out.println(postNewSpartan);
        System.out.println(patchSpartan);
    }

    @DisplayName("All Spartans")
    @Test
    public void test01() {
        given().accept(ContentType.JSON).
                when().get(apiSpartans).prettyPeek();
    }

    @DisplayName("GET Spartan by ID ")
    @Test
    public void test02() {
        given().accept(ContentType.JSON).pathParam("id", 7).
                when().get(apiSpartans + "{id}").
                prettyPeek().then().statusCode(HttpStatus.SC_OK);
    }///
//
    @DisplayName("POST new Spartan")
    @Test
    public void test03() {
        Response response = given().
                contentType(ContentType.JSON).
                when().body(postNewSpartan).
                post(apiSpartans).then().extract().response();

        System.out.println(response.statusCode());

        assertThat(response.jsonPath().
                        getString("success"),
                is(equalToIgnoringCase("A Spartan is Born!")));
    }
//sss
    @DisplayName("DELETE posted Spartan")
    @Test
    public void test04() {
        Response response = given().accept(ContentType.JSON).
                pathParam("id", 151).
                when().delete(apiSpartans + "{id}").
                then().extract().response();
        System.out.println(response.statusCode());

    }

    @DisplayName("PUT posted Spartan")
    @Test
    public void test05() {
        Response response = given().contentType(ContentType.JSON).
                pathParam("id", 145).body(putSpartan).
                when().put(apiSpartans + "{id}");
        System.out.println(response.statusCode());
        response.prettyPeek();
    }

    @DisplayName("PATCH posted Spartan")
    @Test
    public void test06() {
        Response response = given().contentType(ContentType.JSON).pathParam("id", 3).
                body(patchSpartan).when().patch(apiSpartans + "{id}");

        System.out.println(response.statusCode());
        response.prettyPrint();
//sssssss
    }
}

import base.baseSteps;
import domain.Token;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CreateToken extends baseSteps {

    /* Create Token API lets user create a new token, that needs to be passed in for updating existing transactions.
        Tests covered as part of this test suite are:
        1. Create Token with valid details
        2. Create Token with invalid login credentials.
     */


    @Test
    public void createTokenHappyPath(){
       //Build request body using jsonpojo
       Token newToken = new Token();

       /*
       Either the login credentials can be provided/overwritten from here or defaulted in the domain class
       newToken.setUsername("admin");
       newToken.setPassword("password123");
        */
        Response response = given().
                                  spec(requestSpecification).
                                   body(newToken).
                             when().
                                   post("/auth").then().log().all().extract().response();

        response.then().spec(responseSpecification);

        JsonPath responseJson=response.jsonPath();
        assertNotEquals(responseJson.get("token"),null);
    }


    public String createToken(){
        Token newToken = new Token();
        Response response = given().
                spec(requestSpecification).
                body(newToken).
                when().
                post("/auth").then().log().all().extract().response();

        response.then().spec(responseSpecification);
        JsonPath responseJson=response.jsonPath();

        assertNotEquals(responseJson.get("token"),null);
        String token = response.then().extract().path("token");
        return token;
    }


    @Test
    public void createTokenWithWrongCredentials(){
        //Build request body using jsonpojo
        Token newToken = new Token();

             newToken.setUsername("admin");
             newToken.setPassword("password321");

        Response response = given().
                spec(requestSpecification).
                body(newToken).
                when().
                post("/auth").then().log().all().extract().response();

        response.then().spec(responseSpecification);
        JsonPath responseJson=response.jsonPath();

        assertEquals("Bad credentials",responseJson.get("reason"));


    }

}

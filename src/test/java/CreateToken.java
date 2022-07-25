import base.baseSteps;
import domain.Token;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CreateToken extends baseSteps {

    //NOT A TEST
    public String createToken(){
        Token newToken = new Token();
        Response response = given().
                spec(requestSpecification).
                body(newToken).
                when().
                post("/auth").then().log().all().extract().response();

        response.then().spec(responseSpecification);
        JsonPath responseJson=response.jsonPath();

        assertNotNull(responseJson.get("token"));
        String token = response.then().extract().path("token");
        return token;
    }


    /* Create Token API lets user create a new token, that needs to be passed in for updating existing transactions.
        Tests covered as part of this test suite are:
        1. Create Token with valid details
        2. Create Token with invalid login credentials.
     */


    @Test
    public void createTokenHappyPath(){
       Token newToken = new Token();

       /*
       Either the login credentials can be provided/overwritten from here or defaulted in the domain class
       newToken.setUsername("admin");
       newToken.setPassword("password123");
        */
        logger.info("Creating a new token and logging all the response details");
        Response response = given().
                                  spec(requestSpecification).
                                   body(newToken).
                             when().
                                   post("/auth").then().log().all().extract().response();

        response.then().spec(responseSpecification);

        JsonPath responseJson=response.jsonPath();
        assertNotNull(responseJson.get("token"));
    }


    @Test
    public void createTokenWithWrongCredentials(){
        logger.info("Creating a new token with incorrect details");
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
        logger.info("Request fails with error");


    }

}

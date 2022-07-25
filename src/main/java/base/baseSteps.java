package base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.event.Level;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class baseSteps {
    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;
    public static final Logger logger = LoggerFactory.getLogger(baseSteps.class);


    @BeforeTest
    public void setRequestPrerequisites() {


    String baseUri= "https://restful-booker.herokuapp.com";
    logger.atLevel(Level.INFO);
    logger.info("Setting base uri for the request as "+baseUri);
    logger.info("Setting ContentType as Json" );

    requestSpecification = new RequestSpecBuilder().
            setBaseUri("https://restful-booker.herokuapp.com").
            setContentType(ContentType.JSON).
            setAccept("application/json").
            build().log().all();
}

    @BeforeMethod
    public void setResponseSpecification() {
        responseSpecification = new ResponseSpecBuilder().
                                    expectStatusCode(200).
                                    expectContentType("application/json").
                                    build();
        logger.info("Asserting the response code is 200");
        logger.info("AssertingContentType as Json");
    }




}

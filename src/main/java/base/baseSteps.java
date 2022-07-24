package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


public class baseSteps {
    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;

@BeforeTest
    public void setRequestPrerequisites() {
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
    }




}

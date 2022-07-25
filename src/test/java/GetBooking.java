import base.baseSteps;
import domain.booking.BookingDates;
import domain.booking.BookingRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class GetBooking extends baseSteps {


    public Response returnGetIdResponse(Integer bookingId) {
        Response response = given().
                spec(requestSpecification).
                pathParam("id", bookingId).
                when().
                get("/booking/{id}").then().log().all().extract().response();

        response.then().spec(responseSpecification);
        return response;
    }

    /* Get Booking API lets user to retrieve an existing booking.
    Tests covered as part of this test suite are:
    1. Create and retrieve a  booking.
    2. Make a request to retrieve non existent booking.
 */

    @Test
    public void getBookingHappyPath() {
    CreateBooking createBooking=new CreateBooking();
    BookingRequest bookingRequest=new BookingRequest();
    BookingDates bookingDates=new BookingDates();
    logger.info("Create a new booking with default values, then extract booking id");
    Integer bookingId= createBooking.createABookingWithDefaultValues();
        Response response = given().
                spec(requestSpecification).
                pathParam("id", bookingId).
                when().
                get("/booking/{id}").then().log().all().extract().response();

        response.then().spec(responseSpecification);

        JsonPath responseJson = response.jsonPath();
        assertNull(responseJson.get("bookingid"));

        Map bookedDates=responseJson.getMap("bookingdates");
        logger.info("Assert all the details retrieved match the original booking");
        assertEquals(bookingRequest.getFirstName(),responseJson.get("firstname"));
        assertEquals(bookingRequest.getLastName(),responseJson.get("lastname"));
        assertEquals(bookingRequest.getDepositPaid(),responseJson.get("depositpaid"));
        assertEquals(bookingRequest.getTotalPrice(),responseJson.get("totalprice"));
        assertEquals(bookingRequest.getAdditionalNeeds(),responseJson.get("additionalneeds"));

        assertEquals(bookingDates.getCheckIn(),bookedDates.get("checkin"));
        assertEquals(bookingDates.getCheckOut(),bookedDates.get("checkout"));


    }

    @Test
    public void getBookingHappyWithInvalidId() {
        logger.info("Make a Get request with non exisiting booking id should return not found error");
               Response response = given().
                spec(requestSpecification).
                pathParam("id", "-31").
                when().
                get("/booking/{id}").then().log().all().extract().response();

        response.then().statusCode(404);

    }
    }

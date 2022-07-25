import base.baseSteps;
import domain.booking.BookingDates;
import domain.booking.BookingRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;
import static org.testng.Assert.assertNotEquals;


public class UpdateBooking  extends baseSteps {

        /* Update Booking API lets user update an existing booking.
        Tests covered as part of this test suite are:
        1. Create a new booking, create a new token and Update this booking
        2. Make a call to update a non existing booking
        3. Make a call to update booking with invalid token
     */

    @Test
    public void updateBookingWithValidDetails(){

        logger.info("Create a new booking and retrieve booking id");
        CreateBooking createBooking=new CreateBooking();
        BookingRequest bookingRequest=new BookingRequest();
        BookingDates bookingDates=new BookingDates();
        Integer bookingId= createBooking.createABookingWithDefaultValues();
        logger.info("Create a new auth token and retrieve token value");
        CreateToken newToken=new CreateToken();
        String token = newToken.createToken();

        logger.info("Set the updated values for firstName, lastName and Checkout date");
        LocalDate now = LocalDate.now();
        String firstName="Angelina";
        String lastName="Jolie";
        String checkOut = now.plusDays(10).toString();

        bookingRequest.setFirstName(firstName);
        bookingRequest.setLastName(lastName);
        bookingDates.setCheckout(checkOut);
        bookingRequest.setBookingDates(bookingDates);
               Response response = given().
                spec(requestSpecification).
                header("Cookie", "token="+token).
                pathParam("id", bookingId).
                body(bookingRequest).log().body().
                when().
                put("/booking/{id}").then().log().all().extract().response();

        response.then().spec(responseSpecification);
        JsonPath responseJson=response.jsonPath();

        assertNotEquals(responseJson.getMap("bookingdates").size(),null);

        logger.info("Mapping the response json to a map object and validating response");
        Map bookedDates=responseJson.getMap("bookingdates");

        logger.info("Validating the response object is updated");
        assertEquals(firstName,responseJson.get("firstname"));
        assertEquals(lastName,responseJson.get("lastname"));
        assertEquals(checkOut,bookedDates.get("checkout"));

        logger.info("Make a GET API call with the booking id and validate the response has updated details");
        GetBooking getBooking =new GetBooking();
        Response getResponse =getBooking.returnGetIdResponse(bookingId);
        JsonPath getResponseJson=getResponse.jsonPath();
        assertEquals(firstName,getResponseJson.get("firstname"));
        assertEquals(lastName,getResponseJson.get("lastname"));

    }

    @Test
    public void updateBookingWithInvalidToken(){
        CreateBooking createBooking=new CreateBooking();
        BookingRequest bookingRequest=new BookingRequest();
        Integer bookingId= createBooking.createABookingWithDefaultValues();

        String firstName="Angelina";
        bookingRequest.setFirstName(firstName);

        logger.info("Making a request with invalid token");

        Response response = given().
                spec(requestSpecification).
                header("Cookie", "token="+1234).
                pathParam("id", bookingId).
                body(bookingRequest).log().body().
                when().
                put("/booking/{id}").then().log().all().extract().response();;

        logger.info("Request failed with 403 error code");
                assertEquals(403,response.getStatusCode());
    }


    @Test
    public void updateBookingWithInvalidBookingId(){
        CreateBooking createBooking=new CreateBooking();
        BookingRequest bookingRequest=new BookingRequest();

        CreateToken newToken=new CreateToken();
        String token = newToken.createToken();

        logger.info("Making a request with invalid bookingId");
        Response response = given().
                spec(requestSpecification).
                header("Cookie", "token="+token).
                pathParam("id", 123909).
                body(bookingRequest).log().body().
                when().
                put("/booking/{id}").then().log().all().extract().response();;

        assertEquals(405,response.getStatusCode());
        logger.info("Request failed with 403 error code");
    }
}

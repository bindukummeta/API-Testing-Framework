import base.baseSteps;
import domain.booking.BookingDates;
import domain.booking.BookingRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CreateBooking  extends baseSteps {

    /* Create Booking API lets user create a new booking.
        Tests covered as part of this test suite are:
        1. Create booking with default values set in domain class
        2. Create booking with values provided from the test.
        3. Create a booking with non-mandatory details (additional needs)
        4. Make a request to create booking api without mandatory values- firstName
        5. Make a request to create booking api without mandatory values- checkout Date
     */

	@Test
	public Integer createABookingWithDefaultValues() {
    BookingRequest bookingRequest = new BookingRequest();
		Response response = given().
				spec(requestSpecification).
				body(bookingRequest).
				when().
				post("/booking").then().log().all().extract().response();

		response.then().spec(responseSpecification);

		JsonPath responseJson=response.jsonPath();
		assertNotEquals(responseJson.get("bookingid"),null);
        assertNotEquals(responseJson.getMap("booking").size(),null);
		assertNotEquals(responseJson.getMap("booking.bookingdates").size(),null);
		Integer bookingId = response.then().extract().path("bookingid");
		return bookingId;
	}

	@Test
	public void createABookingWithCustomValues() {

		String firstName="Clara";
		String lastName="Johnson";
        Integer totalPaid =0;
		boolean depositPaid=false;
		String checkIn ="2022-08-01";
		String checkOut ="2022-08-15";
		String additionalNeeds="Extra Bedding";

		BookingRequest bookingRequest = new BookingRequest();
		BookingDates bookingDates =new BookingDates();

		bookingRequest.setFirstName(firstName);
		bookingRequest.setLastName(lastName);
		bookingRequest.setTotalPrice(totalPaid);
		bookingRequest.setDepositPaid(depositPaid);
		bookingRequest.setAdditionalNeeds(additionalNeeds);
		bookingDates.setCheckIn(checkIn);
		bookingDates.setCheckout(checkOut);
		bookingRequest.setBookingDates(bookingDates);

		Response response = given().
				spec(requestSpecification).
				body(bookingRequest).
				when().
				post("/booking").then().log().all().extract().response();

		response.then().spec(responseSpecification);

		JsonPath responseJson=response.jsonPath();
		assertNotEquals(responseJson.get("bookingid"),null);
		assertNotEquals(responseJson.getMap("booking").size(),null);
		assertNotEquals(responseJson.getMap("booking.bookingdates").size(),null);
        assertEquals(responseJson.getMap("$").size(),2);

		Map bookedDetails=responseJson.getMap("booking");
		Map bookedDates=responseJson.getMap("booking.bookingdates");

		assertEquals(firstName,bookedDetails.get("firstname"));
		assertEquals(lastName,bookedDetails.get("lastname"));
		assertEquals(totalPaid,bookedDetails.get("totalprice"));
		assertEquals(depositPaid,bookedDetails.get("depositpaid"));
		assertEquals(additionalNeeds,bookedDetails.get("additionalneeds"));

		assertEquals(checkIn,bookedDates.get("checkin"));
		assertEquals(checkOut,bookedDates.get("checkout"));

	}

	@Test
	public void createABookingWithOutMandatoryDetails() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setFirstName(null);
		Response response = given().
				spec(requestSpecification).
				body(bookingRequest).
				when().
				post("/booking").then().log().all().extract().response();

		assertEquals(500,response.getStatusCode());
	}

	@Test
	public void createABookingWithOutBookingDates() {
		BookingRequest bookingRequest = new BookingRequest();
		BookingDates bookingDates = new BookingDates();
		bookingDates.setCheckout(null);
		bookingRequest.setBookingDates(bookingDates);
		Response response = given().
				spec(requestSpecification).
				body(bookingRequest).
				when().
				post("/booking").then().log().all().extract().response();

		assertEquals(500,response.getStatusCode());
	}

	@Test
	public void createABookingWithInsufficientDetails() {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setAdditionalNeeds(null);
		Response response = given().
				spec(requestSpecification).
				body(bookingRequest).
				when().
				post("/booking").then().log().all().extract().response();

		response.then().spec(responseSpecification);

		JsonPath responseJson=response.jsonPath();
		assertNotEquals(responseJson.get("bookingid"),null);
	}

}

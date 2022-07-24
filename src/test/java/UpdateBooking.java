import base.baseSteps;
import domain.booking.BookingDates;
import domain.booking.BookingRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.time.LocalDate;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class UpdateBooking  extends baseSteps {

        /* Update Booking API lets user update an existing booking.
        Tests covered as part of this test suite are:
        1. Create a new booking, create a new token and Update this booking
        2. Make a call to update a non existing booking
        3. Make a call to update booking with invalid token
     */

    @Test
    public void updateBookingWithValidDetails(){
        CreateBooking createBooking=new CreateBooking();
        BookingRequest bookingRequest=new BookingRequest();
        BookingDates bookingDates=new BookingDates();
        Integer bookingId= createBooking.createABookingWithDefaultValues();
        CreateToken newToken=new CreateToken();
        String token = newToken.createToken();

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
                put("/booking/{id}").then().log().all().extract().response();;

    }

    @Test
    public void updateBookingWithInvalidToken(){
        CreateBooking createBooking=new CreateBooking();
        BookingRequest bookingRequest=new BookingRequest();
        Integer bookingId= createBooking.createABookingWithDefaultValues();

        String firstName="Angelina";
        bookingRequest.setFirstName(firstName);

        Response response = given().
                spec(requestSpecification).
                header("Cookie", "token="+1234).
                pathParam("id", bookingId).
                body(bookingRequest).log().body().
                when().
                put("/booking/{id}").then().log().all().extract().response();;

                assertEquals(403,response.getStatusCode());
    }


    @Test
    public void updateBookingWithInvalidBookingId(){
        CreateBooking createBooking=new CreateBooking();
        BookingRequest bookingRequest=new BookingRequest();

        CreateToken newToken=new CreateToken();
        String token = newToken.createToken();

        Response response = given().
                spec(requestSpecification).
                header("Cookie", "token="+token).
                pathParam("id", 123909).
                body(bookingRequest).log().body().
                when().
                put("/booking/{id}").then().log().all().extract().response();;

        assertEquals(405,response.getStatusCode());

    }
}

package domain.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.time.LocalDate;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "checkin",
        "checkout"
})
@Generated("jsonschema2pojo")


public class BookingDates{

    private final LocalDate now = LocalDate.now();
    @JsonProperty("checkin")
    private String checkin = now.toString();
    @JsonProperty("checkout")
    private String checkout = now.plusDays(2).toString();

    @JsonProperty("checkin")
    public String getCheckIn() {
        return checkin;
    }

    @JsonProperty("checkin")
    public void setCheckIn(String checkin) {
        this.checkin = checkin;
    }

    @JsonProperty("checkout")
    public String getCheckOut() {
        return checkout;
    }

    @JsonProperty("checkout")
    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

}
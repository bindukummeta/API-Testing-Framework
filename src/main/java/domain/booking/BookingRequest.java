package domain.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "firstname",
        "lastname",
        "totalprice",
        "depositpaid",
        "bookingdates",
        "additionalneeds"
})
@Generated("jsonschema2pojo")

public class BookingRequest {

    @JsonProperty("firstname")
    private String firstname = "Customer";
    @JsonProperty("lastname")
    private String lastname="Name";
    @JsonProperty("totalprice")
    private Integer totalprice=100;
    @JsonProperty("depositpaid")
    private Boolean depositpaid=true;
    @JsonProperty("bookingdates")
    private BookingDates bookingdates = new BookingDates();
    @JsonProperty("additionalneeds")
    private String additionalneeds="Breakfast";

    @JsonProperty("firstname")
    public String getFirstName() {
        return firstname;
    }

    @JsonProperty("firstname")
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("lastname")
    public String getLastName() {
        return lastname;
    }

    @JsonProperty("lastname")
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty("totalprice")
    public Integer getTotalPrice() {
        return totalprice;
    }

    @JsonProperty("totalprice")
    public void setTotalPrice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    @JsonProperty("depositpaid")
    public Boolean getDepositPaid() {
        return depositpaid;
    }

    @JsonProperty("depositpaid")
    public void setDepositPaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    @JsonProperty("bookingdates")
    public BookingDates getBookingDates() {
        return bookingdates;
    }

    @JsonProperty("bookingdates")
    public void setBookingDates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    @JsonProperty("additionalneeds")
    public String getAdditionalNeeds() {
        return additionalneeds;
    }

    @JsonProperty("additionalneeds")
    public void setAdditionalNeeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }
}
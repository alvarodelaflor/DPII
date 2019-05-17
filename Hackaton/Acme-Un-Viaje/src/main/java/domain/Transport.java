package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Transport
 */
@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Transport extends DomainEntity {

    public int numberOfPlaces;
    public double price;
    public Date date;
    public String vehicleType;
    public String place;

    public Transport() {
    }

    public Transport(int numberOfPlaces, double price, Date date, String vehicleType) {
        this.numberOfPlaces = numberOfPlaces;
        this.price = price;
        this.date = date;
        this.vehicleType = vehicleType;
    }

    @Min(1)
    public int getNumberOfPlaces() {
        return this.numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    @Min(0)
    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Future
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotEmpty
    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @NotEmpty
    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
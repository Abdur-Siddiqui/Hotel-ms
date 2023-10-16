package com.hotelmanagement.staffservice.datalayer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Address {


    private String employeeCity;

    private String employeePostalCode;


    public Address() {
    }


    public Address( String employeeCity, String employeePostalCode) {

        this.employeeCity = employeeCity;

        this.employeePostalCode = employeePostalCode;
    }



    public @NotNull String getCity() {
        return employeeCity;
    }



    public @NotNull String getPostalCode() {
        return employeePostalCode;
    }
}




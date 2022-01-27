package com.example.machinetest;

public class UserList {
    String userProfieleUrl,firstName,lastName,title,userEmail,nat,number,streetName,city,state,country,postCode,offset,description,gender;
    Boolean highLight;

    public UserList(String userProfieleUrl, String firstName, String lastName, String title, String userEmail, String nat, String number, String streetName, String city, String state, String country, String postCode, String offset, String description,String gender,Boolean highLight) {
        this.userProfieleUrl = userProfieleUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.userEmail = userEmail;
        this.nat = nat;
        this.number = number;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postCode = postCode;
        this.offset = offset;
        this.description = description;
        this.gender=gender;
        this.highLight=highLight;
    }

    public Boolean getHighLight() {
        return highLight;
    }

    public void setHighLight(Boolean highLight) {
        this.highLight = highLight;
    }

    public String getGender() {
        return gender;
    }

    public String getOffset() {
        return offset;
    }

    public String getDescription() {
        return description;
    }

    public String getUserProfieleUrl() {
        return userProfieleUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getNat() {
        return nat;
    }

    public String getNumber() {
        return number;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostCode() {
        return postCode;
    }
}

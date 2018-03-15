package hr.fer.android.hw1191226486.homework.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tina on 6/27/17.
 */

public class Repo implements Serializable {
    @SerializedName("avatar_location")
    private String avatarlocation;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone_no")
    private String phone;

    @SerializedName("email_sknf")
    private String email;

    @SerializedName("spouse")
    private String spouse;

    @SerializedName("age")
    private int age;

    public String getAvatarlocation() {
        return avatarlocation;
    }

    public void setAvatarlocation(String avatarlocation) {
        this.avatarlocation = avatarlocation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

package apitests.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class UserTestData {
    private int id;
    @JsonProperty("username")
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    // Конструктор по умолчанию
    public UserTestData() {}

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public static UserTestData generate() {
        UserTestData data = new UserTestData();
        data.setId(ThreadLocalRandom.current().nextInt(1, 10000));
        data.setUserName("user_" + UUID.randomUUID().toString().substring(0, 8));
        data.setFirstName("first_" + UUID.randomUUID().toString().substring(0, 5));
        data.setLastName("last_" + UUID.randomUUID().toString().substring(0, 5));
        data.setEmail(data.getUserName() + "@example.com");
        data.setPassword("pass_" + UUID.randomUUID().toString().substring(0, 5));
        data.setPhone(String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999)));
        data.setUserStatus(1);
        return data;
    }
}

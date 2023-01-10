package com.inch.model;

public class User extends BaseModel{

    private String name;

    private String sex;

    private int age;

    private String phoneNo;

    private String address;

    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public User(String name, String sex, int age, String phoneNo, String address, String hobby) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phoneNo = phoneNo;
        this.address = address;
        this.hobby = hobby;
    }
}

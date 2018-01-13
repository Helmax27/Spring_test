package com.springtest.jsondb;

import java.util.Objects;

public class Users {
    private int id;
    private String name;
    private String address;
    private String phone;

    public int getId() {
        return id;
    }

    public Users withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Users withName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Users withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Users withPhone(String phone) {
        this.phone = phone;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id &&
                Objects.equals(name, users.name) &&
                Objects.equals(address, users.address) &&
                Objects.equals(phone, users.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, phone);
    }
}

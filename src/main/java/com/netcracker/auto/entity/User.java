package com.netcracker.auto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String first_name;
    private String last_name;
    private Date date_of_birth;
    private String phone_number;
    private String email;
    private boolean confirmed;
    private double balance;
    private int seller_rating;
    private int buyers_rating;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
    //private List<String> role;

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public double getBalance() {
        return balance;
    }

    public int getBuyers_rating() {
        return buyers_rating;
    }

    public Integer getUserId() {
        return userId;
    }

    public int getSeller_rating() {
        return seller_rating;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBuyers_rating(int buyers_rating) {
        this.buyers_rating = buyers_rating;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setSeller_rating(int seller_rating) {
        this.seller_rating = seller_rating;
    }
}

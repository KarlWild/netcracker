package com.netcracker.auto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "date_of_birth")
    private LocalDate date_of_birth;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "email")
    private String email;
    @Column(name = "confirmed")
    private boolean confirmed;
    @Column(name = "balance")
    private double balance;
    @Column(name = "seller_rating")
    private int seller_rating;
    @Column(name = "buyers_rating")
    private int buyers_rating;
    @Column(name = "password")
    private String password;
    @Column(name = "images", length = 64)
    private String images;

    @ElementCollection(targetClass = RolesEntity.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<RolesEntity> roles;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void setDefault(){
        confirmed = false;
        date_of_birth =  LocalDate.now();
        balance = 0;
        buyers_rating = 0;
        seller_rating = 0;
        phone_number = "";
    }

    @Transient
    public String getImagesPath() {
        if (images == null || userId == null) return null;
        return "/user-photos/" + userId + "/" + images;
    }
    public String getFullName(){
        return last_name+" "+first_name;
    }

    public void addRole(RolesEntity rolesEntity){
        this.roles.add(rolesEntity);
    }

    public Set<RolesEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolesEntity> roles) {
        this.roles = roles;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
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

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public double getBalance() {
        return balance;
    }

    public int getBuyers_rating() {
        return buyers_rating;
    }

    public Long getUserId() {
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

    public void setDate_of_birth(LocalDate date_of_birth) {
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSeller_rating(int seller_rating) {
        this.seller_rating = seller_rating;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}

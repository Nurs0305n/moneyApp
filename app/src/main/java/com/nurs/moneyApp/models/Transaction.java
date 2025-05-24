package com.nurs.moneyApp.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject {
    @PrimaryKey
    private Long id;
    private String type;
    private Date date;
    private double amount;
    private String category;
    private String account;
    private String note;

    public Transaction(){

    }

    public Transaction(Long id, String type, Date date, double amount, String category, String account, String note) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.account = account;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

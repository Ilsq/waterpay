package org.coworking.ilsq.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int amount;
    private String payer;
    private Date date;
    private String method;
    private int orderaId;

    public Payment() {
    }

    public Payment(int id, int amount, String payer, Date date, String method, int ordera_id) {
        this.id = id;
        this.amount = amount;
        this.payer = payer;
        this.date = date;
        this.method = method;
        this.orderaId = ordera_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getOrdera_id() {
        return orderaId;
    }

    public void setOrder_id(int order_id) {
        this.orderaId = order_id;
    }
}
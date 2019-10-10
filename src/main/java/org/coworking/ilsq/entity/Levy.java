package org.coworking.ilsq.entity;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "levy")
public class Levy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int summ;
    private int prop;
    private String methods;
    private Date date;

    public Levy() {
    }

    public Levy(int id, int summ, int prop) {
        this.id = id;
        this.summ = summ;
        this.prop = prop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSumm() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ = summ;
    }

    public int getProp() {
        return prop;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    @Override
//    public String toString() {
//        return "Levy{" + "id=" + id + ", prop=" + prop + ", summ=" + summ + ", methods='" + methods + '\'' +
//                ", date=" + date + '}';
//    }
}
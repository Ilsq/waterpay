package org.coworking.ilsq.entity;

import javax.persistence.*;

@Entity
@Table(name = "levy")
public class Levy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int summ;

    private int prop;

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
}
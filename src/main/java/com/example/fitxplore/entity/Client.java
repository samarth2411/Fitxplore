package com.example.fitxplore.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_DETAILS")
public class Client extends Subscriber {

    @ManyToOne
    Trainer trainer;

    String image;

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Client{" +
                "trainer=" + trainer +
                ", image='" + image + '\'' +
                '}';
    }
}
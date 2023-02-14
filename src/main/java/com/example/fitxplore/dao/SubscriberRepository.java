package com.example.fitxplore.dao;

import com.example.fitxplore.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
    @Query("SELECT subscriber from Subscriber subscriber where subscriber.userName = :userName")
    public Subscriber getSubscriberByUserName(@Param("userName") String userName);


}
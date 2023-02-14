package com.example.fitxplore.dao;

import com.example.fitxplore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("SELECT client from Client client where client.userName = :userName")
    public Client getClientByUserName(@Param("userName") String userName);
}
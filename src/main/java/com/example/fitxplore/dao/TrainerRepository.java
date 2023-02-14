package com.example.fitxplore.dao;

import com.example.fitxplore.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
    @Query("SELECT trainer from Trainer trainer where trainer.userName = :userName")
    public Trainer getTrainerByUserName(@Param("userName") String userName);

    @Query("SELECT trainer from Trainer trainer where trainer.role= :role")
    public List<Trainer> getTrainersByRole(@Param("role") String role);
}
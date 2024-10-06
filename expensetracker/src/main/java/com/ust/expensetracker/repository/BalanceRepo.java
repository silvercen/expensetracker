package com.ust.expensetracker.repository;
import com.ust.expensetracker.model.Balances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface BalanceRepo extends JpaRepository<Balances, UUID>{
    Optional<Balances> findTopByOrderByDateDesc();
}

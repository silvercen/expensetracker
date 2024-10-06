package com.ust.expensetracker.repository;

import com.ust.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, UUID>{
    public List<Expense> findByDate(LocalDate date);
}

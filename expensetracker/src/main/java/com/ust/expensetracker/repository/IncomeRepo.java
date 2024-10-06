package com.ust.expensetracker.repository;

import com.ust.expensetracker.model.Income;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepo extends JpaRepository<Income,UUID> {
    public List<Income> findByDate(LocalDate date);

    @Query("select coalesce(sum(Income.amount),0) from Income where function('MONTH', date) = :month")
    public double findTotalIncomebyMonth(@Param("month") int month);
}

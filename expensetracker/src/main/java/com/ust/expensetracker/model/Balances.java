package com.ust.expensetracker.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balances {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public Balances(LocalDate date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    @CreatedDate
    @Column(updatable = false)
    private LocalDate date;

    private double amount;
}

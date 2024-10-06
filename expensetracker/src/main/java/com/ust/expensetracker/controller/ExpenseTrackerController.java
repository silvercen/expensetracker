package com.ust.expensetracker.controller;

import com.ust.expensetracker.model.Balances;
import com.ust.expensetracker.model.Expense;
import com.ust.expensetracker.model.Income;
import com.ust.expensetracker.service.ExpenseTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/tracker")
public class ExpenseTrackerController {

    @Autowired
    private ExpenseTrackerService expenseTrackerService;

    @PostMapping("/add-income")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        return ResponseEntity.ok().body(expenseTrackerService.addIncome(income));
    }

    @PostMapping("/add-expense")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok().body(expenseTrackerService.addExpense(expense));
    }

    @GetMapping("/cost-by-date/{date}")
    public ResponseEntity<Double> getCostByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok().body(expenseTrackerService.getCostByDate(date));
    }

    @GetMapping("/cost-per-method/{date}")
    public ResponseEntity<Map<String, Double>> getCostPerMethod(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok().body(expenseTrackerService.getAmountByPaymentMethod(date));
    }

    @GetMapping("/income-per-month/{month}")
    public ResponseEntity<Double> getIncomePerMonth(@PathVariable("month") int month) {
        return ResponseEntity.ok().body(expenseTrackerService.getAllIncomeByMonth(month));
    }

    @GetMapping("/latest-balance")
    public ResponseEntity<Balances> getLatestBalance() {
        return ResponseEntity.ok().body(expenseTrackerService.getLatestBalance());
    }
}

package com.ust.expensetracker.controller;

import com.ust.expensetracker.dto.AuthRequest;
import com.ust.expensetracker.model.Balances;
import com.ust.expensetracker.model.Expense;
import com.ust.expensetracker.model.Income;
import com.ust.expensetracker.model.Userinfo;
import com.ust.expensetracker.service.ExpenseTrackerService;
import com.ust.expensetracker.service.JwtService;
import com.ust.expensetracker.service.Userservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping
public class ExpenseTrackerController {

    @Autowired
    private ExpenseTrackerService expenseTrackerService;

    @Autowired
    private Userservices userservices;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/add-income")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        return ResponseEntity.ok().body(expenseTrackerService.addIncome(income));
    }

    @PostMapping("/adduser")
    public String addUser(@RequestBody Userinfo userinfo)
    {
        return userservices.addUser(userinfo);
    }

    @PostMapping("/add-expense")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok().body(expenseTrackerService.addExpense(expense));
    }

    @GetMapping("/cost-by-date/{date}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Double> getCostByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok().body(expenseTrackerService.getCostByDate(date));
    }

    @GetMapping("/cost-per-method/{date}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Map<String, Double>> getCostPerMethod(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok().body(expenseTrackerService.getAmountByPaymentMethod(date));
    }

    @GetMapping("/income-per-month/{month}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Double> getIncomePerMonth(@PathVariable("month") int month) {
        return ResponseEntity.ok().body(expenseTrackerService.getAllIncomeByMonth(month));
    }

    @GetMapping("/latest-balance")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Balances> getLatestBalance() {
        return ResponseEntity.ok().body(expenseTrackerService.getLatestBalance());
    }

    @PostMapping("/authenticate")
    public String authenticateAndTokenGeneration(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}

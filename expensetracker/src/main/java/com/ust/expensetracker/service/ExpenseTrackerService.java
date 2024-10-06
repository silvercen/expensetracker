package com.ust.expensetracker.service;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.ust.expensetracker.model.Balances;
import com.ust.expensetracker.model.Expense;
import com.ust.expensetracker.model.Income;
import com.ust.expensetracker.repository.BalanceRepo;
import com.ust.expensetracker.repository.ExpenseRepo;
import com.ust.expensetracker.repository.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseTrackerService {

    @Autowired
    private BalanceRepo balanceRepo;
    @Autowired
    private IncomeRepo incomeRepo;
    @Autowired
    private ExpenseRepo expenseRepo;

    private final JPAStreamer jpaStreamer;

    public ExpenseTrackerService(JPAStreamer jpaStreamer) {
        this.jpaStreamer = jpaStreamer;
    }

    public double getAllIncomeByMonth(int month)
    {
        return incomeRepo.findTotalIncomebyMonth(month);
    }

    public double getCostByDate(LocalDate date)
    {
        double sum=0;
        for(Expense expense:expenseRepo.findByDate(date))
        {
            sum = sum + expense.getAmount();
        }

        return sum;
    }

    public Map<String,Double> getAmountByPaymentMethod(LocalDate date)
    {
        return jpaStreamer.stream(Expense.class).filter(expense -> expense.getDate() == date)
                .collect(Collectors.groupingBy(Expense::getPaymentmethod, Collectors.summingDouble(Expense::getAmount)));
    }
    public Income addIncome(Income income) {
        Income savedIncome = incomeRepo.save(income);
        updateBalance(savedIncome.getAmount(), true);
        return savedIncome;
    }

    public Expense addExpense(Expense expense) {
        Expense savedExpense = expenseRepo.save(expense);
        updateBalance(savedExpense.getAmount(), false);
        return savedExpense;
    }

    private void updateBalance(double amount, boolean isIncome) {
        Balances currentBalance = balanceRepo.findTopByOrderByDateDesc()
                .orElse(new Balances(LocalDate.now(),0));

        double newAmount = isIncome ?
                currentBalance.getAmount() + amount :
                currentBalance.getAmount() - amount;

        currentBalance.setAmount(newAmount);
        currentBalance.setDate(LocalDate.now());
        balanceRepo.save(currentBalance);
    }

    public Balances getLatestBalance() {
        return balanceRepo.findTopByOrderByDateDesc()
                .orElse(new Balances(LocalDate.now(),0));
    }

}
package com.ust.expensetracker.repository;

import com.ust.expensetracker.model.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserinfoRepo extends JpaRepository<Userinfo, Integer> {
    Optional<Userinfo> findByName(String username);
}

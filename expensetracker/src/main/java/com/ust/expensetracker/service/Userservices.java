package com.ust.expensetracker.service;

import com.ust.expensetracker.model.Userinfo;
import com.ust.expensetracker.repository.UserinfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Userservices {

    @Autowired
    private UserinfoRepo userinfoRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(Userinfo userinfo)
    {
        userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
        userinfoRepo.save(userinfo);
        return "user added to the system.";
    }
}

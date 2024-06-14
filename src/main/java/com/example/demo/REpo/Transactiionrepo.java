package com.example.demo.REpo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Transaction;

@Repository
public interface Transactiionrepo extends JpaRepository<Transaction, Long>{

}

package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Transaction;
import com.example.demo.REpo.Transactiionrepo;

@Service 
public class tran {
	@Autowired
	Transactiionrepo transactiionrepo;
	
	public void saveTRansaction(Transaction transaction) {
		transactiionrepo.save(transaction);
		
	}

	public List<Transaction> findAll() {
		// TODO Auto-generated method stub
		return transactiionrepo.findAll();
	}

}

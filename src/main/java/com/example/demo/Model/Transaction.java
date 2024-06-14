package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "u_id")
	private long u_id;

	@Column(name = "issue_Date")
	private String issue_date;
	@Column(name = "return_date")
	private String return_date;

	@Column(name = "b_id")
	private long b_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getU_id() {
		return u_id;
	}

	public void setU_id(long u_id) {
		this.u_id = u_id;
	}

	public long getB_id() {
		return b_id;
	}

	public void setB_id(long b_id) {
		this.b_id = b_id;
	}

	public String getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

}

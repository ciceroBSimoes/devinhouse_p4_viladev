package com.devinhouse.village.model;

import java.math.BigDecimal;
import java.util.Date;

public class Resident {
	
	private Integer id;
	private String name;
	private String surname;
	private Date birthDate;
	private BigDecimal income;
	private String cpf;
	
	public Resident() {
	
	}
	
	public Resident(Integer id, String name, String surname, Date birthDate, BigDecimal income, String cpf) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.income = income;
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Resident [id=" + id + ", name=" + name + ", surname=" + surname + ", birthDate=" + birthDate
				+ ", income=" + income + ", cpf=" + cpf + "]";
	}
	
}

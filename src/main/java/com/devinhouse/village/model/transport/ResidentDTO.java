package com.devinhouse.village.model.transport;

import java.math.BigDecimal;
import java.util.Date;

import com.devinhouse.village.model.Resident;

public class ResidentDTO {
	private String name;
	private String surname;
	private Date birthDate;
	private BigDecimal income;
	private String cpf;

	public ResidentDTO() {

	}

	public ResidentDTO(Resident resident) {
		this.name = resident.getName();
		this.surname = resident.getSurname();
		this.birthDate = resident.getBirthDate();
		this.income = resident.getIncome();
		this.cpf = resident.getCpf();
	}

	public ResidentDTO(String name, String surname, Date birthDate, BigDecimal income, String cpf) {
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

}

package com.devinhouse.village.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Optional;

import org.apache.commons.validator.GenericValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devinhouse.village.config.ConnectionJDBC;
import com.devinhouse.village.dao.ResidentDAO;
import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.transport.ResidentDTO;

@Service
public class ResidentService {

	public HashMap<String, String> listAll() throws SQLException {
		HashMap<String, String> residents = new HashMap<>();
		try (Connection conn = new ConnectionJDBC().getConnection()) {
			try {
				residents = new ResidentDAO(conn).listAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return residents;
	}

	public HashMap<String, String> listByName(String name) throws SQLException {
		HashMap<String, String> residents = new HashMap<>();
		try (Connection conn = new ConnectionJDBC().getConnection()) {
			try {
				residents = new ResidentDAO(conn).listByName(name);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return residents;
	}

	public HashMap<String, String> listByMonth(String month) throws SQLException {

		int monthNum = 0;

		while (month.charAt(0) == '0') {
			StringBuffer strBfr = new StringBuffer(month);
			month = strBfr.replace(0, 1, "").toString();
		}

		switch (month.toLowerCase()) {
		case "janeiro":
		case "1":
			monthNum = 1;
			break;
		case "fevereiro":
		case "2":
			monthNum = 2;
			break;
		case "março":
		case "marco":
		case "3":
			monthNum = 3;
			break;
		case "abril":
		case "4":
			monthNum = 4;
			break;
		case "maio":
		case "5":
			monthNum = 5;
			break;
		case "junho":
		case "6":
			monthNum = 6;
			break;
		case "julho":
		case "7":
			monthNum = 7;
			break;
		case "agosto":
		case "8":
			monthNum = 8;
			break;
		case "setembro":
		case "9":
			monthNum = 9;
			break;
		case "outubro":
		case "10":
			monthNum = 10;
			break;
		case "novembro":
		case "11":
			monthNum = 11;
			break;
		case "dezembro":
		case "12":
			monthNum = 12;
			break;
		}

		HashMap<String, String> residents = new HashMap<>();
		try (Connection conn = new ConnectionJDBC().getConnection()) {
			try {
				residents = new ResidentDAO(conn).listByMonth(monthNum);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return residents;
	}

	public HashMap<String, String> listByAge(Integer age) throws SQLException {
		HashMap<String, String> residents = new HashMap<>();
		if (age <= 0 || age == null) {
			throw new IllegalArgumentException("A idade inválida!");
		}
		try (Connection conn = new ConnectionJDBC().getConnection()) {
			try {
				residents = new ResidentDAO(conn).listByAge(age);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return residents;
	}

	public ResidentDTO findById(Integer id) throws SQLException {

		if (id == null) {
			throw new IllegalArgumentException("O Id não pode ser nulo ou inválido!");
		}
		Optional<Resident> resident = null;
		try (Connection conn = new ConnectionJDBC().getConnection()) {
			try {
				resident = new ResidentDAO(conn).findById(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (resident.isPresent()) {
				return new ResidentDTO(resident.get());
			} else {
				return null;
			}
		}

	}

	public ResponseEntity<HttpStatus> delete(Integer id) throws SQLException {
		if (id == null) {
			throw new IllegalArgumentException("Id inválido!");
		}

		int affectedRows = 0;
		try (Connection conn = new ConnectionJDBC().getConnection()) {
			try {
				conn.setAutoCommit(false);
				affectedRows = new ResidentDAO(conn).delete(id);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
			}
		}
		if (affectedRows > 0) {
			return ResponseEntity.accepted().build();
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	public ResponseEntity<HttpStatus> create(ResidentDTO resident) throws SQLException {
		int generatedId = 0;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (!resident.getName().matches("[a-zA-Zá-úÁ-Ú]+") || !resident.getSurname().matches("[a-zA-Zá-úÁ-Ú]+")) {
			throw new IllegalArgumentException("Nome e Sobrenome só podem conter letras!");

		} else if (!GenericValidator.isDate(simpleDateFormat.format(resident.getBirthDate()), "yyyy-MM-dd", true)) {

			throw new IllegalArgumentException("Formato de data inválido, tente: yyyy-MM-dd");

		} else if (!GenericValidator.isDouble(resident.getIncome().toString())) {
			throw new IllegalArgumentException("Formato da");

		} else if (!resident.getCpf().matches("(^([0-9]{3}\\.?){3}-?[0-9]{2}$)")) {
			throw new IllegalArgumentException("Formato de CPF inválaido!");
		} else {
			try (Connection conn = new ConnectionJDBC().getConnection()) {
				try {
					conn.setAutoCommit(false);
					generatedId = new ResidentDAO(conn).create(resident);
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					e.printStackTrace();
				}
			}
		}

		if (generatedId != 0) {
			return ResponseEntity.created(null).build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

}

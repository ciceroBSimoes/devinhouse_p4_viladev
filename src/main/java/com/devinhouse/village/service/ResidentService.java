package com.devinhouse.village.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

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
				conn.rollback();
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
				conn.rollback();
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
				conn.rollback();
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
				conn.rollback();
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
				conn.rollback();
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
				affectedRows = new ResidentDAO(conn).delete(id);

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

}

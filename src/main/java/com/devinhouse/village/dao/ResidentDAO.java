package com.devinhouse.village.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devinhouse.village.model.Resident;
import com.devinhouse.village.model.transport.ResidentDTO;

@Repository
public class ResidentDAO {

	Connection conn;

	public ResidentDAO(Connection conn) {
		this.conn = conn;
	}

	public HashMap<String, String> listAll() throws SQLException {

		HashMap<String, String> residents = new HashMap<>();

		try (PreparedStatement pStmt = conn.prepareStatement("SELECT * FROM residents")) {
			pStmt.execute();
			ResultSet results = pStmt.getResultSet();
			while (results.next()) {
				residents.put(results.getString("id"), results.getString("name") + " " + results.getString("surname"));
			}
		}
		return residents;
	}

	public HashMap<String, String> listByName(String name) throws SQLException {

		HashMap<String, String> residents = new HashMap<>();

		try (PreparedStatement pStmt = conn
				.prepareStatement("SELECT * FROM residents WHERE name ILIKE ? OR surname ILIKE ?")) {
			pStmt.setString(1, name);
			pStmt.setString(2, name);
			pStmt.execute();
			ResultSet results = pStmt.getResultSet();
			while (results.next()) {
				residents.put(results.getString("id"), results.getString("name") + " " + results.getString("surname"));
			}
		}
		return residents;
	}

	public HashMap<String, String> listByMonth(int monthNumber) throws SQLException {
		HashMap<String, String> residents = new HashMap<>();

		try (PreparedStatement pStmt = conn
				.prepareStatement("SELECT * FROM residents WHERE EXTRACT(MONTH FROM birth_date) = ?")) {
			pStmt.setInt(1, monthNumber);
			pStmt.execute();
			ResultSet results = pStmt.getResultSet();
			while (results.next()) {
				residents.put(results.getString("id"), results.getString("name") + " " + results.getString("surname"));
			}
		}
		return residents;
	}

	public HashMap<String, String> listByAge(Integer age) throws SQLException {
		HashMap<String, String> residents = new HashMap<>();
		Date date = new Date();
		try (PreparedStatement pStmt = conn.prepareStatement(
				"SELECT * FROM residents WHERE DATE_PART('year', ?::date) - DATE_PART('year', birth_date) >= ?")) {
			pStmt.setDate(1, new java.sql.Date(date.getTime()));
			pStmt.setInt(2, age);
			pStmt.execute();
			ResultSet results = pStmt.getResultSet();
			while (results.next()) {
				residents.put(results.getString("id"), results.getString("name") + " " + results.getString("surname"));
			}
		}
		return residents;
	}

	public Optional<Resident> findById(int id) throws SQLException {

		Resident resident = null;

		try (PreparedStatement pStmt = conn.prepareStatement("SELECT * FROM residents WHERE id = ?")) {
			pStmt.setInt(1, id);
			pStmt.execute();

			ResultSet result = pStmt.getResultSet();
			while (result.next()) {
				resident = new Resident(result.getInt("id"), result.getString("name"), result.getString("surname"),
						result.getDate("birth_date"), result.getBigDecimal("income"), result.getString("cpf"));
			}

			return Optional.ofNullable(resident);

		}

	}

}

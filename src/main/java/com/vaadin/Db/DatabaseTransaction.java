package com.vaadin.Db;

import com.vaadin.Domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTransaction {

    final String JDBC_CONNECTION_STR = "jdbc:mysql://127.0.0.1:3306/rehber?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    final String USERNAME = "root";
    final String PASSWORD = "123123123";

    public void addPerson(Person person) {

        String sql = "insert into kisiler (adi, soyadi, telefon) values (?, ?, ?) ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, person.getAdi());
            preparedStatement.setString(2, person.getSoyadi());
            preparedStatement.setString(3, person.getNumara());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " kişi eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePerson(Person person){
        String sql = "delete from kisiler where id=? ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, person.getId());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " kişi silindi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(Person person){
        String sql = "update kisiler set adi=?, soyadi=?, telefon=? where id=? ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, person.getAdi());
            preparedStatement.setString(2, person.getSoyadi());
            preparedStatement.setString(3, person.getNumara());
            preparedStatement.setInt(4, person.getId());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " kişi güncellendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> findAllPerson() {
        List<Person> personList = new ArrayList<>();

        String sql = "select * from kisiler";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String adi = resultSet.getString("adi");
                String soyadi = resultSet.getString("soyadi");
                String telefon = resultSet.getString("telefon");

                Person person = new Person();
                person.setId(id);
                person.setAdi(adi);
                person.setSoyadi(soyadi);
                person.setNumara(telefon);
                personList.add(person);
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personList;
    }
}

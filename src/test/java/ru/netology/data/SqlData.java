package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlData {

    public static String getVerificationCode() throws SQLException {
        val selectSQL = "SELECT code FROM auth_codes WHERE created = (select MAX(created) FROM auth_codes);";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            String code = runner.query(conn, selectSQL, new ScalarHandler<>());
            return code;
        }
    }

    public static void cleanData() throws SQLException {
        val runner = new QueryRunner();
        val cleanUsers = "DELETE FROM users;";
        val cleanCards = "DELETE FROM cards;";
        val cleanAuth_Codes = "DELETE FROM auth_codes;";
        val cleanCard_Transactions = "DELETE FROM card_transactions;";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.execute(conn, cleanCards);
            runner.execute(conn, cleanAuth_Codes);
            runner.execute(conn, cleanUsers);
            runner.execute(conn, cleanCard_Transactions);
        }
    }

    public static String getUserStatus(String login) throws SQLException {
        val selectSQL = "SELECT status FROM users WHERE login = ?;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"

                );
        ) {
            return runner.query(conn, selectSQL, new ScalarHandler<>(), login);
        }
    }

    public static void setNewUser() throws SQLException {
        val faker = new Faker();
        val runner = new QueryRunner();
        val dataSQL = "INSERT INTO users(id, login, password) VALUES (?, ?, ?);";
        val cleanUsers = "DELETE FROM users WHERE id = '1';";
        val cleanAuth_Codes = "DELETE FROM auth_codes;";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            runner.execute(conn, cleanAuth_Codes);
            runner.execute(conn, cleanUsers);
            runner.update(conn, dataSQL, 1, faker.name().username(), "$2a$10$LupomVF1pCYXveW2SuzwYuk6kTEuEr7lyhYq8jdv7TdLrIzhNmFd.");
        }
    }

    public static String getNewUser() throws SQLException {
        setNewUser();
        val runner = new QueryRunner();
        val dataSQL = "SELECT login FROM users WHERE id = '1';";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, dataSQL, new ScalarHandler<>());
        }
    }
}


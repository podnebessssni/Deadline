package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlData;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @Test
    void shouldLoginWithExistUser() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = SqlData.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldAddNewUserAndLogin() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getUser();
        val verificationPage = loginPage.validLogin(authInfo);
        sleep(900);
        val verificationCode = SqlData.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldBlockIfLoginWithWrongPasswordThreeTime() throws SQLException {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getUserWithWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.errorMessage();
        loginPage.clearFields();
        loginPage.validLogin(authInfo);
        loginPage.errorMessage();
        loginPage.clearFields();
        loginPage.validLogin(authInfo);
        val status = SqlData.getUserStatus(authInfo.getLogin());
        assertEquals("blocked", status);
    }

    @AfterAll
    static void cleanTables() throws SQLException {
        SqlData.cleanData();
    }
}

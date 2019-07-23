package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpsSession;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {
        String adminlogin = "administrator";
        String adminpassword = "root";
        app.admin().loginAdmin(adminlogin, adminpassword);
        UserData user = app.user().resetPassword();
        String new_password = app.user().settingNewPassword(user);
        HttpsSession session = app.newSession();
        assertTrue(session.login(user.getUserName(), new_password));
        assertTrue(session.isLoggedInAs(user.getUserName()));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }
}
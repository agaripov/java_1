package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class UserHelper extends HelperBase {

    public UserHelper(ApplicationManager app) {
        super(app);
    }

    public UserData resetPassword() {
        app.getDriver().findElement(By.cssSelector("a[href='/mantisbt-2.21.1/manage_overview_page.php']")).click();
        app.getDriver().findElement(By.linkText("manage_users_link")).click();
        UserData user = app.db().users().iterator().next();
        app.getDriver().findElement(By.linkText(user.getUserName())).click();
        app.getDriver().findElement(By.cssSelector("input[value='Reset Password']")).click();
        return user;
    }

    public String settingNewPassword(UserData user) throws MessagingException, IOException {
        String email = user.getEmail();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        String new_password = "new_password";
        app.registration().finish(confirmationLink, new_password);
        return new_password;
    }
    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }
}
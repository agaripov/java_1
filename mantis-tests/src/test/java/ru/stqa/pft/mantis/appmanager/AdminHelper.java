package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class AdminHelper extends HelperBase {

    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void loginAdmin(String adminlogin, String adminpassword) {
        app.getDriver().findElement(By.name("username")).sendKeys(adminlogin);
        click(By.cssSelector("input[value = 'Login']"));
        app.getDriver().findElement(By.name("password")).sendKeys(adminpassword);
        click(By.cssSelector("input[value = 'Login']"));
    }
}
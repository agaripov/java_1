package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoGroupPage() {
        if (isElementPresent(new By.ByTagName("h1"))
                && wd.findElement(new By.ByTagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
                    return;
        }
        click(By.linkText("groups"));
    }
    public void gotoContactPage() {
        if (isElementPresent(new By.ByTagName("h1"))
                && wd.findElement(new By.ByTagName("h1")).getText().equals("Edit / add address book entry")
                && isElementPresent(By.name("Enter"))) {
            return;
        }
        click(By.linkText("add new"));
    }

    public void gotoHomePage(){
        if (isElementPresent(By.id("maintable"))){
            return;
        }
        click(By.linkText("home"));}

    public void closeAlert(){wd.switchTo().alert().accept();}
}

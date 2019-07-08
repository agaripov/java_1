package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnContactPage() {
        click(By.linkText("home page"));
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
    }

    public void initContactCreation() {
        click(By.name("add new"));
    }

    public void initContactModification() {
        click(By.xpath("(//td[8]/a/img)"));
    }
    public void submitContactModification() {
        click(By.name("update"));
    }
    public void markContact(){ click(By.name("selected[]"));}

    public void deleteContact(){
        click(By.xpath("//input[@value='Delete']"));
    }
}

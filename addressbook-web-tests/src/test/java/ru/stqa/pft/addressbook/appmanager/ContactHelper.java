package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnContactPage() {
        click(By.linkText("home"));
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("email"), contactData.getEmail());

    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnContactPage();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification();
        fillContactForm(contact);
        submitContactModification();
        returnContactPage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        closeAlert();
        returnContactPage();
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void initContactModification() {
        click(By.xpath("(//td[8]/a/img)"));
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteContact(){
        click(By.xpath("//input[@value='Delete']"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void closeAlert(){wd.switchTo().alert().accept();}

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            String firstname = element.findElement(By.xpath(".//td[3]")).getText();
            String lastname = element.findElement(By.xpath(".//td[2]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
        }
        return contacts;
    }
}

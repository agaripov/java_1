package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("First name", "Last name", "Company", "Address"));

        }
        int before = app.getContactHelper().getGontactCount();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("First name1", "Last name1", "Company1", "Address1"));
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnContactPage();
        int after = app.getContactHelper().getGontactCount();
        Assert.assertEquals(after, before);
    }
}

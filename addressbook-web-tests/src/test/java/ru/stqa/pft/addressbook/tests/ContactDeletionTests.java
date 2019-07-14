package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomePage();

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("First name", "Last name", "Company", "Address"));

        }
        int before = app.getContactHelper().getGontactCount();
        app.getContactHelper().markContact();
        app.getContactHelper().deleteContact();
        app.getNavigationHelper().closeAlert();
        app.getContactHelper().returnContactPage();
        int after = app.getContactHelper().getGontactCount();
        Assert.assertEquals(after, before - 1);
    }
}

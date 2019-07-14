package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.getNavigationHelper().gotoHomePage();
    int before = app.getContactHelper().getGontactCount();
    app.getContactHelper().createContact(new ContactData("First name", "Last name", "Company", "Address"));
    app.getContactHelper().returnContactPage();
    int after = app.getContactHelper().getGontactCount();
    Assert.assertEquals(after, before + 1);
  }

}

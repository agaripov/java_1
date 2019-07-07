package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion(){
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().markContact();
        app.getContactHelper().deleteContact();
        app.getNavigationHelper().closeAlert();
    }
}

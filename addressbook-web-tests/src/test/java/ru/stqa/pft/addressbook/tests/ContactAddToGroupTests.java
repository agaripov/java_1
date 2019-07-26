package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class ContactAddToGroupTests extends TestBase {

    @BeforeClass
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }

        if (app.db().contacts().size() == 0) {
            File photo = new File("src/test/resources/1.jpg");
            app.goTo().HomePage();
            app.contact().create(new ContactData().withFirstname("First name").withLastname("Last name").withAddress("Address"));
        }
    }


    @Test
    public void testContactAddToGroup() {
        ContactData contactBefore = app.db().contacts().iterator().next();
        GroupData groupBefore = app.db().groups().iterator().next();
        app.goTo().HomePage();
        app.contact().addToGroup(contactBefore, groupBefore);
        app.goTo().HomePage();
        app.contact().showAllContact();

        ContactData contactAfter = app.db().contacts().iterator().next();
        GroupData groupAfter= app.db().groups().iterator().next();

        assertThat(contactAfter.getGroups(), hasItem(groupBefore));
        assertThat(groupAfter.getContacts(), hasItem(contactBefore));
    }


}

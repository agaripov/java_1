package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTests extends TestBase{

    private Contacts contacts;
    private Groups groups;

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
        ContactData contact = app.db().contacts().iterator().next();
        if (contact.getGroups().size() == 0) {
            GroupData group = app.db().groups().iterator().next();
            app.goTo().HomePage();
            app.contact().addToGroup(contact, group);
        }
    }

    @Test
    public void testContactRemoveFromGroup() {
        contacts = app.db().contacts();
        groups = app.db().groups();
        ContactData contactRemove = contacts.iterator().next();
        GroupData groupRemove = groups.iterator().next();
        app.goTo().HomePage();
        app.contact().contactGroupPage(contactRemove);
        app.contact().removeFromGroup(contactRemove);
        app.db().updateContact(contactRemove);
        app.db().updateGroup(groupRemove);
        assertThat(contactRemove.getGroups(), not(groupRemove));
        assertThat(groupRemove.getContacts(), not(contactRemove));
    }
}
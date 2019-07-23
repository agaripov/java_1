package ru.stqa.pft.mantis.appmanager;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;

public class JamesHelper {
    private ApplicationManager app;
    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;
    private Session mailSession;
    private Store store;
    private String mailserver;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        this.telnet = new TelnetClient();
        this.mailSession = Session.getDefaultInstance(System.getProperties());
    }

    public boolean doesUserExist(String name) {
        this.initTelnetSession();
        this.write("verify " + name);
        String result = this.readUntil("exist");
        this.closeTelnetSession();
        return result.trim().equals("User " + name + " exist");
    }

    public void createUser(String name, String passwd) {
        this.initTelnetSession();
        this.write("adduser " + name + " " + passwd);
        this.readUntil("User " + name + " added");
        this.closeTelnetSession();
    }

    public void deleteUser(String name) {
        this.initTelnetSession();
        this.write("deluser " + name);
        this.readUntil("User " + name + " deleted");
        this.closeTelnetSession();
    }

    private void initTelnetSession() {
        this.mailserver = this.app.getProperty("mailserver.host");
        int port = Integer.parseInt(this.app.getProperty("mailserver.port"));
        String login = this.app.getProperty("mailserver.adminlogin");
        String password = this.app.getProperty("mailserver.adminpassword");

        try {
            this.telnet.connect(this.mailserver, port);
            this.in = this.telnet.getInputStream();
            this.out = new PrintStream(this.telnet.getOutputStream());
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.readUntil("Login id:");
        this.write("");
        this.readUntil("Password:");
        this.write("");
        this.readUntil("Login id:");
        this.write(login);
        this.readUntil("Password:");
        this.write(password);
        this.readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    private String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char)this.in.read();

            while(true) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar && sb.toString().endsWith(pattern)) {
                    return sb.toString();
                }

                ch = (char)this.in.read();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private void write(String value) {
        try {
            this.out.println(value);
            this.out.flush();
            System.out.println(value);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private void closeTelnetSession() {
        this.write("quit");
    }

    public void drainEmail(String username, String password) throws MessagingException {
        Folder inbox = this.openInbox(username, password);
        Message[] var4 = inbox.getMessages();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Message message = var4[var6];
            message.setFlag(Flag.DELETED, true);
        }

        this.closeFolder(inbox);
    }

    private void closeFolder(Folder folder) throws MessagingException {
        folder.close(true);
        this.store.close();
    }

    private Folder openInbox(String username, String password) throws MessagingException {
        this.store = this.mailSession.getStore("pop3");
        this.store.connect(this.mailserver, username, password);
        Folder folder = this.store.getDefaultFolder().getFolder("INBOX");
        folder.open(2);
        return folder;
    }

    public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
        long now = System.currentTimeMillis();

        while(System.currentTimeMillis() < now + timeout) {
            List<MailMessage> allMail = this.getAllMail(username, password);
            if (allMail.size() > 0) {
                return allMail;
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var9) {
                var9.printStackTrace();
            }
        }

        throw new Error("No mail :(");
    }

    public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        Folder inbox = this.openInbox(username, password);
        List<MailMessage> messages = (List)Arrays.asList(inbox.getMessages()).stream().map((m) -> {
            return toModelMail(m);
        }).collect(Collectors.toList());
        this.closeFolder(inbox);
        return messages;
    }

    public static MailMessage toModelMail(Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String)m.getContent());
        } catch (MessagingException var2) {
            var2.printStackTrace();
            return null;
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }
}


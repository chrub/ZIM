package com.technicalkeeda.app;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class CheckingMails {

    private String mailAddress;
    private String password;
    private String subject;

    public CheckingMails(String mailAddress, String password, String subject) {
        this.mailAddress = mailAddress;
        this.password = password;
        this.subject = subject;
    }

    public void findMail() {

        Properties props = new Properties();

        try {
            props.load(new FileInputStream(new File("src\\main\\java\\smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", mailAddress, password);

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (Message mess : messages) {
                if(mess.getSubject().equals(subject)) {
                    System.out.println(mess.getSubject());
                }
            }

            inbox.close(true);
            store.close();
            throw new Exception("The mail not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
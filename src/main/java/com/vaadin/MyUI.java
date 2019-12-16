package com.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.Db.DatabaseTransaction;
import com.vaadin.Domain.Person;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.vaadin.MyAppWidgetset")
public class MyUI extends UI {

    Person person=new Person();
    DatabaseTransaction dbTransaction=new DatabaseTransaction();
    Table table = new Table("KİŞİ LİSTESİ");
    FormLayout formLayout = new FormLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Label labelKayit=new Label("*******Kullanıcı Kaydet*******");
        formLayout.addComponents(labelKayit);
        kullaniciKaydet();

        Label labelSil=new Label("*******Kullanıcı Sil*******");
        formLayout.addComponents(labelSil);
        kullaniciSil();


        Label labelGuncelle=new Label("*******Kullanıcı Güncelle*******");
        formLayout.addComponents(labelGuncelle);
        kullaniciGuncelle();

        setContent(formLayout);

        table.addContainerProperty("ID", Integer.class, null);
        table.addContainerProperty("AD", String.class, null);
        table.addContainerProperty("SOYAD", String.class, null);
        table.addContainerProperty("NUMARA", String.class, null);
        tableListele(table);
        formLayout.addComponents(table);
    }

    private void kullaniciGuncelle() {
        KullaniciTextField id = new KullaniciTextField();
        id.setCaption("Güncellenecek Kişinin ID'si ");
        formLayout.addComponent(id);

        KullaniciTextField newNameField = new KullaniciTextField();
        newNameField.setCaption("Adı");
        formLayout.addComponent(newNameField);

        KullaniciTextField newSurnameField = new KullaniciTextField();
        newSurnameField.setCaption("Soyadı");
        formLayout.addComponent(newSurnameField);

        KullaniciTextField newPhoneField = new KullaniciTextField();
        newPhoneField.setCaption("Telefon");
        formLayout.addComponent(newPhoneField);

        Button update=new Button();
        update.setCaption("Güncelle");
        update.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Person person = new Person();

                person.setId(Integer.parseInt(id.getValue()));
                person.setAdi(newNameField.getValue());
                person.setSoyadi(newSurnameField.getValue());
                person.setNumara(newPhoneField.getValue());

                DatabaseTransaction dbTransaction = new DatabaseTransaction();
                dbTransaction.updatePerson(person);
                Notification.show("Kişi Güncellendi!");
                tableListele(table);
            }
        });
        formLayout.addComponents(update);
    }

    private void kullaniciSil() {
        KullaniciTextField idField = new KullaniciTextField();
        idField.setCaption("Silinecek Kişi Id:");
        formLayout.addComponent(idField);
        Button remove = new Button();
        remove.setCaption("Sil");
        remove.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Person person = new Person();
                person.setId(Integer.parseInt(idField.getValue()));

                DatabaseTransaction dbTransaction = new DatabaseTransaction();
                dbTransaction.removePerson(person);
                Notification.show("Kişi Silindi!");
                tableListele(table);
            }
        });
        formLayout.addComponents(remove);
    }

    private void kullaniciKaydet() {
        KullaniciTextField nameField = new KullaniciTextField();
        nameField.setCaption("Adı");
        formLayout.addComponent(nameField);

        KullaniciTextField surnameField = new KullaniciTextField();
        surnameField.setCaption("Soyadı");
        formLayout.addComponent(surnameField);

        KullaniciTextField phoneField = new KullaniciTextField();
        phoneField.setCaption("Telefon");
        formLayout.addComponent(phoneField);

        Button insert = new Button();
        insert.setCaption("Kaydet");
        insert.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                person.setAdi(nameField.getValue());
                person.setSoyadi(surnameField.getValue());
                person.setNumara(phoneField.getValue());

                dbTransaction.addPerson(person);
                Notification.show("Kişi Eklendi");
                tableListele(table);
            }
        });
        formLayout.addComponent(insert);
    }

    private void tableListele(Table table) {
        table.removeAllItems();
        List<Person> personList = dbTransaction.findAllPerson();

        for (Person person:personList) {
            table.addItem(new Object[]{person.getId(), person.getAdi(),person.getSoyadi(),person.getNumara()}, person.getId());
        }

        table.setPageLength(table.size());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)

    public static class MyUIServlet extends VaadinServlet {
    }
}

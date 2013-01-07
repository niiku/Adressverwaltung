package de.gkjava.addr.model;

import java.text.Collator;

/**
 * @author vmadmin
 */
public class Address implements Comparable<Address> {

    private int id;
    private String lastname;
    private String firstname;
    private String email;
    private String emailAdditional;
    private String homepage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailAdditional() {
        return emailAdditional;
    }

    public void setEmailAdditional(String emailAdditional) {
        this.emailAdditional = emailAdditional;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    // Wird für die Sortierung der Adressliste benutzt
    public int compareTo(Address a) {
        Collator collator = Collator.getInstance();
        String s1 = lastname + ", " + firstname;
        String s2 = a.getLastname() + ", " + a.getFirstname();
        return collator.compare(s1, s2);
    }
}

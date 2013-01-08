package de.gkjava.addr.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author vmadmin
 */
public class Address implements Comparable<Address>, Serializable {

    private Integer id;
    private String lastname;
    private String firstname;
    private String email;
    private String emailAdditional;
    private String homepage;
    private String fixedNetwork;
    private String mobile;
    public static final String[] COLUMN_NAMES = new String[]{"id", "lastname", "firstname", "email", "emailAdditional", "homepage", "fixedNetwork", "mobile"};

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFixedNetwork() {
        return fixedNetwork;
    }

    public void setFixedNetwork(String fixedNetwork) {
        this.fixedNetwork = fixedNetwork;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        return firstname.equals(address.firstname) && lastname.equals(address.lastname);
    }

    public List<String> getAddressDataAsStringList() {
        List<String> addressData = new ArrayList<String>();
        for (String columnName : COLUMN_NAMES) {

            try {
                addressData.add("" + getClass().getDeclaredField(columnName).get(this));
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Address.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return addressData;
    }
}

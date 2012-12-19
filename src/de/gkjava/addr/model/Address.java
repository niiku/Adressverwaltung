package de.gkjava.addr.model;

import java.text.Collator;

/**
 * @author  vmadmin
 */
public class Address implements Comparable<Address> {
  /**
 * @uml.property  name="id"
 */
private int id;
  /**
 * @uml.property  name="lastname"
 */
private String lastname;
  /**
 * @uml.property  name="firstname"
 */
private String firstname;
  /**
 * @uml.property  name="email"
 */
private String email;
  /**
 * @uml.property  name="emailAdditional"
 */
private String emailAdditional;
  /**
 * @uml.property  name="homepage"
 */
private String homepage;

  /**
 * @return
 * @uml.property  name="id"
 */
public int getId() {
    return id;
  }

  /**
 * @param id
 * @uml.property  name="id"
 */
public void setId(int id) {
    this.id = id;
  }

  /**
 * @return
 * @uml.property  name="lastname"
 */
public String getLastname() {
    return lastname;
  }

  /**
 * @param lastname
 * @uml.property  name="lastname"
 */
public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  /**
 * @return
 * @uml.property  name="firstname"
 */
public String getFirstname() {
    return firstname;
  }

  /**
 * @param firstname
 * @uml.property  name="firstname"
 */
public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
 * @return
 * @uml.property  name="email"
 */
public String getEmail() {
    return email;
  }

  /**
 * @param email
 * @uml.property  name="email"
 */
public void setEmail(String email) {
    this.email = email;
  }

  /**
 * @return
 * @uml.property  name="emailAdditional"
 */
public String getEmailAdditional() {
    return emailAdditional;
  }

  /**
 * @param emailAdditional
 * @uml.property  name="emailAdditional"
 */
public void setEmailAdditional(String emailAdditional) {
    this.emailAdditional = emailAdditional;
  }

  /**
 * @return
 * @uml.property  name="homepage"
 */
public String getHomepage() {
    return homepage;
  }

  /**
 * @param homepage
 * @uml.property  name="homepage"
 */
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

package it.sad.sii.AndroidPassReminder;

import java.io.Serializable;


/**
 * Created by lconcli on 30/09/15.
 */
public class PersonalInfo implements Serializable {


    private Integer ticketCode;
    private String ticketDescIt;
    private String ticketDescDe;
    private String name;
    private String surname;
    private String codfisc;
    private String email;
    private String phone;
    private String mobile;
    private String fax;
    private String street;
    private String street2;
    private String location;
    private String townNameIt;
    private String townNameDe;
    private String district;
    private String zip;
    private String countryNameIt;
    private String countryNameDe;
    private String code;
    private Boolean eu;


    public Integer getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(Integer ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getTicketDescIt() {
        return ticketDescIt;
    }

    public void setTicketDescIt(String ticketDescIt) {
        this.ticketDescIt = ticketDescIt;
    }

    public String getTicketDescDe() {
        return ticketDescDe;
    }

    public void setTicketDescDe(String ticketDescDe) {
        this.ticketDescDe = ticketDescDe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCodfisc() {
        return codfisc;
    }

    public void setCodfisc(String codfisc) {
        this.codfisc = codfisc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTownNameIt() {
        return townNameIt;
    }

    public void setTownNameIt(String townNameIt) {
        this.townNameIt = townNameIt;
    }

    public String getTownNameDe() {
        return townNameDe;
    }

    public void setTownNameDe(String townNameDe) {
        this.townNameDe = townNameDe;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountryNameIt() {
        return countryNameIt;
    }

    public void setCountryNameIt(String countryNameIt) {
        this.countryNameIt = countryNameIt;
    }

    public String getCountryNameDe() {
        return countryNameDe;
    }

    public void setCountryNameDe(String countryNameDe) {
        this.countryNameDe = countryNameDe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEu() {
        return eu;
    }

    public void setEu(Boolean eu) {
        this.eu = eu;
    }
}

package com.bayer.turfid;

public class TIDistributor {
    public Integer DistributorId;
    public String DistributorName;
    public String DistributorCountry;
    public String DistributorAddress;
    public String DistributorTelephone;
    public String DistributorFax;
    public String DistributorEmail;
    public String DistributorWebsite;

    public TIDistributor(Integer Id, String name, String country, String address, String telephone, String fax, String email, String website) {
        this.DistributorId = Id;
        this.DistributorName = name;
        this.DistributorCountry = country;
        this.DistributorAddress = address;
        this.DistributorTelephone = telephone;
        this.DistributorFax = fax;
        this.DistributorEmail = email;
        this.DistributorWebsite = website;
    }

    public Integer getDistributorId() {
        return DistributorId;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public String getDistributorCountry() {
        return DistributorCountry;
    }

    public String getDistributorAddress() {
        return DistributorAddress;
    }
}

package com.example.notesapptutorial;

public class firebasemodelp {

    private String site;
    private String password;


    public firebasemodelp()
    {

    }

    public firebasemodelp(String site, String password)
    {
        this.site=site;
        this.password=password;
    }


    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


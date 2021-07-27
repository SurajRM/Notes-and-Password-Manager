package com.example.notesapptutorial;

public class firebasemodel {

    private String title;
    private String content;
    private String site;
    private String password;


    public firebasemodel()
    {

    }

    public  firebasemodel (String title, String content, String site, String password)
    {
        this.title=title;
        this.content=content;
        this.site=site;
        this.password=password;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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


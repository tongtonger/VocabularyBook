package com.example.vocabularybook;

public class Words {
    private String name;
    private String pro;
    private String example;

    public Words(String name,String example)
    {
        this.name=name;
        this.example=example;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }
    public String getPro() {
        return pro;
    }

    public void setExample(String example) {
        this.example = example;
    }
    public String getExample() {
        return example;

    }
}

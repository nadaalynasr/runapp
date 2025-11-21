package com.example.runapp.models;

public class InputTitle {
    private String id;
    private String name;
    private String placeholder;

    public InputTitle() {}

    public InputTitle(String id, String name, String placeholder) {
        this.id = id;
        this.name = name;
        this.placeholder = placeholder;
    }

    //getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPlaceholder() {
        return placeholder;
    }

    //setters
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}
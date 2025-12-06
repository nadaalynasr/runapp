package com.example.runapp.models;

public class InputMetrics {
    private String id;
    private String name;
    private String label;
    private String dataType;
    private String placeholder;

    public InputMetrics() {}

    public InputMetrics(String id, String name, String label, String dataType) {
        this(id, name, label, dataType, null);
    }

    public InputMetrics(String id, String name, String label, String dataType, String placeholder) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.dataType = dataType;
        this.placeholder = placeholder;
    }

    //getters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLabel() {
        return label;
    }
    public String getDataType() {
        return dataType;
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
    public void setLabel(String label) {
        this.label = label;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    
}

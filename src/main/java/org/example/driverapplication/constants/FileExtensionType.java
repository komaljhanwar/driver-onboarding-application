package org.example.driverapplication.constants;

public enum FileExtensionType {

    PDF("pdf"),
    JPEG("jpeg");


    private String type;

    FileExtensionType(String type){
        this.type =type;
    }

    public String getType(){
        return type;
    }
}

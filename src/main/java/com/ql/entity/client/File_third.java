package com.ql.entity.client;

import javafx.beans.property.StringProperty;

public class File_third {
    StringProperty file_name;
    StringProperty file_length;
    StringProperty file_modify_time;
    public File_third(){}
    File_third(StringProperty file_name,StringProperty file_length,StringProperty file_modify_time){
        this.file_name = file_name;
        this.file_length = file_length;
        this.file_modify_time = file_modify_time;
    }

    public void setFile_name(StringProperty file_name) {
        this.file_name=file_name;
    }

    public void setFile_modify_time(StringProperty file_modify_time) {
        this.file_modify_time=file_modify_time;
    }

    public void setFile_length(StringProperty file_length) {
        this.file_length=file_length;
    }

    public StringProperty getFile_length() {
        return file_length;
    }

    public StringProperty getFile_modify_time() {
        return file_modify_time;
    }

    public StringProperty getFile_name() {
        return file_name;
    }
}

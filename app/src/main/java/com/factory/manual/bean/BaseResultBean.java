package com.factory.manual.bean;

import java.io.Serializable;

public class BaseResultBean implements Serializable {
    private String result;
    private String resultNote;

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


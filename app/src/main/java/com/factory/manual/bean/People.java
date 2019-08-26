package com.factory.manual.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class People implements Serializable {
    private String groupName;
    private String peopleName;
    private ArrayList<People> peoples;

    public People(String groupName, String peopleName) {
        this.groupName = groupName;
        this.peopleName = peopleName;
    }

    public People(String groupName, String peopleName, ArrayList<People> peoples) {
        this.groupName = groupName;
        this.peopleName = peopleName;
        this.peoples = peoples;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public ArrayList<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(ArrayList<People> peoples) {
        this.peoples = peoples;
    }
}

package com.factory.manual.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    private String groupName;
    private ArrayList<People> peoples;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(ArrayList<People> peoples) {
        this.peoples = peoples;
    }
}

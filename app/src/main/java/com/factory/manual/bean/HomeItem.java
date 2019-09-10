package com.factory.manual.bean;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class HomeItem {
    public String title;
    public int icon;
    public Type type;
    public String content;

    public HomeItem(String title, int icon, Type type, String content) {
        this.title = title;
        this.icon = icon;
        this.type = type;
        this.content = content;
    }

    public static enum Type {
        知识手册,
        任务管理,
        审批列表
    }
}

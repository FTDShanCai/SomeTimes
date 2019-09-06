package com.factory.manual.ui.work;

public class WorkUtil {
    public static String getDetailState(String state) {
        switch (state) {
            case "1":
                return "进行中";
            case "2":
                return "暂停中";
            case "3":
                return "审批中";
            case "4":
                return "已完成";
            case "5":
                return "已超期";
            case "6":
                return "异常(已完成)";
            case "7":
                return "部门（已完成）";
        }
        return "";
    }

    public static String getChildState(String state) {
        switch (state) {
            case "1":
                return "未开始";
            case "2":
                return "已完成";
            case "3":
                return "暂停";
            case "4":
                return "重启";
            case "5":
                return "上报";
            case "6":
                return "审批成功";
            case "7":
                return "审批失败";
            case "8":
                return "异常";
            case "9":
                return "异常回退";
            case "10":
                return "异常结束";
            case "11":
                return "已转交";
        }
        return "";
    }
}

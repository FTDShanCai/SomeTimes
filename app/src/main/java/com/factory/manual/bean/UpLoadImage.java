package com.factory.manual.bean;

/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class UpLoadImage {

    private String path;
    private String uploadurl;
    private boolean isAdd;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploadurl() {
        return uploadurl == null ? "" : uploadurl;
    }

    public void setUploadurl(String uploadurl) {
        this.uploadurl = uploadurl;
    }
}

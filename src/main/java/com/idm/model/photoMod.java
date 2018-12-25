package com.idm.model;

public class photoMod {
    private int photoId;
    private int productId;
    private String photoLink;
    private String photoDescriptions;
    private String photoAlt;
    private String addDate;
    private String addBy;
    private String editedDate;
    private String editedBy;
    private String isActive;
    private String isPrimary;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getPhotoDescriptions() {
        return photoDescriptions;
    }

    public void setPhotoDescriptions(String photoDescriptions) {
        this.photoDescriptions = photoDescriptions;
    }

    public String getPhotoAlt() {
        return photoAlt;
    }

    public void setPhotoAlt(String photoAlt) {
        this.photoAlt = photoAlt;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(String editedDate) {
        this.editedDate = editedDate;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }
}

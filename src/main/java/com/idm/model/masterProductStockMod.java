package com.idm.model;

public class masterProductStockMod {
    private int productStockId;
    private int productId;
    private int platformId;
    private int stockQty;
    private int wasteQty;
    private String stockDescriptions;
    private String addDate;
    private String addBy;
    private String editedDate;
    private String editedBy;
    private String isActive;

    public int getProductStockId() {
        return productStockId;
    }

    public void setProductStockId(int productStockId) {
        this.productStockId = productStockId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getWasteQty() {
        return wasteQty;
    }

    public void setWasteQty(int wasteQty) {
        this.wasteQty = wasteQty;
    }

    public String getStockDescriptions() {
        return stockDescriptions;
    }

    public void setStockDescriptions(String stockDescriptions) {
        this.stockDescriptions = stockDescriptions;
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
}

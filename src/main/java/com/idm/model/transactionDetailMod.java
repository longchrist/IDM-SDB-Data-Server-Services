package com.idm.model;

public class transactionDetailMod {
    private int transactionDetailId;
    private int transactionId;
    private int productId;
    private String productName;
    private int productQty;
    private int productPricePerUnit;
    private int productSalesPerUnit;
    private String isPreorder;
    private String descriptions;

    public int getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(int transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getProductPricePerUnit() {
        return productPricePerUnit;
    }

    public void setProductPricePerUnit(int productPricePerUnit) {
        this.productPricePerUnit = productPricePerUnit;
    }

    public int getProductSalesPerUnit() {
        return productSalesPerUnit;
    }

    public void setProductSalesPerUnit(int productSalesPerUnit) {
        this.productSalesPerUnit = productSalesPerUnit;
    }

    public String getIsPreorder() {
        return isPreorder;
    }

    public void setIsPreorder(String isPreorder) {
        this.isPreorder = isPreorder;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}

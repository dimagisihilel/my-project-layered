package lk.ijse.pawnshop.entity;

public class Inventory {
    private String inventoryId;
    private String customerId;
    private String itemType;
    private String caratValue;
    private double pricePerGram;
    private double weight;
    private String description;
    private double assessedValue;

    public Inventory() {
    }

    public Inventory(String inventoryId, String customerId, String itemType, String caratValue, double pricePerGram, double weight, String description, double assessedValue) {
        this.inventoryId = inventoryId;
        this.customerId = customerId;
        this.itemType = itemType;
        this.caratValue = caratValue;
        this.pricePerGram = pricePerGram;
        this.weight = weight;
        this.description = description;
        this.assessedValue = assessedValue;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getCaratValue() {
        return caratValue;
    }

    public void setCaratValue(String caratValue) {
        this.caratValue = caratValue;
    }

    public double getPricePerGram() {
        return pricePerGram;
    }

    public void setPricePerGram(double pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAssessedValue() {
        return assessedValue;
    }

    public void setAssessedValue(double assessedValue) {
        this.assessedValue = assessedValue;
    }
}

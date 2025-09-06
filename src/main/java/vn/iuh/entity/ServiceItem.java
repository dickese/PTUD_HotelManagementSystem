package vn.iuh.entity;

public class ServiceItem {
    private String id;
    private String itemName;
    private String serviceCategoryId;

    public ServiceItem() {
    }

    public ServiceItem(String id, String itemName, String serviceCategoryId) {
        this.id = id;
        this.itemName = itemName;
        this.serviceCategoryId = serviceCategoryId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(String serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }
}

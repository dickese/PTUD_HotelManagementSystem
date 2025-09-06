package vn.iuh.entity;

public class ServiceCategory {
    private String id;
    private String categoryName;

    public ServiceCategory() {
    }

    public ServiceCategory(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

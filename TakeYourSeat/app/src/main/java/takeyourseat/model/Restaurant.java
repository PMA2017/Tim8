package takeyourseat.beans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Manager")
    @Expose
    private Integer manager;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Image")
    @Expose
    private Object image;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Webste")
    @Expose
    private String webste;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Location")
    @Expose
    private Integer location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebste() {
        return webste;
    }

    public void setWebste(String webste) {
        this.webste = webste;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

}
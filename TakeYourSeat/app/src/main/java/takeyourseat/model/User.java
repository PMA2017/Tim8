package takeyourseat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nenad on 5/9/2017.
 */
@DatabaseTable(tableName = User.TABLE_NAME_USERS)
public class User {
    public static final String TABLE_NAME_USERS = "users";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_TOKEN = "token";
    public static final String FIELD_NAME_LASTNAME = "lastName";
    public static final String FIELD_NAME_ADDRESS = "address";
    public static final String FIELD_NAME_EMAIL = "email";
    public static final String FIELD_NAME_PASS = "password";
    public static final String FIELD_NAME_IMAGE = "image";

    @SerializedName("Id")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private Integer id;
    @SerializedName("Token")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_TOKEN)
    private String token;
    @SerializedName("Name")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;
    @SerializedName("LastName")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_LASTNAME)
    private String lastName;
    @SerializedName("Address")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_ADDRESS)
    private String address;
    @SerializedName("Email")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_EMAIL)
    private String email;
    @SerializedName("Password")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_PASS)
    private String password;
    @SerializedName("Image")
    @Expose
    @DatabaseField(columnName = FIELD_NAME_IMAGE)
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

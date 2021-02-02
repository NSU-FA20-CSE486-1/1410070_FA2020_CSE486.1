package food.restra.hungerbite.login.model;

/**
 * Created by Yeahia Muhammad Arif on 02,February,2021
 */
public class AppUser {
    String email;
    String type;
    AppUser(){}
    public AppUser(String email, String type) {
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

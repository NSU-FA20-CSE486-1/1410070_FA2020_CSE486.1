package food.restra.hungerbite.common.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yeahia Muhammad Arif on 09,February,2021
 */
public class RootModel {

    @SerializedName("to") //  "to" changed to token
    private String token;

    @SerializedName("notification")
    private NotificationModel notification;

//    @SerializedName("data")
//    private DataModel data;

    public RootModel(String token, NotificationModel notification, DataModel data) {
        this.token = token;
        this.notification = notification;
        //this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NotificationModel getNotification() {
        return notification;
    }

    public void setNotification(NotificationModel notification) {
        this.notification = notification;
    }

//    public DataModel getData() {
//        return data;
//    }
//
//    public void setData(DataModel data) {
//        this.data = data;
//    }
}

package food.restra.hungerbite.common.network.model;

/**
 * Created by Yeahia Muhammad Arif on 09,February,2021
 */
public class NotificationModel {

    private String title;
    private String body;

    public NotificationModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

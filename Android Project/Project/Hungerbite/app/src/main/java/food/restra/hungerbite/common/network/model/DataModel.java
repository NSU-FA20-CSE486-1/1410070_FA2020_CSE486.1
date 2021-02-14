package food.restra.hungerbite.common.network.model;

/**
 * Created by Yeahia Muhammad Arif on 09,February,2021
 */
public class DataModel {

    private String name;
    private String age;

    public DataModel(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}

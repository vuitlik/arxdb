package arxdb.manipulator.entities;

import java.util.ArrayList;

public class M_User extends M_UnitBase {
    private ArrayList<M_Group> groups;
    public M_User(String name) {
        this.name = name;
    }
    public void create_user(String name, String password) {

    }
}

package arxdb.manipulator;

import arxdb.lang.Par;
import arxdb.manipulator.entities.M_User;

public class M_Core {
    public static void distributor(Par par) {
        M_User act_user = new M_User("__global");
        if (par.id.equals("create")) {
            if (par.unit.equals("user")) {

            }
        }
        else if (par.id.equals("select")) {

        }
        else if (par.id.equals("delete")) {

        }
        else if (par.id.equals("forgot")) {

        }
        else if (par.id.equals("set")) {

        }
    }
}

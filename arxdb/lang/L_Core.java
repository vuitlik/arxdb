package arxdb.lang;

import java.util.ArrayList;
import java.util.Arrays;

public class L_Core {
    public static Par distributor(String command) {
        Par par = Parser.parser(Lexer.lexer(command));
        for (ArrayList<String[]> comp : par.comps) {
            for (String[] lexem_descr : comp) {
                System.out.println(Arrays.toString(lexem_descr));
            }
            System.out.println();
        }
        return par;
    }
}
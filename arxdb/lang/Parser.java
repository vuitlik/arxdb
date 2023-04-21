package arxdb.lang;

import java.util.ArrayList;

class Parser {
    static Par parser(ArrayList<String[]> lex) {
        Par par = new Par();
        ArrayList<String[]> lexem_descr_bufer = new ArrayList<String[]>();
        for (String[] lexem_descr : lex) {
            if (lexem_descr[1].equals("id")) par.id = lexem_descr[0];
            else if (lexem_descr[1].equals("unit")) par.unit = lexem_descr[0];
            else if (lexem_descr[1].equals("select")) par.select = lexem_descr[0];
            else if (lexem_descr[1].equals("union")) {
                lexem_descr_bufer.add(lexem_descr);
                par.comps.add(new ArrayList<String[]>(lexem_descr_bufer));
                lexem_descr_bufer.clear();
            }
            else lexem_descr_bufer.add(lexem_descr);
        }
        if (lexem_descr_bufer.size() != 0) par.comps.add(new ArrayList<String[]>(lexem_descr_bufer));
        return par;
    }
}
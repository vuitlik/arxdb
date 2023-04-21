package arxdb.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class L_String {
    public String str;
    public int start_pos;
    public int end_pos;
    public L_String(int start_pos) {
        this.str = "";
        this.start_pos = start_pos;
        this.end_pos = 0;
    }
}

class Lexer {
    private static Map<String, String> vocabulary = new HashMap<String, String>();
    private static void set_vocabulary() {
        String[][] arr_vocabulary = {
            {"create", "id"},
            {"delete", "id"},
            {"select", "id"},
            {"forgot", "id"},
            {"set", "id"},

            {"user", "unit"},
            {"db", "unit"},
            {"class", "unit"},
            {"templ", "unit"},
            {"object", "unit"},

            {"\"", ""},
            {" ", ""},
            {"\t", ""},
            
            {":", "select"},
            {"~", "select"},
            {"-", "select"},

            {"!", "comp"},
            {"=", "comp"},
            {"<", "comp"},
            {">", "comp"},

            {"|", "union"},
            {"&", "union"}
        };
        for (String[] lexem_descr : arr_vocabulary) vocabulary.put(lexem_descr[0], lexem_descr[1]);
    }
    private static ArrayList<String[]> lex1 = new ArrayList<String[]>();
    private static void add_value_to_lex1(String lexem) {
        if (vocabulary.containsKey(lexem)) lex1.add(new String[] {lexem, vocabulary.get(lexem)});
        else lex1.add(new String[] {lexem, "data"});
    }
    static ArrayList<String[]> lexer(String command) {
        set_vocabulary();
        String lex_str = "";
        for (char a : command.toCharArray()) {
            if (vocabulary.containsKey("" + a)) {
                if (!lex_str.equals("")) add_value_to_lex1(lex_str);
                lex1.add(new String[] {"" + a, vocabulary.get("" + a)});
                lex_str = "";
            }
            else lex_str += a;
        }
        if (!lex_str.equals("")) add_value_to_lex1(lex_str);
        boolean is_str = false;
        ArrayList<L_String> strs_bufer = new ArrayList<L_String>();
        int count = 0;
        for (String[] lexem_descr : lex1) {
            if (lexem_descr[0].equals("\"")) { 
                is_str = !is_str;
                if (is_str) strs_bufer.add(new L_String(count));
                else strs_bufer.get(strs_bufer.size() - 1).end_pos = count;
            }
            else if (is_str) strs_bufer.get(strs_bufer.size() - 1).str += lexem_descr[0];
            count++;
        }
        if (strs_bufer.size() != 0) {
            for (L_String str : strs_bufer) {
                count = str.start_pos;
                while (count != str.end_pos + 1) {
                    lex1.get(count)[0] = " ";
                    count++;
                }
                lex1.set(str.start_pos, new String[] {str.str, "string"});
            }
        }
        ArrayList<String[]> lex2 = new ArrayList<String[]>();
        for (String[] lexem_descr : lex1) { 
            if (!lexem_descr[0].equals(" ") && !lexem_descr[0].equals("\t")) {
                if (vocabulary.containsKey(lexem_descr[0])) lex2.add(lexem_descr.clone());
                else if (lexem_descr[1].equals("string")) lex2.add(new String[] {lexem_descr[0], lexem_descr[1]});
                else {
                    //if (lexem_descr[0].isdigit()) lex2.add(new String[] {lexem_descr[0], "num"});
                    if (lexem_descr[0].equals("true") || lexem_descr[0].equals("false")) lex2.add(new String[] {lexem_descr[0], "bool"});
                    else lex2.add(new String[] {lexem_descr[0], "attr"});
                }
            }
        }
        lex1.clear();
        return lex2;
    }
}

package arxdb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import arxdb.lang.L_Core;
import arxdb.manipulator.M_Core;

public class Main {
    private static BufferedReader in;
    private static int count = 1;
    private static Map<Integer, String> commands = new HashMap<Integer, String>();
    private static void trans_distributor() {
        for (int count : commands.keySet()) 
        M_Core.distributor(L_Core.distributor(commands.get(count)));
    }
    private static void file_mode(String path, String[] gets) {
        String command;
        try {
            Reader file = new FileReader(path);
            in = new BufferedReader(file);
            while (in.ready()) {
                command = in.readLine().strip();
                if (!command.equals("")) commands.put(count, command);
                count++;
            }
            in.close();
            file.close();
        } catch (FileNotFoundException err) {

        } catch (IOException err) {

        }
    }
    private static void repl_mode() {
        in = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        boolean is_trans = false;
        do {
            if (is_trans) System.out.print("[" + count + "]> ");
            else System.out.print(">>> ");
            try {
                command = in.readLine().trim();
                if (!command.equals("")) {
                    if (command.charAt(0) == '?') {
                        command = command.substring(1).toLowerCase();
                        if (command.equals("close")) {
                            in.close();
                            break;
                        }
                        else if (command.equals("run")) {
                            if (is_trans) trans_distributor();
                        }
                        else if (command.equals("clear")) {
                            if (is_trans) {
                                count = 1;
                                commands.clear();
                                continue;
                            }
                        }
                        else if (command.equals("exit")) {
                            if (is_trans) {
                                is_trans = !is_trans;
                                count++;
                            }
                            else {
                                in.close();
                                break;
                            }
                        }
                        else if (command.equals("trans")) {
                            if (is_trans) {}
                            else {
                                is_trans = !is_trans;
                                continue;
                            }
                        }
                        else System.out.println("[!] Unknown command.");
                    }
                    else {
                        if (is_trans) commands.put(count, command);
                        else M_Core.distributor(L_Core.distributor(command));
                    }
                }
                if (is_trans) count++;
            } catch (IOException err) {

            }
        } while(true);
    }
    public static void main(String[] args) {
        if (args.length >= 2) {
            String[] gets = new String[args.length - 2];
            for (int i = 2; i < args.length; i++) gets[i - 2] = args[i];
            file_mode(args[1], gets);
        }
        else repl_mode();
    }
}
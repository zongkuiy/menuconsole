package cn.zongkuiy.menuconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * General variables and functions definitions of a menu node
 * @author zongkuiy
 * 10:56:57 AM May 21, 2013
 */
public abstract class MenuBase {
    protected String name;

    public abstract void print();

    public abstract void execute();

    public abstract void setName(String newName);

    public MenuBase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static public int getInt(String prompt, int minVal, int maxVal) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(MenuSystem.socketLocal.get().getIs()));
        String str = "";
        while (MenuSystem.socketLocal.get().getPs().checkError() == false) {
        	MenuSystem.getInstance().println(prompt);
            str = in.readLine();
            if (str == null) {
                //end of the input stream throw the exception
                throw new IOException();
            }
            if ((str != null) && (str.length() > 0)) {
                try {
                    int response = Integer.parseInt(str);
                    if ((response >= minVal) && (response <= maxVal))
                        return response;
                }
                catch (Exception e) {

                }
            }
            MenuSystem.getInstance().println("[ERROR] Invalid Value, please enter a value between " + minVal + " and " + maxVal);
        }
        throw new IOException();
    }

    static public int getInt(String prompt, int minVal, int maxVal, int specificVal, Socket s) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(MenuSystem.socketLocal.get().getIs()));
        String str = "";
        while (MenuSystem.socketLocal.get().getPs().checkError() == false) {
        	MenuSystem.getInstance().println(prompt);
            str = in.readLine();
            if (str == null) {
                //end of the input stream throw the exception
                throw new IOException();
            }
            if ((str != null) && (str.length() > 0)) {
                try {
                    int response = Integer.parseInt(str);
                    if ((response >= minVal) && (response <= maxVal))
                        return response;
                }
                catch (Exception e) {

                }
            }
            MenuSystem.getInstance().println("[ERROR] Invalid Value, please enter a value between " + minVal + " and " + maxVal
                    + " or enter " + specificVal);
        }
        throw new IOException();
    }

    static public String getString(String prompt) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(MenuSystem.socketLocal.get().getIs()));
        String str = "";
        while (MenuSystem.socketLocal.get().getPs().checkError() == false) {
        	MenuSystem.getInstance().println(prompt);
            str = in.readLine();
            if (str == null) {
                //end of the input stream throw the exception
                throw new IOException();
            }
            if ((str != null) && (str.length() > 0)) {
                return str;
            }
            MenuSystem.getInstance().println("[ERROR] Invalid Value, please enter a valid string ");
        }

        throw new IOException();
    }
}


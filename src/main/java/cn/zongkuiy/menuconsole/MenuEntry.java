package cn.zongkuiy.menuconsole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The leaf node entry in the system. 
 * It is binded to a given method.
 * The method will be executed through reflection mechanism 
 * 
 * @author zongkuiy
 * 10:49:44 AM May 21, 2013
 */
public class MenuEntry extends MenuBase {
    private Object obj;
    private String methodName;
    
    /**
     * Construct the entry by given inputs
     * @param menuEntryName: displaying name of the entry
     * @param parentMenu: parent menu node of this entry
     * @param obj: the Object(executor) of this entry
     * @param methodName: the Operation will be executed of this entry
     */
    public MenuEntry(String menuEntryName, Menu parentMenu, Object obj, String methodName) {
        super(menuEntryName);
        this.obj = obj;
        this.methodName = methodName;
        parentMenu.addEntry(this);
    }

    /**
     * run the method by the target object and display the result to the socket
     */
    public void execute() {
        if (obj == null)
            return;
        Class cls = obj.getClass();
        Method method;
        try {
            method = cls.getMethod(methodName);
        }
        catch (NoSuchMethodException e) {
        	MenuSystem.getInstance().println("NoSuchMethodException, method name is " + methodName);
            return;
        }
        try {
            method.invoke(obj);
        }
        catch (IllegalArgumentException e) {
        	MenuSystem.getInstance().println("IllegalArgumentException, method name is " + methodName);
        }
        catch (IllegalAccessException e) {
        	MenuSystem.getInstance().println("IllegalAccessException, method name is " + methodName);
        }
        catch (InvocationTargetException e) {
        	MenuSystem.getInstance().println("InvocationTargetException, method name is " + methodName);
        }

    }

    public void print() {
    	MenuSystem.getInstance().println(name);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}


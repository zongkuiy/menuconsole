package cn.zongkuiy.menuconsole;

import java.util.ArrayList;
import java.util.List;

public class Menu extends MenuBase {
    private ArrayList<MenuBase> menuElements = new ArrayList<MenuBase>();
    private Menu parentMenu;
    
    public Menu(Menu menu, String menuName) {
        super(menuName);
        parentMenu = menu;
        parentMenu.addEntry(this);
    }

    public Menu(String menuName) {
        super(menuName);
        parentMenu = null;
    }
    
    public Menu getParentMenu(){
    	return parentMenu;
    }

    public void setName(String newName) {
        if (isNameIgnored(newName) || !isSubMenuNameExisting(newName)) {
            this.name = newName;
        }
    }

    private ArrayList<MenuBase> printMenu() {
    	MenuSystem.getInstance().println("");
    	List<String> pathStrs = new ArrayList<String>();
    	pathStrs.add(name);
    	
    	// Print the full path of this menu
    	Menu parent = this.parentMenu;
    	while(parent != null){
    		pathStrs.add(" >> ");
    		pathStrs.add(parent.name);
    		parent = parent.parentMenu;
    	}
    	for(int i = pathStrs.size() - 1; i >= 0; i --){
    		MenuSystem.getInstance().print(pathStrs.get(i));
    	}
    	MenuSystem.getInstance().println("");
        for (int i = 0; i < name.length(); i++)
        	MenuSystem.getInstance().print("-");
        MenuSystem.getInstance().println("");

        //Print the sub menu of this menu
        MenuSystem.getInstance().println("0. Return");
        ArrayList<MenuBase> tmpMenuElements = new ArrayList<MenuBase>();
        int index = 0;
        MenuBase tmpMenuElement;
        for (int i = 0; i < menuElements.size(); i++) {
            tmpMenuElement = menuElements.get(i);
            if (!isNameIgnored(tmpMenuElement.getName())) {
            	MenuSystem.getInstance().print((index + 1) + ". ");
                tmpMenuElement.print();
                tmpMenuElements.add(tmpMenuElement);
                index++;
            }
        }

        return tmpMenuElements;
    }

    /**
     * Execute the menu which is selected by user
     */
    public void execute() {
        while (MenuSystem.socketLocal.get().getPs().checkError() == false) {
        	ArrayList<MenuBase> tmpMenuElements = printMenu();
            try {
                int response = getInt("Enter the number :", 0, tmpMenuElements.size());
                if (response == 0) {
                    return;
                }
                MenuBase menuElement = tmpMenuElements.get(response - 1);
                if (menuElement != null) {
                    menuElement.execute();
                }
            }
            catch (Exception e) {
                return;
            }
        }
    }

    public void print() {
    	MenuSystem.getInstance().println(name);
    }

    public void addEntry(MenuBase menuElement) {
        if (menuElement != null) {
            String menuElementName = menuElement.getName();
            if (!isSubMenuNameExisting(menuElementName)) {
                menuElements.add(menuElement);
            }
        }
    }

    public void delEntry(MenuBase menuElement) {
        if (menuElement != null) {
            menuElements.remove(menuElement);
        }
    }

    
    /**
     * whether there is already a sub-menu with same name
     */
    private boolean isSubMenuNameExisting(String subMenuName) {
        if (isNameIgnored(subMenuName)) {
            return false;
        }

        String tmpName;
        for (int i = 0; i < menuElements.size(); i++) {
            tmpName = menuElements.get(i).getName();
            if (!isNameIgnored(tmpName) && tmpName.equals(subMenuName)) {
                return true;
            }
        }

        return false;
    }

    private boolean isNameIgnored(String menuElementName) {
        return (menuElementName == null) || (menuElementName.trim().length() == 0);
    }
}


package cn.zongkuiy.menuconsole;

import java.net.Socket;

public class MenuTest {
	public static void main (String[] args) {
		new MenuTest();
	}
	
	public MenuTest() {
		
		Menu menu1 = MenuSystem.getInstance().addMenu("Menu Test 1", MenuSystem.getInstance().getRootMenu());
		Menu menu2 = MenuSystem.getInstance().addMenu("Menu Test 2", MenuSystem.getInstance().getRootMenu());
		Menu menu3 = MenuSystem.getInstance().addMenu("Menu Test Read", MenuSystem.getInstance().getRootMenu());
		
		MenuSystem.getInstance().addMenuEntry("Menu Entry 1A", this, "test1a", menu1);
		MenuSystem.getInstance().addMenuEntry("Menu Entry 1B", this, "test1b", menu1);
		
		MenuSystem.getInstance().addMenuEntry("Menu Entry 2A", this, "test2a", menu2);
		MenuSystem.getInstance().addMenuEntry("Menu Entry 2B", this, "test2b", menu2);
		
		MenuSystem.getInstance().addMenuEntry("Menu Entry Read", this, "testread", menu3);
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void test1a () {
		MenuSystem.getInstance().println("test1a");
	}
	public void test1b () {
		MenuSystem.getInstance().println("test1b");
	}
	public void test2a () {
		MenuSystem.getInstance().println("test2a");
	}
	public void test2b () {
		MenuSystem.getInstance().println("test2b");
	}
	
	public void testread() {
		MenuSystem.getInstance().println("please input your name");
		String content = MenuSystem.getInstance().read();
		MenuSystem.getInstance().println("hello, " + content);
	}
}

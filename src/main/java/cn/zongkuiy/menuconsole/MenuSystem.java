package cn.zongkuiy.menuconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The main thread of the menu console. It will <br>
 * - listen on given address and port <br>
 * - accept the socket from the command line <br> 
 * - display the menus defined <br>
 * - accept the input <br>
 * - execute the method selected through java reflection mechanism <br>
 * <br>
 * Usage: > telnet bindIp listenPort<br>
 * 
 * @author zongkuiy 10:10:45 AM May 21, 2013
 */
public class MenuSystem extends Thread {
	private static MenuSystem instance;

	private Menu rootMenu;
	private List<Socket> currSocket = new ArrayList<Socket>();
	private ServerSocket serverSocket;
	private boolean destroyFlag = false;

	private ConcurrentHashMap<String, Menu> menuMap = new ConcurrentHashMap<String, Menu>();

	private static int listenPort = 7000;
	private static String bindIp = "127.0.0.1";
	private static String title = "Debug Console";
	
	private static final String ROOT_MENU_ID = "i_am_root_menu";

	public static void setBindIp(String _bindIp) {
		bindIp = _bindIp;
	}

	public static void setListenPort(int _listenPort) {
		listenPort = _listenPort;
	}

	public static void setTitle(String _title) {
		title = _title;
	}

	/*
	 * Singleton access
	 */
	public static MenuSystem getInstance() {
		if (instance == null) {
			synchronized (MenuSystem.class) {
				if (instance == null) {
					instance = new MenuSystem();
					instance.start();
				}
			}
		}
		return instance;
	}

	private MenuSystem() {
		super();

		this.setName(title);

		rootMenu = new Menu(title);
		menuMap.put(ROOT_MENU_ID, rootMenu);
	}

	/**
	 * close the server socket and all client socket connected
	 */
	public void destroy() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
			if (currSocket != null && currSocket.size() > 0) {
				for (Socket s : currSocket) {
					s.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		destroyFlag = true;
	}

	public Menu getRootMenu() {
		return rootMenu;
	}

	public static ThreadLocal<SocketInfo> socketLocal = new ThreadLocal<SocketInfo>();
	
	private class DataThread implements Runnable {
		
		Socket socket;

		public DataThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			if (socket != null) {
				try {
					socketLocal.set(new SocketInfo(socket, new PrintStream(socket.getOutputStream()), socket.getInputStream()));

					MenuSystem.getInstance().println("==============================================");
					MenuSystem.getInstance().println("Current Online User Count: ");
					MenuSystem.getInstance().println("==============================================");
					
					rootMenu.execute();
					
					socket.close();

				} catch (Exception e) {
					try {
						socket.close();
					} catch (IOException e1) {
					}
				}
				return;
			}

		}
	}

	public void run() {
		try {
			InetAddress inetAddr = InetAddress.getByName(bindIp);
			serverSocket = new ServerSocket(listenPort, 1, inetAddr);

			while (!destroyFlag) {
				// Wait for connection from client.
				Socket socket = serverSocket.accept();
				currSocket.add(socket);

				// create new thread to handle the request from this socket
				new Thread(new DataThread(socket)).start();
			}

		} catch (IOException e) {
		}
	}



	public void println(String msg) {
		socketLocal.get().getPs().println(msg);
	}

	public void print(String msg) {
		socketLocal.get().getPs().print(msg);
	}

	/**
	 * read the input String from given socket
	 */
	public String read() {
		BufferedReader in = new BufferedReader(new InputStreamReader(socketLocal.get().getIs()));
		try {
			String readStr = in.readLine();
			return readStr;
		} catch (IOException e) {
			e.printStackTrace(socketLocal.get().getPs());
			return null;
		}
	}

	public Menu addMenu(String title, Menu parentMenu) {
		return new Menu(parentMenu, title);
	}
	
	public void addMenuEntry(String title, Object obj, String methodName, Menu parentMenu) {
		new MenuEntry(title, parentMenu, obj, methodName);
	}

}
class SocketInfo {
	Socket socket;
	PrintStream ps;
	InputStream is;
	
	public SocketInfo(Socket socket, PrintStream ps, InputStream is) {
		super();
		this.socket = socket;
		this.ps = ps;
		this.is = is;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public PrintStream getPs() {
		return ps;
	}
	public void setPs(PrintStream ps) {
		this.ps = ps;
	}
	public InputStream getIs() {
		return is;
	}
	public void setIs(InputStream is) {
		this.is = is;
	}
	
}

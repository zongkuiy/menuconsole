# menuconsole

Menuconsole provides the functionalities to using the telnet protocal to communicate with java applications during runtime.

### Usage

#### configuration

Before calling the getInstance()(the working thread starts once the getInstance called), you can specify the console title and the listening IP/Port using following method:

```
MenuSystem.setTitle(_title);
MenuSystem.setBindIp(_bindIp);
MenuSystem.setListenPort(_listenPort);
```

> the default title is "Debug Console"   
> the default listening IP/Port is 127.0.0.1/7000   

#### build the menu structure
>* addMenu create a node which has children   
Parameters:   
    - title - the display name of menu   
    - parentMenu - the parent menu   
Returns:   
    - the added menu   
>* addMenuEntry create a leaf node, and it can be used to execute corresponded method   
Parameters:   
    - title - the display name of menu   
    - obj - the target execution object   
    - methodName - the target execution method name in the object   
    - parentMenu - the parent menu  
* Example:
```
Menu menu1 = MenuSystem.getInstance().addMenu("Menu Test 1", MenuSystem.getInstance().getRootMenu());
Menu menu2 = MenuSystem.getInstance().addMenu("Menu Test 2", MenuSystem.getInstance().getRootMenu());
Menu menu3 = MenuSystem.getInstance().addMenu("Menu Test Read", MenuSystem.getInstance().getRootMenu());
		
MenuSystem.getInstance().addMenuEntry("Menu Entry 1A", this, "test1a", menu1);
MenuSystem.getInstance().addMenuEntry("Menu Entry 1B", this, "test1b", menu1);
		
MenuSystem.getInstance().addMenuEntry("Menu Entry 2A", this, "test2a", menu2);
MenuSystem.getInstance().addMenuEntry("Menu Entry 2B", this, "test2b", menu2);
		
MenuSystem.getInstance().addMenuEntry("Menu Entry Read", this, "testread", menu3);
```

#### define the corresponded public method in corresponded class 
```
public void test2b () {
		MenuSystem.getInstance().println("test2b");
	}
	
	public void testread() {
    // println method will print the message on the console
		MenuSystem.getInstance().println("please input your name");
    
    // read method will read the input content from the console
		String content = MenuSystem.getInstance().read();
		MenuSystem.getInstance().println("hello, " + content);
	}
```

#### connect to console through the telnet function
```
root@yzk:~# telnet localhost 7000
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
==============================================

Debug Console
-------------
0. Return
1. Menu Test 1
2. Menu Test 2
3. Menu Test Read
Enter the number :
3

Debug Console >> Menu Test Read
--------------
0. Return
1. Menu Entry Read
Enter the number :
1
please input your name
zongkuiy
hello, zongkuiy

Debug Console >> Menu Test Read
--------------
0. Return
1. Menu Entry Read
Enter the number :
0

Debug Console
-------------
0. Return
1. Menu Test 1
2. Menu Test 2
3. Menu Test Read
Enter the number :
1

Debug Console >> Menu Test 1
-----------
0. Return
1. Menu Entry 1A
2. Menu Entry 1B
Enter the number :
1
test1a

Debug Console >> Menu Test 1
-----------
0. Return
1. Menu Entry 1A
2. Menu Entry 1B
Enter the number :
```

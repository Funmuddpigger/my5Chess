package server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author: 李检辉
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:用于定义服务器要用到的一些常量及相关变量
 * @Project: 网络编程技术
 * @Copyright: All rights reserved
 */

public class ServerConfig {
	public final static SocketAddress SERVER_ADDR 
		= new InetSocketAddress("172.16.160.96", 6000);//服务器开启的UDP端口
	//public final static int CLIENT_LISTEN_PORT_DEFAULT = 5000;
	public static HashMap<String, String> watchMap
	        = new HashMap<String, String>();
	
	static HashMap<String, User> userMap = new HashMap<String, User>();// 用于存放用户信息
	
	public static HashMap<String, Player> hostMap
			= new HashMap<String, Player>();//用于存放擂主信息
	static HashMap<String, Player> playerMap
			= new HashMap<String, Player>();//用于存放所有玩家的信息
	
	public static void addWatch(String str, String name){
		watchMap.put(str, name);
	}
	public static boolean containWatch(String name){
		return watchMap.containsKey(name);
	}
	
	public static void addHost(String name, Player player){
		hostMap.put(name, player);
	}

	public static boolean containHost(String name){
		return hostMap.containsKey(name);
	}
	public static HashMap<String, Player> getPlayerMap(){
		return playerMap;
	}
	public static boolean containPlayer(String name){
		return playerMap.containsKey(name);
	}
	public static Player getPlayer(String name){
		return playerMap.get(name);
	}
	public static void addPlayer(String name, Player player){
		playerMap.put(name, player);
	}

	public static void addUser(User user) {
		userMap.put(user.getUserName(), user);
	}
	public static User getUser(String name){
		return userMap.get(name);
	}
	public static boolean containUser(String name) {
		return userMap.containsKey(name);
	}
	
	public static String getUserNamesList() {
		String str = "";
		Iterator<String> it = userMap.keySet().iterator();
		while (it.hasNext()) {
			str = str + it.next() + ":";
		}
		System.out.println("get:" + str);
		return str;
	}
	

	public static String getHostNamesList(){
		String str = "";	
		Iterator<String> it = hostMap.keySet().iterator();	
		while(it.hasNext()){
			str = str  + it.next() + ":";		
		}
		System.out.println("get:"+str);
		return str;
	}
	
	public static String getWatchNamesList(){
		String str = "";	
		Iterator<String> it = watchMap.keySet().iterator();	
		while(it.hasNext()){
			str = str  + it.next() + ":";		
		}
		System.out.println("get:"+str);
		return str;
	}
}

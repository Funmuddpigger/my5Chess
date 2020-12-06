package server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author: ����
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:���ڶ��������Ҫ�õ���һЩ��������ر���
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */

public class ServerConfig {
	public final static SocketAddress SERVER_ADDR 
		= new InetSocketAddress("172.16.160.96", 6000);//������������UDP�˿�
	//public final static int CLIENT_LISTEN_PORT_DEFAULT = 5000;
	public static HashMap<String, String> watchMap
	        = new HashMap<String, String>();
	
	static HashMap<String, User> userMap = new HashMap<String, User>();// ���ڴ���û���Ϣ
	
	public static HashMap<String, Player> hostMap
			= new HashMap<String, Player>();//���ڴ��������Ϣ
	static HashMap<String, Player> playerMap
			= new HashMap<String, Player>();//���ڴ��������ҵ���Ϣ
	
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

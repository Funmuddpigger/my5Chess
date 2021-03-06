package server;

import java.io.Serializable;
import java.net.SocketAddress;

import javafx.scene.paint.Color;
/**
 * @Author: 李检辉
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:用于封装玩家信息
 * @Project: 网络编程技术
 * @Copyright: All rights reserved
 */
public class Player implements Serializable{
	String name;//玩家名字
	SocketAddress address;//玩家的socket地址
	int color;//玩家的棋子颜色

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Player(String name, SocketAddress address){
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public SocketAddress getAddress()  {
		return address;
	}
	public void setAddress(SocketAddress address) {
		this.address = address;
	}
	public String toString(){
		return "[" + name+"," + address + "]";
	}
}

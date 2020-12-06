package server;

import java.io.Serializable;
import java.net.SocketAddress;

public class User implements Serializable{
	String userName; 
	String password;
	SocketAddress address;
	public User(String userName,String password,SocketAddress address){
		this.userName = userName;
		this.password = password;
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAddress(SocketAddress address) {
		this.address = address;
	}
	public SocketAddress getAddress() {
		return address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString(){
		return "[" + userName + ", " + password + ","+address+"]";
	}
}

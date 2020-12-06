package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * @Author: 李检辉
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:用于封装信息
 * @Project: 网络编程技术
 * @Copyright: All rights reserved
 */
public class Message implements Serializable{
	int msgType,msgType1,msgType2,msgType3,msgType5;//信息类型，参看类MessageType中的定义
	int status;//信息的状态：分为成功和失败
	Object content;//信息的内容
	Object content1,content2,content3,msg_content;
	Player fromPlayer;//信息的发送方
	Player toPlayer;//信息的接收方
	
	public Player getFromPlayer() {
		return fromPlayer;
	}
	public void setFromPlayer(Player fromPlayer) {
		this.fromPlayer = fromPlayer;
	}
	public Player getToPlayer() {
		return toPlayer;
	}
	public void setToPlayer(Player toPlayer) {
		this.toPlayer = toPlayer;
	}	
	public int getMsgType() {
		return msgType;
	}
	public int getMsgType1() {
		return msgType1;
	}
	public int getMsgType2() {
		return msgType2;
	}
	public int getMsgType3() {
		return msgType3;
	}
	public int getMsgType5() {
		return msgType5;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public void setMsgType1(int msgType) {
		this.msgType1 = msgType;
	}
	public void setMsgType2(int msgType) {
		this.msgType2 = msgType;
	}
	public void setMsgType3(int msgType) {
		this.msgType3 = msgType;
	}
	public void setMsgType5(int msgType) {
		this.msgType5 = msgType;
	}
	public void setMsgContent(Object msg_content) {
		this.msg_content = msg_content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Object getContent() {
		return content;
	}
	public Object getContent1() {
		return content1;
	}
	public Object getContent2() {
		return content2;
	}
	public Object getContent3() {
		return content3;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public void setContent1(Object content) {
		this.content1 = content;
	}
	public void setContent2(Object content) {
		this.content2 = content;
	}
	public void setContent3(Object content) {
		this.content3 = content;
	}
	public String toString(){
		return "["+msgType +"," + status + ", " + fromPlayer + ", " + toPlayer + ", " + content;
	}
	public Object getMsgContent() {
		return msg_content;
	}

	
	/**
	 * 将信息Message的对象转换成字节数组（传递给DatagramPacket），用于socket通信
	 * @param obj
	 * @return
	 */
	public static byte[] convertToBytes(Message obj){
		try(
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream ois = new ObjectOutputStream(baos);					
			) {
			ois.writeObject(obj);
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
    
	/**
	 * 将字节数组（从DatagramPacket中获取）转换成Message的对象。
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Message convertToObj(byte[] bytes,int offset,int length){
		try (
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);		
		){
			return (Message) ois.readObject();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;	
	}
	
}

package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import client.ClientConfig;
import client.Competition;


/**
 * @Author: ����
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:������������Ϸ������
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */
public class Chess5Server {
	
	/**
	 * @param args
	 */
	String gamer;String str1;Competition competition = new Competition();String vs;SocketAddress wat;SocketAddress to0;SocketAddress to1;String[] vsv;
	public List<Player> playerList = new ArrayList<Player>();
	public List<String> stringList = new ArrayList<String>();
	public List<String> nameList = new ArrayList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chess5Server cs = new Chess5Server();
		cs.service();
	}
	/*public Chess5Server(){
		init();
	}
	public void init(){	
		String [] hostStrings = {"�����","�������","���Ŵ�ѩ","����С��","����",};
				//"�����ɾ�","С��ɵ�","���Ƕ�","��һ��","ˮ�Ϸ�","��������","����ɱ��","������"};		
		for(String str : hostStrings){
			Player player = new Player(str, new InetSocketAddress("localhost", 5000));
			ServerConfig.addHost(str, player);
			System.out.println(str);
		}
	}*/
	
	public void service() {
		Chess5ServerThread t = new Chess5ServerThread();
		t.start();
	}
	
	class Chess5ServerThread extends Thread{
		public void run() {
	//public void service (){
		
	   try(DatagramSocket socket = new DatagramSocket(ServerConfig.SERVER_ADDR);
		){
	    while(true){
	      try{
			DatagramPacket pin = new DatagramPacket(new byte[1024], 1024);
			socket.receive(pin);
			Message msg = (Message) Message.convertToObj(pin.getData(),0,pin.getLength());
			Message rmsg = new Message();
			/**
			 * 1.����ǡ�JOIN_GAME������ʾ���û�Ҫ������Ϸ�������ǳ��Ƿ��Ѿ����ڣ�������ڣ���ܾ�������ǰ�������������ظ��¼�����ң�
			 * 2.����ǡ�JOIN_HOST������ʾ�����Ҫ������̨�������Ƿ��Ѿ����룬����ǣ���ܾ�������ǰ�������������ظ��¼�����̨����ң�
			 * 3.����ǡ�CHALLENGE������ʾ����ҷ�����һ����ս���������󣬽�������ת������Ӧ��������
			 * 4.����ǡ�CHALLENGE_RSP������ʾ�б���ս�������Ļظ�����ս���󣬽��ûظ�ת����������ս����ң�
			 * 5.����ǡ�PLAY������ʾ���ڱ����е�һ���������ӣ�����һ��������߶Է����ӵ���Ϣ��������ת������Ϣ����һ����
			 */
			if(msg.getMsgType()==MessageType.JOIN_GAME){
				String str = (String)msg.getContent();
    			if(!ServerConfig.containPlayer(str)){
    				SocketAddress aaa = new InetSocketAddress(pin.getAddress(),pin.getPort());
    				Player player = new Player(str,aaa);
    				System.out.println("This is a new player:" + player.getName());
    				ServerConfig.addPlayer(str,player);
    				msg.setMsgType(MessageType.JOIN_GAME);
    				msg.setContent(ServerConfig.getHostNamesList());
    				
    				playerList.add(player);
    				nameList.add(str);
    				for(int i=0; i<playerList.size(); i++) {//һ�����˼�����Ϸ��Ⱥ�����߶�ս�б�
    					Player toplayer = playerList.get(i);
    					SocketAddress to = toplayer.getAddress();
    					rmsg.setMsgType1(MessageType.WATCH0);
    					rmsg.setContent1(stringList);
    					byte[] Bytes = Message.convertToBytes(rmsg);
    					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
    					socket.send(pout);
    				}
    				
    				for(int i=0; i<playerList.size(); i++) {//һ�����˼�����Ϸ��Ⱥ����������б�
    					Player toplayer = playerList.get(i);
    					SocketAddress to = toplayer.getAddress();
    					
    					rmsg.setMsgType(MessageType.ChatAll);
						String mess = "-----" + str + "������Ϸ" + "-----";
						rmsg.setMsgContent(mess);
						
    					rmsg.setMsgType1(MessageType.playerList);
    					rmsg.setContent1(nameList);
    					byte[] Bytes = Message.convertToBytes(rmsg);
    					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
    					socket.send(pout);
    				}
    				
    				
    				byte[] Bytes = Message.convertToBytes(msg);
					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,pin.getAddress(),pin.getPort());
					socket.send(pout);

    			} 
    			else {
    				    msg.setMsgType(MessageType.FAIL);
    				    byte[] Bytes = Message.convertToBytes(msg);
    					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,pin.getAddress(),pin.getPort());
    					socket.send(pout);
    		         }

			} 
			
			
			else if(msg.getMsgType()== MessageType.JOIN_HOST){
				String str = (String)msg.getContent();
				if(!ServerConfig.containHost(str)){
					ServerConfig.addHost(str,ServerConfig.getPlayer(str));
					msg.setMsgType(MessageType.JOIN_HOST);
					
					byte[] Bytes = Message.convertToBytes(msg);
					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,pin.getAddress(),pin.getPort());
					socket.send(pout);
					
				}
				else {
				    msg.setMsgType(MessageType.FAIL);
				    byte[] Bytes = Message.convertToBytes(msg);
					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,pin.getAddress(),pin.getPort());
					socket.send(pout);
		         }

			} 
			
			
			else if(msg.getMsgType()== MessageType.UPDATE_HOST){
				msg.setMsgType(MessageType.UPDATE_HOST);
				msg.setContent1(ServerConfig.getHostNamesList());
				
				byte[] Bytes = Message.convertToBytes(msg);
				DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,pin.getAddress(),pin.getPort());
				socket.send(pout);

			} 
			
			
			else if(msg.getMsgType() == MessageType.CHALLENGE){
				str1 = (String)msg.getContent();//�� ��ս��
				gamer = (String)msg.getContent1();//��ս��
					Player toplayer = ServerConfig.hostMap.get(str1);
					Player fromplayer = ServerConfig.hostMap.get(gamer);
					SocketAddress to = toplayer.getAddress();            //��̫���ĵط�
					if(msg.getMsgType1() == MessageType.WHITE) {
						String str = "��������";
						msg.setContent3(str);
					}
					else if(msg.getMsgType1() == MessageType.BLACK) {
						String str = "���Ӻ���";
						msg.setContent3(str);
					}
					msg.setMsgType1(msg.getMsgType1());
					msg.setMsgType(MessageType.M);
					msg.setContent(fromplayer);//00
					msg.setContent1(toplayer);//01
					msg.setContent2(gamer);
					byte[] Bytes = Message.convertToBytes(msg);		
					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
					socket.send(pout);
			} 
			
			
			else if(msg.getMsgType() == MessageType.CHALLENGE_RSP0){
				Player fromplayer = ServerConfig.hostMap.get(str1);
				Player toplayer = ServerConfig.hostMap.get(gamer);
				SocketAddress to = toplayer.getAddress();            //��̫���ĵط�
				msg.setMsgType(MessageType.CHALLENGE_RSP0);
				msg.setContent(toplayer);
				msg.setContent1(fromplayer);
				byte[] Bytes = Message.convertToBytes(msg);		
				DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
				socket.send(pout);
			}
			
			
			
			else if(msg.getMsgType() == MessageType.CHALLENGE_RSP1){
				Player toplayer = ServerConfig.hostMap.get(gamer);
				SocketAddress to = toplayer.getAddress();            //��̫���ĵط�
				msg.setMsgType(MessageType.CHALLENGE_RSP1);
				byte[] Bytes = Message.convertToBytes(msg);		
				DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
				socket.send(pout);
			} 
			
			
			
			else if(msg.getMsgType()== MessageType.PLAY){
				String str = (String) msg.getContent();
				msg.getFromPlayer();//���ͷ�
				msg.getToPlayer();//���շ�
				Player fromplayer = msg.getFromPlayer();
				Player toplayer = msg.getToPlayer();
				SocketAddress from = fromplayer.getAddress();
				SocketAddress to = toplayer.getAddress();
				msg.setMsgType(MessageType.PLAY);
				msg.setMsgType3(msg.getMsgType3());
				msg.setContent(str);
				byte[] Bytes = Message.convertToBytes(msg);		
				DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
				socket.send(pout);
				
				
				String str1 = (String) msg.getContent3();
				if(str1.equals(vsv[0])) {//�ж���������ǲ��ǹ�ս�����ս���Ǹ���
					rmsg.setMsgType2(MessageType.WATCH4);
					rmsg.setMsgType3(msg.getMsgType3());//��Ӯ�ź�
					rmsg.setContent(wat);//��ս�˵�ַ����˫��
					rmsg.setContent2(msg.getContent2());
					byte[] Bytes1 = Message.convertToBytes(rmsg);		
					DatagramPacket pout1  = new DatagramPacket(Bytes1,Bytes1.length,from);
					socket.send(pout1);
				}
				else if(str1.equals(vsv[1])) {
					rmsg.setMsgType2(MessageType.WATCH4);
					rmsg.setMsgType3(msg.getMsgType3());
					rmsg.setContent(wat);
					rmsg.setContent2(msg.getContent2());
					byte[] Bytes1 = Message.convertToBytes(rmsg);		
					DatagramPacket pout1  = new DatagramPacket(Bytes1,Bytes1.length,from);
					socket.send(pout1);
				}
	
			}
			
			
			
			if(msg.getMsgType1()== MessageType.WATCH0){
				vs = str1 + "vs" + gamer;
				stringList.add(vs);
				for(int i=0; i<playerList.size(); i++) {//һ�����˼�����Ϸ��Ⱥ�����߶�ս�б�
					Player toplayer = playerList.get(i);
					SocketAddress to = toplayer.getAddress();
					rmsg.setMsgType1(MessageType.WATCH0);
					rmsg.setContent1(stringList);
					byte[] Bytes = Message.convertToBytes(rmsg);
					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
					socket.send(pout);
				}
			}
			
			
			else if(msg.getMsgType()== MessageType.WATCH1){
				String str = (String) msg.getContent3();
				vsv = str.split("vs");
				String watch = (String) msg.getContent2();
				Player watc = ServerConfig.playerMap.get(watch);
				Player toplayer0 = ServerConfig.playerMap.get(vsv[0]);
				Player toplayer1 = ServerConfig.playerMap.get(vsv[1]);
				wat = watc.getAddress();
				SocketAddress to0 = toplayer0.getAddress();
				SocketAddress to1 = toplayer1.getAddress();
				
				rmsg.setMsgType(MessageType.WATCH1);
				rmsg.setContent(wat);
				byte[] Bytes = Message.convertToBytes(rmsg);		
				DatagramPacket pout0  = new DatagramPacket(Bytes,Bytes.length,to0);
				socket.send(pout0);
				DatagramPacket pout1  = new DatagramPacket(Bytes,Bytes.length,to1);
				socket.send(pout1);
				
				Message wmsg = new Message();
				wmsg.setMsgType2(MessageType.WATCH6);//�Ѷ�ս���˵ĵ�ַ������ս��
				wmsg.setContent1(to0);
				wmsg.setContent2(to1);
				byte[] WBytes = Message.convertToBytes(wmsg);
				DatagramPacket watchpout  = new DatagramPacket(WBytes,WBytes.length,wat);
				socket.send(watchpout);
			}
			
			
			
			else if(msg.getMsgType5()== MessageType.WATCH5){//����˳���ս�����ս�ߵĹ�ս�������
				vsv = null;
			}
			
			
			else if(msg.getMsgType3()== MessageType.WHITE|msg.getMsgType3()== MessageType.BLACK){//����������ͨ���ж����Ƴ�ս��
				String str = (String) msg.getContent3();
				for(int i=0;i<stringList.size();i++) {
					String s = stringList.get(i);
					String[] ss = s.split("vs");
					if(str.equals(ss[0])|str.equals(ss[1])) {
						stringList.remove(i);
						for(int n=0; n<playerList.size(); n++) {
	    					Player toplayer = playerList.get(n);
	    					SocketAddress to = toplayer.getAddress();
	    					rmsg.setMsgType1(MessageType.WATCH0);
	    					rmsg.setContent1(stringList);
	    					byte[] Bytes = Message.convertToBytes(rmsg);
	    					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
	    					socket.send(pout);
	    				}
					}
				}
			}
			
			
			
			else if (msg.getMsgType()== MessageType.A) {
				  User user = (User)msg.getContent();
				if(!ServerConfig.containUser(user.getUserName())) {
					ServerConfig.addUser(user);
					msg.setMsgType(MessageType.B);
					SocketAddress a2 = user.getAddress();
					byte[] msgBytes = Message.convertToBytes(msg);
					DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, a2);
					socket.send(pout);
					
				}
				else {
					   
						     msg.setMsgType(MessageType.D);
						     SocketAddress a3 = user.getAddress();
							 byte[] msgBytes = Message.convertToBytes(msg);
							 DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, a3);
							 socket.send(pout);
					   
				}
				
			}
			
			
			else if(msg.getMsgType()== MessageType.P) {
				User user = (User)msg.getContent();
				User b = ServerConfig.userMap.get(user.getUserName());
				
				
				if(!ServerConfig.containUser(user.getUserName())) { 
					 msg.setMsgType(MessageType.D);
					   SocketAddress a2 = user.getAddress();
						byte[] msgBytes = Message.convertToBytes(msg);
						DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, a2);
						socket.send(pout);
		    	}
				else if (user.getPassword().equals(b.getPassword())) {
					   msg.setMsgType(MessageType.C);
					   SocketAddress a2 = user.getAddress();
						byte[] msgBytes = Message.convertToBytes(msg);
						DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, a2);
						socket.send(pout);
				   }
				   else  {   
					   msg.setMsgType(MessageType.D);
					   SocketAddress a2 = user.getAddress();
						byte[] msgBytes = Message.convertToBytes(msg);
						DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, a2);
						socket.send(pout);
				  }
			}
			
			
			else if (msg.getMsgType() == MessageType.ChatAll) {
				String chatmsg = (String) msg.getMsgContent();
				
				for(int i=0; i<playerList.size(); i++) {
					msg.setMsgContent(chatmsg);
					msg.setMsgType(MessageType.ChatAll);
					Player toplayer = playerList.get(i);
					SocketAddress to = toplayer.getAddress();
					byte[] Bytes = Message.convertToBytes(msg);
					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
					socket.send(pout);
				}
				
			}
			
			
			else if(msg.getMsgType()==MessageType.ChatPrivate) {
				String str = (String) msg.getContent1();
				
				Player toplayer = ServerConfig.playerMap.get(str);
				SocketAddress to = toplayer.getAddress();
				SocketAddress u = toplayer.getAddress();
				String chatmsg = (String)msg.getContent();
				msg.setContent(chatmsg);
				msg.setMsgType(MessageType.ChatPrivate);
				
				
				byte[] msgBytes = Message.convertToBytes(msg);
				DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, u);
				socket.send(pout);
			}
			
			
			else if(msg.getMsgType()==MessageType.EXIT) {
				String str = (String) msg.getContent();
				Player exitplayer = ServerConfig.playerMap.get(str);
				for(int i=0;i<nameList.size();i++) {
					String s = nameList.get(i);
					if(str.equals(s)) {
						nameList.remove(i);
						for(int j=0;j<playerList.size();j++) {
							Player removeplayer = playerList.get(j);
							if(exitplayer.equals(removeplayer)) {
								playerList.remove(i);
								ServerConfig.playerMap.remove(exitplayer);
								ServerConfig.hostMap.remove(exitplayer);
								
						       for(int n=0; n<playerList.size(); n++) {
		    					Player toplayer = playerList.get(n);
		    					SocketAddress to = toplayer.getAddress();
		    					
		    					rmsg.setMsgType(MessageType.ChatAll);
								String mess = "~~~" + str + "�˳���Ϸ" + "~~~";
								rmsg.setMsgContent(mess);
		    					
		    					rmsg.setMsgType1(MessageType.playerList);
		    					rmsg.setContent1(nameList);
		    					byte[] Bytes = Message.convertToBytes(rmsg);
		    					DatagramPacket pout  = new DatagramPacket(Bytes,Bytes.length,to);
		    					socket.send(pout);
		    				}
						  }
					   }
					}
				}
			}
	      }catch(Exception e) {
				// TODO: handle exception
		  }
	    } //while
	    
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	//}
		}
	}

}

package client;



import java.net.DatagramPacket;
import java.net.DatagramSocket;


import server.Message;
import server.MessageType;

import server.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

 

/**
 * @Author: ����
 * @Date: 2019/11/20
 * @version V1.0
 * @Description:�û���¼��ע����洰��:����SSL Socketͨ��,��¼��ע��ɹ����ٵ�������������
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */
public class MyInitGUI extends Application {
	
	Button btn_regist, btn_login;
	TextField tf_name;
	PasswordField pf;
	Stage initStage;
	String regist,login,number;
	DatagramSocket s ;
	@Override
	public void start(Stage stage) throws Exception {
		initStage = stage;
		// TODO Auto-generated method stub
		Label lb_name = new Label("�û�:");
		tf_name = new TextField();
		
		Label lb_passwd = new Label("���룺");
		pf = new PasswordField();
		
		btn_regist = new Button("ע��");
		btn_regist.setOnAction(event -> {
			try {
				btnRegistHandler(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		btn_login = new Button("��¼");
		btn_login.setOnAction(event -> {
			try {
				btnLoginHandler(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		GridPane gPane = new GridPane();
		gPane.setAlignment(Pos.CENTER);
		gPane.setPrefWidth(300);
		gPane.setPrefHeight(200);
		gPane.setVgap(30);
		gPane.setHgap(20);
		gPane.addRow(0, lb_name,tf_name);
		gPane.addRow(1, lb_passwd,pf);
		gPane.addRow(2, btn_regist,btn_login);
						
		Scene scene = new Scene(gPane);
		stage.setTitle("�򵥵������������1707");
		stage.setScene(scene);
		stage.show();
		s = new DatagramSocket(ClientConfig.LOCAL_ADDR);
	}
	public void btnRegistHandler(ActionEvent e) throws Exception{
             regist = tf_name.getText();
             number = pf.getText();
             User user = new User(regist,number,ClientConfig.LOCAL_ADDR);
             Message msg = new Message();
			 msg.setMsgType(MessageType.A);
			 msg.setContent(user);
		     byte[] m = Message.convertToBytes(msg);
		     DatagramPacket pout = new DatagramPacket(m,m.length,ClientConfig.SERVER_ADDR);
		     s.send(pout);
		     
		     DatagramPacket pin = new DatagramPacket(new byte[1024], 1024);
			 s.receive(pin);
			 Message msg1 = (Message) Message.convertToObj(pin.getData(), 0, pin.getLength());
			 if(msg1.getMsgType()==MessageType.B) {
				// new Chess5GUI();//�����촰��
			//	initStage.close();//�ر�ע���¼����
				Chess5Utils.showAlert("ע��ɹ�");
			 }else if ( msg1.getMsgType()==MessageType.D){
				 System.out.println("aaa");
				 Chess5Utils.showAlert("ע��ʧ��,�û��Ѵ���");
			 }
			 
	}
	public void btnLoginHandler(ActionEvent e) throws Exception{
		  regist = tf_name.getText();
	    	 number = pf.getText();
		
         Message msg = new Message();
		 msg.setMsgType(MessageType.P);
		 User user = new User(regist,number,ClientConfig.LOCAL_ADDR);
		 msg.setContent(user);
		 System.out.println(user);
	     byte[] m = Message.convertToBytes(msg);
	     DatagramPacket pout = new DatagramPacket(m,m.length,ClientConfig.SERVER_ADDR);
	     s.send(pout);
	     DatagramPacket pin = new DatagramPacket(new byte[1024], 1024);
		 s.receive(pin);
		 Message msg1 = (Message) Message.convertToObj(pin.getData(), 0, pin.getLength());
		 
		 if(msg1.getMsgType()==MessageType.C) {
			 s.close();
			 new Chess5GUI();//�����촰��
			initStage.close();//�ر�ע���¼����
			Chess5Utils.showAlert("��¼�ɹ�");
		 }else if ( msg1.getMsgType()==MessageType.D){
			 
			 
			 
			 
			 Chess5Utils.showAlert("��¼ʧ��,������� / �û�������");
		 }
		 
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}

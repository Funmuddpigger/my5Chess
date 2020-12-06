package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import server.Message;
import server.MessageType;
import server.Player;
import server.Point;
import server.ServerConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @Author: 李检辉
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:五子棋在线游戏用户界面主窗口
 * @Project: 网络编程技术
 * @Copyright: All rights reserved
 */



public class Chess5GUI extends Application {

	Chess5Pane pane;// 五子棋盘
	Label lbl_name,lbl_name_use,lbl_null,lbl_null1;// 用于显示玩家信息
	ChoiceBox<String> cBox, cBox1, cBox2;// 用于擂主列表
	Button btn_join_game,btn_join_host,btn_update_host,btn_challenge,btn_watch,btn_exitwatch,btn_reject,btn_clear,btn_exit;
	static RadioButton rbtn_white;
	static RadioButton rbtn_black;
	Player player;
	static String gamer;
	DatagramSocket s;SocketAddress s1,s2;SocketAddress sss;
	Competition competition = new Competition();
	TextField tf_msg, tf_addr;
	TextArea ta_info;
	TextField ta_info3;
	
    public Chess5GUI() throws Exception {
    	
 		start(new Stage());
 	
 }
	@Override
	
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		pane = new Chess5Pane();
		pane.setMouseTransparent(true);// 屏蔽棋盘上鼠标事件

		FlowPane fPane_01 = new FlowPane();
		fPane_01.setAlignment(Pos.BOTTOM_CENTER);
		fPane_01.setMinHeight(70);
		fPane_01.setHgap(10);
		for (int n = 0; n < 5; n++) {
			Circle c = new Circle(20 - n * 2, Color.BLACK);
			fPane_01.getChildren().add(c);
		}
		
		Button btn_join_game = new Button("加入游戏");
		btn_join_game.setOnAction(this::btnJoinGameHandler);
		fPane_01.getChildren().add(btn_join_game);
		
		for (int n = 0; n < 5; n++) {
			Circle c = new Circle(12 + n * 2, Color.WHITE);
			fPane_01.getChildren().add(c);
		}
		
		tf_msg = new TextField();
		tf_msg.setPrefColumnCount(20);
		Button btn_send = new Button("   发送  ");
		btn_send.setPrefSize(75, 30);
		btn_send.setOnAction(this::btnSendHandler);
		
		//GridPane pane_com = new GridPane();
		ta_info3 = new TextField();
		ta_info3.setPrefSize(140, 30);
		//ta_info3.getMaxHeight();
		ScrollPane pane_info3 = new ScrollPane(ta_info3);		
		pane_info3.setFitToWidth(true);		
		TitledPane pane_03 = new TitledPane("Chat",pane_info3);
		pane_03.setCollapsible(false);
		
		ta_info = new TextArea();
		ta_info.setPrefSize(280, 545);
		ta_info.getMaxHeight();
		ScrollPane pane_info = new ScrollPane(ta_info);		
		pane_info.setFitToWidth(true);
		TitledPane pane_02 = new TitledPane("群聊",pane_info);
		pane_02.setCollapsible(false);
		
	
		Button btn_private = new Button(" 私聊  ");
		btn_private.setPrefSize(75, 30);
		btn_private.setOnAction(this::btnPrivateHandler);

		HBox hb = new HBox();
		hb.getChildren().addAll(ta_info3, btn_send, btn_private);
		hb.setSpacing(0);
		
		
		

		btn_join_host = new Button("加入擂台");
		btn_join_host.setDisable(false);
		btn_join_host.setOnAction(this::btnJoinHostHandler);

		btn_update_host = new Button("更新擂台");
		btn_update_host.setOnAction(this::btnUpdateHostHandler);

		cBox = new ChoiceBox<String>();
		cBox1 = new ChoiceBox<String>();
		cBox2 = new ChoiceBox<String>();

		btn_challenge = new Button("挑战");
		btn_challenge.setDisable(false);
		btn_challenge.setOnAction(this::btnChallengeHandler);

		ToggleGroup tg = new ToggleGroup();
		rbtn_white = new RadioButton("白子先手");
		rbtn_white.setToggleGroup(tg);
		//rbtn_white.setSelected(true);
		rbtn_black = new RadioButton("黑子后手");
		rbtn_black.setToggleGroup(tg);
		
		btn_reject = new Button("悔棋");
		btn_reject.setDisable(false);
		//btn_reject.setOnAction(this::btnRejectHandler);
		
		btn_watch = new Button("观战");
		btn_watch.setDisable(false);
		btn_watch.setOnAction(this::btnWatchHandler);
		
		btn_exitwatch = new Button("退出观战");
		btn_exitwatch.setDisable(false);
		btn_exitwatch.setOnAction(this::btnExitWatchHandler);
		
		btn_clear = new Button("清盘");
		btn_clear.setDisable(false);
		btn_clear.setOnAction(this::btnClearHandler);
		
		btn_exit = new Button("退出游戏");
		btn_exit.setDisable(false);
		btn_exit.setOnAction(this::btnExitHandler);

		FlowPane fPane_02 = new FlowPane(btn_join_host, btn_update_host, cBox, btn_challenge, rbtn_white, rbtn_black, btn_watch, cBox1, btn_exitwatch, btn_exit);
		fPane_02.setAlignment(Pos.CENTER);
		fPane_02.setHgap(40);

		lbl_name = new Label("我是一个匆匆过客......");
		
		lbl_null = new Label("                                                                                                        ");
		
		FlowPane fPane_03 = new FlowPane(lbl_name);
		fPane_03.setAlignment(Pos.CENTER);

		FlowPane fPane_05 = new FlowPane(btn_clear, btn_reject, lbl_null);
		fPane_05.setAlignment(Pos.CENTER);
		fPane_05.setHgap(40);

		lbl_name_use = new Label("当前在线人列表： ");
		VBox box2 = new VBox();
		box2.getChildren().add(lbl_name_use);
		box2.getChildren().add(cBox2);
		for (int n = 0; n < 10; n++) {
			
			Circle c = new Circle(12 + n * 2, Color.WHITE);
			box2.getChildren().add(c);			
			box2.setSpacing(10);
			
		}
		box2.setAlignment(Pos.CENTER);
		
		VBox box1 = new VBox(pane_02, hb);
		FlowPane fPane_04 = new FlowPane(pane, box1,box2);
		fPane_04.setAlignment(Pos.CENTER);
		fPane_04.setHgap(40);
		
		VBox box = new VBox(fPane_01, fPane_02, fPane_03, fPane_04, fPane_05);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		box.setStyle("-fx-border-color:DarkCyan;-fx-background-color:DarkCyan;-fx-border-width:5");

		Scene scene = new Scene(box, 1100, 900);
		stage.setScene(scene);
		stage.setTitle("五子棋");
		stage.show();
		
		s = new DatagramSocket(ClientConfig.LOCAL_ADDR);
		new Chess5Thread(s).start();
				
	}

	/**
	 * 当玩家点击“加入游戏”按钮后，会响应该方法进行处理：会弹出对话框输入玩家的昵称；发送加入游戏的信息给服务器， 并等待服务器回复确认，同时更新擂台列表信息
	 * 
	 * @param e
	 */
	public void btnJoinGameHandler(ActionEvent e) {
		// pane.setMouseTransparent(false);
		TextInputDialog dialog = new TextInputDialog();
		Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		dialog.setTitle("Join the Game,plss enter your nickname");
		dialog.setHeaderText(null);
		dialog.setContentText("input your nickname: ");
			
			// s.connect(serverAddr);
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					Message msg = new Message();
					gamer = dialog.getEditor().getText();
					msg.setContent(gamer);
					System.out.println("");
					msg.setMsgType(MessageType.JOIN_GAME);
					byte[] msgBytes = Message.convertToBytes(msg);
					DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length, ClientConfig.SERVER_ADDR);
					s.send(pout);

				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		dialog.show();

	}

	public void btnJoinHostHandler(ActionEvent e) {
		// pane.setMouseTransparent(false);
		try {
			Message msg = new Message();
			msg.setMsgType(MessageType.JOIN_HOST);
			msg.setContent(gamer);
			byte[] Bytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(Bytes, Bytes.length, ClientConfig.SERVER_ADDR);
			s.send(pout);

		} catch (Exception e1) {

		}
	}

	public void btnUpdateHostHandler(ActionEvent e) {
		try {
			Message msg = new Message();
			msg.setMsgType(MessageType.UPDATE_HOST);
			byte[] Bytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(Bytes, Bytes.length, ClientConfig.SERVER_ADDR);
			s.send(pout);
			
		} catch (Exception e1) {

		}
	}

	public void btnChallengeHandler(ActionEvent e) {
		try {
			Message msg = new Message();
			if (rbtn_white.isSelected() == true) {
				msg.setMsgType1(MessageType.WHITE);
			}
			else if(rbtn_black.isSelected() == true) {
				msg.setMsgType1(MessageType.BLACK);
			}
			msg.setMsgType(MessageType.CHALLENGE);
			msg.setContent(cBox.getValue());
			msg.setContent1(gamer);

			byte[] Bytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(Bytes, Bytes.length, ClientConfig.SERVER_ADDR);
			s.send(pout);

		} catch (Exception e1) {

		}
	}
	
	
	public void btnWatchHandler(ActionEvent e){
		try {
			Message msg = new Message();
			pane.clear();
			
			msg.setMsgType(MessageType.WATCH1);
			msg.setContent3(cBox1.getValue());
			msg.setContent2(gamer);

			byte[] Bytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(Bytes, Bytes.length, ClientConfig.SERVER_ADDR);
			s.send(pout);

		} catch (Exception e1) {

		}
	}
	
	

	public void btnExitWatchHandler(ActionEvent e){
		try {		
			Message msg = new Message();
			msg.setMsgType5(MessageType.WATCH5);

			byte[] Bytes = Message.convertToBytes(msg);
			DatagramPacket pout1 = new DatagramPacket(Bytes, Bytes.length, s1);
			s.send(pout1);
			DatagramPacket pout2 = new DatagramPacket(Bytes, Bytes.length, s2);
			s.send(pout2);
			DatagramPacket pout3 = new DatagramPacket(Bytes, Bytes.length, ClientConfig.SERVER_ADDR);
			s.send(pout3);
			pane.clear();
		} catch (Exception e1) {

		}
	}
	
	
	public void btnSendHandler(ActionEvent e) {
		try {
			Message msg = new Message();
			msg.setMsgType(MessageType.ChatAll);
			String chatmsg = ta_info3.getText();
			//ta_info.appendText(chatmsg + "\n");
			String str = "from " + gamer + ":  " + chatmsg;
			msg.setMsgContent(str);

			byte[] msgBytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length,ClientConfig.SERVER_ADDR);
			s.send(pout);
		} catch (Exception e1) {

		}
	}
	
	
	public void btnPrivateHandler(ActionEvent e) {
		try {    
		    Message msg = new Message();
		    msg.setMsgType(MessageType.ChatPrivate);
		    //String number;
		    String name = cBox2.getValue();
	        //User user = new User(cBox1.getValue(),number,ClientConfig.LOCAL_ADDR);
		    
		    msg.setContent1(name);
		    String chatmsg = ta_info3.getText();
			ta_info.appendText("to " + name + ":  " +chatmsg + "\n");
			
			String str = "private " + gamer + ":  " + chatmsg;
			msg.setContent(str);
			
			byte[] msgBytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length,ClientConfig.SERVER_ADDR);
			s.send(pout);
		}catch(Exception e1) {
			
		}	
	}
	
	
	public void btnClearHandler(ActionEvent e) {
		pane.clear();
	}
	
	
	public void btnExitHandler(ActionEvent e) {
		try {    
		    Message msg = new Message();
		    msg.setMsgType(MessageType.EXIT);
		    msg.setContent(gamer);
		    
			byte[] msgBytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length,ClientConfig.SERVER_ADDR);
			s.send(pout);
			s.close();
		}catch(Exception e1) {
			
		}	
	}
	
	
	/*public void btnRejectHandler(ActionEvent e) {
		try {    
		    Message msg = new Message();
		    Message wmsg = new Message();
		    String str = Chess5Pane.column + "," + Chess5Pane.row + "," + Chess5Pane.color + "," + ClientConfig.ACTION_TYPE_DEL;
		    System.out.println(str+11111);
		    msg.setMsgType(MessageType.PLAY);
		    msg.setFromPlayer(competition.getLocalPlayer());
			msg.setToPlayer(competition.getRemotePlayer());
		    msg.setContent(str);
		    
		    wmsg.setMsgType2(MessageType.WATCH2);
		    wmsg.setContent2(str);
		    
			byte[] msgBytes = Message.convertToBytes(msg);
			DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length,ClientConfig.SERVER_ADDR);
			s.send(pout);
			
			byte[] wmsgBytes = Message.convertToBytes(wmsg);
			DatagramPacket wpout = new DatagramPacket(wmsgBytes, wmsgBytes.length,sss);
			s.send(wpout);
		}catch(Exception e1) {
			
		}	
	}*/
	
	
	class Chess5Thread extends Thread {
		DatagramSocket s;

		public Chess5Thread(DatagramSocket s) {
			this.s = s;
		}

		public void run() {
			while(true) {
				try{	
					DatagramPacket pin = new DatagramPacket(new byte[1024],1024);
					s.receive(pin);
					
					Message msg = (Message) Message.convertToObj(pin.getData(),0,pin.getLength());
						
		    /* 挑战 */if (msg.getMsgType() == MessageType.M) {
						Platform.runLater(() -> {
							try {
								String str = (String) msg.getContent2();
								String str1 = (String) msg.getContent3();
								Optional<ButtonType> bType = Chess5Utils.showClert(str + "请求挑战" + "     对方" + str1);
								if (bType.get() == ButtonType.OK) {
									Platform.runLater(() -> lbl_name.setText("我是" + gamer + "     对方是" + str));
									Player player = (Player) msg.getContent();
									competition.setRemotePlayer(player);

									Player player1 = (Player) msg.getContent1();
									competition.setLocalPlayer(player1);
									pane.setCompetition(competition);

									msg.setMsgType(MessageType.CHALLENGE_RSP0);

									if (msg.getMsgType1() == MessageType.WHITE) {
										rbtn_black.setSelected(true);
										pane.setMouseTransparent(true);
									} else if (msg.getMsgType1() == MessageType.BLACK) {
										rbtn_white.setSelected(true);
										pane.setMouseTransparent(false);
									}
									
									msg.setMsgType1(MessageType.WATCH0);
									byte[] Bytes1 = Message.convertToBytes(msg);
									DatagramPacket pout1 = new DatagramPacket(Bytes1, Bytes1.length, ClientConfig.SERVER_ADDR);
									s.send(pout1);
								} else if (bType.get() == ButtonType.NO) {
									msg.setMsgType(MessageType.CHALLENGE_RSP1);
									byte[] Bytes1 = Message.convertToBytes(msg);
									DatagramPacket pout1 = new DatagramPacket(Bytes1, Bytes1.length, ClientConfig.SERVER_ADDR);
									s.send(pout1);
								}
							} catch (Exception e) {

							}
						});
					}
					
				
						else if(msg.getMsgType()==MessageType.CHALLENGE_RSP0) {
							Platform.runLater(()->lbl_name.setText("我是" + gamer + "     对方是" + cBox.getValue()));
							if(rbtn_white.isSelected()==true) {
								String str = "黑子后手";
								Platform.runLater(()->Chess5Utils.showAlert(cBox.getValue() + "接受挑战！" + "     对方" + str));
								//int n = JOptionPane.showOptionDialog(null, cBox.getValue() + "接受挑战！" + "     对方" + str, "消息", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, "OK");
							}
							else if(rbtn_black.isSelected()==true) {
								String str = "白子先手";
								Platform.runLater(()->Chess5Utils.showAlert(cBox.getValue() + "接受挑战！" + "     对方" + str));
								//int n = JOptionPane.showOptionDialog(null, cBox.getValue() + "接受挑战！" + "     对方" + str, "消息", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, "OK");
							}
							
							Player player = (Player)msg.getContent();
							competition.setLocalPlayer(player);
							
							Player player1 = (Player)msg.getContent1();
							competition.setRemotePlayer(player1);
							pane.setCompetition(competition);
							if(rbtn_white.isSelected()==true) {
								pane.setMouseTransparent(false);
							}
							else if(rbtn_black.isSelected()==true) {
								pane.setMouseTransparent(true);
							}
							
						    }
						else if(msg.getMsgType()==MessageType.CHALLENGE_RSP1) {
							Platform.runLater(()->Chess5Utils.showAlert(cBox.getValue() + "拒绝挑战！"));
							//int n = JOptionPane.showOptionDialog(null, "对方拒绝挑战！", "消息", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, "OK"); 
						}
						
						
			/*加入游戏*/else if (msg.getMsgType() == MessageType.JOIN_GAME) {
							Platform.runLater(()->Chess5Utils.showAlert("欢迎" + gamer + "加入游戏"));
							Platform.runLater(()->lbl_name.setText("我是" + gamer));
							System.out.println(gamer);
							String by = (String) msg.getContent();
							String[] a = by.split(":");
							for (String r : a) {
								Platform.runLater(()->cBox.getItems().add(r));
								Platform.runLater(()->cBox.setValue(a[0]));
							}

						}

						else if (msg.getMsgType() == MessageType.JOIN_GAME_FAIL) {
							Platform.runLater(()->Chess5Utils.showAlert("昵称已存在！"));
						}
						
						
			/*加入擂台*/else if (msg.getMsgType() == MessageType.JOIN_HOST) {
							Platform.runLater(()->cBox.getItems().add(gamer));
							Platform.runLater(()->Chess5Utils.showAlert("欢迎加入擂台"));
						} else if(msg.getMsgType() == MessageType.JOIN_HOST_FAIL){
							Platform.runLater(()->Chess5Utils.showAlert("你已在擂台里，加入擂台失败！"));
						}
	                    
	                    
			/*更新擂台*/else if (msg.getMsgType() == MessageType.UPDATE_HOST) {
							Platform.runLater(()->cBox.getItems().clear());
							String by = (String) msg.getContent1();
							String b[] = by.split(":");
							for (String r : b) {
								Platform.runLater(()->cBox.getItems().add(r));
								Platform.runLater(()->cBox.setValue(b[0]));
							}
						}
						
						
			   /*下棋*/else if (msg.getMsgType() == MessageType.PLAY) {
							String str = (String) msg.getContent();
							String[] a = str.split(",");
							int b0 = Integer.valueOf(a[0]);
							int b1 = Integer.valueOf(a[1]);
							int b2 = Integer.valueOf(a[2]);
							int b3 = Integer.valueOf(a[3]);
							if(b3==1) {
								Platform.runLater(()->pane.drawChess(b1, b0, b2));
								pane.setMouseTransparent(false);
								if(msg.getMsgType3() == MessageType.WHITE) {
									Platform.runLater(()->Chess5Utils.showAlert("白子胜"));
									pane.setMouseTransparent(true);
									//Platform.runLater(()->pane.clear());
								}else if(msg.getMsgType3() == MessageType.BLACK) {
									Platform.runLater(()->Chess5Utils.showAlert("黑子胜"));
									pane.setMouseTransparent(true);
									//Platform.runLater(()->pane.clear());
								}
							}
							else if(b3==0) {
								//pane.reject();
								pane.setMouseTransparent(false);
							}
						}
		    
		    
					    if (msg.getMsgType1() == MessageType.WATCH0) {
					    	Platform.runLater(()->cBox1.getItems().clear());
					    	ArrayList<String> str = (ArrayList) msg.getContent1();
					    	for(int i=0;i<str.size();i++) {
					    		String a = str.get(i);
								Platform.runLater(()->cBox1.getItems().add(a));
								Platform.runLater(()->cBox1.setValue(a));
					    	}
					    }
					    
					    
					    
					    else if (msg.getMsgType() == MessageType.WATCH1) {//有人观战立刻把之前的所有棋子发给观战者
					    	sss = (SocketAddress) msg.getContent();
					    	msg.setMsgType(MessageType.WATCH3);
					    	msg.setContent(pane.stringList);
					    	byte[] Bytes = Message.convertToBytes(msg);
							DatagramPacket pout = new DatagramPacket(Bytes, Bytes.length, sss);
							s.send(pout);
					    	
					    }
					    
					    
					    else if (msg.getMsgType2() == MessageType.WATCH2) {//直播画棋子
					    	String str = (String) msg.getContent2();
							String[] a = str.split(",");
							int b0 = Integer.valueOf(a[0]);
							int b1 = Integer.valueOf(a[1]);
							int b2 = Integer.valueOf(a[2]);
							int b3 = Integer.valueOf(a[3]);
							if(b3==1) {
								Platform.runLater(()->pane.drawChess(b1, b0, b2));
								pane.setMouseTransparent(true);
								if(msg.getMsgType3() == MessageType.WHITE) {
									Platform.runLater(()->Chess5Utils.showAlert("白子胜"));
									pane.setMouseTransparent(true);
									//Platform.runLater(()->pane.clear());
								}else if(msg.getMsgType3() == MessageType.BLACK) {
									Platform.runLater(()->Chess5Utils.showAlert("黑子胜"));
									pane.setMouseTransparent(true);
									//Platform.runLater(()->pane.clear());
								}
							}
							else if(b3==0) {
								//pane.reject();
							}
					    }
					    
					    
					    else if (msg.getMsgType() == MessageType.WATCH3) {//中途加入画出之前的棋子
					    	ArrayList<String> str = (ArrayList) msg.getContent();
					    	for(int i=0; i<str.size(); i++) {
					    		String[] a = (str.get(i)).split(",");
					    		int b0 = Integer.valueOf(a[0]);
								int b1 = Integer.valueOf(a[1]);
								int b2 = Integer.valueOf(a[2]);
								int b3 = Integer.valueOf(a[3]);
								if(b3==1) {
									Platform.runLater(()->pane.drawChess(b1, b0, b2));
									pane.setMouseTransparent(true);
								}
					    	}
					    }
					    
					    
					    else if (msg.getMsgType2() == MessageType.WATCH4) {
						SocketAddress sss = (SocketAddress) msg.getContent();
						if (msg.getMsgType5() == MessageType.WATCH5) {     //退出观战
							break;
						} else {
							msg.setMsgType2(MessageType.WATCH2);
							msg.setMsgType3(msg.getMsgType3());//输赢信号，即WHITE或者BLACK
							msg.setContent2(msg.getContent2());
							byte[] Bytes = Message.convertToBytes(msg);
							DatagramPacket pout = new DatagramPacket(Bytes, Bytes.length, sss);
							s.send(pout);
						}
					    }
					    
					    
					    else if (msg.getMsgType2() == MessageType.WATCH6) {
					    	s1 = (SocketAddress) msg.getContent1();
					    	s2 = (SocketAddress) msg.getContent2();
					    }
					    
					    
					    if (msg.getMsgType1() == MessageType.playerList) {
					    	Platform.runLater(()->cBox2.getItems().clear());
					    	ArrayList<String> str = (ArrayList) msg.getContent1();
					    	for(int i=0;i<str.size();i++) {
					    		String a = str.get(i);
								Platform.runLater(()->cBox2.getItems().add(a));
								Platform.runLater(()->cBox2.setValue(a));
					    	}
					    }
					    
					    
					    if(msg.getMsgType()==MessageType.ChatAll) {
							String chatmsg = (String)msg.getMsgContent();
							Platform.runLater(() -> ta_info.appendText(chatmsg + "\n"));
							
						}
					    
					    
					    if(msg.getMsgType()==MessageType.ChatPrivate) {
							Platform.runLater(()->ta_info.appendText((String) msg.getContent() + "\n"));
							
						}
				}catch(Exception e) {
					
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}

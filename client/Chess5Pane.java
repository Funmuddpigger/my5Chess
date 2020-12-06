package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import server.Message;
import server.MessageType;
import server.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import client.Chess5GUI;
/**
 * @Author: 李检辉
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:五子棋棋盘（面板）：响应鼠标事件，添加棋子
 * @Project: 网络编程技术
 * @Copyright: All rights reserved
 */
public class Chess5Pane extends Pane {
	
	private int x_0=30, y_0=30; //棋盘左上角的位置	
	private int N = 9; //N*N的棋盘
	private int k = 60; //方格宽（高）度	
	private int r = 20; //棋子（圆）半径	
	private int color = ClientConfig.COLOR_BLACK;//先手为黑子: 1表示黑子，2表示白子，0表示没有棋子
	private int[][]data = new int [N+1][N+1];
	private Competition competition;Message msg = new Message();
	public List<String> stringList = new ArrayList<String>();int Count;
	public Chess5Pane(){			
		setMaxSize(N*k+x_0*2,N*k+y_0*2);
		setMinSize(N*k+x_0*2,N*k+y_0*2);
		
		//-fx-border-color：设置边框颜色；-fx-border-width设置边框宽度；-fx-background-color：设置背景颜色
		setStyle("-fx-border-color:red;-fx-background-color:#9AC0CD;-fx-border-width:5");//#CAE1FF
        
		setOnMouseClicked(this::mouseClickHandler);	
		
		//画棋盘
		drawBoard();			
	}
	
	/**
	 * 画棋盘线
	 */
	public void drawBoard(){	
		//画横向线	
		for(int i=0; i<=N; i++) {
			getChildren().add(new Line(x_0, y_0+i*k, x_0+N*k, y_0+i*k));	
		}
		//画纵向线
		for(int i=0; i<=N; i++) {
			getChildren().add(new Line(x_0+i*k, y_0, x_0+i*k, y_0+N*k));
		}
	}
	public void setColor(int color){
		this.color = color;
	}
	public void setCompetition(Competition competition){
		this.competition = competition;
	}
	public int[][] getData(){
		return data;
	}
    /** 
    *  在指定棋盘下棋点绘制指定颜色的棋子（圆）
     * @param row 棋盘行数
     * @param column 棋盘列数
     * @param color 棋子颜色，1为黑子，2为白子
     */
	public void drawChess(int row, int column, int color){
		//根据给定的行列值，求出实际的坐标点
		int x_c = x_0+column*k;
		int y_c = y_0+row*k;				
		Circle circle = new Circle(x_c,y_c,r,color==1?Color.BLACK:Color.WHITE);						
		getChildren().add(circle);	
		data[row][column]=color;
		
	}
	/**
	 * 响应鼠标事件，检查对应位置的落子情况，并在对应位置添加棋子
	 * @param e
	 */
	public void mouseClickHandler(MouseEvent e){
		
		if(e.getClickCount()==2){//鼠标双击
			
			int column = (int)Math.round( (e.getX()-x_0)/(k) );
			int row = (int)Math.round( (e.getY()-y_0)/(k) );	
			
			if(data[row][column]==0){
				if(Chess5GUI.rbtn_white.isSelected()==true) {
					setColor(2);
				}
				else if(Chess5GUI.rbtn_black.isSelected()==true) {
					setColor(1);
				}
				
				drawChess(row, column,color);	//画棋子
				String str = column + "," + row + "," + color + "," + ClientConfig.ACTION_TYPE_ADD;
				stringList.add(str);
				//Count++;
				if(Judgement.judge(data)==color){
					msg.setMsgType3(color==1?MessageType.BLACK:MessageType.WHITE);
					Chess5Utils.showAlert((color==1?"黑子":"白子") + "胜");
					setMouseTransparent(true);
					//clear();
					//return;
				}
				
				try(DatagramSocket socket = new DatagramSocket(0);){
					socket.setSoTimeout(5000);
					msg.setMsgType(MessageType.PLAY);
					msg.setFromPlayer(competition.getLocalPlayer());
					msg.setToPlayer(competition.getRemotePlayer());
					msg.setContent(str);
					msg.setContent2(str);
					msg.setContent3(Chess5GUI.gamer);

					byte[] msgBytes = Message.convertToBytes(msg);
					
					DatagramPacket pout = new DatagramPacket(msgBytes, msgBytes.length,
							ClientConfig.SERVER_ADDR);
					socket.send(pout);
					
					if(Judgement.judge(data)==color){
						stringList = null;
					}
					setMouseTransparent(true);
				} catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}	
				//color= color==1?2:1;//变换棋子颜色
				
			} else {
				Chess5Utils.showAlert("该位置已下子");
			}
		} else {
			//System.out.println("click");
		}
	}
		
	/**
	 * 1. 清除棋盘上所有棋子，　
	 * 2. 同时将数组data的所有元素值重置为0，
	 */
	public void clear(){
		//只移除棋子，在这里不能用getChildren().clear()，否则会将棋盘线也清除
		getChildren().removeIf(shape ->(shape instanceof Circle));
		//将数组data的所有元素值重置为0
		data = new int [N+1][N+1];
	}
	
	
	/*public void reject(){
		//stringList[Count--] = null;
		if(Integer.valueOf(stringList.get(color)) == 1) {
			Circle circle = new Circle(Integer.valueOf(stringList.get(column)),Integer.valueOf(stringList.get(row)),r,color==1?Color.BLACK:Color.WHITE);
			//getChildren().removeIf(shape ->(shape instanceof Circle));
			//getChildren().removeIf(circle);
			stringList.remove(1);
		}
		else if(Integer.valueOf(stringList.get(color)) == 2) {
			Circle circle = new Circle(Integer.valueOf(stringList.get(column)),Integer.valueOf(stringList.get(row)),r,color==1?Color.BLACK:Color.WHITE);
			//getChildren().removeIf(shape ->(shape instanceof Circle));
			getChildren().remove(circle);
			stringList.remove(1);
		}
	}*/
}


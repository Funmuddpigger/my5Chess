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
 * @Author: ����
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:���������̣���壩����Ӧ����¼����������
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */
public class Chess5Pane extends Pane {
	
	private int x_0=30, y_0=30; //�������Ͻǵ�λ��	
	private int N = 9; //N*N������
	private int k = 60; //������ߣ���	
	private int r = 20; //���ӣ�Բ���뾶	
	private int color = ClientConfig.COLOR_BLACK;//����Ϊ����: 1��ʾ���ӣ�2��ʾ���ӣ�0��ʾû������
	private int[][]data = new int [N+1][N+1];
	private Competition competition;Message msg = new Message();
	public List<String> stringList = new ArrayList<String>();int Count;
	public Chess5Pane(){			
		setMaxSize(N*k+x_0*2,N*k+y_0*2);
		setMinSize(N*k+x_0*2,N*k+y_0*2);
		
		//-fx-border-color�����ñ߿���ɫ��-fx-border-width���ñ߿��ȣ�-fx-background-color�����ñ�����ɫ
		setStyle("-fx-border-color:red;-fx-background-color:#9AC0CD;-fx-border-width:5");//#CAE1FF
        
		setOnMouseClicked(this::mouseClickHandler);	
		
		//������
		drawBoard();			
	}
	
	/**
	 * ��������
	 */
	public void drawBoard(){	
		//��������	
		for(int i=0; i<=N; i++) {
			getChildren().add(new Line(x_0, y_0+i*k, x_0+N*k, y_0+i*k));	
		}
		//��������
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
    *  ��ָ��������������ָ����ɫ�����ӣ�Բ��
     * @param row ��������
     * @param column ��������
     * @param color ������ɫ��1Ϊ���ӣ�2Ϊ����
     */
	public void drawChess(int row, int column, int color){
		//���ݸ���������ֵ�����ʵ�ʵ������
		int x_c = x_0+column*k;
		int y_c = y_0+row*k;				
		Circle circle = new Circle(x_c,y_c,r,color==1?Color.BLACK:Color.WHITE);						
		getChildren().add(circle);	
		data[row][column]=color;
		
	}
	/**
	 * ��Ӧ����¼�������Ӧλ�õ�������������ڶ�Ӧλ���������
	 * @param e
	 */
	public void mouseClickHandler(MouseEvent e){
		
		if(e.getClickCount()==2){//���˫��
			
			int column = (int)Math.round( (e.getX()-x_0)/(k) );
			int row = (int)Math.round( (e.getY()-y_0)/(k) );	
			
			if(data[row][column]==0){
				if(Chess5GUI.rbtn_white.isSelected()==true) {
					setColor(2);
				}
				else if(Chess5GUI.rbtn_black.isSelected()==true) {
					setColor(1);
				}
				
				drawChess(row, column,color);	//������
				String str = column + "," + row + "," + color + "," + ClientConfig.ACTION_TYPE_ADD;
				stringList.add(str);
				//Count++;
				if(Judgement.judge(data)==color){
					msg.setMsgType3(color==1?MessageType.BLACK:MessageType.WHITE);
					Chess5Utils.showAlert((color==1?"����":"����") + "ʤ");
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
				//color= color==1?2:1;//�任������ɫ
				
			} else {
				Chess5Utils.showAlert("��λ��������");
			}
		} else {
			//System.out.println("click");
		}
	}
		
	/**
	 * 1. ����������������ӣ���
	 * 2. ͬʱ������data������Ԫ��ֵ����Ϊ0��
	 */
	public void clear(){
		//ֻ�Ƴ����ӣ������ﲻ����getChildren().clear()������Ὣ������Ҳ���
		getChildren().removeIf(shape ->(shape instanceof Circle));
		//������data������Ԫ��ֵ����Ϊ0
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


package client;

import java.net.DatagramSocket;

import client.Chess5GUI.Chess5Thread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import server.Player;

public class WatchGUI extends Application{
	Chess5Pane pane;// ��������
	Label lbl_name;// ������ʾ�����Ϣ
	ChoiceBox<String> cBox;// ���������б�
	Button btn_watch,btn_chat;
	static RadioButton rbtn_white;
	static RadioButton rbtn_black;
	Player player;
	String gamer;
	DatagramSocket s;
	Competition competition = new Competition();
	TextField tf_msg, tf_addr;
	TextArea ta_info,ta_info3;

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		pane = new Chess5Pane();
		pane.setMouseTransparent(true);// ��������������¼�

		Label lb_msg = new Label("Ⱥ��:");
		tf_msg = new TextField();
		tf_msg.setPrefColumnCount(20);
		Button btn_send = new Button("   ����  ");
		// btn_send.setOnAction(this::btnSendHandler);
		
		
		
		GridPane pane_com = new GridPane();
		ta_info3 = new TextArea();
		ta_info3.setPrefSize(500, 100);
		ta_info3.getMaxHeight();
		ScrollPane pane_info3 = new ScrollPane(ta_info3);		
		pane_info3.setFitToWidth(true);
		TitledPane pane_03 = new TitledPane("Ⱥ��",pane_info3);
		pane_03.setCollapsible(false);
		
		pane_com.addRow(1, ta_info3, btn_send);
		GridPane.setHalignment(ta_info3,HPos.RIGHT);
		
		pane_com.setAlignment(Pos.CENTER);
		pane_com.setHgap(20);
		pane_com.setVgap(10);
		pane_com.setMinSize(200, 60);	
		
		TitledPane pane_01 = new TitledPane("Communication Border",pane_com);
		pane_01.setCollapsible(false);
		
		
		
		
		btn_watch = new Button("��ս");
		btn_watch.setDisable(false);
		btn_watch.setOnAction(this::btnWatchHandler);

		cBox = new ChoiceBox<String>();

		FlowPane fPane_01 = new FlowPane(btn_watch, cBox, lb_msg, tf_msg);
		fPane_01.setAlignment(Pos.CENTER);
		fPane_01.setHgap(40);
		
		ta_info = new TextArea();
		ta_info.setPrefSize(500, 400);
		ta_info.getMaxHeight();
		ScrollPane pane_info = new ScrollPane(ta_info);		
		pane_info.setFitToWidth(true);
		TitledPane pane_02 = new TitledPane("Ⱥ��",pane_info);
		pane_02.setCollapsible(false);
		
		
		VBox box1 = new VBox(pane_02, pane_01);
		
		
		FlowPane fPane_02 = new FlowPane(pane, box1);
		fPane_02.setAlignment(Pos.CENTER);
		fPane_02.setHgap(40);

		lbl_name = new Label("���ڹ�ս");
		FlowPane fPane_03 = new FlowPane(lbl_name);
		fPane_03.setAlignment(Pos.CENTER);
		
		VBox box = new VBox(fPane_01, fPane_02, fPane_03);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		box.setStyle("-fx-border-color:green;-fx-background-color:#EED8AE;-fx-border-width:5");// #B2DFEE

		Scene scene = new Scene(box, 1300, 700);
		stage.setScene(scene);
		stage.setTitle("������");
		stage.show();
		
	}
	
	
	public void btnWatchHandler(ActionEvent e) {
		
	}
	
	
	public void btnChatHandler(ActionEvent e) {

	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}
}

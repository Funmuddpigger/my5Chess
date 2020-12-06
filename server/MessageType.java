package server;

/**
 * @Author: 李检辉
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:用于定义信息类型
 * @Project: 网络编程技术
 * @Copyright: All rights reserved
 */
public class MessageType {
	public static final  int SUCCESS = 0;
	public static final  int FAIL = 1;
	public static final  int JOIN_GAME = 1;//申请加入游戏的请求信息
	public static final int JOIN_HOST = 2;//申请加入擂主的请求信息
	public static final int CHALLENGE = 3;//挑战擂主的请求信息
	public static final int M = 9;
	public static final int JOIN_HOST_FAIL = 11;
	public static final  int JOIN_GAME_FAIL = 12;
	public static final int CHALLENGE_RSP = 4;
	public static final int CHALLENGE_RSP0 = 7;//挑战擂主的请求信息
	public static final int CHALLENGE_RSP1 = 8;
	public static final int UPDATE_HOST = 5;//更新擂主列表的信息
	public static final int PLAY = 6;//玩家下了一步棋的信息
	public static final int WHITE = 13;
	public static final int BLACK = 14;
	public static final int WATCH0 = 15;//加入可观战列表
	public static final int WATCH1 = 16;//请求观战
	public static final int WATCH2 = 17;//观战返回棋子
	public static final int WATCH3 = 18;//中途观战返回之前的棋子
	public static final int WATCH4 = 19;//
	public static final int WATCH5 = 20;//退出观战
	public static final int WATCH6 = 21;//把对战双方的地址发给观战者
	public static final  int A = 23;
	public static final  int B = 24;
	public static final  int C = 25;
	public static final  int D = 26;
	public static final  int P = 27;
	public static final  int ChatAll = 200;
	public static final int ChatPrivate = 201;
	public static final int playerList = 202;
	public static final int EXIT = 30;
	
	
}

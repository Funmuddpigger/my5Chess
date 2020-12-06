package server;

/**
 * @Author: ����
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:���ڶ�����Ϣ����
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */
public class MessageType {
	public static final  int SUCCESS = 0;
	public static final  int FAIL = 1;
	public static final  int JOIN_GAME = 1;//���������Ϸ��������Ϣ
	public static final int JOIN_HOST = 2;//�������������������Ϣ
	public static final int CHALLENGE = 3;//��ս������������Ϣ
	public static final int M = 9;
	public static final int JOIN_HOST_FAIL = 11;
	public static final  int JOIN_GAME_FAIL = 12;
	public static final int CHALLENGE_RSP = 4;
	public static final int CHALLENGE_RSP0 = 7;//��ս������������Ϣ
	public static final int CHALLENGE_RSP1 = 8;
	public static final int UPDATE_HOST = 5;//���������б����Ϣ
	public static final int PLAY = 6;//�������һ�������Ϣ
	public static final int WHITE = 13;
	public static final int BLACK = 14;
	public static final int WATCH0 = 15;//����ɹ�ս�б�
	public static final int WATCH1 = 16;//�����ս
	public static final int WATCH2 = 17;//��ս��������
	public static final int WATCH3 = 18;//��;��ս����֮ǰ������
	public static final int WATCH4 = 19;//
	public static final int WATCH5 = 20;//�˳���ս
	public static final int WATCH6 = 21;//�Ѷ�ս˫���ĵ�ַ������ս��
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

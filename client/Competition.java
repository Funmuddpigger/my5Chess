package client;

import server.Player;

/**
 * @Author: ����
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:���ڱ�ʾһ���������ȷ���Ϣ���������ݡ��������
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */
public class Competition {
	Player localPlayer, remotePlayer;
	int[][]data;//��������
	int result;//���
	public Player getLocalPlayer() {
		return localPlayer;
	}
	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}
	public Player getRemotePlayer() {
		return remotePlayer;
	}
	public void setRemotePlayer(Player remotePlayer) {
		this.remotePlayer = remotePlayer;
	}
	public int[][] getData() {
		return data;
	}
	public void setData(int[][] data) {
		this.data = data;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

}

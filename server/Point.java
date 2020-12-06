package server;

import java.io.Serializable;

/**
 * @Author: ����
 * @Date: 2019/8/20
 * @version V1.0
 * @Description:���ڷ�װ���ӵ����Ϣ�������С���ֵ��������ɫ��������������ӻ����Ƴ����ӣ�����壩
 * @Project: �����̼���
 * @Copyright: All rights reserved
 */
public class Point implements Serializable{
	
	public int column, row,action;
	public int color;
	
	public Point(int column, int row, int color, int action){
		this.column = column;//��
		this.row = row;//��
		this.color = color;
		this.action = action;
	}
	public String toString(){
		return "[" + this.column + ", " + row + "," + color + "," + action + "]";
	}
	
}

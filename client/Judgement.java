package client;

public class Judgement {
	/** ������������Ӯ */
	public static int MAX = 5; 
	/**
	 * ��������״̬����ʤ����
	 * ��ˮƽ����ֱ��б�߻�б��4�������У���������MAX����ͬ��ɫ�����ӣ���ʤ���ѷ֣�
	 * @param data ����״̬����ά���飩��0���ӣ�1���ӣ�2����
	 * @return ����������У�0ʤ��δ�֣�1��ʤ��2��ʤ
	 */
	public static int judge(int[][] data){
		int j1 = hLine(data);
		if(j1>0) return j1;
		
		int j2 = vLine(data);
		if(j2>0) return j2;
		
		int j3 = xLine(data);
		if(j3>0) return j3;
		
		int j4 = rxLine(data);
		if(j4>0) return j4;
		return 0;
	}
	/** 
	 * ˮƽ�����ʤ���ж� 
	 * @param data ����״̬����ά���飩��0���ӣ�1���ӣ�2����
	 * @return ����������У�0����ʤ����1��ʤ��2��ʤ
	 */
	private static int hLine(int[][] data){
		int black=0,white=0;
		for(int i=0; i<data.length; i++){
			black=0; white=0;
			for(int j=0; j<data[i].length; j++){
				if(data[i][j]==1){
					black++;
					white = 0;
				}
				else if(data[i][j]==2){
					white++;
					black = 0;
				}else{
					black = white = 0;
				}
				if(black>=MAX) return 1;
				if(white>=MAX) return 2;
			}			
		}
		return 0;
	}	
	/** 
	 * ��ֱ�����ʤ���ж� 
 	 * @param data ����״̬����ά���飩��0���ӣ�1���ӣ�2����
	 * @return ����������У�0����ʤ����1��ʤ��2��ʤ
	 */
	private static int vLine(int[][] data){
		int black=0,white=0;
		for(int i=0; i<data.length; i++){
			black=0; white=0;
			for(int j=0; j<data[i].length; j++){
				if(data[j][i]==1){
					black++;
					white = 0;
				}
				else if(data[j][i]==2){
					white++;
					black = 0;
				}else{
					black = white = 0;
				}
				if(black>=MAX) return 1;
				if(white>=MAX) return 2;
			}			
		}
		return 0;
	}
	/** 
	 * б�ߣ�����-���£����� 
	 * ͬһ�����ϣ�x+y���ڳ���������Ϊ0��N+N����
	 * @param data ����״̬����ά���飩��0���ӣ�1���ӣ�2����
	 * @return ����������У�0����ʤ����1��ʤ��2��ʤ
	 */
	private static int xLine(int[][] data){
		int black=0,white=0;
		//Bug: sum<=data.length => sum<=data.length*2-2
		for(int sum=0; sum<=data.length*2-2; sum++){
			black=0; white=0;
			for(int i=0; i<=sum; i++) {//Bug: i<sum => i<=sum
				int j = sum - i;
				if(i>=data.length || i<0) continue;
				if(j>=data.length || j<0) continue;				
				if(data[i][j]==1){
					black++;
					white = 0;
				}
				else if(data[i][j]==2){
					white++;
					black = 0;
				}else{
					black = white = 0;
				}
				if(black>=MAX) return 1;
				if(white>=MAX) return 2;
			}			
		}
		return 0;
	}
	/** 
	 * ��б�ߣ�����-���£����� 
	 * ͬһ�����ϣ�x-y���ڳ���������Ϊ�Ӹ�N-1����N-1��
	 * @param data ����״̬����ά���飩��0���ӣ�1���ӣ�2����
	 * @return ����������У�0����ʤ����1��ʤ��2��ʤ
	 */
	private static int rxLine(int[][] data){
		int black=0,white=0;
		for(int sub=-(data.length-1); sub<=data.length-1; sub++){
			black=0; white=0;
			for(int i=0; i<data.length; i++) {
				int j = i - sub;
				if(i>=data.length || i<0) continue;
				if(j>=data.length || j<0) continue;
				if(data[i][j]==1){
					black++;
					white = 0;
				}
				else if(data[i][j]==2){
					white++; //Bug: black++ => white++
					black = 0;
				}else{
					black = white = 0;
				}
				if(black>=MAX) return 1;
				if(white>=MAX) return 2;
			}			
		}
		return 0;
	}
}
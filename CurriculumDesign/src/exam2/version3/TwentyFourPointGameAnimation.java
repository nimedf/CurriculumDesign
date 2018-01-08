package exam2.version3;

import java.util.ArrayList;
import java.util.Arrays;

import exam2.version2.FindVarietySolution;

public class TwentyFourPointGameAnimation {
	private static double[] b;
	private static int sum = 0;	// ��¼�ж��ٿ��Ե���24�Ľ�
	private static int totalcount = 0;	// �����ȡ�Ĵ���
	
	public static void main(String[] args) {
		double[] index = new double[52];	// ���������Ƶ��±�
		int k = 0;
		for (int i = 0; i < 4 && k < 52; i++) {
			for (int j = 1; j <= 13; j++) {
				index[k++] = j;
			}
		}

		long str = System.currentTimeMillis();
		permutation(index, 0, 0, 4);
		System.out.println("startTime: " + str);
		System.out.println("endTime: " + System.currentTimeMillis());
		System.out.println("Total number of combinations is " + totalcount);
		System.out.println("Total number of combinations with solutions is 217785");
		System.out.println("The solution ratio is " + (217785.0 / totalcount));
	}

	/**
	 * ����һ����һ�������г�ȡ���������������з�ʽ
	 * @param a ����ȡ������
	 * @param count ��ÿ�γ�ȡ�������в��ظ��Ĵ���
	 * @param count2 �ǳ�ȡ�ĵڼ��Σ�һ��ֻ�ܳ�ȡexcept��
	 * @param except ��ȡ�ĸ���
	 */
	private static void permutation(double[] a, int count, int count2, int except) {
		if (count2 == except) {
			System.out.println(Arrays.toString(b));
			int temp = FindVarietySolution.findVaritySolution1(b);
			if (temp > 0)
				sum++;
			totalcount++;
		}
		else {
			if (count2 == 0) {
				b = new double[except];
			}
			for (int i = count; i < a.length; i++) {
				b[count2] = a[i];
				permutation(a, i+1, count2+1, except);
			}
		}
	}
}

package exam2.version2;

import java.util.ArrayList;
import java.util.Arrays;

public class FindVarietySolution {
	
	public static void main(String[] args) {
		double[] num = {1,2,3,4};
		System.out.println(findVaritySolution(num));
	}
	
	/**
	 * 根据用户提供的4个数来找出这四个数所能组成的所有的和为24的解
	 * 返回包含各种解的String的线性表
	 * @param nums	保存四个操作数的数组
	 * @return
	 */
	public static ArrayList<String> findVaritySolution(double[] nums) {
		String[] operator = {"+", "-", "*", "/"};	// 存储操作符的数组
		double[] newNum;	//	用来保存新的数所组成的数组
		double sum = 0;	// 保存任意两个数的计算结果
		ArrayList<double[]> list1 = new ArrayList<double[]>();//存三个数的链表
		ArrayList<double[]> list2 = new ArrayList<double[]>();//存两个数的链表
		ArrayList<String> listException1 = new ArrayList<String>();//保存运算表迏式(四个抽三个）
		ArrayList<String> listException2 = new ArrayList<String>();//保存运算表迏式（三个中抽两个）
		ArrayList<String> listException3 = new ArrayList<String>();//保存运算表迏式（最后两个）
		ArrayList<String> listException = new ArrayList<String>();// 保存最后结果等于24的表达式
		String expt;	// 拼接运算表达式
		
		expt = "(";
		// 四个数中任取两个计算值，并且保留留下的那三个数
		for (int i = 0; i < 4; i++) {// 取第一个数
			for (int j = 0; j < 4; j++) {// 取第二个数
				if (j == i)
					continue;
				for (int k = 0;k < 4; k++) {// 随机的操作字符
					sum = evaluateTwoNum(nums[i], nums[j], operator[k]);
					newNum = create(nums[i], nums[j], sum, nums);
					expt = expt + nums[i] + operator[k] + nums[j] + ")";
//					System.out.println(expt);
					listException1.add(expt);
					expt = "(";
					list1.add(newNum);
				}
			}
		}
		
		// 三个数中任取两个，计算并保留剩下的两个数
		for (int m = 0; m < list1.size(); m++) {
			double[] num1 = list1.get(m);
			expt = listException1.get(m);
			for (int i = 0; i < 3; i++) {// i=0, i = 1
				for (int j = 0; j < 3; j++) {// j=1,2, j= 2
					if (i == j)
						continue;
					for (int k = 0; k < 4; k++) {
//						System.out.println(num1[i] + " " + num1[j]);
						sum = evaluateTwoNum(num1[i], num1[j], operator[k]);
						newNum = create(num1[i], num1[j], sum, num1);
						if (i == 0) {
//							System.out.println(operator[k]);
							expt = "(" + expt + operator[k] + num1[j] + ")";
						}
						else if (i != 0 && j == 0) {
							expt = num1[i] + operator[k] + expt;
						}
						else {
							expt = "z" + expt + "(" + num1[i] + operator[k] + num1[j] + ")";
						}
//						System.out.println(expt);
						listException2.add(expt);
						expt = listException1.get(m);
						list2.add(newNum);
					}
				}
			}
		}
		
		// 计算最终结果
		ArrayList<Double> result = new ArrayList<Double>();
		for (int i = 0; i < list2.size(); i++) {
			double[] num2 = list2.get(i);
			expt = listException2.get(i);
			for (int k = 0; k < 2; k++) {
				for (int m = 0; m < 2; m++) {
					if (m == k) 
						continue;
					for (int j = 0;j < 4; j++) {
						sum = evaluateTwoNum(num2[k], num2[m], operator[j]);
						if (expt.charAt(0) == 'z') {
							char[] ch = expt.toCharArray();
							for (int z = 0; z < 9; z++)
								ch[z] = ch[z+1];
							if (operator[j].equals("+"))
								ch[9] = '+';
							else if (operator[j].equals("-"))
								ch[9] = '-';
							else if (operator[j].equals("*"))
								ch[9] = '*';
							else 
								ch[9] = '/';
							expt = new String(ch);
						}
						else {
							if (k == 0) {
								expt = expt + operator[j] + num2[1];
							}
							else {
								expt = num2[1] + operator[j] + expt;
							}
						}
						listException3.add(expt);
						expt = listException2.get(i);
						result.add(sum);
					}
				}
			}
		}
		
//		int count = 0;
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i) == 24.0) {
				listException.add(listException3.get(i));
//				count++;
			}
		}
	
		return listException;
	}
	
	/**
	 * 清楚两个数
	 * @param num1
	 * @param num2
	 * @param newNum
	 * @param num
	 * @return
	 */
	private static double[] create(double num1, double num2, double newNum, double[] num) {
		double[] newNums = new double[num.length - 1];
		newNums[0] = newNum;
		for (int i = 0,k = 1; i < num.length && k < newNums.length; i++) {
			if (num[i] != num1 && num[i] != num2){
				newNums[k] = num[i];
				k++;
			}
		}
		
		return newNums;
	}
	
	/**
	 * 根据传过来的运算符进行两个数的运算 
	 * @param num1
	 * @param num2
	 * @param operator
	 * @return
	 */
	private static double evaluateTwoNum(double num1, double num2, String operator) {
		if (operator.equals("+"))
			return num1 + num2;
		else if (operator.equals("-"))
			return num1 - num2;
		else if (operator.equals("*"))
			return num1 * num2;
		else if (num2 != 0)
			return num1 / num2;
		else 
			return -1;
	}
	
	/**
	 * 根据用户提供的4个数来找出这四个数所能组成的所有的和为24的解
	 * 返回包含各种解的String的线性表
	 * @param nums	保存四个操作数的数组
	 * @return
	 */
	public static int findVaritySolution1(double[] nums) {
		String[] operator = {"+", "-", "*", "/"};	// 存储操作符的数组
		double[] newNum;	//	用来保存新的数所组成的数组
		double sum = 0;	// 保存任意两个数的计算结果
		ArrayList<double[]> list1 = new ArrayList<double[]>();//存三个数的链表
		ArrayList<double[]> list2 = new ArrayList<double[]>();//存两个数的链表
	
		// 四个数中任取两个计算值，并且保留留下的那三个数
		for (int i = 0; i < 4; i++) {// 取第一个数
			for (int j = 0; j < 4; j++) {// 取第二个数
				if (j == i)
					continue;
				for (int k = 0;k < 4; k++) {// 随机的操作字符
					sum = evaluateTwoNum(nums[i], nums[j], operator[k]);
					newNum = create(nums[i], nums[j], sum, nums);
					list1.add(newNum);
				}
			}
		}
		
		// 三个数中任取两个，计算并保留剩下的两个数
		for (int m = 0; m < list1.size(); m++) {
			double[] num1 = list1.get(m);
			for (int i = 0; i < 3; i++) {// i=0, i = 1
				for (int j = 0; j < 3; j++) {// j=1,2, j= 2
					if (i == j)
						continue;
					for (int k = 0; k < 4; k++) {
//						System.out.println(num1[i] + " " + num1[j]);
						sum = evaluateTwoNum(num1[i], num1[j], operator[k]);
						newNum = create(num1[i], num1[j], sum, num1);
						list2.add(newNum);
					}
				}
			}
		}
		
		// 计算最终结果
		ArrayList<Double> result = new ArrayList<Double>();
		for (int i = 0; i < list2.size(); i++) {
			double[] num2 = list2.get(i);
			for (int k = 0; k < 2; k++) {
				for (int m = 0; m < 2; m++) {
					if (m == k) 
						continue;
					for (int j = 0;j < 4; j++) {
						sum = evaluateTwoNum(num2[k], num2[m], operator[j]);
						result.add(sum);
					}
				}
			}
		}
		
		for (int i = 0; i < result.size(); i++) {
			if (result.get(i) == 24.0)
				return 1;
		}
		return -1;
	}
}

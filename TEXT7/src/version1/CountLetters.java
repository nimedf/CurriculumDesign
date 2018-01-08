package version1;

import java.util.Scanner;

public class CountLetters {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);

		System.out.print("请输入一个字符串：");
		String str = input.nextLine();
		int count = countLetters(str);
		System.out.println("您输入的字符串中共有 " + count + "个字母");
	}

	public static int countLetters(String s){
		String str = s.toUpperCase();
		char[] ch = str.toCharArray();
		int count = 0;

		for (int i = 0; i < ch.length; i++){
			if (ch[i] > 'A' && ch[i] < 'Z')
				count++;
		}

		return count;
	}

}

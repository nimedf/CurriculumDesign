package version3;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class ShowFrequency {
	public static void main(String[] args)throws Exception{
		Scanner input = new Scanner(System.in);

		int[] num = new int[900000];
		System.out.println("请输入文件名：");
		String name = input.nextLine();

		File file = new File(name + ".dat");

		int[] n = new int[900000];
		int i = 0,count = 0;
		Scanner input1 = new Scanner(file);
		while(input1.hasNext()){
			n[i++] = input1.nextInt();
			count++;
		}
		int[] acount = new int[26];
		int count1 = 0;
		char[] ch = new char[10000];
		for (i = 0; i < count; i++){
			ch[i] = (char)n[i];
			if (ch[i] >= 'a' && ch[i] <= 'z'){
				acount[ch[i] - 'a']++;
				count1++;
			}
		}

		for (i = 0; i < 26; i++){
			if (acount[i] == 0)
				continue;
			System.out.println((char)('A' + i) + " 的出现率为：" + (acount[i] / (count1 * 1.0)));
		}
		input1.close();
		input.close();
	}


}

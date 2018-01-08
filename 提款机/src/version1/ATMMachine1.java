package version1;

import java.util.Scanner;

public class ATMMachine1 {
    public static final int MISTAKE = 2;		//判断输入的id是否是合法的
    public static final int BEYOUND = 1; 		//检查输入的id是否是合格的，若不合格则返回beyound=1
    static Account1[] account = new Account1[100];		//创建一个拥有100个账户的数组



    public static void main(String[] args){
		/*	对这100个账户进行初始化，
		 * 	id初始化为0到99之间的数
		 *  初始化余额都为1000元
		 */
        for (int i = 0; i < 100; i++){
            account[i] = new Account1(i,1000);
        }

        menu();
    }

    public static void menu(){
        Scanner input = new Scanner(System.in);
        System.out.println("Main menu:");
        System.out.println("1: check balance");
        System.out.println("2: withdraw");
        System.out.println("3: deposit");
        System.out.println("4: exit");

        int num = insert();
        if (num != BEYOUND){
            System.out.println("请输入你想进行的那一项操作(输入这项操作前面的数字即可)：");
            int choose = input.nextInt();
            switch (choose){
                case 1:checkBalance(account[num]);System.out.println();menu();break;
                case 2:System.out.print("请输入取款金额："); double outmoney = input.nextDouble();
                    withdraw(account[num],outmoney);System.out.println();menu();break;
                case 3:System.out.print("请输入存款金额："); double inmoney = input.nextDouble();
                    deposit(account[num], inmoney);System.out.println();menu();break;
                case 4:System.exit(0);
                default: System.out.println("没有这项操作，请重新输入.");menu();break;
            }
        }
        else{
            System.out.println("账户不存在，清重新输入账户.");
            menu();
        }
    }

    /* 实现用户选择每个功能时需要输入自己的id
     * 若id输入正确或者是已经存在的则返回id
     * 若id输入不合理，或者账户不存在，则返回BEYOUND
     */
    public static int insert(){
        Scanner input = new Scanner(System.in);
        System.out.print("请输入id：");
        String idStr = input.next();
        char[] ch = idStr.toCharArray();
        int flag = 0;

        for (int i = 0; i < ch.length; i++){
            if (ch[i] > '0' && ch[i] < '9')
                flag = 1;
        }

        if (flag == 1){
            int id = Integer.valueOf(idStr);
            if (id >= 0 && id < 100)
                return id;
            else
                return BEYOUND;
        }
        else {
            System.out.println("id输入错误，请重新输入");
            return MISTAKE;
        }
    }

    // 查看当前的余额，绑定用户
    public static void checkBalance(Account1 account){
        System.out.println( "当前余额为：" + account.getBalance());
    }


    //取款，
    public static void withdraw(Account1 account,double money){
        account.withDraw(money);
    }

    //存款
    public static void deposit(Account1 account, double money){
        account.deposit(money);
    }
}

package version2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.HashMap;

public class ATMMachine2 {
    static Scanner input = new Scanner(System.in);
    private static HashMap<Integer,Account2> accounts = null;     //保存账户的列表
    static File file = new File("account.dat");

    public ATMMachine2() throws ClassNotFoundException{
        accounts = outputFromFile();

        if(accounts == null){
            accounts = new HashMap<Integer, Account2>();
        }
    }

    public static Account2 getAccount(){
        System.out.print("请输入id：");
        String idStr  = input.next();
        System.out.print("请输入密码：");
        String password = input.next();

        if (idStr.length() != 6){
            System.out.println("id号只能是六位数");
            return null;
        }

        int id = Integer.valueOf(idStr);

        Account2 account = accounts.get(id);


        if (account == null){
            System.out.println("用户不存在");
            return null;
        }

        if (!password.equals(account.getPassword())){
            System.out.println("密码输入错误");
            return null;
        }

        return account;
    }

    public static void gui(){
        System.out.println("Main menu");
        System.out.println("0:creat a account");
        System.out.println("1:check a balance");
        System.out.println("2:withdraw");
        System.out.println("3:deposit");
        System.out.println("4：details of the transaction");
        System.out.println("5: change password");
        System.out.println("6: exit");

    }

    public static void menu() throws Exception{
        System.out.print("请输入你的选择(登录或者注册）：");
        String loginOrRegister = input.next();
        if (loginOrRegister.equals("登录")){
            Account2 account = getAccount();
            while(true){
                System.out.println();
                gui();
                System.out.print("请选择你想要实现的功能：");
                int choose = input.nextInt();
                switch(choose){
                    case 0:creatAccount();System.out.println();break;
                    case 1:checkBalance(account);System.out.println();break;
                    case 2:withDraw(account);System.out.println();break;
                    case 3:deposit(account);System.out.println();break;
                    case 4:detailOfTransaction(account);System.out.println();break;
                    case 5:changePassword(account);System.out.println();break;
                    case 6:inputToFile();System.exit(0);
                    default:System.out.println("系统里面没有这种操作，请输入正确的功能");menu();
                }
            }
        }
        else if(loginOrRegister.equals("注册")){
            creatAccount();
            System.out.println();
            Account2 account = getAccount();
            while(true){
                System.out.println();
                gui();
                System.out.print("请选择你想要实现的功能：");
                int choose = input.nextInt();
                switch(choose){
                    case 0:creatAccount();System.out.println();break;
                    case 1:checkBalance(account);System.out.println();break;
                    case 2:withDraw(account);System.out.println();break;
                    case 3:deposit(account);System.out.println();break;
                    case 4:detailOfTransaction(account);System.out.println();break;
                    case 5:changePassword(account);System.out.println();break;
                    case 6:inputToFile();System.exit(0);
                    default:System.out.println("系统里面没有这种操作，请输入正确的功能");menu();
                }
            }
        }
        else{
            System.out.println("没有这项选择,请重新输入");
            menu();
        }
    }

    public static void creatAccount() throws Exception{
        System.out.print("请输入你的真实姓名:");
        String name = input.next();
        System.out.print("请输入密码(password):");
        String password = input.next();
        System.out.print("请输入账户余额(balance):");
        double balance = input.nextDouble();
        System.out.print("请输入年利率(annualInterestRate):");
        double annualInterestRate = input.nextDouble();
        Account2 account = new Account2(name,balance,password,annualInterestRate);
        accounts.put(account.getId(),account);
        System.out.println("创建成功，请记住你的id：" + account.getId());
    }

    public static void checkBalance(Account2 account){
        if (account != null){
            System.out.println("目前的账户的余额为：" + account.getBalance());
        }
    }

    public static void withDraw(Account2 account) {
        if (account != null){
            System.out.print("请输入您的取款金额：");
            double outMoney = input.nextDouble();
            account.withDraw(outMoney);
        }
    }

    public static void deposit(Account2 account){
        if (account != null){
            System.out.print("请输入您的存款金额：");
            double inMoney = input.nextDouble();
            account.deposit(inMoney);
        }
    }

    /**
     * 打印出支取记录
     *
     * 把account对象中的ArrayList<Transaction>获取出来
     * 遍历打印
     * @throws ClassNotFoundException
     */
    public static void detailOfTransaction(Account2 account){
        if(account != null){
            for(Transaction t : account.getTransactions()){
                System.out.println(t.getDescription());
            }
        }
    }

    public static void changePassword(Account2 account){
        if (account != null){
            System.out.print("请输入你原来的密码：");
            String op = input.next();
            System.out.print("请输入新密码：");
            String nw1 = input.next();
            System.out.print("再次输入新的密码进行确认： ");
            String nw2 = input.next();

            account.modifyPassword(op,nw1,nw2);
        }
    }

    //将对象写入文件
    public static void inputToFile(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(accounts);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //将对象从文件读出来
    public static HashMap<Integer, Account2> outputFromFile() throws ClassNotFoundException{
        HashMap<Integer, Account2> newAccounts = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            newAccounts = (HashMap<Integer, Account2>)in.readObject();
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newAccounts;
    }

    public static void main(String[] args) throws Exception{
        new ATMMachine2().menu();
    }
}

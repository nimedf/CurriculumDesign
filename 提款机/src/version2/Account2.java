package version2;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import version1.Account1;

public class Account2 extends Account1 implements Serializable{
    // 修改密码时，两次密码不一致
    public static final int TWICE_NOT_SAME = 1;
    // 密码错误
    public static final int WRONG_PASSWORD = 2;
    // 非法的密码，范围不在6-10位内
    public static final int INVALID_PASSWORD = 3;

    private String password; 		//存储账号密码
    private String name;  			//存储客户名字
    ArrayList<Transaction> transactions = new ArrayList<>(); //记录交易记录

    /*
     * 判断密码是否是合格的，若是合格的，则返回true，否则返回false
     * 判断密码是否在6到10位数字或字母,不满足则返回false
     * 判断密码是否只包含数字或字母，不满足则返回false
     */
    public boolean isPassword(String password){
        int flag = 0;
        if (password.length() >= 6 && password.length() <= 10)
            flag = 1;
        else
            flag = 0;

        for (int i = 0; i < password.length(); i++){
            char ch = password.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                flag = 1;
            }
            else
                flag = 0;
        }
        if (flag == 1)
            return true;
        else
            return false;
    }

    /*
     * Account2的构造方法，创建一个带客户姓名，账户余额，密码，年利率初始值的一个Account2的对象
     * 账户ID是在建立对象时自动生成的通过调用randomID()方法
     */
    public Account2(String name, double balance, String password,double annualInterestRate) throws Exception{
        setName(name);

        if (!isPassword(password))
            throw new Exception("密码设置错误,密码只能设置为数字或者字母且只能在6位到10位数之间");
        setPassword(password);

        super.setAnnualInterestRate(annualInterestRate);
        super.setId(randomID());

        if (balance < 0){
            throw new Exception("账户余额不能为负数");
        }
        super.setBalance(balance);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /*
     * 获取id号，方法是把从100000到1000000中所有的数放入一个id.txt的文件中
     * 再通过读取到一个数组中，通过随机函数获取一个随机下标值
     * 在把已经抽取到的下标所表示的那个id号放入数组最后
     * 最后删除最后一个元素，就不会出现同一个id号
     */
    public int randomID() throws FileNotFoundException{
        File file = new File("id.txt");
        Scanner input = new Scanner(file);

        int[] num = new int[900000];
        int i = 0;
        while (input.hasNext()){
            num[i] = input.nextInt();
            i++;
        }
        input.close();
        int len = num.length;
        int index = (int)(Math.random() * len);
        int id = num[index];
        int t = num[len-1];
        num[len-1] = num[index];
        num[index] = t;
        len -= 1;
        PrintWriter output = new PrintWriter("id.txt");
        for(i = 0; i < len; i++){
            output.print(num[i] + " ");
        }
        output.close();
        return id;
    }

    //若是当天超过5000则返回true，否则返回false
    public boolean isOverWithDraw(double money){
        double account = 0;
        Date newDate = new Date();
        for (int i = transactions.size() - 1;i >= 0; i--){
            Transaction t = transactions.get(i);

            if(!t.isToday(newDate))
                break;

            if (t.getType() == t.GET){
                account += t.getMoney();
                if (account > 5000)
                    return true;
            }
        }
        return money + account > 5000;
    }

    /*
     * (non-Javadoc)
     * @see version1.Account1#withDraw(double)
     */
    @Override
    public int withDraw(double outMoney){
        if (outMoney < 0){
            System.out.println("取款失败，取款金额不能为负数");
            return FAILURE;
        }
        else if(outMoney % 100 != 0){
            System.out.println("取款失败，取款金额只能是100的整数倍");
            return  FAILURE;
        }
        else if (outMoney > super.getBalance()){
            System.out.println("取款失败，账户余额不足");
            return FAILURE;
        }
        else if (isOverWithDraw(outMoney)){
            System.out.println("取款失败，今日取款已达上限");
            return FAILURE;
        }
        else {
            setBalance(super.getBalance() - outMoney);
            System.out.println("取款成功，欢迎下次光临");
            transactions.add(new Transaction(Transaction.GET, getBalance(), outMoney));
            return SUCCESS;
        }
    }

    @Override
    public int deposit(double inMoney){
        if (inMoney < 0){
            System.out.println("存款金额错误，不能为负");
            return FAILURE;
        }
        setBalance(getBalance() + inMoney);
        transactions.add(new Transaction(Transaction.PUT,getBalance(),inMoney));
        System.out.println("存款成功，欢迎下次在来");
        return SUCCESS;
    }

    /*
     * 修改密码
     */
    public int modifyPassword(String op, String nw1, String nw2){
        if (!op.equals(password)){
            System.out.println("账户原来的密码输入错误");
            return INVALID_PASSWORD;
        }
        if (!nw1.equals(nw2)){
            System.out.println("两次输入的密码不一样，请重新输入");
            return TWICE_NOT_SAME;
        }
        if (!isPassword(nw1)){
            System.out.println("账户原来的密码输入错误");
            return INVALID_PASSWORD;
        }
        if (op.equals(nw1))	{
            System.out.println("新的密码不能和原来的密码一样。");
            return WRONG_PASSWORD;
        }

        this.password = nw1;
        System.out.println("密码修改成功");
        return SUCCESS;
    }

}

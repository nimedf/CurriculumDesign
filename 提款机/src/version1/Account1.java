package version1;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class Account1 implements Serializable{
    public static final int FAILURE = -1; //存款失败，或者取款失败，返回-1
    public static final int SUCCESS = 0;  //存款，取款成功，返回0

    private int id = 0;						//账户id
    private double balance = 0;				//账户余额
    private double annualInterestRate = 0;	//利率annualInterestRate
    private Date dateCreated; 				//账户的开户日期

    DecimalFormat df = new DecimalFormat("#.00"); //控制所有的double类型的数据保留两位小数

    //默认的一个无 参数的构造方法
    public Account1(){
        dateCreated = new Date();
    }

    /*
     * 带特定的id和余额的有参构造方法，
     * 初始余额不能为负数,账户id只能为6位数
    */
    public Account1(int id, double balance){
        if (id < 1000000)
            setId(id);
        else
            System.out.println("账户id超过6位数，错误");
        if (balance >= 0)
            setBalance(balance);
        else
            System.out.println("初始化余额不能为负数");
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        if (id < 1000000)
            this.id = id;
        else
            System.out.println("账户id超过6位数，错误");
    }

    /*df.format()返回的是一个字符串，
     * 调用Double.parserDouble()来将其转换为浮点数
     */
    public double getBalance(){
        return Double.parseDouble(df.format(balance));
    }

    public void setBalance(double balance){
        if (balance >= 0)
            this.balance = balance;
        else
            System.out.println("初始化余额不能为负数");
    }

    public double getAnnualInterestRate(){
        return Double.parseDouble(df.format(annualInterestRate / 100.0));
    }

    public void setAnnualInterestRate(double ann){
        this.annualInterestRate = ann;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    //获取月利率,
    public double getMonthlyInterestRate(){
        return annualInterestRate / 1200.0;
    }

    /*从账户余额中取出特定的金额
     * 若取款金额超过账户余额，返回FAILURE =-1表明取款失败
     * 取款金额为负数，返回FAILURE = -1表明取款失败
     * 一切都满足条件则返回SUCCESS = 0表明取款成功
     */
    public int withDraw(double outMoney){
        if (outMoney > balance){
            System.out.println("余额不足，目前余额为：" + this.balance);
            return FAILURE;
        }
        else if (outMoney < 0){
            System.out.println("取款金额错误，不能为负");
            return FAILURE;
        }
        else{
            balance -= outMoney;
            System.out.println("取款成功，当前余额为: " + balance);
            return SUCCESS;
        }
    }

    /*从账户余额中存入特定的金额
     * 存款金额为负数，返回FAILURE = -1表明存款失败
     * 一切都满足条件则返回SUCCESS = 0表明存款成功
     */
    public int deposit(double inMoney){
        if (inMoney < 0){
            System.out.println("存款金额错误，不能为负");
            return FAILURE;
        }
        else{
            balance += inMoney;
            System.out.println("存款成功，当前余额为: " + balance);
            return SUCCESS;
        }
    }

}

package version2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

public class Transaction implements Serializable {
    public static final char GET = 'w'; //取钱
    public static final char PUT = 'u'; //存钱

    private Date date;					//保存日期
    private double balance;				//余额的记录
    private double money;				//交易金额
    private char type;					//交易的类型，是取钱还是存钱
    private String description;				//记录交易记录的字符串

    public Transaction(char type, double balance, double money){
        this.type = type;
        this.balance = balance;
        this.money = money;
        date = new Date();
        this.description = date.toString() + " " + (type == 'w' ? "取出 ":"存入") + money + "当前余额为：" + balance;
    }

    public double getBalance(){
        return balance;
    }

    public String getDescription(){
        return description;
    }

    public Date getDate() {
        return date;
    }

    public double getMoney() {
        return money;
    }

    public char getType() {
        return type;
    }


    /*
     * 判断是否是今天，
     * 若是，则返回true，否则返回false
     */
    public boolean isToday(Date newDate){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if(fmt.format(newDate).toString().equals(fmt.format(new Date()).toString())){
            return true;
        }
        else
            return false;
    }

    @Override
    public String toString(){
        return description;
    }
}

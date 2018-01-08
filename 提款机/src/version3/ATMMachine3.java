package version3;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import version2.ATMMachine2;
import version2.Account2;
import version2.Transaction;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;



public class ATMMachine3 extends Application{
    protected static Stage primaryStage = new Stage();
    private static HashMap<Integer,Account2> accounts = null;
    private static File file = new File("account.dat");
    private static int ID;          //保存用户的id
    private ArrayList<Transaction> transactions = new ArrayList<>(); //记录交易记录

    public ATMMachine3() throws ClassNotFoundException{
        accounts = outputFromFile();

        if(accounts == null){
            accounts = new HashMap<Integer, Account2>();
        }
    }

    //将对象从文件读出来
    public HashMap<Integer, Account2> outputFromFile() throws ClassNotFoundException{
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

    //将对象写入文件
    public void inputToFile(){
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

    @Override
    public void start(Stage stage){
        Text textTiltle = new Text("ATM");
        textTiltle.setStyle("-fx-font-size: 60px;");
        Button btLogin = new Button("登录");
        btLogin.setStyle("-fx-min-width: 150px;-fx-min-height: 100px;-fx-font-size: 40px");
        Button btRegister = new Button("注册");
        btRegister.setStyle("-fx-min-width: 150px;-fx-min-height: 100px;-fx-font-size: 40px");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(btLogin,btRegister);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(textTiltle,hBox);
        vBox.setSpacing(150);
        vBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);

        btLogin.setOnAction(event -> {
                Scene scene = new Login().start();
                primaryStage.setScene(scene);
                primaryStage.show();
        });

        btRegister.setOnAction(event -> {
            primaryStage.setScene(new Register().start());
            primaryStage.show();
        });

        Scene scene = new Scene(borderPane,400,400);
        primaryStage.setTitle("ATM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        Application.launch(args);
    }

    /*
 *  这是登录成功之后的界面
 *  包含存钱、取钱、查询余额、交易记录、修改密码、退出这六项功能
 *  设置了留个按钮对应六个功能，每三个功能放在一个VBox面板中，
 *  在通过borderPane进行布局
 */
    class Menu {
        public Scene start(){

            Button btWithDraw = new Button("取钱");
            btWithDraw.setStyle("-fx-font-size: 20px;-fx-min-height: 100px;-fx-min-width: 80px;");
            Button btDespite = new Button("存钱");
            btDespite.setStyle("-fx-font-size: 20px;-fx-min-height: 100px;-fx-min-width: 80px;");
            Button btSearch = new Button("查询余额");
            btSearch.setStyle("-fx-font-size: 20px;-fx-min-height: 100px;-fx-min-width: 80px;");
            Button btTransaction = new Button("交易记录");
            btTransaction.setStyle("-fx-font-size: 20px;-fx-min-height: 100px;-fx-min-width: 80px;");
            Button btchangePassword = new Button("修改密码");
            btchangePassword.setStyle("-fx-font-size: 20px;-fx-min-height: 100px;-fx-min-width: 80px;");
            Button btExit = new Button("退出");
            btExit.setStyle("-fx-font-size: 20px;-fx-min-height: 100px;-fx-min-width: 80px;");

            VBox vBox1 = new VBox();
            vBox1.getChildren().addAll(btWithDraw,btDespite,btExit);

            Text textTitle = new Text("ATM");
            textTitle.setStyle("-fx-font-size: 60px;");
            Text text = new Text("选择你\n需要的功能");
            text.setStyle("-fx-font-size: 30px;");

            VBox vBox2 = new VBox();
            vBox2.getChildren().addAll(btTransaction,btchangePassword,btSearch);

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(vBox1);
            borderPane.setRight(vBox2);
            borderPane.setTop(textTitle);
            borderPane.setCenter(text);
            borderPane.setAlignment(textTitle, Pos.CENTER);

            btExit.setOnAction(event -> {
                inputToFile();
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new Login().start());
                primaryStage.show();
            });

            btWithDraw.setOnAction(event -> {
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new WithDrawUI().start());
                primaryStage.show();
            });

            btDespite.setOnAction(event -> {
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new Despite().start());
                primaryStage.show();
            });

            btSearch.setOnAction(event -> {
                new checkMoney().start();
            });

            btTransaction.setOnAction(event -> {
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new TransactionUI().start());
                primaryStage.show();
            });

            btchangePassword.setOnAction(event -> {
                primaryStage.setScene(new ChangePassword().start());
                primaryStage.show();
            });

            Scene scene = new Scene(borderPane,400,400);
            return scene;
        }
    }

    //登录功能过的UI界面
    class Login {

        public Scene start(){

        /*
         *  账户文本框，以及密码文本框
         *  通过gridPane来进行布局的
         */
            GridPane gridPane1 = new GridPane();
            Text text = new Text(0,1,"登录");
            text.setStyle("-fx-font-size: 30px;");
            text.setFont(Font.font("登录",FontWeight.BOLD,FontPosture.ITALIC,20));
            gridPane1.add(text,1,0);
            gridPane1.setHalignment(text, HPos.LEFT);
            TextField username = new TextField();
            TextField password = new TextField();
            gridPane1.add(new Label("Username:"),0,1);
            gridPane1.add(username,1,1);
            gridPane1.add(new Label("Password:"),0,2);
            gridPane1.add(password,1,2);
            gridPane1.setVgap(10);
            gridPane1.setAlignment(Pos.CENTER);

        /*
        *   小键盘设置与布局
        *   一共十二个键，就是十二个按钮
        */
            Button bt1 = new Button("1");
            bt1.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt2 = new Button("2");
            bt2.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt3 = new Button("3");
            bt3.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt4 = new Button("4");
            bt4.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt5 = new Button("5");
            bt5.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt6 = new Button("6");
            bt6.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt7 = new Button("7");
            bt7.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt8 = new Button("8");
            bt8.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt9 = new Button("9");
            bt9.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt0 = new Button("0");
            bt0.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt10 = new Button("删除");
            bt10.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt11 = new Button(".");
            bt11.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击username这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            username.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = username.getText() + "1";
                    username.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = username.getText() + "2";
                    username.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = username.getText() + "3";
                    username.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = username.getText() + "4";
                    username.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = username.getText() + "5";
                    username.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = username.getText() + "6";
                    username.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = username.getText() + "7";
                    username.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = username.getText() + "8";
                    username.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = username.getText() + "9";
                    username.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = username.getText() + "0";
                    username.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (username.getText().length() != 0) {
                        String str = username.getText().substring(0, username.getText().length() - 1);
                        username.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = username.getText() + ".";
                    username.setText(str);
                });
            });

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击password这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            password.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = password.getText() + "1";
                    password.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = password.getText() + "2";
                    password.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = password.getText() + "3";
                    password.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = password.getText() + "4";
                    password.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = password.getText() + "5";
                    password.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = password.getText() + "6";
                    password.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = password.getText() + "7";
                    password.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = password.getText() + "8";
                    password.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = password.getText() + "9";
                    password.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = password.getText() + "0";
                    password.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (password.getText().length() != 0) {
                        String str = password.getText().substring(0, password.getText().length() - 1);
                        password.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = password.getText() + ".";
                    password.setText(str);
                });
            });

            GridPane gridPane = new GridPane();
            gridPane.add(bt1,0,0);
            gridPane.add(bt2,1,0);
            gridPane.add(bt3,2,0);
            gridPane.add(bt4,0,1);
            gridPane.add(bt5,1,1);
            gridPane.add(bt6,2,1);
            gridPane.add(bt7,0,2);
            gridPane.add(bt8,1,2);
            gridPane.add(bt9,2,2);
            gridPane.add(bt11,0,3);
            gridPane.add(bt0,1,3);
            gridPane.add(bt10,2,3);
            gridPane.setAlignment(Pos.CENTER);

            Button btLogin = new Button("登录");
            btLogin.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");
            Button btRegister = new Button("注册");
            btRegister.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(gridPane1,gridPane);
            vBox.setSpacing(15);
            vBox.setMargin(gridPane1,new Insets(25,0,0,0));

            HBox hBox = new HBox();
            hBox.getChildren().addAll(btLogin,btRegister);
            hBox.setSpacing(50);

            BorderPane borderPane = new BorderPane();
            borderPane.setBottom(hBox);
            borderPane.setCenter(vBox);

            //登录功能，如果密码错误或者账户不存在，不合法都弹出报错
            btLogin.setOnAction(event -> {
                //创建一个alert的对象，并申明这个弹出框的类型为information
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (username.getText() == null || password.getText() == null){
                    alert.setTitle("提示框");
                    alert.setHeaderText("错误");
                    alert.setContentText("账户,密码不能为空");
                    alert.show();
                }
                else {
                    int id = Integer.valueOf(username.getText());
                    //通过id从accounts这个集合里遍历出我们所登录的账户
                    ID = id;
                    Account2 account = accounts.get(id);

                    if (username.getText().length() != 6) {
                        alert.setTitle("提示框");
                        alert.setHeaderText("错误");
                        alert.setContentText("用户不合法，id为六位数");
                        alert.show();
                    } else if (account == null) {
                        alert.setTitle("提示框");
                        alert.setHeaderText("错误");
                        alert.setContentText("用户不存在");
                        alert.show();
                    } else if (!password.getText().equals(account.getPassword())) {
                        alert.setTitle("提示框");
                        alert.setHeaderText("错误");
                        alert.setContentText("密码输入错误");
                        alert.show();
                    } else {
                        Scene scene = new Menu().start();
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                }
            });

            btRegister.setOnAction(event -> {
                primaryStage.setScene(new Register().start());
                primaryStage.show();
            });
            Scene scene = new Scene(borderPane,400,400);
            return scene;
        }
    }

    //取款功能的默认UI界面
    class WithDrawUI{

        public Scene start() {
            //创建一个弹出框的对象，并在后面实现对这个弹出框的初始化
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //通过ID在accounts这个集合里面遍历出这个账户
            Account2 account = accounts.get(ID);

            //创建一组按钮，用于默认取款界面的一些快捷的取款金额
            Button bt100 = new Button("100");
            bt100.setStyle("-fx-min-width: 150px;-fx-min-height: 40px;");
            Button bt200 = new Button("200");
            bt200.setStyle("-fx-min-width: 150px;-fx-min-height: 40px;");
            Button bt500 = new Button("500");
            bt500.setStyle("-fx-min-width: 150px;-fx-min-height: 40px;");
            Button bt1000 = new Button("1000");
            bt1000.setStyle("-fx-min-width: 150px;-fx-min-height: 40px;");
            Button bt2000 = new Button("200");
            bt2000.setStyle("-fx-min-width: 150px;-fx-min-height: 40px;");
            Button btOther = new Button("其他");
            btOther.setStyle("-fx-min-width: 150px;-fx-min-height: 40px;");

            VBox vBoxLeft = new VBox();
            vBoxLeft.getChildren().addAll(bt100,bt200,bt500);
            vBoxLeft.setStyle("-fx-start-margin: 20px;");
            vBoxLeft.setAlignment(Pos.CENTER);
            vBoxLeft.setSpacing(50);
            VBox vBoxRight = new VBox();
            vBoxRight.setStyle("-fx-end-margin: 20px;");
            vBoxRight.setAlignment(Pos.CENTER);
            vBoxRight.setSpacing(50);
            vBoxRight.getChildren().addAll(bt1000,bt2000,btOther);

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(vBoxLeft);
            borderPane.setRight(vBoxRight);

            Text text = new Text("取款界面");
            text.setStyle("-fx-font-size: 50px;");
            text.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC,20));
            borderPane.setTop(text);
            borderPane.setAlignment(text,Pos.CENTER);

            bt100.setOnAction(event -> {
                if (account.isOverWithDraw(100)){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("今日取款金额已达上限");
                    alert.show();
                }
                else if (100 > account.getBalance()){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("余额不足");
                    alert.show();
                }
                else {
                    account.withDraw(100);
                    alert.setTitle("提示框");
                    alert.setContentText("取款成功");
                    alert.show();

                }
            });

            bt200.setOnAction(event -> {
                if (account.isOverWithDraw(200)){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("今日取款金额已达上限");
                    alert.show();
                }
                else if (200 > account.getBalance()){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("余额不足");
                    alert.show();
                }
                else {
                    account.withDraw(200);
                    alert.setTitle("提示框");
                    alert.setContentText("取款成功");
                    alert.show();

                }
            });

            bt500.setOnAction(event -> {
                if (account.isOverWithDraw(500)){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("今日取款金额已达上限");
                    alert.show();
                }
                else if (500 > account.getBalance()){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("余额不足");
                    alert.show();
                }
                else {
                    account.withDraw(500);
                    alert.setTitle("提示框");
                    alert.setContentText("取款成功");
                    alert.show();

                }
            });

            bt1000.setOnAction(event -> {
                if (account.isOverWithDraw(1000)){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("今日取款金额已达上限");
                    alert.show();
                }
                else if (1000 > account.getBalance()){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("余额不足");
                    alert.show();
                }
                else {
                    account.withDraw(1000);
                    alert.setTitle("提示框");
                    alert.setContentText("取款成功");
                    alert.show();

                }
            });

            bt2000.setOnAction(event -> {
                if (account.isOverWithDraw(2000)){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("今日取款金额已达上限");
                    alert.show();
                }
                else if (2000 > account.getBalance()){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("余额不足");
                    alert.show();
                }
                else {
                    account.withDraw(2000);
                    alert.setTitle("提示框");
                    alert.setContentText("取款成功");
                    alert.show();

                }
            });

            btOther.setOnAction(event -> {
                inputToFile();
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new WithDraw().start());
                primaryStage.show();
            });

            Scene scene = new Scene(borderPane,500,500);
            return scene;
        }
    }

    //取款功能的其他的UI界面
    class WithDraw{

        public Scene start(){
            /*
             * 设置一个标题，以及一个取款金额输入框
             */
            GridPane gridPane1 = new GridPane();
            Text text = new Text(0,1,"取款");
            text.setStyle("-fx-font-size: 30px;");
            text.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC,20));
            gridPane1.add(text,1,0);
            gridPane1.setHalignment(text, HPos.LEFT);
            TextField withDrawMoney = new TextField();
            gridPane1.add(new Label("outMoney:"),0,1);
            gridPane1.add(withDrawMoney,1,1);
            gridPane1.setVgap(20);
            gridPane1.setAlignment(Pos.CENTER);

        /*
        *   小键盘设置与布局
        *   一共十二个键，就是十二个按钮
        */
            Button bt1 = new Button("1");
            bt1.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt2 = new Button("2");
            bt2.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt3 = new Button("3");
            bt3.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt4 = new Button("4");
            bt4.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt5 = new Button("5");
            bt5.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt6 = new Button("6");
            bt6.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt7 = new Button("7");
            bt7.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt8 = new Button("8");
            bt8.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt9 = new Button("9");
            bt9.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt0 = new Button("0");
            bt0.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt10 = new Button("删除");
            bt10.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt11 = new Button(".");
            bt11.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击username这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            withDrawMoney.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "1";
                    withDrawMoney.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "2";
                    withDrawMoney.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "3";
                    withDrawMoney.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "4";
                    withDrawMoney.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "5";
                    withDrawMoney.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "6";
                    withDrawMoney.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "7";
                    withDrawMoney.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "8";
                    withDrawMoney.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "9";
                    withDrawMoney.setText(str);
                });
                bt0.setOnAction(event -> {
                        String str = withDrawMoney.getText() + "0";
                        withDrawMoney.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (withDrawMoney.getText().length() != 0) {
                        String str = withDrawMoney.getText().substring(0, withDrawMoney.getText().length() - 1);
                        withDrawMoney.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = withDrawMoney.getText() + ".";
                    withDrawMoney.setText(str);
                });
            });

            //小键盘的设置
            GridPane gridPane = new GridPane();
            gridPane.add(bt1,0,0);
            gridPane.add(bt2,1,0);
            gridPane.add(bt3,2,0);
            gridPane.add(bt4,0,1);
            gridPane.add(bt5,1,1);
            gridPane.add(bt6,2,1);
            gridPane.add(bt7,0,2);
            gridPane.add(bt8,1,2);
            gridPane.add(bt9,2,2);
            gridPane.add(bt11,0,3);
            gridPane.add(bt0,1,3);
            gridPane.add(bt10,2,3);
            gridPane.setAlignment(Pos.CENTER);

            Button btOK = new Button("确认");
            btOK.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");
            Button btGiveUp = new Button("取消");
            btGiveUp.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(gridPane1,gridPane);
            vBox.setSpacing(15);
            vBox.setMargin(gridPane1,new Insets(25,0,0,0));

            HBox hBox = new HBox();
            hBox.getChildren().addAll(btOK,btGiveUp);
            hBox.setSpacing(50);

            BorderPane borderPane = new BorderPane();
            borderPane.setBottom(hBox);
            borderPane.setCenter(vBox);



            btOK.setOnAction(event -> {
                //创建一个弹出框的对象，并在后面实现对这个弹出框的初始化
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //通过ID在accounts这个集合里面遍历出这个账户
                Account2 account = accounts.get(ID);
                //把文本框里的内容转换成数字
                int outMoney = Integer.valueOf(withDrawMoney.getText());

                /*
                *   开始取款操作，
                *   取款金额不能为负数，不能超过账户余额，
                *   只能是100 的整数倍，每天的取款金额不能超过5000
                *
                * */
                if (withDrawMoney.getText() == null){
                }
                else if (account.isOverWithDraw(outMoney)){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("今日取款金额已达上限");
                    alert.show();
                }else if (outMoney < 0){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("取款金额不能为负数");
                    alert.show();
                }
                else if (outMoney % 100 != 0){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("取款金额必须是100的整数倍");
                    alert.show();
                }
                else if (outMoney > account.getBalance()){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("余额不足");
                    alert.show();
                }
                else {
                    account.withDraw(outMoney);
                    alert.setTitle("提示框");
                    alert.setContentText("取款成功");
                    alert.show();

                }
            });

            //若点击取消按钮则返回到才菜单界面并保存文件
            btGiveUp.setOnAction(event -> {
                inputToFile();
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new Menu().start());
                primaryStage.show();
            });

            Scene scene = new Scene(borderPane,400,400);
            return scene;
        }
    }

    //存款功能的UI界面
    class Despite{

        public Scene start(){
            /*
             * 设置一个标题，以及一个存款金额输入框
             */
            GridPane gridPane1 = new GridPane();
            Text text = new Text(0,1,"存款");
            text.setStyle("-fx-font-size: 30px;");
            text.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC,20));
            gridPane1.add(text,1,0);
            gridPane1.setHalignment(text, HPos.LEFT);
            TextField withDrawMoney = new TextField();
            gridPane1.add(new Label("InputMoney:"),0,1);
            gridPane1.add(withDrawMoney,1,1);
            gridPane1.setVgap(20);
            gridPane1.setAlignment(Pos.CENTER);

        /*
        *   小键盘设置与布局
        *   一共十二个键，就是十二个按钮
        */
            Button bt1 = new Button("1");
            bt1.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt2 = new Button("2");
            bt2.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt3 = new Button("3");
            bt3.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt4 = new Button("4");
            bt4.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt5 = new Button("5");
            bt5.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt6 = new Button("6");
            bt6.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt7 = new Button("7");
            bt7.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt8 = new Button("8");
            bt8.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt9 = new Button("9");
            bt9.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt0 = new Button("0");
            bt0.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt10 = new Button("删除");
            bt10.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt11 = new Button(".");
            bt11.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击username这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            withDrawMoney.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "1";
                    withDrawMoney.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "2";
                    withDrawMoney.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "3";
                    withDrawMoney.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "4";
                    withDrawMoney.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "5";
                    withDrawMoney.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "6";
                    withDrawMoney.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "7";
                    withDrawMoney.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "8";
                    withDrawMoney.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "9";
                    withDrawMoney.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = withDrawMoney.getText() + "0";
                    withDrawMoney.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (withDrawMoney.getText() != null) {
                        String str = withDrawMoney.getText().substring(0, withDrawMoney.getText().length() - 1);
                        withDrawMoney.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = withDrawMoney.getText() + ".";
                    withDrawMoney.setText(str);
                });
            });

            //小键盘的设置
            GridPane gridPane = new GridPane();
            gridPane.add(bt1,0,0);
            gridPane.add(bt2,1,0);
            gridPane.add(bt3,2,0);
            gridPane.add(bt4,0,1);
            gridPane.add(bt5,1,1);
            gridPane.add(bt6,2,1);
            gridPane.add(bt7,0,2);
            gridPane.add(bt8,1,2);
            gridPane.add(bt9,2,2);
            gridPane.add(bt11,0,3);
            gridPane.add(bt0,1,3);
            gridPane.add(bt10,2,3);
            gridPane.setAlignment(Pos.CENTER);

            Button btOK = new Button("确认");
            btOK.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");
            Button btGiveUp = new Button("取消");
            btGiveUp.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(gridPane1,gridPane);
            vBox.setSpacing(15);
            vBox.setMargin(gridPane1,new Insets(25,0,0,0));

            HBox hBox = new HBox();
            hBox.getChildren().addAll(btOK,btGiveUp);
            hBox.setSpacing(50);

            BorderPane borderPane = new BorderPane();
            borderPane.setBottom(hBox);
            borderPane.setCenter(vBox);



            btOK.setOnAction(event -> {
                //创建一个弹出框的对象，并在后面实现对这个弹出框的初始化
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //通过ID在accounts这个集合里面遍历出这个账户
                Account2 account = accounts.get(ID);
                //把文本框里的内容转换成数字
                int inputMoney = Integer.valueOf(withDrawMoney.getText());

                /*
                *
                * */
                if (withDrawMoney.getText() == null){

                }
                else if (inputMoney < 0){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("存款金额不能为负数");
                    alert.show();
                }
                else if (inputMoney % 100 != 0){
                    alert.setTitle("警告");
                    alert.setHeaderText("错误");
                    alert.setContentText("存款金额必须是100的整数倍");
                    alert.show();
                }
                else {
                    account.deposit(inputMoney);
                    alert.setTitle("提示框");
                    alert.setContentText("存款成功");
                    alert.show();

                }
            });

            //若点击取消按钮则返回到才菜单界面
            btGiveUp.setOnAction(event -> {
                inputToFile();
                primaryStage.setTitle("ATM");
                primaryStage.setScene(new Menu().start());
                primaryStage.show();
            });

            Scene scene = new Scene(borderPane,400,400);
            return scene;
        }
    }

    //查询余额的UI界面
    class checkMoney {
        public void start(){
            Account2 account = accounts.get(ID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示框");
            alert.setHeaderText(account.getName() + "的账户余额为：" + account.getBalance());
            alert.show();
        }

    }

    //交易记录的UI界面
    class TransactionUI {
        public Scene start() {
            Account2 account = accounts.get(ID);
            VBox vBox = new VBox();
            for (Transaction t : account.getTransactions()){
                Text text = new Text(t.getDescription());
                vBox.getChildren().add(text);
            }
            vBox.setSpacing(20);
            vBox.setAlignment(Pos.CENTER);
            Button btOK = new Button("OK");
            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(vBox);
            borderPane.setBottom(btOK);
            borderPane.setAlignment(btOK,Pos.CENTER);

            btOK.setStyle("-fx-min-width: 40px;-fx-min-height: 100px;-fx-font-size: 30px;");

            btOK.setOnAction(event -> {
                primaryStage.setScene(new Menu().start());
                primaryStage.show();
            });

            Scene scene = new Scene(borderPane,600,600);
            return scene;
        }
    }

    //修改密码的UI界面
    class ChangePassword {

        public Scene start(){

        /*
         *  三个密码文本框
         *  通过gridPane来进行布局的
         */
            GridPane gridPane1 = new GridPane();
            Text text = new Text(0,1,"修改密码");
            text.setStyle("-fx-font-size: 30px;");
            text.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC,20));
            gridPane1.add(text,1,0);
            gridPane1.setHalignment(text, HPos.LEFT);

            TextField oldPassword = new TextField();
            TextField newPassword = new TextField();
            TextField newPasswordOK = new TextField();

            gridPane1.add(new Label("oldPassword:"),0,1);
            gridPane1.add(oldPassword,1,1);
            gridPane1.add(new Label("newPassword:"),0,2);
            gridPane1.add(newPassword,1,2);
            gridPane1.add(new Label("newPasswordOK:"),0,3);
            gridPane1.add(newPasswordOK,1,3);
            gridPane1.setVgap(10);
            gridPane1.setAlignment(Pos.CENTER);

        /*
        *   小键盘设置与布局
        *   一共十二个键，就是十二个按钮
        */
            Button bt1 = new Button("1");
            bt1.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt2 = new Button("2");
            bt2.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt3 = new Button("3");
            bt3.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt4 = new Button("4");
            bt4.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt5 = new Button("5");
            bt5.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt6 = new Button("6");
            bt6.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt7 = new Button("7");
            bt7.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt8 = new Button("8");
            bt8.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt9 = new Button("9");
            bt9.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt0 = new Button("0");
            bt0.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt10 = new Button("删除");
            bt10.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt11 = new Button(".");
            bt11.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击username这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            oldPassword.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = oldPassword.getText() + "1";
                    oldPassword.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = oldPassword.getText() + "2";
                    oldPassword.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = oldPassword.getText() + "3";
                    oldPassword.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = oldPassword.getText() + "4";
                    oldPassword.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = oldPassword.getText() + "5";
                    oldPassword.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = oldPassword.getText() + "6";
                    oldPassword.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = oldPassword.getText() + "7";
                    oldPassword.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = oldPassword.getText() + "8";
                    oldPassword.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = oldPassword.getText() + "9";
                    oldPassword.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = oldPassword.getText() + "0";
                    oldPassword.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (oldPassword.getText().length() != 0) {
                        String str = oldPassword.getText().substring(0, oldPassword.getText().length() - 1);
                        oldPassword.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = oldPassword.getText() + ".";
                    oldPassword.setText(str);
                });
            });

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击password这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            newPassword.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = newPassword.getText() + "1";
                    newPassword.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = newPassword.getText() + "2";
                    newPassword.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = newPassword.getText() + "3";
                    newPassword.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = newPassword.getText() + "4";
                    newPassword.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = newPassword.getText() + "5";
                    newPassword.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = newPassword.getText() + "6";
                    newPassword.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = newPassword.getText() + "7";
                    newPassword.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = newPassword.getText() + "8";
                    newPassword.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = newPassword.getText() + "9";
                    newPassword.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = newPassword.getText() + "0";
                    newPassword.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (newPassword.getText().length() != 0) {
                        String str = newPassword.getText().substring(0, newPassword.getText().length() - 1);
                        newPassword.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = newPassword.getText() + ".";
                    newPassword.setText(str);
                });
            });

            newPasswordOK.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "1";
                    newPasswordOK.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "2";
                    newPasswordOK.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "3";
                    newPasswordOK.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "4";
                    newPasswordOK.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "5";
                    newPasswordOK.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "6";
                    newPasswordOK.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "7";
                    newPasswordOK.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "8";
                    newPasswordOK.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "9";
                    newPasswordOK.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = newPasswordOK.getText() + "0";
                    newPasswordOK.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (newPasswordOK.getText().length() != 0) {
                        String str = newPasswordOK.getText().substring(0, newPasswordOK.getText().length() - 1);
                        newPasswordOK.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = newPasswordOK.getText() + ".";
                    newPasswordOK.setText(str);
                });
            });

            GridPane gridPane = new GridPane();
            gridPane.add(bt1,0,0);
            gridPane.add(bt2,1,0);
            gridPane.add(bt3,2,0);
            gridPane.add(bt4,0,1);
            gridPane.add(bt5,1,1);
            gridPane.add(bt6,2,1);
            gridPane.add(bt7,0,2);
            gridPane.add(bt8,1,2);
            gridPane.add(bt9,2,2);
            gridPane.add(bt11,0,3);
            gridPane.add(bt0,1,3);
            gridPane.add(bt10,2,3);
            gridPane.setAlignment(Pos.CENTER);

            Button btLogin = new Button("确认");
            btLogin.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");
            Button btRegister = new Button("取消");
            btRegister.setStyle("-fx-min-height: 80px;-fx-min-width: 175px;-fx-font-size: 20px;");

            VBox vBox = new VBox();
            vBox.getChildren().addAll(gridPane1,gridPane);
            vBox.setSpacing(15);
            vBox.setMargin(gridPane1,new Insets(25,0,0,0));

            HBox hBox = new HBox();
            hBox.getChildren().addAll(btLogin,btRegister);
            hBox.setSpacing(50);

            BorderPane borderPane = new BorderPane();
            borderPane.setBottom(hBox);
            borderPane.setCenter(vBox);

            //修改密码功能，新密码不能和原来的相同，不能小于6位数，也不能多于十位数
            btLogin.setOnAction(event -> {
                //创建一个alert的对象，并申明这个弹出框的类型为information
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Account2 account = accounts.get(ID);

                if (oldPassword.getText() == null || newPassword.getText() == null || newPasswordOK.getText() == null){
                    alert.setTitle("提示框");
                    alert.setHeaderText("密码不能为空");
                    alert.show();
                }
                else if (!account.getPassword().equals(oldPassword.getText())){
                    alert.setTitle("提示框");
                    alert.setHeaderText("原来的密码输入错误");
                    alert.show();
                }
                else if (oldPassword.getText().equals(newPassword.getText())){
                    alert.setTitle("提示框");
                    alert.setHeaderText("新的密码不能和原来的密码一样");
                    alert.show();
                }
                else if (!newPassword.getText().equals(newPasswordOK.getText())){
                    alert.setTitle("提示框");
                    alert.setHeaderText("两次输入的密码不一样");
                    alert.show();
                }
                else {
                    account.setPassword(newPassword.getText());
                    alert.setTitle("提示框");
                    alert.setHeaderText("密码修改成功");
                    alert.show();
                    primaryStage.setScene(new Login().start());
                    primaryStage.show();
                }
            });

            btRegister.setOnAction(event -> {
                primaryStage.setScene(new Menu().start());
                primaryStage.show();
            });
            Scene scene = new Scene(borderPane,400,400);
            return scene;
        }
    }

    //用户注册的界面
    class Register {
        public Scene start() {
            HBox hBox1 = new HBox();    //注册面板下侧的组件

            Button btRegister
                    = new Button("注册");
            btRegister.setStyle("-fx-font-size: 30px;-fx-min-height: 40px;-fx-min-width: 60px;");
            Button btGiveUp = new Button("取消");
            btGiveUp.setStyle("-fx-font-size: 30px;-fx-min-height: 40px;-fx-min-width: 60px;");
            hBox1.getChildren().addAll(btRegister, btGiveUp);
            hBox1.setSpacing(20);
            hBox1.setAlignment(Pos.BOTTOM_RIGHT);
            hBox1.setMargin(btGiveUp,new Insets(0,50,0,0));

            //面板右侧的组件
            TextField username = new TextField();
            TextField password = new TextField();
            TextField beginBalance = new TextField();
            TextField annualInterestRate = new TextField();
            GridPane gridPane1 = new GridPane();
            gridPane1.add(new Label("姓名："),0,0);
            gridPane1.add(username,1,0);
            gridPane1.add(new Label("密码："),0,1);
            gridPane1.add(password,1,1);
            gridPane1.add(new Label("初始金额："),0,2);
            gridPane1.add(beginBalance,1,2);
            gridPane1.add(new Label("年利率："),0,3);
            gridPane1.add(annualInterestRate,1,3);
            gridPane1.setVgap(20);
            gridPane1.setAlignment(Pos.CENTER);

             /*
            *   小键盘设置与布局
            *   一共十二个键，就是十二个按钮
            *   面板左侧的组件
            */
            Button bt1 = new Button("1");
            bt1.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt2 = new Button("2");
            bt2.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt3 = new Button("3");
            bt3.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt4 = new Button("4");
            bt4.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt5 = new Button("5");
            bt5.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt6 = new Button("6");
            bt6.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt7 = new Button("7");
            bt7.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt8 = new Button("8");
            bt8.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt9 = new Button("9");
            bt9.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt0 = new Button("0");
            bt0.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt10 = new Button("删除");
            bt10.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

            Button bt11 = new Button(".");
            bt11.setStyle("-fx-min-width: 80px;-fx-min-height: 40px;");

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击username这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            username.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = username.getText() + "1";
                    username.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = username.getText() + "2";
                    username.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = username.getText() + "3";
                    username.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = username.getText() + "4";
                    username.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = username.getText() + "5";
                    username.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = username.getText() + "6";
                    username.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = username.getText() + "7";
                    username.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = username.getText() + "8";
                    username.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = username.getText() + "9";
                    username.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = username.getText() + "0";
                    username.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (username.getText() != null) {
                        String str = username.getText().substring(0, username.getText().length() - 1);
                        username.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = username.getText() + ".";
                    username.setText(str);
                });
            });

        /*
        *   通过设置鼠标点击事件的注册器来进行监听
        *   当鼠标点击password这一栏的文本框时
        *   就对这一行进行输入，在通过button事件的监听来实现点击一个按钮就输入一个值
        *   每次就先通过getText方法获取当前文本框里的内容，在通过在这个内容后面追加一个值
        *   再通过setText方法吧新的值放进文本框里
         */
            password.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = password.getText() + "1";
                    password.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = password.getText() + "2";
                    password.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = password.getText() + "3";
                    password.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = password.getText() + "4";
                    password.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = password.getText() + "5";
                    password.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = password.getText() + "6";
                    password.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = password.getText() + "7";
                    password.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = password.getText() + "8";
                    password.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = password.getText() + "9";
                    password.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = password.getText() + "0";
                    password.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (password.getText() != null) {
                        String str = password.getText().substring(0, password.getText().length() - 1);
                        password.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = password.getText() + ".";
                    password.setText(str);
                });
            });

            beginBalance.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = beginBalance.getText() + "1";
                    beginBalance.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = beginBalance.getText() + "2";
                    beginBalance.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = beginBalance.getText() + "3";
                    beginBalance.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = beginBalance.getText() + "4";
                    beginBalance.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = beginBalance.getText() + "5";
                    beginBalance.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = beginBalance.getText() + "6";
                    beginBalance.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = beginBalance.getText() + "7";
                    beginBalance.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = beginBalance.getText() + "8";
                    beginBalance.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = beginBalance.getText() + "9";
                    beginBalance.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = beginBalance.getText() + "0";
                    beginBalance.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (beginBalance.getText() != null) {
                        String str = beginBalance.getText().substring(0, beginBalance.getText().length() - 1);
                        beginBalance.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = beginBalance.getText() + ".";
                    beginBalance.setText(str);
                });
            });

            annualInterestRate.setOnMouseClicked(e -> {
                bt1.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "1";
                    annualInterestRate.setText(str);
                });
                bt2.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "2";
                    annualInterestRate.setText(str);
                });
                bt3.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "3";
                    annualInterestRate.setText(str);
                });
                bt4.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "4";
                    annualInterestRate.setText(str);
                });
                bt5.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "5";
                    annualInterestRate.setText(str);
                });
                bt6.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "6";
                    annualInterestRate.setText(str);
                });
                bt7.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "7";
                    annualInterestRate.setText(str);
                });
                bt8.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "8";
                    annualInterestRate.setText(str);
                });
                bt9.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "9";
                    annualInterestRate.setText(str);
                });
                bt0.setOnAction(event -> {
                    String str = annualInterestRate.getText() + "0";
                    annualInterestRate.setText(str);
                });
                bt10.setOnAction(event -> {
                    if (annualInterestRate.getText() != null) {
                        String str = annualInterestRate.getText().substring(0, annualInterestRate.getText().length() - 1);
                        annualInterestRate.setText(str);
                    }
                });
                bt11.setOnAction(event -> {
                    String str = annualInterestRate.getText() + ".";
                    annualInterestRate.setText(str);
                });
            });

            GridPane gridPane = new GridPane();
            gridPane.add(bt1,0,0);
            gridPane.add(bt2,1,0);
            gridPane.add(bt3,2,0);
            gridPane.add(bt4,0,1);
            gridPane.add(bt5,1,1);
            gridPane.add(bt6,2,1);
            gridPane.add(bt7,0,2);
            gridPane.add(bt8,1,2);
            gridPane.add(bt9,2,2);
            gridPane.add(bt11,0,3);
            gridPane.add(bt0,1,3);
            gridPane.add(bt10,2,3);
            gridPane.setAlignment(Pos.CENTER);

            HBox hBox = new HBox();
            hBox.getChildren().addAll(gridPane,gridPane1);
            hBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(hBox,hBox1);
            vBox.setSpacing(20);
            vBox.setAlignment(Pos.CENTER);

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(vBox);

            btRegister.setOnAction(event -> {
                try {
                    Account2 account = new Account2(username.getText(), Double.parseDouble(beginBalance.getText()), password.getText(), Double.parseDouble(annualInterestRate.getText()));
                    accounts.put(account.getId(),account);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("提示框");
                    alert.setHeaderText("请记住你的id：" + account.getId());
                    alert.show();
                    primaryStage.setScene(new Login().start());
                    primaryStage.show();
                }
                catch (Exception e){
                    e.getCause();
                }
                });

            btGiveUp.setOnAction(event -> {
                primaryStage.setScene(new Login().start());
                primaryStage.show();
            });

            Scene scene = new Scene(borderPane,600,400);
            return scene;
        }
    }
}

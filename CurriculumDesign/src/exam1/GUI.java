package exam1;

import exam4.version1.StackAnimation;
import exam4.version2.BinaryTreeAnimation;
import exam6.MazeAnimation;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static exam4.version2.BinaryTreeAnimation.RADIUS;
import static exam4.version2.BinaryTreeAnimation.VGAP;

public class GUI extends Application implements Serializable{
    private HuffmanCode.Tree.Node root; // 保存输的根结点
    private HashMap<Integer, DataCode> data = new HashMap<Integer, DataCode>();  // 保存Huffman树
    private ArrayList<ToggleButton> buttons = new ArrayList<ToggleButton>();    // 保存按钮的线性表
    private File file = new File("codefile.txt");
    private HashMap<Integer, DataCode> newMap = null;
    private String bitCode = "";    // 保存解压出来的字符串

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane(); // 整个UI的总面板容器
        StackPane paneTop = new StackPane(); // UI顶部的面板容器
        VBox paneLeft = new VBox(20);// UI左边的菜单栏，功能面板容器
        HBox paneCenter = new HBox(); // UI 界面的右边的展示区

        // 构建UI的顶部，以及设置其样式
        Text txTitle = new Text("Huffman Code");
        changeTxStyle(txTitle);
        paneTop.setMinWidth(800);
        paneTop.setMinHeight(100);
        paneTop.setStyle("-fx-background-color: gray;");
        paneTop.getChildren().add(txTitle);

        // 构建UI左面的菜单栏，以及设置其样式
        MazeAnimation.newButton btCoding = new MazeAnimation.newButton(120, 60, "编码");
        MazeAnimation.newButton btUnPack = new MazeAnimation.newButton(120, 60, "解码");
        MazeAnimation.newButton btSaveToFile = new MazeAnimation.newButton(120, 60, "保存文件");
        MazeAnimation.newButton btDisplayTree = new MazeAnimation.newButton(120, 60, "画哈夫曼树");
        changeBtStyle(btCoding);
        changeBtStyle(btUnPack);
        changeBtStyle(btSaveToFile);
        changeBtStyle(btDisplayTree);
        paneLeft.setMinWidth(150);
        paneLeft.setMinHeight(400);
        paneLeft.setStyle("-fx-background-color: burlywood;");
        paneLeft.setAlignment(Pos.CENTER);
        paneLeft.getChildren().addAll(btCoding, btUnPack, btSaveToFile, btDisplayTree);

        // 构建UI界面的右边的功能展示区,以及设置其样式
        paneCenter.setMinWidth(650);
        paneCenter.setMinHeight(400);
        paneCenter.setStyle("-fx-background-color: darkgray;");
        paneCenter.setAlignment(Pos.CENTER);

        // 菜单栏的各个按钮的触发事件
        btCoding.setOnAction(event -> {             // 编码按钮的点击事件
            paneCenter.getChildren().clear();           // 避免用户多次点击该按钮，导致UI界面崩掉
            HBox paneCenter_top = new HBox(10); // 展示区顶部的面板容器
            VBox paneCenter_all = new VBox(10); // 展示区所有结点，面板的总容器
            VBox vBox1 = new VBox(10);
            Label labelTip = new Label("Enter a text: ");
            TextField tfText = new TextField();
            MazeAnimation.newButton btOk = new MazeAnimation.newButton(300, 40, "确认");
            MazeAnimation.newButton btCancel = new MazeAnimation.newButton(300, 40, "退出");
            TextArea taShow = new TextArea();

            // 设置各个结点的样式
            effectButton(btOk);
            effectButton(btCancel);
            btOk.setStyle("-fx-background-color: green;-fx-font-size: 20px;");
            labelTip.setFont(new Font("宋体", 20));
            tfText.setMinHeight(30);
            taShow.setMaxWidth(300);
            taShow.setMaxHeight(240);
            taShow.setFont(new Font("宋体", 15));
            paneCenter_top.setMinWidth(300);
            paneCenter_top.setMinHeight(50);
            paneCenter_top.setAlignment(Pos.CENTER);
            vBox1.setAlignment(Pos.CENTER);
            paneCenter_all.setAlignment(Pos.CENTER_RIGHT);

            // 设置各个按钮的触发事件
            btOk.setOnAction(event1 -> {    // 确认按钮的点击事件
                if (!tfText.getText().isEmpty()) {
                    HuffmanCode huffmanCode = new HuffmanCode(tfText.getText()); // 以特定的字符串创建一个Huffman的对象
                    HuffmanCode.Tree tree = huffmanCode.getTree();  // 得到Huffman树
                    String[] codes = HuffmanCode.getCode(tree.root);    // 获取它对应Huffman树的编码
                    String codeException = tfText.getText() + "：";
                    for (int i = 0; i < tfText.getText().length(); i++) {
                        codeException += codes[(int)tfText.getText().charAt(i)];
                    }
                    data.put(data.size(), new DataCode(tree, tfText.getText(), codeException));  // 保存用户输入每一个字符串所得到的Huffman树
                    codeException = taShow.getText() + "\n" + codeException;
                    taShow.setText(codeException);
                    tfText.setText("");
                } else
                    StackAnimation.alert(Alert.AlertType.WARNING, "警告：输入框为空!");
            });

            btCancel.setOnAction(event1 -> {  // 退出按钮的点击事件
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setHeaderText("确定退出?");
                Optional newResult = alert1.showAndWait();
                if (newResult.get() == ButtonType.OK) {
                    paneCenter.getChildren().clear();
                }
            });

            paneCenter_top.getChildren().addAll(labelTip, tfText);
            vBox1.getChildren().addAll(btOk, btCancel);
            paneCenter_all.getChildren().addAll(paneCenter_top, taShow, vBox1);
            paneCenter.getChildren().add(paneCenter_all);
        });
        btUnPack.setOnAction(event -> { // 解码按钮的点击事件
            paneCenter.getChildren().clear();  //  切换界面
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            MazeAnimation.newButton btGetFromFile = new MazeAnimation.newButton(140, 40, "从文件中获取");
            MazeAnimation.newButton btGetFromInput = new MazeAnimation.newButton(140, 40, "从编码输入中获取");
            changeBtStyle(btGetFromFile);
            changeBtStyle(btGetFromInput);
            hBox.getChildren().addAll(btGetFromFile, btGetFromInput);
            paneCenter.getChildren().add(hBox);

            StackPane stackPane = new StackPane();      // 顶部的面板容器
            TextField tfValue = new TextField("请先输入需要解压的字符串，在点击下面各项规则");
            stackPane.getChildren().add(tfValue);

            FlowPane flowPane = new FlowPane(10, 10);       // 中间的面板
            flowPane.setMaxWidth(300);
            flowPane.setMinHeight(200);
            flowPane.setStyle("-fx-background-color: #fff;");
            flowPane.setAlignment(Pos.CENTER);

            VBox vBox1 = new VBox(10);      // 底部的面板
            MazeAnimation.newButton btReturn = new MazeAnimation.newButton(300, 40, "返回");
            btReturn.setStyle("-fx-background-color: red;-fx-font-size: 20px;");
            btReturn.setTextFill(Color.WHITE);
            effectButton(btReturn);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.getChildren().add(btReturn);
            btReturn.setOnAction(event2 -> {
                paneCenter.getChildren().clear();
                paneCenter.getChildren().add(hBox);
            });

            btGetFromFile.setOnAction(event1 -> {
                paneCenter.getChildren().clear();  //  切换界面
                flowPane.getChildren().clear();

                HashMap<Integer, DataCode> map = getDataFromFile();
                if (map != null) {  // 表示成功读取文件
                    if (map.size() == 0) {  // 如果读了文件，但是为空
                        StackAnimation.alert(Alert.AlertType.WARNING, "文件为空");
                    } else {
                            for (int i = 0; i < map.size(); i++) {  // 看文件里面保存了几条数据
                                DataButton dataButton = new DataButton(map.get(i).getUserInput(), i);
                                effectButton(dataButton);
                                dataButton.setCursor(Cursor.HAND);
                                flowPane.getChildren().add(dataButton);
                                clickUnPack(dataButton, map.get(i), tfValue);
                            }

                        VBox vBox = new VBox(10);       // 添加到总的面板容器中去
                        vBox.setMinWidth(300);
                        vBox.setMinHeight(400);
                        vBox.setAlignment(Pos.CENTER);
                        vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                        paneCenter.getChildren().add(vBox);
                    }
                } else
                    StackAnimation.alert(Alert.AlertType.WARNING, "读取文件错误");
            });

            btGetFromInput.setOnAction(event1 -> {
                paneCenter.getChildren().clear();   // 情况paneCenter这个面板
                flowPane.getChildren().clear();
                for (int i = 0; i < data.size(); i++) {  // 看文件里面保存了几条数据
                    DataButton dataButton = new DataButton(data.get(i).getUserInput(), i);
                    effectButton(dataButton);
                    dataButton.setCursor(Cursor.HAND);
                    flowPane.getChildren().add(dataButton);
                    clickUnPack(dataButton, data.get(i), tfValue);
                }

                VBox vBox = new VBox(10);       // 添加到总的面板容器中去
                vBox.setMinWidth(300);
                vBox.setMinHeight(400);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                paneCenter.getChildren().add(vBox);
            });
        });
        btSaveToFile.setOnAction(event -> { // 保存文件按钮的点击事件
            paneCenter.getChildren().clear();  //  切换界面
            buttons.clear();        // 清空线性表
            if (data.size() == 0) {
                StackAnimation.alert(Alert.AlertType.WARNING, "您还没有输入任何东西进行压缩");
            } else {
                StackPane stackPane = new StackPane();  // 上方Text的面板容器
                StackPane stackPaneCenter = new StackPane();    // 中间显示数据的面板容器
                Text text = new Text("请选择以下您要保存的数据：");
                text.setFont(new Font("宋体", 20));
                stackPane.getChildren().add(text);

                FlowPane flowPane = new FlowPane(10, 10);
                flowPane.setMaxWidth(300);
                flowPane.setMinHeight(200);
                flowPane.setStyle("-fx-background-color: #fff;");
                flowPane.setAlignment(Pos.CENTER);

                VBox vBox1 = new VBox(10);
                MazeAnimation.newButton btOk = new MazeAnimation.newButton(300, 40, "确认");
                MazeAnimation.newButton btCancel = new MazeAnimation.newButton(300, 40, "取消");
                btOk.setStyle("-fx-background-color: green;-fx-font-size: 20px;");
                effectButton(btOk);
                effectButton(btCancel);
                vBox1.setAlignment(Pos.CENTER);
                vBox1.getChildren().addAll(btOk, btCancel);
                stackPaneCenter.getChildren().add(flowPane);

                for (int i = 0; i < data.size(); i++) {
                    newToggleButton bt = new newToggleButton(data.get(i).getUserInput()); // 创建切换按钮
                    bt.setStyle("-fx-background-color: aqua;");
                    bt.setMinWidth(60);
                    bt.setMinHeight(30);
                    buttons.add(bt);        // 加入到线性表中
                    flowPane.getChildren().add(bt);
                    bt.setOnAction(event1 -> {  // 按钮的点击事件
                        if (bt.isSelected()) {  // 如果被选中
                            bt.setStyle("-fx-background-color: crimson;");
                            bt.setTextFill(Color.WHITE);
                        } else {    // 未被选中
                            bt.setStyle("-fx-background-color: aqua;");
                            bt.setTextFill(Color.BLACK);
                        }
                    });
                    bt.setCursor(Cursor.HAND);      // 将鼠标箭头变成手
                }
                btOk.setOnAction(event1 -> {    // 点击Ok就保存数据到文件
                    newMap = getDataFromFile();  // 保存文件里面已经保存的数据
                    if (newMap == null)     // 如果这个是为空的话，就创建一个这个对象
                        newMap = new HashMap<Integer, DataCode>();
                    int count = 0;
                   for (int i = 0; i < buttons.size(); i++) {
                       if (buttons.get(i).isSelected()) {
                           newMap.put(newMap.size(), data.get(i));
                           count++;
                       }
                   }
                   if (count != 0) {
                       if (saveToFile(newMap)) {
                           StackAnimation.alert(Alert.AlertType.INFORMATION, "保存成功");
                       } else
                           StackAnimation.alert(Alert.AlertType.INFORMATION, "保存失败");
                   } else
                       StackAnimation.alert(Alert.AlertType.ERROR, "请选中你要保存的数据");
                });
                btCancel.setOnAction(event1 -> {    // 点击取消就把所有选中的取消选中
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("确定全部取消吗？");
                    Optional result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        for (int i = 0; i < buttons.size(); i++) {
                            buttons.get(i).setSelected(false);
                            buttons.get(i).setStyle("-fx-background-color: aqua;");
                            buttons.get(i).setTextFill(Color.BLACK);
                        }
                    }
                });

                VBox vBox = new VBox(10);
                vBox.setMinWidth(300);
                vBox.setMinHeight(400);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(stackPane, stackPaneCenter, vBox1);
                paneCenter.getChildren().add(vBox);
            }
        });
        btDisplayTree.setOnAction(event -> { // 保显示Huffman树按钮的点击事件
            paneCenter.getChildren().clear();  //  切换界面

            StackPane stackPane = new StackPane();      // 顶部的面板容器
            Text text = new Text("请点击你要查看树的按钮:");
            text.setFont(new Font("宋体", 20));
            stackPane.getChildren().add(text);

            FlowPane flowPane = new FlowPane(10, 10);       // 中间的面板
            flowPane.setMaxWidth(300);
            flowPane.setMinHeight(200);
            flowPane.setStyle("-fx-background-color: #fff;");
            flowPane.setAlignment(Pos.CENTER);

            MazeAnimation.newButton btGetFromFile = new MazeAnimation.newButton(300, 40, "从文件读取数据");
            changeBtStyle(btGetFromFile);
            if (data.size() == 0) { // 如果用户没输入，就从文件中获取数据
                paneCenter.getChildren().clear();
                StackPane stackPane1 = new StackPane();
                stackPane1.getChildren().add(btGetFromFile);
                paneCenter.getChildren().add(stackPane1);
            } else {
                paneCenter.getChildren().clear();   // 情况paneCenter这个面板
                for (int i = 0; i < data.size(); i++) {  // 看文件里面保存了几条数据
                    DataButton dataButton = new DataButton(data.get(i).getUserInput(), i);
                    effectButton(dataButton);
                    dataButton.setCursor(Cursor.HAND);
                    buttonListener(dataButton, data.get(i));
                    flowPane.getChildren().add(dataButton);
                }

                VBox vBox1 = new VBox(10);      // 底部的面板
                vBox1.setAlignment(Pos.CENTER);
                vBox1.getChildren().add(btGetFromFile);

                VBox vBox = new VBox(10);       // 添加到总的面板容器中去
                vBox.setMinWidth(300);
                vBox.setMinHeight(400);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                paneCenter.getChildren().add(vBox);
            }

            btGetFromFile.setOnAction(event1 -> {
                paneCenter.getChildren().clear();
                flowPane.getChildren().clear();
                HashMap<Integer, DataCode> map = getDataFromFile();
                if (map != null) {  // 表示成功读取文件
                    if (map.size() == 0) {  // 如果读了文件，但是为空
                        StackAnimation.alert(Alert.AlertType.WARNING, "文件为空");
                    } else {
                            for (int i = 0; i < map.size(); i++) {  // 看文件里面保存了几条数据
                                DataButton dataButton = new DataButton(map.get(i).getUserInput(), i);
                                effectButton(dataButton);
                                dataButton.setCursor(Cursor.HAND);
                                buttonListener(dataButton, map.get(i));
                                flowPane.getChildren().add(dataButton);
                            }

                            VBox vBox1 = new VBox(10);      // 底部的面板
                            MazeAnimation.newButton btReturn = new MazeAnimation.newButton(300, 40, "返回");
                            btReturn.setStyle("-fx-background-color: red;-fx-font-size: 20px;");
                            btReturn.setTextFill(Color.WHITE);
                            effectButton(btReturn);
                            vBox1.setAlignment(Pos.CENTER);
                            vBox1.getChildren().add(btReturn);
                            btReturn.setOnAction(event2 -> {
                                paneCenter.getChildren().clear();
                                StackPane stackPane1 = new StackPane();
                                stackPane1.getChildren().add(btGetFromFile);
                                paneCenter.getChildren().add(stackPane1);
                            });

                            VBox vBox = new VBox(10);       // 添加到总的面板容器中去
                            vBox.setMinWidth(300);
                            vBox.setMinHeight(400);
                            vBox.setAlignment(Pos.CENTER);
                            vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                            paneCenter.getChildren().add(vBox);
                        }
                } else
                    StackAnimation.alert(Alert.AlertType.WARNING, "读取文件错误");
            });
        });

        pane.setTop(paneTop);
        pane.setLeft(paneLeft);
        pane.setCenter(paneCenter);
        Scene scene = new Scene(pane, 800, 500);
        primaryStage.setTitle("Huffman Code");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 对指定的按钮进行设置监听事件
     *  从而根据对应按钮的规则，解码
     * @param bt
     * @param dataCode
     * @param tf
     */
    private void clickUnPack(Button bt, DataCode dataCode, TextField tf) {
        bt.setOnAction(event -> {
            if (!tf.getText().isEmpty()) {
                bitCode = "";   // 先重置这个
                if (isCode(tf.getText())) {
                    unPack(dataCode.getTree().root, tf.getText());
                    StackAnimation.alert(Alert.AlertType.INFORMATION, tf.getText() + "解压后是：" + bitCode);
                } else
                    StackAnimation.alert(Alert.AlertType.ERROR, "输入错误,只能是1和0组成的二进制码");
            } else
                StackAnimation.alert(Alert.AlertType.ERROR, "输入框为空");
        });
    }

    /**
     * 判断用户输入的是否是只含有0或1的字符串
     * @param str
     * @return
     */
    private boolean isCode(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0' && str.charAt(i) != '1')
                return false;
        }

        return true;
    }

    /**
     * 解压
     * @param current
     * @param bitStr
     */
    private void unPack(HuffmanCode.Tree.Node current, String bitStr) {
        if (bitStr.length() == 0)   // 递归终止条件
            return;

        if (bitStr.length() == 1) {
            if (bitStr.charAt(0) == '0')
                bitCode += current.left.element;
            else if (bitStr.charAt(0) == '1')
                bitCode += current.right.element;
        }

        if (bitStr.charAt(0) == '0') {  // 表示下一步向左子树走
            if (current.left != null) { // 表示当前的左孩子不为空,还没有走到叶结点
                String newStr = bitStr.substring(1, bitStr.length());
                unPack(current.left, newStr);
            } else {    // 到了叶结点, 输出字符
                bitCode += current.element;
                unPack(root, bitStr);   // 返回根结点继续解压
            }
        }

        if (bitStr.charAt(0) == '1') {  // 表示下一步应该访问右子树
            if (current.right != null) { // 表示右孩子不为空
                String newStr = bitStr.substring(1, bitStr.length());
                unPack(current.right, newStr);
            } else {
                bitCode += current.element;
                unPack(root, bitStr);
            }
        }
    }

    /**
     * 将指定的Map存入文件
     * @param map
     * @return
     */
    private boolean saveToFile(HashMap<Integer, DataCode> map) {
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("codefile.txt")));    // 创建流
            out.writeObject(map);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 从文件里面取出数据，返回一个Map
     * @return
     */
    private HashMap<Integer, DataCode> getDataFromFile() {
        HashMap<Integer, DataCode> map = null;  // 临时存储从文件里面取出来的数据
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("codefile.txt")));
            map = (HashMap<Integer, DataCode>)in.readObject();
            System.out.println(map.size());
            in.close(); // 关闭这个流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 按钮的监听方法
     * @param bt
     */
    private void buttonListener(DataButton bt, DataCode dataCode) {
        bt.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("是否选择这一条数据进行演示Huffman树?");
            alert.setContentText(dataCode.getUserInput() + "的编码是：" + dataCode.getCodeException());
            Optional result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {    // 如果用户点击确定
                Pane pane = new Pane(); // 画树的面板
                pane.setMinWidth(800);
                pane.setMinHeight(600);
                MazeAnimation.newButton btExit = new MazeAnimation.newButton("返回");
                btExit.setLayoutX(50);
                btExit.setLayoutY(50);

                pane.getChildren().add(btExit);
                displayTree(pane, dataCode.getTree().root, 400, 30, 200);

                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(new Scene(pane, 800, 500));
                stage.show();

                btExit.setOnAction(event1 -> {
                    stage.close();
                });
            }
        });
    }

    /**
     * 在指定的面板中画出Huffman树
     * @param pane
     * @param root
     * @param x
     * @param y
     * @param hGap
     */
    public static void displayTree(Pane pane, HuffmanCode.Tree.Node root, int x, int y, int hGap) {
        BinaryTreeAnimation.NewCircle circle = new BinaryTreeAnimation.NewCircle(root.weight + "");    // 创建结点
        circle.setLayoutX(x - RADIUS);  // 先画圆圈
        circle.setLayoutY(y - RADIUS);
        Label label = new Label(root.element + "");
        label.setLayoutX(x);
        label.setLayoutY(y + RADIUS);
        pane.getChildren().addAll(circle, label);
//        System.out.print(root.element + " ");
        if (root.left != null) {    // 如果该树存在左子树，就向左开始画线
            connectLeftChild(pane, x - hGap, y + VGAP, x, y);
            displayTree(pane, root.left, x - hGap, y + VGAP, hGap / 2);
        }

        if (root.right != null) {
            connectRightChild(pane, x + hGap, y + VGAP, x, y);
            displayTree(pane, root.right, x + hGap, y + VGAP, hGap / 2);
        }
    }

    /**
     * 向左边画树
     * @param pane
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private static void connectLeftChild(Pane pane, int x1, int y1, int x2, int y2) {
        double d = Math.sqrt(VGAP * VGAP + (x2 - x1) * (x2 - x1));
        int x11 = (int)(x1 + RADIUS * (x2 - x1) /d);
        int y11 = (int)(y1 - RADIUS * VGAP / d);
        int x21 = (int)(x2 - RADIUS * (x2 - x1) / d);
        int y21 = (int)(y2 + RADIUS * VGAP / d);
        Line line = new Line(x11, y11, x21, y21);
        Label label = new Label("0");
        label.setLayoutX((x11 + x21) / 2);
        label.setLayoutY((y11 + y21) / 2);
        pane.getChildren().addAll(line, label);
    }

    private static void connectRightChild(Pane pane, int x1, int y1, int x2, int y2) {
        double d = Math.sqrt(VGAP * VGAP + (x2 - x1) * (x2 - x1));
        int x11 = (int)(x1 - RADIUS * (x1 - x2) / d);
        int y11 = (int)(y1 - RADIUS * VGAP / d);
        int x21 = (int)(x2 + RADIUS * (x1 - x2) / d);
        int y21 = (int)(y2 + RADIUS * VGAP / d);
        Line line = new Line(x11, y11, x21, y21);
        Label label = new Label("1");
        label.setLayoutX((x11 + x21) / 2);
        label.setLayoutY((y11 + y21) / 2);
        pane.getChildren().addAll(line, label);
    }

    /**
     * 修改指定的Text的样式以及为它设置一个闪烁的动画
     * @param tx
     */
    private void changeTxStyle(Text tx) {
        tx.setFill(Color.RED);
        tx.setFont(new Font("华文琥珀", 40));
        FadeTransition ft = new FadeTransition(Duration.millis(1000), tx);  // Fade Transition（淡入淡出）,
        // 该效果在给定时间内改变节点的不透明度效果来实现。
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    /**
     * 给指定的按钮增加阴影
     * @param bt
     */
    private void effectButton(Button bt) {
        DropShadow shadow = new DropShadow();   // 设置阴影
        bt.setCursor(Cursor.HAND);      // 将鼠标箭头变成手
        bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            bt.setEffect(shadow);
        });

        bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            bt.setEffect(null);
        });
    }

    /**
     * 设置对应的按钮的样式以及监听事件
     * @param bt
     */
    private void changeBtStyle(Button bt) {
        bt.setStyle("-fx-border-radius: 20px;");
        bt.setFont(new Font("华文行楷", 20));   // 设置字体和字体大小
        bt.setCursor(Cursor.HAND);      // 将鼠标箭头变成手
        DropShadow shadow = new DropShadow();   // 设置阴影
        // Adding the shadow when the mouse cursor is on, 监听事件
        bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            bt.setEffect(shadow);
            bt.setTextFill(Color.WHITE);
            bt.setFont(new Font("华文行楷", 20));
            bt.setStyle("-fx-background-color: mediumblue;");
        });

        // Removing the shadow when the mouse cursor is off
        bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            bt.setEffect(null);
            bt.setTextFill(Color.BLACK);
            bt.setFont(new Font("华文行楷", 20));
            bt.setStyle("-fx-background-color:antiquewhite;");
        });
    }

    class newToggleButton extends ToggleButton implements Serializable{
        public newToggleButton(String text) {
            setText(text);
        }
    }

    /**
     * 顶一个展示数据的button类
     */
    class DataButton extends Button {
        private int num = 0;
        private int isChoose = 0;   // 判断该按钮是否被点击

        public DataButton(String text, int i) {
            this.num = i;
            setText(text);
            setMinWidth(50);
            setMinHeight(50);
            setStyle("-fx-background-color: aliceblue;");
        }

        public void setIsChoose() {
            this.isChoose = 1;
        }

        public int getIsChoose() {
            return isChoose;
        }

        public void setNum(int newNum) {
            num = newNum;
        }

        public int getNum() {
            return num;
        }
    }

    /**
     * 封装所有的数据
     */
    class DataCode implements Serializable {
        private HuffmanCode.Tree tree;
        private String userInput;
        private String codeException;

        /**
         * 以特定的Huffman树和对应的字符串
         * @param newTree
         * @param newUserInput
         * @param newCodeException
         */
        public DataCode(HuffmanCode.Tree newTree, String newUserInput, String newCodeException) {
            this.tree = newTree;
            this.userInput = newUserInput;
            this.codeException = newCodeException;
        }

        public HuffmanCode.Tree getTree() {
            return tree;
        }

        public String getCodeException() {
            return codeException;
        }

        public String getUserInput() {
            return userInput;
        }
    }
}
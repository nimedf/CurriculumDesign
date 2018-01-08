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
    private HuffmanCode.Tree.Node root; // ������ĸ����
    private HashMap<Integer, DataCode> data = new HashMap<Integer, DataCode>();  // ����Huffman��
    private ArrayList<ToggleButton> buttons = new ArrayList<ToggleButton>();    // ���水ť�����Ա�
    private File file = new File("codefile.txt");
    private HashMap<Integer, DataCode> newMap = null;
    private String bitCode = "";    // �����ѹ�������ַ���

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane(); // ����UI�����������
        StackPane paneTop = new StackPane(); // UI�������������
        VBox paneLeft = new VBox(20);// UI��ߵĲ˵����������������
        HBox paneCenter = new HBox(); // UI ������ұߵ�չʾ��

        // ����UI�Ķ������Լ���������ʽ
        Text txTitle = new Text("Huffman Code");
        changeTxStyle(txTitle);
        paneTop.setMinWidth(800);
        paneTop.setMinHeight(100);
        paneTop.setStyle("-fx-background-color: gray;");
        paneTop.getChildren().add(txTitle);

        // ����UI����Ĳ˵������Լ���������ʽ
        MazeAnimation.newButton btCoding = new MazeAnimation.newButton(120, 60, "����");
        MazeAnimation.newButton btUnPack = new MazeAnimation.newButton(120, 60, "����");
        MazeAnimation.newButton btSaveToFile = new MazeAnimation.newButton(120, 60, "�����ļ�");
        MazeAnimation.newButton btDisplayTree = new MazeAnimation.newButton(120, 60, "����������");
        changeBtStyle(btCoding);
        changeBtStyle(btUnPack);
        changeBtStyle(btSaveToFile);
        changeBtStyle(btDisplayTree);
        paneLeft.setMinWidth(150);
        paneLeft.setMinHeight(400);
        paneLeft.setStyle("-fx-background-color: burlywood;");
        paneLeft.setAlignment(Pos.CENTER);
        paneLeft.getChildren().addAll(btCoding, btUnPack, btSaveToFile, btDisplayTree);

        // ����UI������ұߵĹ���չʾ��,�Լ���������ʽ
        paneCenter.setMinWidth(650);
        paneCenter.setMinHeight(400);
        paneCenter.setStyle("-fx-background-color: darkgray;");
        paneCenter.setAlignment(Pos.CENTER);

        // �˵����ĸ�����ť�Ĵ����¼�
        btCoding.setOnAction(event -> {             // ���밴ť�ĵ���¼�
            paneCenter.getChildren().clear();           // �����û���ε���ð�ť������UI�������
            HBox paneCenter_top = new HBox(10); // չʾ���������������
            VBox paneCenter_all = new VBox(10); // չʾ�����н�㣬����������
            VBox vBox1 = new VBox(10);
            Label labelTip = new Label("Enter a text: ");
            TextField tfText = new TextField();
            MazeAnimation.newButton btOk = new MazeAnimation.newButton(300, 40, "ȷ��");
            MazeAnimation.newButton btCancel = new MazeAnimation.newButton(300, 40, "�˳�");
            TextArea taShow = new TextArea();

            // ���ø���������ʽ
            effectButton(btOk);
            effectButton(btCancel);
            btOk.setStyle("-fx-background-color: green;-fx-font-size: 20px;");
            labelTip.setFont(new Font("����", 20));
            tfText.setMinHeight(30);
            taShow.setMaxWidth(300);
            taShow.setMaxHeight(240);
            taShow.setFont(new Font("����", 15));
            paneCenter_top.setMinWidth(300);
            paneCenter_top.setMinHeight(50);
            paneCenter_top.setAlignment(Pos.CENTER);
            vBox1.setAlignment(Pos.CENTER);
            paneCenter_all.setAlignment(Pos.CENTER_RIGHT);

            // ���ø�����ť�Ĵ����¼�
            btOk.setOnAction(event1 -> {    // ȷ�ϰ�ť�ĵ���¼�
                if (!tfText.getText().isEmpty()) {
                    HuffmanCode huffmanCode = new HuffmanCode(tfText.getText()); // ���ض����ַ�������һ��Huffman�Ķ���
                    HuffmanCode.Tree tree = huffmanCode.getTree();  // �õ�Huffman��
                    String[] codes = HuffmanCode.getCode(tree.root);    // ��ȡ����ӦHuffman���ı���
                    String codeException = tfText.getText() + "��";
                    for (int i = 0; i < tfText.getText().length(); i++) {
                        codeException += codes[(int)tfText.getText().charAt(i)];
                    }
                    data.put(data.size(), new DataCode(tree, tfText.getText(), codeException));  // �����û�����ÿһ���ַ������õ���Huffman��
                    codeException = taShow.getText() + "\n" + codeException;
                    taShow.setText(codeException);
                    tfText.setText("");
                } else
                    StackAnimation.alert(Alert.AlertType.WARNING, "���棺�����Ϊ��!");
            });

            btCancel.setOnAction(event1 -> {  // �˳���ť�ĵ���¼�
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setHeaderText("ȷ���˳�?");
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
        btUnPack.setOnAction(event -> { // ���밴ť�ĵ���¼�
            paneCenter.getChildren().clear();  //  �л�����
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            MazeAnimation.newButton btGetFromFile = new MazeAnimation.newButton(140, 40, "���ļ��л�ȡ");
            MazeAnimation.newButton btGetFromInput = new MazeAnimation.newButton(140, 40, "�ӱ��������л�ȡ");
            changeBtStyle(btGetFromFile);
            changeBtStyle(btGetFromInput);
            hBox.getChildren().addAll(btGetFromFile, btGetFromInput);
            paneCenter.getChildren().add(hBox);

            StackPane stackPane = new StackPane();      // �������������
            TextField tfValue = new TextField("����������Ҫ��ѹ���ַ������ڵ������������");
            stackPane.getChildren().add(tfValue);

            FlowPane flowPane = new FlowPane(10, 10);       // �м�����
            flowPane.setMaxWidth(300);
            flowPane.setMinHeight(200);
            flowPane.setStyle("-fx-background-color: #fff;");
            flowPane.setAlignment(Pos.CENTER);

            VBox vBox1 = new VBox(10);      // �ײ������
            MazeAnimation.newButton btReturn = new MazeAnimation.newButton(300, 40, "����");
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
                paneCenter.getChildren().clear();  //  �л�����
                flowPane.getChildren().clear();

                HashMap<Integer, DataCode> map = getDataFromFile();
                if (map != null) {  // ��ʾ�ɹ���ȡ�ļ�
                    if (map.size() == 0) {  // ��������ļ�������Ϊ��
                        StackAnimation.alert(Alert.AlertType.WARNING, "�ļ�Ϊ��");
                    } else {
                            for (int i = 0; i < map.size(); i++) {  // ���ļ����汣���˼�������
                                DataButton dataButton = new DataButton(map.get(i).getUserInput(), i);
                                effectButton(dataButton);
                                dataButton.setCursor(Cursor.HAND);
                                flowPane.getChildren().add(dataButton);
                                clickUnPack(dataButton, map.get(i), tfValue);
                            }

                        VBox vBox = new VBox(10);       // ��ӵ��ܵ����������ȥ
                        vBox.setMinWidth(300);
                        vBox.setMinHeight(400);
                        vBox.setAlignment(Pos.CENTER);
                        vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                        paneCenter.getChildren().add(vBox);
                    }
                } else
                    StackAnimation.alert(Alert.AlertType.WARNING, "��ȡ�ļ�����");
            });

            btGetFromInput.setOnAction(event1 -> {
                paneCenter.getChildren().clear();   // ���paneCenter������
                flowPane.getChildren().clear();
                for (int i = 0; i < data.size(); i++) {  // ���ļ����汣���˼�������
                    DataButton dataButton = new DataButton(data.get(i).getUserInput(), i);
                    effectButton(dataButton);
                    dataButton.setCursor(Cursor.HAND);
                    flowPane.getChildren().add(dataButton);
                    clickUnPack(dataButton, data.get(i), tfValue);
                }

                VBox vBox = new VBox(10);       // ��ӵ��ܵ����������ȥ
                vBox.setMinWidth(300);
                vBox.setMinHeight(400);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                paneCenter.getChildren().add(vBox);
            });
        });
        btSaveToFile.setOnAction(event -> { // �����ļ���ť�ĵ���¼�
            paneCenter.getChildren().clear();  //  �л�����
            buttons.clear();        // ������Ա�
            if (data.size() == 0) {
                StackAnimation.alert(Alert.AlertType.WARNING, "����û�������κζ�������ѹ��");
            } else {
                StackPane stackPane = new StackPane();  // �Ϸ�Text���������
                StackPane stackPaneCenter = new StackPane();    // �м���ʾ���ݵ��������
                Text text = new Text("��ѡ��������Ҫ��������ݣ�");
                text.setFont(new Font("����", 20));
                stackPane.getChildren().add(text);

                FlowPane flowPane = new FlowPane(10, 10);
                flowPane.setMaxWidth(300);
                flowPane.setMinHeight(200);
                flowPane.setStyle("-fx-background-color: #fff;");
                flowPane.setAlignment(Pos.CENTER);

                VBox vBox1 = new VBox(10);
                MazeAnimation.newButton btOk = new MazeAnimation.newButton(300, 40, "ȷ��");
                MazeAnimation.newButton btCancel = new MazeAnimation.newButton(300, 40, "ȡ��");
                btOk.setStyle("-fx-background-color: green;-fx-font-size: 20px;");
                effectButton(btOk);
                effectButton(btCancel);
                vBox1.setAlignment(Pos.CENTER);
                vBox1.getChildren().addAll(btOk, btCancel);
                stackPaneCenter.getChildren().add(flowPane);

                for (int i = 0; i < data.size(); i++) {
                    newToggleButton bt = new newToggleButton(data.get(i).getUserInput()); // �����л���ť
                    bt.setStyle("-fx-background-color: aqua;");
                    bt.setMinWidth(60);
                    bt.setMinHeight(30);
                    buttons.add(bt);        // ���뵽���Ա���
                    flowPane.getChildren().add(bt);
                    bt.setOnAction(event1 -> {  // ��ť�ĵ���¼�
                        if (bt.isSelected()) {  // �����ѡ��
                            bt.setStyle("-fx-background-color: crimson;");
                            bt.setTextFill(Color.WHITE);
                        } else {    // δ��ѡ��
                            bt.setStyle("-fx-background-color: aqua;");
                            bt.setTextFill(Color.BLACK);
                        }
                    });
                    bt.setCursor(Cursor.HAND);      // ������ͷ�����
                }
                btOk.setOnAction(event1 -> {    // ���Ok�ͱ������ݵ��ļ�
                    newMap = getDataFromFile();  // �����ļ������Ѿ����������
                    if (newMap == null)     // ��������Ϊ�յĻ����ʹ���һ���������
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
                           StackAnimation.alert(Alert.AlertType.INFORMATION, "����ɹ�");
                       } else
                           StackAnimation.alert(Alert.AlertType.INFORMATION, "����ʧ��");
                   } else
                       StackAnimation.alert(Alert.AlertType.ERROR, "��ѡ����Ҫ���������");
                });
                btCancel.setOnAction(event1 -> {    // ���ȡ���Ͱ�����ѡ�е�ȡ��ѡ��
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("ȷ��ȫ��ȡ����");
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
        btDisplayTree.setOnAction(event -> { // ����ʾHuffman����ť�ĵ���¼�
            paneCenter.getChildren().clear();  //  �л�����

            StackPane stackPane = new StackPane();      // �������������
            Text text = new Text("������Ҫ�鿴���İ�ť:");
            text.setFont(new Font("����", 20));
            stackPane.getChildren().add(text);

            FlowPane flowPane = new FlowPane(10, 10);       // �м�����
            flowPane.setMaxWidth(300);
            flowPane.setMinHeight(200);
            flowPane.setStyle("-fx-background-color: #fff;");
            flowPane.setAlignment(Pos.CENTER);

            MazeAnimation.newButton btGetFromFile = new MazeAnimation.newButton(300, 40, "���ļ���ȡ����");
            changeBtStyle(btGetFromFile);
            if (data.size() == 0) { // ����û�û���룬�ʹ��ļ��л�ȡ����
                paneCenter.getChildren().clear();
                StackPane stackPane1 = new StackPane();
                stackPane1.getChildren().add(btGetFromFile);
                paneCenter.getChildren().add(stackPane1);
            } else {
                paneCenter.getChildren().clear();   // ���paneCenter������
                for (int i = 0; i < data.size(); i++) {  // ���ļ����汣���˼�������
                    DataButton dataButton = new DataButton(data.get(i).getUserInput(), i);
                    effectButton(dataButton);
                    dataButton.setCursor(Cursor.HAND);
                    buttonListener(dataButton, data.get(i));
                    flowPane.getChildren().add(dataButton);
                }

                VBox vBox1 = new VBox(10);      // �ײ������
                vBox1.setAlignment(Pos.CENTER);
                vBox1.getChildren().add(btGetFromFile);

                VBox vBox = new VBox(10);       // ��ӵ��ܵ����������ȥ
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
                if (map != null) {  // ��ʾ�ɹ���ȡ�ļ�
                    if (map.size() == 0) {  // ��������ļ�������Ϊ��
                        StackAnimation.alert(Alert.AlertType.WARNING, "�ļ�Ϊ��");
                    } else {
                            for (int i = 0; i < map.size(); i++) {  // ���ļ����汣���˼�������
                                DataButton dataButton = new DataButton(map.get(i).getUserInput(), i);
                                effectButton(dataButton);
                                dataButton.setCursor(Cursor.HAND);
                                buttonListener(dataButton, map.get(i));
                                flowPane.getChildren().add(dataButton);
                            }

                            VBox vBox1 = new VBox(10);      // �ײ������
                            MazeAnimation.newButton btReturn = new MazeAnimation.newButton(300, 40, "����");
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

                            VBox vBox = new VBox(10);       // ��ӵ��ܵ����������ȥ
                            vBox.setMinWidth(300);
                            vBox.setMinHeight(400);
                            vBox.setAlignment(Pos.CENTER);
                            vBox.getChildren().addAll(stackPane, flowPane, vBox1);
                            paneCenter.getChildren().add(vBox);
                        }
                } else
                    StackAnimation.alert(Alert.AlertType.WARNING, "��ȡ�ļ�����");
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
     * ��ָ���İ�ť�������ü����¼�
     *  �Ӷ����ݶ�Ӧ��ť�Ĺ��򣬽���
     * @param bt
     * @param dataCode
     * @param tf
     */
    private void clickUnPack(Button bt, DataCode dataCode, TextField tf) {
        bt.setOnAction(event -> {
            if (!tf.getText().isEmpty()) {
                bitCode = "";   // ���������
                if (isCode(tf.getText())) {
                    unPack(dataCode.getTree().root, tf.getText());
                    StackAnimation.alert(Alert.AlertType.INFORMATION, tf.getText() + "��ѹ���ǣ�" + bitCode);
                } else
                    StackAnimation.alert(Alert.AlertType.ERROR, "�������,ֻ����1��0��ɵĶ�������");
            } else
                StackAnimation.alert(Alert.AlertType.ERROR, "�����Ϊ��");
        });
    }

    /**
     * �ж��û�������Ƿ���ֻ����0��1���ַ���
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
     * ��ѹ
     * @param current
     * @param bitStr
     */
    private void unPack(HuffmanCode.Tree.Node current, String bitStr) {
        if (bitStr.length() == 0)   // �ݹ���ֹ����
            return;

        if (bitStr.length() == 1) {
            if (bitStr.charAt(0) == '0')
                bitCode += current.left.element;
            else if (bitStr.charAt(0) == '1')
                bitCode += current.right.element;
        }

        if (bitStr.charAt(0) == '0') {  // ��ʾ��һ������������
            if (current.left != null) { // ��ʾ��ǰ�����Ӳ�Ϊ��,��û���ߵ�Ҷ���
                String newStr = bitStr.substring(1, bitStr.length());
                unPack(current.left, newStr);
            } else {    // ����Ҷ���, ����ַ�
                bitCode += current.element;
                unPack(root, bitStr);   // ���ظ���������ѹ
            }
        }

        if (bitStr.charAt(0) == '1') {  // ��ʾ��һ��Ӧ�÷���������
            if (current.right != null) { // ��ʾ�Һ��Ӳ�Ϊ��
                String newStr = bitStr.substring(1, bitStr.length());
                unPack(current.right, newStr);
            } else {
                bitCode += current.element;
                unPack(root, bitStr);
            }
        }
    }

    /**
     * ��ָ����Map�����ļ�
     * @param map
     * @return
     */
    private boolean saveToFile(HashMap<Integer, DataCode> map) {
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("codefile.txt")));    // ������
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
     * ���ļ�����ȡ�����ݣ�����һ��Map
     * @return
     */
    private HashMap<Integer, DataCode> getDataFromFile() {
        HashMap<Integer, DataCode> map = null;  // ��ʱ�洢���ļ�����ȡ����������
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("codefile.txt")));
            map = (HashMap<Integer, DataCode>)in.readObject();
            System.out.println(map.size());
            in.close(); // �ر������
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
     * ��ť�ļ�������
     * @param bt
     */
    private void buttonListener(DataButton bt, DataCode dataCode) {
        bt.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("�Ƿ�ѡ����һ�����ݽ�����ʾHuffman��?");
            alert.setContentText(dataCode.getUserInput() + "�ı����ǣ�" + dataCode.getCodeException());
            Optional result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {    // ����û����ȷ��
                Pane pane = new Pane(); // ���������
                pane.setMinWidth(800);
                pane.setMinHeight(600);
                MazeAnimation.newButton btExit = new MazeAnimation.newButton("����");
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
     * ��ָ��������л���Huffman��
     * @param pane
     * @param root
     * @param x
     * @param y
     * @param hGap
     */
    public static void displayTree(Pane pane, HuffmanCode.Tree.Node root, int x, int y, int hGap) {
        BinaryTreeAnimation.NewCircle circle = new BinaryTreeAnimation.NewCircle(root.weight + "");    // �������
        circle.setLayoutX(x - RADIUS);  // �Ȼ�ԲȦ
        circle.setLayoutY(y - RADIUS);
        Label label = new Label(root.element + "");
        label.setLayoutX(x);
        label.setLayoutY(y + RADIUS);
        pane.getChildren().addAll(circle, label);
//        System.out.print(root.element + " ");
        if (root.left != null) {    // �������������������������ʼ����
            connectLeftChild(pane, x - hGap, y + VGAP, x, y);
            displayTree(pane, root.left, x - hGap, y + VGAP, hGap / 2);
        }

        if (root.right != null) {
            connectRightChild(pane, x + hGap, y + VGAP, x, y);
            displayTree(pane, root.right, x + hGap, y + VGAP, hGap / 2);
        }
    }

    /**
     * ����߻���
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
     * �޸�ָ����Text����ʽ�Լ�Ϊ������һ����˸�Ķ���
     * @param tx
     */
    private void changeTxStyle(Text tx) {
        tx.setFill(Color.RED);
        tx.setFont(new Font("��������", 40));
        FadeTransition ft = new FadeTransition(Duration.millis(1000), tx);  // Fade Transition�����뵭����,
        // ��Ч���ڸ���ʱ���ڸı�ڵ�Ĳ�͸����Ч����ʵ�֡�
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    /**
     * ��ָ���İ�ť������Ӱ
     * @param bt
     */
    private void effectButton(Button bt) {
        DropShadow shadow = new DropShadow();   // ������Ӱ
        bt.setCursor(Cursor.HAND);      // ������ͷ�����
        bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            bt.setEffect(shadow);
        });

        bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            bt.setEffect(null);
        });
    }

    /**
     * ���ö�Ӧ�İ�ť����ʽ�Լ������¼�
     * @param bt
     */
    private void changeBtStyle(Button bt) {
        bt.setStyle("-fx-border-radius: 20px;");
        bt.setFont(new Font("�����п�", 20));   // ��������������С
        bt.setCursor(Cursor.HAND);      // ������ͷ�����
        DropShadow shadow = new DropShadow();   // ������Ӱ
        // Adding the shadow when the mouse cursor is on, �����¼�
        bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            bt.setEffect(shadow);
            bt.setTextFill(Color.WHITE);
            bt.setFont(new Font("�����п�", 20));
            bt.setStyle("-fx-background-color: mediumblue;");
        });

        // Removing the shadow when the mouse cursor is off
        bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            bt.setEffect(null);
            bt.setTextFill(Color.BLACK);
            bt.setFont(new Font("�����п�", 20));
            bt.setStyle("-fx-background-color:antiquewhite;");
        });
    }

    class newToggleButton extends ToggleButton implements Serializable{
        public newToggleButton(String text) {
            setText(text);
        }
    }

    /**
     * ��һ��չʾ���ݵ�button��
     */
    class DataButton extends Button {
        private int num = 0;
        private int isChoose = 0;   // �жϸð�ť�Ƿ񱻵��

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
     * ��װ���е�����
     */
    class DataCode implements Serializable {
        private HuffmanCode.Tree tree;
        private String userInput;
        private String codeException;

        /**
         * ���ض���Huffman���Ͷ�Ӧ���ַ���
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
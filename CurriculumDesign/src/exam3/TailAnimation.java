package exam3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TailAnimation extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        BorderPane pane = new BorderPane(); // 总面板
        FlowPane flowPane = new FlowPane(10, 10);   // 展示解的面板
        HBox hBox = new HBox(10);   // 在面板底部的按钮和输入框的面板
        ArrayList<ArrayList<Button>> buttons = new ArrayList<ArrayList<Button>>();

        // 设置内边距
        flowPane.setPadding(new Insets(20, 20, 20, 20));
        Text txRow = new Text("row:");
        Text txColumn = new Text("column:");
        Text txRule = new Text("rule:");
        TextField tfRow = new TextField();
        TextField tfColumn = new TextField();
        Button btStart = new Button("Start");
        Button btSolve = new Button("Solve");
        ComboBox<String> cbo = new ComboBox<String>();
        cbo.getItems().addAll("Row", "Column", "Default", "RightInclined", "LeftInclined", "DoubleInclined");
        cbo.setValue("Default");

        hBox.setPadding(new Insets(0, 20, 20, 20));
        hBox.getChildren().addAll(txRow, tfRow, txColumn, tfColumn, btStart, btSolve, txRule, cbo);
        pane.setCenter(flowPane);
        pane.setBottom(hBox);

        btStart.setOnAction(event -> {
            if (isSafe(tfRow) && isSafe(tfColumn))
            {
                flowPane.getChildren().clear();
                flowPane.getChildren().add(new Coins(Integer.parseInt(tfRow.getText()), Integer.parseInt(tfColumn.getText())));
            }
        });

        btSolve.setOnAction(event -> {
            flowPane.getChildren().clear();
            String type = cbo.getValue();
            if (isSafe(tfRow) && isSafe(tfColumn)) {
                int row = Integer.parseInt(tfRow.getText());    // 获取行数
                int column = Integer.parseInt(tfColumn.getText());  // 获取列数
                char[] node = new char[row * column];   // 创建默认的字符数组
                Coins coins = new Coins(row, column);
                ArrayList<Button> button = coins.getButtons();
                // 获取硬币的正反面得到一个字符数组
                for (int i = 0; i < button.size(); i++)
                {
                    if (button.get(i).getText().equals("H"))
                        node[i] = 'H';
                    else
                        node[i] = 'T';
                }

                buttons.add(button);

                TailModel model = new TailModel(row, column, type);
                List<Integer> path = model.getShortestPath(TailModel.getIndex(node));

                for (int i = 0; i < path.size(); i++)
                {
                    Coins newCoin = new Coins(TailModel.getNode(path.get(i).intValue()), row, column);
                    ArrayList<Button> listBt = newCoin.getButtons();
                    for (int j = 0; j < buttons.get(buttons.size() - 1).size(); j++) {
                        if (!listBt.get(j).getText().equals(buttons.get(buttons.size() - 1).get(j).getText()))
                            listBt.get(j).setStyle("-fx-background-color: red;");
                    }
                    buttons.add(listBt);
                    flowPane.getChildren().add(newCoin);
                }
                tfRow.setText("");
                tfColumn.setText("");
            }
        });

        cbo.setOnAction(event -> {
//            System.out.println(cbo.getValue());
        });

        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setTitle("TailAnimation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 判断一个输入框中的值是否全是数字，或者和是否输入
     * @param tf
     * @return
     */
    private boolean isSafe(TextField tf)
    {
        if (tf.getText().isEmpty())
        {
            alert(Alert.AlertType.WARNING, "输入框为空");
            return false;
        }
        else
        {
            char[] ch = tf.getText().toCharArray();
            for (int i = 0; i < ch.length; i++)
                if (ch[i] < '0' || ch[i] > '9')
                {
                    alert(Alert.AlertType.WARNING, "输入不合法，有非数字字符输入");
                    return false;
                }
        }
        return true;
    }

    /**
     * 一个弹出框的方法
     * @param alertType
     * @param exception
     */
    private void alert(Alert.AlertType alertType, String exception)
    {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(exception);
        alert.showAndWait();
    }
}

/**
 * 一个硬币的面板
 */
class Coins extends GridPane
{
    ArrayList<Button> buttons = new ArrayList<Button>();

    public Coins(int row, int column)
    {
        int k = 0;
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                buttons.add(new Button("H"));
                buttons.get(k).setMinHeight(30);
                buttons.get(k).setMinWidth(50);
                add(buttons.get(k), j, i);
                k++;
            }
        }
    }

    public Coins(char[] node, int row, int column)
    {
        int k = 0;
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                buttons.add(new Button(""+ node[k]));
                buttons.get(k).setMinHeight(30);
                buttons.get(k).setMinWidth(50);
                add(buttons.get(k), j, i);
                k++;
            }
        }
    }

    /**
     * 修改指定下标处的的按钮的文本值
     * @param index
     * @param ch
     */
    public void changeButtonText(int index, char ch) {
        buttons.set(index, new Button(ch + ""));
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public char[] getButtonText() {
        char[] temp = new char[buttons.size()];
        for (int i = 0; i < buttons.size(); i++) {
            temp[i] = buttons.get(i).getText().charAt(0);
        }
        return temp;
    }
}

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
        BorderPane pane = new BorderPane(); // �����
        FlowPane flowPane = new FlowPane(10, 10);   // չʾ������
        HBox hBox = new HBox(10);   // �����ײ��İ�ť�����������
        ArrayList<ArrayList<Button>> buttons = new ArrayList<ArrayList<Button>>();

        // �����ڱ߾�
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
                int row = Integer.parseInt(tfRow.getText());    // ��ȡ����
                int column = Integer.parseInt(tfColumn.getText());  // ��ȡ����
                char[] node = new char[row * column];   // ����Ĭ�ϵ��ַ�����
                Coins coins = new Coins(row, column);
                ArrayList<Button> button = coins.getButtons();
                // ��ȡӲ�ҵ�������õ�һ���ַ�����
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
     * �ж�һ��������е�ֵ�Ƿ�ȫ�����֣����ߺ��Ƿ�����
     * @param tf
     * @return
     */
    private boolean isSafe(TextField tf)
    {
        if (tf.getText().isEmpty())
        {
            alert(Alert.AlertType.WARNING, "�����Ϊ��");
            return false;
        }
        else
        {
            char[] ch = tf.getText().toCharArray();
            for (int i = 0; i < ch.length; i++)
                if (ch[i] < '0' || ch[i] > '9')
                {
                    alert(Alert.AlertType.WARNING, "���벻�Ϸ����з������ַ�����");
                    return false;
                }
        }
        return true;
    }

    /**
     * һ��������ķ���
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
 * һ��Ӳ�ҵ����
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
     * �޸�ָ���±괦�ĵİ�ť���ı�ֵ
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

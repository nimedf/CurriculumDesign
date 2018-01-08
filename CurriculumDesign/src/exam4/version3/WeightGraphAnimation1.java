package exam4.version3;

import exam4.version1.StackAnimation;
import exam6.MazeAnimation;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class WeightGraphAnimation1 extends Application {
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();   // 总的面板的容器
        Pane pane = new Pane(); // 中间的面板容器
        HBox hBox = new HBox(15);   // 底部的面板容器
        hBox.setPadding(new Insets(10));    // 设置内边距
        GridPane gridPaneLeft = new GridPane(); // 底部左边的面板容器
        GridPane gridPaneRight = new GridPane();    // 底部右边的面板容器

        // 各个部分的组件
        Text textName = new Text("Vertex name:");
        Text textX = new Text("x-coordinate:");
        Text textY = new Text("y-coordinate:");
        Text textU = new Text("Vertex u(index):");
        Text textV = new Text("Vertex v(index):");
        Text textWeight = new Text("Weight(int):");
        TextField tfName = new TextField();
        TextField tfX = new TextField();
        TextField tfY = new TextField();
        TextField tfU = new TextField();
        TextField tfV = new TextField();
        TextField tfWeight = new TextField();
        MazeAnimation.newButton btAddNewVertex = new MazeAnimation.newButton("Add Vertex");
        MazeAnimation.newButton btEdge = new MazeAnimation.newButton("Add Edge");

        // 将以上的结点添加到面板容器中去
        gridPaneLeft.add(textName, 0, 0);
        gridPaneLeft.add(textX, 0, 1);
        gridPaneLeft.add(textY, 0, 2);
        gridPaneLeft.add(tfName, 1, 0);
        gridPaneLeft.add(tfX, 1, 1);
        gridPaneLeft.add(tfY, 1, 2);
        gridPaneLeft.add(btAddNewVertex, 1, 3);
        gridPaneLeft.setHalignment(btAddNewVertex, HPos.RIGHT);
        gridPaneRight.add(textU, 0, 0);
        gridPaneRight.add(textV, 0, 1);
        gridPaneRight.add(textWeight, 0, 2);
        gridPaneRight.add(tfU, 1, 0);
        gridPaneRight.add(tfV, 1, 1);
        gridPaneRight.add(tfWeight, 1, 2);
        gridPaneRight.add(btEdge, 1, 3);
        gridPaneRight.setHalignment(btEdge, HPos.RIGHT);
        StackPane stackPaneLeft = addStackPane(gridPaneLeft, "Add a new vertex");
        StackPane stackPaneRight = addStackPane(gridPaneRight, "Add a new edge");

        // 各个按钮的注册事件
        btAddNewVertex.setOnAction(event -> {
            if (!tfName.getText().isEmpty()) {  // 看用户是否输入值
                String name = tfName.getText(); // 获取该字符串
                if (StackAnimation.isSafety(tfX) && StackAnimation.isSafety(tfY)) { // 判断输入坐标的输入框是否为空，是否全是数字
                    int x = Integer.parseInt(tfX.getText());    // 获取到对应的坐标
                    int y = Integer.parseInt(tfY.getText());
                    Node node = new Node(name, x, y);
                    nodes.add(node);
                    pane.getChildren().add(node);
                }
            } else
                StackAnimation.alert(Alert.AlertType.WARNING, "警告：输入框为空!");
        });

        btEdge.setOnAction(event -> {
            if (StackAnimation.isSafety(tfU) && StackAnimation.isSafety(tfV) && StackAnimation.isSafety(tfWeight)) {
                int u = Integer.parseInt(tfU.getText());
                int v = Integer.parseInt(tfV.getText());
                int weight = Integer.parseInt(tfWeight.getText());

                int x1 = nodes.get(u).getX();   // 连线的起始坐标
                int y1 = nodes.get(u).getY();
                int x2 = nodes.get(v).getX();
                int y2 = nodes.get(v).getY();
                int x = (x1 + x2) / 2;
                int y = (y1 + y2) / 2 - 5;  // 权的定位坐标

                Line line = new Line(x1, y1, x2, y2);
                Label label = new Label(weight + "");
                label.setLayoutX(x);
                label.setLayoutY(y);
                line.setStroke(Color.RED);
                pane.getChildren().addAll(line, label);
            }
        });

        hBox.getChildren().addAll(stackPaneLeft, stackPaneRight);
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);
        primaryStage.setTitle("加权图");
        primaryStage.setScene(new Scene(borderPane, 635, 500));
        primaryStage.show();
    }

    /**
     * 将text放置在border上
     * @param grid
     * @param text
     * @return
     */
    public StackPane addStackPane(GridPane grid, String text) {
        StackPane pane = new StackPane();
        Label title = new Label(text);
        title.setStyle("-fx-translate-y: -5");
        pane.setAlignment(title, Pos.TOP_LEFT);
        grid.setStyle("-fx-content-display: top");
        grid.setStyle("-fx-border-insets: 20 15 15 15");
        grid.setStyle("-fx-background-color: white");
        grid.setStyle("-fx-border-color: black");
        grid.setHgap(30);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 10, 25, 10));
        pane.getChildren().addAll(grid, title);

        return pane;
    }

    class Node extends VBox {
        private Label label;
        private Circle circle;
        private int x;
        private int y;

        public Node(String name, int x, int y) {
            this.x = x + 8;
            this.y = y + 24;
            label = new Label(name);
            circle = new Circle(8);
            setLayoutX(x);
            setLayoutY(y);
            getChildren().addAll(label, circle);
            setSpacing(5);  // 设置层距为5
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public Label getLabel() {
            return label;
        }

        public Circle getCircle() {
            return circle;
        }
    }
}

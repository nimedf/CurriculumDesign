package exam4.version3;

import exam4.version1.StackAnimation;
import exam6.MazeAnimation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class UnweightGraphAnimation extends Application{
    private DisplayUSMap displayUSMap = new DisplayUSMap(); // 创建毅哥DisplayUSMap的对象
    private Graph<DisplayUSMap.City> graph = displayUSMap.getGraph();   // 获取对应的城市图

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();     // 保存图的面板容器
        BorderPane borderPane = new BorderPane();   // 保存整个面板的面板容器
        HBox hBox = new HBox(10);   // 保存底部的面板容器
        hBox.setAlignment(Pos.CENTER);  // 设置底部的结点对齐方式为居中对齐
        hBox.setPadding(new Insets(10));

        Text text = new Text("Starting City:");
        text.setStyle("-fx-font-size:20px;");
        TextField tfCity = new TextField();
        MazeAnimation.newButton btDFS = new MazeAnimation.newButton("Display DFS Tree");
        MazeAnimation.newButton btBFS = new MazeAnimation.newButton("Display BFS Tree");
        MazeAnimation.newButton btReset = new MazeAnimation.newButton("还原成原来的Tree");
        paintComponent(pane);   // 展示图

        // 各个按钮的注册事件
        btDFS.setOnAction(event -> {
           String name = tfCity.getText();  // 获取城市的名称
            AbstractGraph.Tree tree = null;
            List<DisplayUSMap.City> vertis = graph.getVertices();
            for (int i = 0; i < vertis.size(); i++) {
                if (vertis.get(i).getName().equals(name)) {
                    tree = graph.dfs(graph.getIndex(vertis.get(i)));    // 获取到深度遍历的tree
                    break;
                }
            }

            if (tree != null) {
                pane.getChildren().clear();
                List<Integer> searchOrders = tree.getSearchOrders();
                for (int i = 0; i < searchOrders.size(); i++) {
                    if (i == 5) {
                        NewCircle circle = new NewCircle(graph.getVertex(searchOrders.get(i)).getName());
                        circle.setLayoutX(graph.getVertex(searchOrders.get(i)).getX() - 8);
                        circle.setLayoutY(graph.getVertex(searchOrders.get(i)).getY() - 21);
                        pane.getChildren().addAll(circle);
                    }
                    if (tree.getParent(i) != -1) {
                        int x1 = graph.getVertex(i).getX();
                        int y1 = graph.getVertex(i).getY();
                        int x2 = graph.getVertex(tree.getParent(i)).getX();
                        int y2 = graph.getVertex(tree.getParent(i)).getY();
                        Line line = new Line(x1, y1, x2, y2);
                        NewCircle circle = new NewCircle(graph.getVertex(searchOrders.get(i)).getName());
                        circle.setLayoutX(graph.getVertex(searchOrders.get(i)).getX() - 8);
                        circle.setLayoutY(graph.getVertex(searchOrders.get(i)).getY() - 21);
                        pane.getChildren().addAll(circle, line);
                        drawingArrowhead(x2, y2, x1, y1, pane); // 画箭头
                    }
                }
            } else
                StackAnimation.alert(Alert.AlertType.WARNING, "报告：没有这个城市！");
            tfCity.setText(""); // 初始化这个输入框
        });

        btBFS.setOnAction(event -> {
            String name = tfCity.getText();  // 获取城市的名称
            AbstractGraph.Tree tree = null;
            List<DisplayUSMap.City> vertis = graph.getVertices();
            for (int i = 0; i < vertis.size(); i++) {
                if (vertis.get(i).getName().equals(name)) {
                    tree = graph.bfs(graph.getIndex(vertis.get(i)));    // 获取到深度遍历的tree
                    break;
                }
            }

            if (tree != null) {
                pane.getChildren().clear();
                List<Integer> searchOrders = tree.getSearchOrders();
                for (int i = 0; i < searchOrders.size(); i++) {
                    System.out.print(graph.getVertex(searchOrders.get(i)).getName() + " ");
                    NewCircle circle = new NewCircle(graph.getVertex(searchOrders.get(i)).getName());
                    circle.setLayoutX(graph.getVertex(searchOrders.get(i)).getX() - 8);
                    circle.setLayoutY(graph.getVertex(searchOrders.get(i)).getY() - 21);
                    pane.getChildren().addAll(circle);
                    if (tree.getParent(i) != -1) {
                        int x1 = graph.getVertex(i).getX();
                        int y1 = graph.getVertex(i).getY();
                        int x2 = graph.getVertex(tree.getParent(i)).getX();
                        int y2 = graph.getVertex(tree.getParent(i)).getY();
                        Line line = new Line(x1, y1, x2, y2);
                        pane.getChildren().add(line);
                        drawingArrowhead(x2, y2, x1, y1, pane); // 画箭头
                    }
                }
            } else
                StackAnimation.alert(Alert.AlertType.WARNING, "报告：没有这个城市！");
            tfCity.setText(""); // 初始化这个输入框
        });

        btReset.setOnAction(event -> {
            pane.getChildren().clear();
            paintComponent(pane);
        });

        hBox.getChildren().addAll(text, tfCity, btDFS, btBFS, btReset);
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);

        primaryStage.setTitle("图的可视化");
        primaryStage.setScene(new Scene(borderPane, 800, 500));
        primaryStage.show();
    }

    public static void drawingArrowhead(int x1, int y1, int x2, int y2, Pane pane){
        Line line = new Line(x1, y1, x2, y2);
        line.setFill(Color.RED);
        line.setStroke(Color.RED);
        pane.getChildren().add(line);

        //斜率
        double slope = ((((double) y1) - (double) y2))
                / (((double) x1) - (((double) x2)));

        double arctan = Math.atan(slope);

        // This will flip the arrow 45 off of a
        // perpendicular line at pt x2
        double set45 = 1.57 / 2;

        // arrows should always point towards i, not i+1
        if (x1 < x2) {
            // add 90 degrees to arrow lines
            set45 = -1.57 * 1.5;
        }

        // set length of arrows
        int arrlen = 15;

        Line line1 = new Line(x2, y2, (x2 + (Math.cos(arctan + set45) * arrlen)), ((y2)) + (Math.sin(arctan + set45) * arrlen));
        Line line2 = new Line(x2, y2, (x2 + (Math.cos(arctan - set45) * arrlen)), ((y2)) + (Math.sin(arctan - set45) * arrlen));

        line1.setStroke(Color.RED);
        line2.setStroke(Color.RED);
        // draw arrows on line
        pane.getChildren().add(line1);

        pane.getChildren().add(line2);

    }

    /**
     * 将图中的结点传入面板pane中
     * @param pane
     */
    private void paintComponent(Pane pane) {
        List<DisplayUSMap.City> vertices = graph.getVertices(); // 获取地名

        // 将各个城市加入面板中
        for (int i = 0; i < vertices.size(); i++) {
            NewCircle circle = new NewCircle(vertices.get(i).getName());
            circle.setLayoutX(vertices.get(i).getX() - 8);
            circle.setLayoutY(vertices.get(i).getY() - 21);
            pane.getChildren().add(circle);
        }

        // 将各个城市连接起来
        for (int i = 0; i < graph.getSize(); i++) {
            List<Integer> neighbors = graph.getNeighbors(i);    // 获取邻接线性表
            for (int j = 0; j < neighbors.size(); j++) {
                int v = neighbors.get(j);
                int x1 = graph.getVertex(i).getX();
                int y1 = graph.getVertex(i).getY();
                int x2 = graph.getVertex(v).getX();
                int y2 = graph.getVertex(v).getY();

                Line line = new Line(x1, y1, x2, y2);
                pane.getChildren().add(line);
            }
        }
    }
}

class NewCircle extends VBox {
    private Label label;
    private Circle circle;

    public NewCircle(String name) {
        label = new Label(name);
        circle = new Circle(8);
        getChildren().addAll(label, circle);
        setSpacing(5);  // 设置层距为5
    }

    public Label getLabel() {
        return label;
    }

    public Circle getCircle() {
        return circle;
    }
}
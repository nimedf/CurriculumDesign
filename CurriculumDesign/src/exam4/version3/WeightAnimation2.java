package exam4.version3;

import exam6.MazeAnimation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.PriorityQueue;

public class WeightAnimation2 extends Application {
    final DisplayUSMap.City[] vertices =
            { new DisplayUSMap.City("Seattle", 75, 50), new DisplayUSMap.City("San Franciso", 50, 210),
                    new DisplayUSMap.City("Los Angeles", 75, 275), new DisplayUSMap.City("Denver", 275, 175),
                    new DisplayUSMap.City("Kansas City", 400, 245), new DisplayUSMap.City("Chicago", 450, 100),
                    new DisplayUSMap.City("Boston", 700, 80), new DisplayUSMap.City("New York", 675, 120),
                    new DisplayUSMap.City("Atlanta", 575, 295), new DisplayUSMap.City("Miami", 600, 400),
                    new DisplayUSMap.City("Dallas", 408, 325), new DisplayUSMap.City("Houston", 450, 360)
            };

    private final int[][] edges =
            {{ 0, 1, 807}, { 0, 3, 1331},{ 0, 5, 2097},
                    { 1, 0, 807}, { 1, 2, 381}, { 1, 3, 1267},
                    { 2, 1, 381}, { 2, 3, 1015}, { 2, 4, 1663}, { 2, 10, 1435},
                    { 3, 0, 1331}, { 3, 1, 1267}, { 3, 2, 1015}, { 3, 4, 599}, { 3, 5, 1003},
                    { 4, 2, 1663}, { 4, 3, 599}, { 4, 5, 533}, { 4, 7, 1260}, { 4, 8, 864}, { 4, 10, 496},
                    { 5, 0, 2097}, { 5, 3, 1003}, { 5, 4, 533}, { 5, 6, 983}, { 5, 7, 787},
                    { 6, 5, 983}, { 6, 7, 214},
                    { 7, 4, 1260}, { 7, 5, 787}, { 7, 6, 214}, { 7, 8, 888},
                    { 8, 4, 864}, { 8, 7, 888}, { 8, 9, 661}, { 8, 10, 781}, { 8, 11, 810},
                    { 9, 8, 661}, { 9, 11, 1187},
                    { 10, 2, 1435}, { 10, 4, 496}, { 10, 8, 781}, { 10, 11, 239},
                    { 11, 8, 810}, { 11, 9, 1187}, { 11, 10, 239}
            };

    private WeightedGraph<DisplayUSMap.City> graph = new WeightedGraph<DisplayUSMap.City>(edges, vertices);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();   // 总的面板容器
        Pane pane = new Pane();     // 中间的面板容器
        HBox hBox = new HBox(10);   // 底部的面板容器
        hBox.setPadding(new Insets(10));

        Text textStart = new Text("Starting City:");
        Text textEnd = new Text("Ending City:");
        TextField tfStart = new TextField();
        TextField tfEnd = new TextField();
        MazeAnimation.newButton btDisplay = new MazeAnimation.newButton("Display Shortest Path");

        paintGraph(pane);   // 显示加权图
        hBox.getChildren().addAll(textStart, tfStart, textEnd, tfEnd, btDisplay);

        btDisplay.setOnAction(event -> {
            pane.getChildren().clear();
           String start = tfStart.getText();
           String end = tfEnd.getText();
           int startIndex = -1;
           int endIndex = -1;
           List<DisplayUSMap.City> vertex = graph.getVertices();
           for (int i = 0; i < vertex.size(); i++) {
               if (vertex.get(i).getName().equals(start)) {
                   startIndex = i;
               } else if (vertex.get(i).getName().equals(end)) {
                   endIndex = i;
               }
           }
           WeightedGraph.ShortestPathTree tree = graph.getShortestPath(graph.getIndex(vertex.get(startIndex)));
           List<DisplayUSMap.City> path = tree.getPath(graph.getIndex(vertex.get(endIndex)));
           for (int i = path.size() - 2; i >= 0; i--) {
               int x1 = path.get(i + 1).getX();
               int y1 = path.get(i + 1).getY();
               int x2 = path.get(i).getX();
               int y2 = path.get(i).getY();
               NewCircle circle = new NewCircle(path.get(i).getName());
               circle.setLayoutY(y1 - 21);
               circle.setLayoutX(x1 - 8);
               Line line = new Line(x1, y1, x2, y2);
               line.setStroke(Color.RED);
               UnweightGraphAnimation.drawingArrowhead(x1, y1, x2, y2, pane);
               pane.getChildren().addAll(line, circle);
           }
            NewCircle circle = new NewCircle(path.get(0).getName());
            circle.setLayoutY(path.get(0).getY() - 21);
            circle.setLayoutX(path.get(0).getX() - 8);
            pane.getChildren().add(circle);
        });

        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);
        primaryStage.setTitle("加权图");
        primaryStage.setScene(new Scene(borderPane, 800, 500));
        primaryStage.show();
    }

    private void paintGraph(Pane pane) {
        for (int i = 0; i < graph.getSize(); i++) {
            NewCircle circle = new NewCircle(graph.getVertex(i).getName());
            circle.setLayoutX(graph.getVertex(i).getX() - 8);
            circle.setLayoutY(graph.getVertex(i).getY() - 21);
            pane.getChildren().add(circle);
        }

        List<DisplayUSMap.City> vertices = graph.getVertices();
        for (int i = 0; i < graph.queues.size(); i ++)
        {
            for (WeightedEdge edge : graph.queues.get(i))
            {
                int x1 = vertices.get(edge.u).getX();
                int y1 = vertices.get(edge.u).getY();
                int x2 = vertices.get(edge.v).getX();
                int y2 = vertices.get(edge.v).getY();
                int w = edge.weight;

                Line line = new Line(x1, y1, x2, y2);
                Label label = new Label(w + "");
                label.setLayoutX((x1 + x2) / 2);
                label.setLayoutY((y1 + y2) / 2);
                pane.getChildren().addAll(label, line);
            }
        }
    }

}

package exam6;

import exam4.version1.StackAnimation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.*;

public class MazeAnimation extends Application implements Serializable{
    public final int MAX_NUM_OF_BARRIER = 25;   // 障碍的最大数量
    private ArrayList<MapMaze> mazes = new ArrayList<MapMaze>();    // 保存迷宫地图的
    private File file = new File("maze.txt");       // 以默认的文件名创建一个文件对象
    private HashMap<Integer, Solution> listMap = null;  // 保存数据，每次保存一条通路到这个集合里面
    private int[] barrierIndex; // 保存障碍的下标的数组
    private ArrayList<int[]> list;    // 保存路径坐标的，链表里面每一个数组都是一个点的坐标{x, y}
    private ArrayList<String> postion; // 保存通路的方向的，up, down, left, right
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane(); // 迷宫地图的面板容器
        VBox vBox = new VBox(20);   // 迷宫右边的按钮面板容器
        vBox.setAlignment(Pos.CENTER);  // 设置vBox这个容器的结点的对齐方式为居中
        BorderPane borderPane = new BorderPane();   // 整个GUI的总面板容器
        ArrayList<Integer> listBarrier = new ArrayList<Integer>();  // 保存障碍的下标的数组
        // 设置内边距, 上下左右都是10px
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        // 创建右边的按钮功能栏
        newButton btOk = new newButton("Ok");
        newButton btCancel = new newButton("Cancel");
        newButton btRandomCreate = new newButton("随机生成");
        newButton btPut = new newButton("保存数据");
        newButton btGet = new newButton("取出数据");
        newButton btClear = new newButton("清空数据");
        vBox.getChildren().addAll(btOk, btCancel, btRandomCreate, btPut, btGet, btClear);

        createMap(gridPane);    // 生成GUI左边栏
        borderPane.setCenter(gridPane);
        borderPane.setRight(vBox);

        // 各个按钮的注册事件
        btRandomCreate.setOnAction(event -> {
            listBarrier.clear();        // 清空保存障碍的线性表
            mazes.clear();
            gridPane.getChildren().clear(); // 清楚gridPane里面的结点
            ArrayList<MapMaze> newList = createMap(gridPane);   // 保存图中所有的格子
            getMap(newList);
            barrierIndex = getRanomNum(newList);  // 生成随机的障碍
            for (int i = 0; i < barrierIndex.length; i++)
                listBarrier.add(barrierIndex[i]);
        });

        // 开始迷宫游戏
        btOk.setOnAction(event -> {
            int[][] maze = getMaze(listBarrier);   // 保存迷宫的二维数组模型
            MazeAlgorithm a = new MazeAlgorithm(maze);  // 为了获取最终的解的
            a.bfsGetPath(0, 0);
            list = a.getPath();    // 保存路径坐标的
            postion = a.getPostion(); // 保存通路的方向的
            if (list.size() == 0 && postion.size() == 0){
                StackAnimation.alert(Alert.AlertType.WARNING, "无解");
                return;
            }
            int[] indexOfNode = new int[list.size()];   // 保存每个坐标所对应的图中的位置
            for (int i = 0; i < list.size(); i++) {
                int[] temp = list.get(i);
                indexOfNode[i] = temp[0] * 8 + temp[1];
            }

            printResult(postion, indexOfNode);
        });

        // 取消
        btCancel.setOnAction(event -> {
            createMap(gridPane);    // 重置grid Pane面板容器
        });

        // 保存数据
        btPut.setOnAction(event -> {
            if (barrierIndex != null) {    // 确保不是空的
                listMap = getDataFromFile();    // 先获取之前存在文件里面的数据
                if (listMap == null) {
                    listMap = new HashMap<Integer, Solution>();
                    listMap.put(0, new Solution(list, postion, barrierIndex));
                }else
                    // 向集合里面添加一条数据
                    listMap.put(listMap.size(), new Solution(list, postion, barrierIndex));
                saveToFile(listMap);       // 保存数据到文件
                StackAnimation.alert(Alert.AlertType.INFORMATION, "保存成功");
            } else
                StackAnimation.alert(Alert.AlertType.WARNING, "警告：没有什么东西可以保存!");
        });

        // 取出数据
        btGet.setOnAction(event -> {
            VBox vBoxGetData = new VBox(10);

            HashMap<Integer, Solution> map = getDataFromFile(); // 从文件里面取出数据
            for (int i = 0; i < map.size(); i++) {
                String text = "数据" + (i + 1);
                newButton bt = new newButton(440, 20, text);
                linster(bt, gridPane);
                vBoxGetData.getChildren().add(bt);
            }
            alert(vBoxGetData);
        });

        btClear.setOnAction(event -> {
            listMap = getDataFromFile();    // 先获取之前存在文件里面的数据
            if (listMap == null)
                listMap = new HashMap<Integer, Solution>();
            else
                listMap.clear();

            saveToFile(listMap);       // 保存数据到文件
            StackAnimation.alert(Alert.AlertType.WARNING, "删除成功");
        });

        Scene scene = new Scene(borderPane, 440, 360);
        primaryStage.setTitle("创建迷宫");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 取出数据里面的每条数据的监听函数
     * @param bt
     * @param gridPane
     */
    private void linster(newButton bt, GridPane gridPane) {
        bt.setOnAction(event -> {
            gridPane.getChildren().clear(); // 清空这个面板
            mazes.clear();      // 清空这个线性表
            mazes = createMap(gridPane);    // 重新画这个地图
            int index = (int)(bt.getText().charAt(bt.getText().length() - 1)) - 49; // 得到最后一个数字，就是所对应的下标
            if (listMap == null)
                listMap = getDataFromFile();
            Solution solution = listMap.get(index); //  获取当前这条数据，包含通路的方向，坐标，障碍的下标
            barrierIndex = solution.getIndex();

            for (int i = 0; i < barrierIndex.length; i++) {
                mazes.get(barrierIndex[i]).setImage("/card/barrier.png");
            }
            postion = solution.getPostion();
            list = solution.getPath();

            int[] indexOfNode = new int[list.size()];   // 保存每个坐标所对应的图中的位置
            for (int i = 0; i < list.size(); i++) {
                int[] temp = list.get(i);
                indexOfNode[i] = temp[0] * 8 + temp[1]; // 知道当前格子在mazes这个线性表中的位置
            }

            printResult(postion, indexOfNode);
        });
    }

    /**
     * 从文件里面取出数据
     */
    private HashMap<Integer, Solution> getDataFromFile() {
        HashMap<Integer, Solution> map = null;  // 临时存储从文件里面取出来的数据
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("maze.txt")));
            map = (HashMap<Integer, Solution>)in.readObject();
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
     * 保存数据到文件
     * @param map
     */
    private void saveToFile(HashMap<Integer, Solution> map) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));    // 创建流
            out.writeObject(map);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取出数据弹出框
     */
    public void alert(VBox vBoxGetData) {
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("取出数据");
        stage.setScene(new Scene(vBoxGetData, 440, 360));
        stage.show();
    }

    /**
     * 打印最终的解
     * @param postion
     * @param indexOfNode
     */
    private void printResult(ArrayList<String> postion, int[] indexOfNode) {
        for (int i = 1; i < postion.size(); i++) {
            if (postion.get(i).equals("right")){
                mazes.get(indexOfNode[i]).setImage("/card/right.png");
            }else if (postion.get(i).equals("left")) {
                mazes.get(indexOfNode[i]).setImage("/card/left.png");
            }else if (postion.get(i).equals("up")) {
                mazes.get(indexOfNode[i]).setImage("/card/up.png");
            }else {
                mazes.get(indexOfNode[i]).setImage("/card/down.png");
            }
        }
    }

    /**
     * 获取当前UI界面上显示的迷宫地图的链表
     * @param list
     */
    private void getMap(ArrayList<MapMaze> list) {
        for (int i = 0; i < list.size(); i++)
            mazes.add(list.get(i));
    }

    /**
     * 获取迷宫
     * @param list
     * @return
     */
    private int[][] getMaze(ArrayList<Integer> list) {
        int[][] maze = new int[9][8];
        int count = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 8; j++) {
                if (!list.contains(count)) {
                    maze[i][j] = 0;
                    count++;
                }
                else {
                    maze[i][j] = 1;
                    count++;
                }

            }
        }
        return maze;
    }

    /**
     * 获取随机个随机数,就是说障碍的数量
     */
    public int[] getRanomNum(ArrayList<MapMaze> list) {
        int num = (int)(Math.random() * (MAX_NUM_OF_BARRIER - 10) + 10);    // 障碍的随机数量
        int[] barrierIndex = new int[num]; // 保存障碍下标的数组
        HashSet<Integer> hashSet = new HashSet<Integer>();  // 确保生成的随机数是不重复的，所以用散列集保存

        while (hashSet.size() < num){
            hashSet.add((int)(Math.random() * 70 + 1));
        }

        int i = 0;
        Iterator<Integer> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            barrierIndex[i++] = index;
            list.get(index).setImage("/card/barrier.png");
        }

        return barrierIndex;
    }

    /**
     * 在指定的面板容器中产生一个迷宫地图,并返回包含该地图所有格子的线性表
     * @param pane
     */
    private ArrayList<MapMaze> createMap(GridPane pane) {
        ArrayList<MapMaze> mapList = new ArrayList<MapMaze>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 8; y++) {
                MapMaze maze = new MapMaze();
                if (x == 0 && y == 0)
                    maze.setImage("/card/label.png");
                if (x == 8 && y == 7)
                    maze.setImage("/card/flower.png");
                    pane.add(maze,y,x);
                mapList.add(maze);
            }
        }
        return mapList;
    }

    /**
     * 迷宫地图类
     */
    class MapMaze extends StackPane implements Serializable{
        private NewRectangle rectangle = new NewRectangle(35,35); // 默认的小矩形
        private NewImageView image;    // 矩形上面的图片

        public MapMaze() {
            rectangle.setFill(Color.AQUA);
            image = new NewImageView();
            setStyle("-fx-border-width: 1.5px;-fx-border-color: black;");
            getChildren().addAll(rectangle, image);
        }

        public void setImage(String url) {
            image.setImage(new Image(url));
        }
    }

    /**
     * 一个新的button类
     */
    public static class newButton extends Button {
        public newButton(String text) {
            setText(text);
            setMinWidth(70);
            setMinHeight(30);
            setStyle("-fx-border-color: gray;-fx-background-color: antiquewhite;");
        }

        public newButton(int width, int height, String text) {
            setMinWidth(width);
            setMinHeight(height);
            setText(text);
            setStyle("-fx-border-color: gray;-fx-background-color: antiquewhite;");
        }
    }

    class Solution implements Serializable {
         private ArrayList<int[]> path; // 保准路径
         private ArrayList<String> postion; // 保存方向
         private int[] index;   // 保存障碍的下标

        public Solution(ArrayList<int[]> newPath, ArrayList<String> newPostion, int[] indexOfBarrizer) {
           path = new ArrayList<int[]>();
           postion = new ArrayList<String>();
           index = new int[indexOfBarrizer.length];
           for (int i = 0; i < newPath.size(); i++) {
               path.add(newPath.get(i));
           }
           for (int i = 0; i < newPostion.size(); i++) {
               postion.add(newPostion.get(i));
           }
           System.arraycopy(indexOfBarrizer, 0, index, 0, indexOfBarrizer.length);
        }

        public int[] getIndex() {
            return index;
        }

        public ArrayList<int[]> getPath() {
            return path;
        }

        public ArrayList<String> getPostion() {
            return postion;
        }
    }

    class NewImageView extends ImageView implements Serializable{
    }

    class NewRectangle extends Rectangle implements Serializable {
        public NewRectangle(int width, int height) {
            super(width, height);
        }
    }
}

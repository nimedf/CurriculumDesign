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
    public final int MAX_NUM_OF_BARRIER = 25;   // �ϰ����������
    private ArrayList<MapMaze> mazes = new ArrayList<MapMaze>();    // �����Թ���ͼ��
    private File file = new File("maze.txt");       // ��Ĭ�ϵ��ļ�������һ���ļ�����
    private HashMap<Integer, Solution> listMap = null;  // �������ݣ�ÿ�α���һ��ͨ·�������������
    private int[] barrierIndex; // �����ϰ����±������
    private ArrayList<int[]> list;    // ����·������ģ���������ÿһ�����鶼��һ���������{x, y}
    private ArrayList<String> postion; // ����ͨ·�ķ���ģ�up, down, left, right
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane(); // �Թ���ͼ���������
        VBox vBox = new VBox(20);   // �Թ��ұߵİ�ť�������
        vBox.setAlignment(Pos.CENTER);  // ����vBox��������Ľ��Ķ��뷽ʽΪ����
        BorderPane borderPane = new BorderPane();   // ����GUI�����������
        ArrayList<Integer> listBarrier = new ArrayList<Integer>();  // �����ϰ����±������
        // �����ڱ߾�, �������Ҷ���10px
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        // �����ұߵİ�ť������
        newButton btOk = new newButton("Ok");
        newButton btCancel = new newButton("Cancel");
        newButton btRandomCreate = new newButton("�������");
        newButton btPut = new newButton("��������");
        newButton btGet = new newButton("ȡ������");
        newButton btClear = new newButton("�������");
        vBox.getChildren().addAll(btOk, btCancel, btRandomCreate, btPut, btGet, btClear);

        createMap(gridPane);    // ����GUI�����
        borderPane.setCenter(gridPane);
        borderPane.setRight(vBox);

        // ������ť��ע���¼�
        btRandomCreate.setOnAction(event -> {
            listBarrier.clear();        // ��ձ����ϰ������Ա�
            mazes.clear();
            gridPane.getChildren().clear(); // ���gridPane����Ľ��
            ArrayList<MapMaze> newList = createMap(gridPane);   // ����ͼ�����еĸ���
            getMap(newList);
            barrierIndex = getRanomNum(newList);  // ����������ϰ�
            for (int i = 0; i < barrierIndex.length; i++)
                listBarrier.add(barrierIndex[i]);
        });

        // ��ʼ�Թ���Ϸ
        btOk.setOnAction(event -> {
            int[][] maze = getMaze(listBarrier);   // �����Թ��Ķ�ά����ģ��
            MazeAlgorithm a = new MazeAlgorithm(maze);  // Ϊ�˻�ȡ���յĽ��
            a.bfsGetPath(0, 0);
            list = a.getPath();    // ����·�������
            postion = a.getPostion(); // ����ͨ·�ķ����
            if (list.size() == 0 && postion.size() == 0){
                StackAnimation.alert(Alert.AlertType.WARNING, "�޽�");
                return;
            }
            int[] indexOfNode = new int[list.size()];   // ����ÿ����������Ӧ��ͼ�е�λ��
            for (int i = 0; i < list.size(); i++) {
                int[] temp = list.get(i);
                indexOfNode[i] = temp[0] * 8 + temp[1];
            }

            printResult(postion, indexOfNode);
        });

        // ȡ��
        btCancel.setOnAction(event -> {
            createMap(gridPane);    // ����grid Pane�������
        });

        // ��������
        btPut.setOnAction(event -> {
            if (barrierIndex != null) {    // ȷ�����ǿյ�
                listMap = getDataFromFile();    // �Ȼ�ȡ֮ǰ�����ļ����������
                if (listMap == null) {
                    listMap = new HashMap<Integer, Solution>();
                    listMap.put(0, new Solution(list, postion, barrierIndex));
                }else
                    // �򼯺��������һ������
                    listMap.put(listMap.size(), new Solution(list, postion, barrierIndex));
                saveToFile(listMap);       // �������ݵ��ļ�
                StackAnimation.alert(Alert.AlertType.INFORMATION, "����ɹ�");
            } else
                StackAnimation.alert(Alert.AlertType.WARNING, "���棺û��ʲô�������Ա���!");
        });

        // ȡ������
        btGet.setOnAction(event -> {
            VBox vBoxGetData = new VBox(10);

            HashMap<Integer, Solution> map = getDataFromFile(); // ���ļ�����ȡ������
            for (int i = 0; i < map.size(); i++) {
                String text = "����" + (i + 1);
                newButton bt = new newButton(440, 20, text);
                linster(bt, gridPane);
                vBoxGetData.getChildren().add(bt);
            }
            alert(vBoxGetData);
        });

        btClear.setOnAction(event -> {
            listMap = getDataFromFile();    // �Ȼ�ȡ֮ǰ�����ļ����������
            if (listMap == null)
                listMap = new HashMap<Integer, Solution>();
            else
                listMap.clear();

            saveToFile(listMap);       // �������ݵ��ļ�
            StackAnimation.alert(Alert.AlertType.WARNING, "ɾ���ɹ�");
        });

        Scene scene = new Scene(borderPane, 440, 360);
        primaryStage.setTitle("�����Թ�");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * ȡ�����������ÿ�����ݵļ�������
     * @param bt
     * @param gridPane
     */
    private void linster(newButton bt, GridPane gridPane) {
        bt.setOnAction(event -> {
            gridPane.getChildren().clear(); // ���������
            mazes.clear();      // ���������Ա�
            mazes = createMap(gridPane);    // ���»������ͼ
            int index = (int)(bt.getText().charAt(bt.getText().length() - 1)) - 49; // �õ����һ�����֣���������Ӧ���±�
            if (listMap == null)
                listMap = getDataFromFile();
            Solution solution = listMap.get(index); //  ��ȡ��ǰ�������ݣ�����ͨ·�ķ������꣬�ϰ����±�
            barrierIndex = solution.getIndex();

            for (int i = 0; i < barrierIndex.length; i++) {
                mazes.get(barrierIndex[i]).setImage("/card/barrier.png");
            }
            postion = solution.getPostion();
            list = solution.getPath();

            int[] indexOfNode = new int[list.size()];   // ����ÿ����������Ӧ��ͼ�е�λ��
            for (int i = 0; i < list.size(); i++) {
                int[] temp = list.get(i);
                indexOfNode[i] = temp[0] * 8 + temp[1]; // ֪����ǰ������mazes������Ա��е�λ��
            }

            printResult(postion, indexOfNode);
        });
    }

    /**
     * ���ļ�����ȡ������
     */
    private HashMap<Integer, Solution> getDataFromFile() {
        HashMap<Integer, Solution> map = null;  // ��ʱ�洢���ļ�����ȡ����������
        try {
            if (!file.exists())
                file.createNewFile();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("maze.txt")));
            map = (HashMap<Integer, Solution>)in.readObject();
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
     * �������ݵ��ļ�
     * @param map
     */
    private void saveToFile(HashMap<Integer, Solution> map) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));    // ������
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
     * ȡ�����ݵ�����
     */
    public void alert(VBox vBoxGetData) {
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("ȡ������");
        stage.setScene(new Scene(vBoxGetData, 440, 360));
        stage.show();
    }

    /**
     * ��ӡ���յĽ�
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
     * ��ȡ��ǰUI��������ʾ���Թ���ͼ������
     * @param list
     */
    private void getMap(ArrayList<MapMaze> list) {
        for (int i = 0; i < list.size(); i++)
            mazes.add(list.get(i));
    }

    /**
     * ��ȡ�Թ�
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
     * ��ȡ����������,����˵�ϰ�������
     */
    public int[] getRanomNum(ArrayList<MapMaze> list) {
        int num = (int)(Math.random() * (MAX_NUM_OF_BARRIER - 10) + 10);    // �ϰ����������
        int[] barrierIndex = new int[num]; // �����ϰ��±������
        HashSet<Integer> hashSet = new HashSet<Integer>();  // ȷ�����ɵ�������ǲ��ظ��ģ�������ɢ�м�����

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
     * ��ָ������������в���һ���Թ���ͼ,�����ذ����õ�ͼ���и��ӵ����Ա�
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
     * �Թ���ͼ��
     */
    class MapMaze extends StackPane implements Serializable{
        private NewRectangle rectangle = new NewRectangle(35,35); // Ĭ�ϵ�С����
        private NewImageView image;    // ���������ͼƬ

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
     * һ���µ�button��
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
         private ArrayList<int[]> path; // ��׼·��
         private ArrayList<String> postion; // ���淽��
         private int[] index;   // �����ϰ����±�

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

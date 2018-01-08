package exam4.version2;

import exam4.version1.StackAnimation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BinaryTreeAnimation extends Application{
	public final static int RADIUS = 20;	// Ĭ�ϵİ뾶����
	public final static int VGAP = 50;		// Ĭ�ϵ�����߶�
	public final static int WIDTH_OF_PANE = 800;   // Ĭ�ϵ������
	private static BinaryTree<Integer> binaryTree = new BinaryTree<Integer>(); // �������ݵĶ�����
    private static ArrayList<NewCircle> circles = new ArrayList<NewCircle>();  // ����ԲȦ��

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();	// �ܵ�UI������������
		Pane pane = new Pane();	// �ܵ�UI�����в����������
		HBox hBox = new HBox(10);	// ���ײ����������
		hBox.setAlignment(Pos.CENTER_RIGHT);
		hBox.setPadding(new Insets(10));    // �����ڱ߾࣬�ϡ��¡����Ҷ�Ϊ10

		Text text = new Text("Enter a key:");
		TextField tfKey = new TextField();
		Button btSearch = new Button("Search");
		Button btInsert = new Button("Insert");
		Button btRemove = new Button("Remove");
		hBox.getChildren().addAll(text, tfKey, btSearch, btInsert, btRemove);

		// ������ť��ע���¼�
		btSearch.setOnAction(event -> {
			if (StackAnimation.isSafety(tfKey)) {   // �ж�������Ƿ�Ϸ�����ȷ
                for (int i = 0; i < circles.size(); i++) {      // ��ʼ������ԲȦ
                    circles.get(i).setFill(Color.WHITE);
                    circles.get(i).getLabel().setTextFill(Color.BLACK);
                }
			    int key = Integer.parseInt(tfKey.getText());    // ��ȡҪ��ѯ��ֵ
                if (binaryTree.search(key)) {   // ����ܲ�ѯ��ֵ
                    for (int i = 0; i < circles.size(); i++) {  // �����ҵ���Ԫ����ӱ���ɫ
                        if (circles.get(i).getLabelValue() == key) {
                            circles.get(i).setFill(Color.RED);
                            circles.get(i).getLabel().setTextFill(Color.WHITE);
                        }
                    }
                }else
                    StackAnimation.alert(Alert.AlertType.WARNING, "���棺û���ҵ������ֵ!");
			}
		});

		btInsert.setOnAction(event -> {
			if (StackAnimation.isSafety(tfKey)) {	// �ж�������Ƿ�Ϸ�����ȷ
				int value = Integer.parseInt(tfKey.getText());	// ��ȡ��Ӧ��ֵ
                if (!binaryTree.search(value)) {
                    binaryTree.insert(value);	// ��������в�������
                    pane.getChildren().clear(); // ������
                    displayTree(pane, binaryTree.getRoot(), WIDTH_OF_PANE / 2, 30, WIDTH_OF_PANE / 4);
                }else
                    StackAnimation.alert(Alert.AlertType.WARNING, "���棺�Ѵ��ڸ�Ԫ��!");
			}
		});

		btRemove.setOnAction(event -> {
			if (StackAnimation.isSafety(tfKey)) {
			    int key = Integer.parseInt(tfKey.getText());
			    if (binaryTree.delete(key)) {
                    pane.getChildren().clear();
                    displayTree(pane, binaryTree.getRoot(), WIDTH_OF_PANE / 2, 30, WIDTH_OF_PANE / 4);
                }else
                    StackAnimation.alert(Alert.AlertType.WARNING, "���棺ɾ��ʧ��!");
			}
		});

		borderPane.setCenter(pane);
		borderPane.setBottom(hBox);
		primaryStage.setTitle("������");
		primaryStage.setScene(new Scene(borderPane, 800, 500));
		primaryStage.show();
	}

	public static void displayTree(Pane pane, BinaryTree.TreeNode root, int x, int y, int hGap) {
	    NewCircle circle = new NewCircle(root.element + "");    // �������
        circle.setLayoutX(x - RADIUS);
        circle.setLayoutY(y - RADIUS);
        circles.add(circle);
        pane.getChildren().add(circle);
        if (root.left != null) {
            connectLeftChild(pane, x - hGap, y + VGAP, x, y);
            displayTree(pane, root.left, x - hGap, y + VGAP, hGap / 2);
        }

        if (root.right != null) {
            connectRightChild(pane, x + hGap, y + VGAP, x, y);
            displayTree(pane, root.right, x + hGap, y + VGAP, hGap / 2);
        }
    }

    private static void connectLeftChild(Pane pane, int x1, int y1, int x2, int y2) {
	    double d = Math.sqrt(VGAP * VGAP + (x2 - x1) * (x2 - x1));
	    int x11 = (int)(x1 + RADIUS * (x2 - x1) /d);
	    int y11 = (int)(y1 - RADIUS * VGAP / d);
	    int x21 = (int)(x2 - RADIUS * (x2 - x1) / d);
	    int y21 = (int)(y2 + RADIUS * VGAP / d);
	    Line line = new Line(x11, y11, x21, y21);
	    pane.getChildren().add(line);
    }

    private static void connectRightChild(Pane pane, int x1, int y1, int x2, int y2) {
        double d = Math.sqrt(VGAP * VGAP + (x2 - x1) * (x2 - x1));
        int x11 = (int)(x1 - RADIUS * (x1 - x2) / d);
        int y11 = (int)(y1 - RADIUS * VGAP / d);
        int x21 = (int)(x2 + RADIUS * (x1 - x2) / d);
        int y21 = (int)(y2 + RADIUS * VGAP / d);
        Line line = new Line(x11, y11, x21, y21);
        pane.getChildren().add(line);
    }

	public static void main(String[] args) {
		Application.launch(args);
	}

	public static class NewCircle extends StackPane {
		Circle circle;
		Label label;

		public NewCircle(String text) {
			label = new Label(text);
			circle = new Circle(RADIUS);
			circle.setFill(Color.WHITE);
			setStyle("-fx-border-width: 1px;-fx-border-color: black;-fx-border-radius: 20;");
			getChildren().addAll(circle, label);
		}

        public Label getLabel() {
            return label;
        }

        public void setFill(Paint paint) {
		    circle.setFill(paint);
        }

		/**
		 * ����ÿһ������value
		 * @return
		 */
		public int getLabelValue() {
			return Integer.parseInt(label.getText());
		}
	}
}

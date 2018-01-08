package exam4.version1;

import java.util.LinkedList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QueueAnimation extends Application{
	private LinkedList<EachNode> nodes = new LinkedList<EachNode>();

	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		// ����һ��BorderPane�����
		BorderPane borPane = new BorderPane();
		FlowPane flowPaneCenter = new FlowPane(0,20);	// ����д�Ž������
		flowPaneCenter.setAlignment(Pos.CENTER_LEFT);	// ����flow Pane�Ľ��Ķ��뷽ʽ
		flowPaneCenter.setPadding(new Insets(0, 10, 0, 10));	// �����ڱ߾�

		// ����һ�仰
		Text txValue = new Text("Enter a value: ");	// ����ֵ����ʾ��
		txValue.setStyle("-fx-font-size:20px;");	// ���������СΪ20px
		TextField tfValue = new TextField();		// ����ֵ�������
		Button btEnqueue = new Button("Enqueue");		// ���밴ť Insert
		Button btDequeue = new Button("Dequeue");		// ɾ����ť Delete
		HBox hboxBottom = new HBox(8);		// ����һ��HBox��壬��ʼ���ڵ���ڵ�֮��ľ���Ϊ8
		// �����еĽڵ���ӽ�hboxBottom��
		hboxBottom.getChildren().addAll(txValue, tfValue, btEnqueue, btDequeue);
		hboxBottom.setAlignment(Pos.BOTTOM_RIGHT);	// ��������нڵ�Ķ��뷽ʽΪ���Ҷ���
		hboxBottom.setPadding(new Insets(0, 20, 20, 0));
		borPane.setBottom(hboxBottom);

		// ������ť��ע���¼�
		btEnqueue.setOnAction(event -> {
			if (!tfIsEmpty(tfValue) && isNumOfTf(tfValue)) {	// �������Ϊ�ղ������붼������
				int value = Integer.parseInt(tfValue.getText());
				EachNode node = new EachNode(value + "");
				if (nodes.size() == 0) {	// �������֮ǰһ����㶼û��
					nodes.add(node);
					flowPaneCenter.getChildren().add(node);
				}else {	// ����֮ǰ�Ѿ��н�������
					Pane pane = new Pane();
					pane.getChildren().addAll(new Line(0, 60, 30, 60),
							new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// ��ͷ
					nodes.get(nodes.size() - 1).setLabelTail("");
					node.setLabelHead("");
					nodes.add(node);
					flowPaneCenter.getChildren().addAll(pane, node);
				}
			} else
				alert(AlertType.WARNING, "���棺����Ϊ�ջ�������Ĳ�������!");
		});

		btDequeue.setOnAction(event -> {
			if (nodes.size() > 0) {
				flowPaneCenter.getChildren().clear();	// ���flow Pane��������н��
				nodes.removeFirst();
				for (int i = 0; i < nodes.size(); i++) {
					if (i == 0) {
						flowPaneCenter.getChildren().add(nodes.get(i));
						nodes.get(i).setLabelTail("head");
					}
					else {
						Pane pane = new Pane();
						pane.getChildren().addAll(new Line(0, 60, 30, 60),
								new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// ��ͷ
						flowPaneCenter.getChildren().addAll(pane, nodes.get(i));
					}
				}
			} else
				alert(AlertType.WARNING, "���棺����Ϊ��!");
		});

		borPane.setCenter(flowPaneCenter);

		primaryStage.setTitle("LinkedList������ʾ");
		primaryStage.setScene(new Scene(borPane, 1200, 400));
		primaryStage.show();
	}

	/**
	 * ������ķ���
	 * @param type
	 * @param tx
	 */
	public void alert(Alert.AlertType type, String tx) {
		Alert alert = new Alert(type);
		alert.setHeaderText(tx);
		alert.showAndWait();
	}

	/**
	 * ���������е��Ƿ�Ϊ����
	 * @param tf
	 * @return
	 */
	public boolean isNumOfTf(TextField tf) {
		if (!tfIsEmpty(tf)) {
			char[] ch = tf.getText().toCharArray();
			for (int i = 0; i < ch.length; i++)
				if (ch[i] < '0' && ch[i] > '9')
					return false;

			return true;
		}

		return false;
	}

	/**
	 * �ж�����������Ƿ�������ֵ
	 * Ϊtrue���ʾ������ǿյ�
	 * Ϊfalse��ʾ������ǿյ�
	 * @param tfIndex
	 * @return
	 */
	public boolean tfIsEmpty(TextField tfIndex) {
		if (tfIndex.getText().isEmpty()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	class NewRectangle extends StackPane {
		private Rectangle rectangle;
		private Label label = new Label("null");

		public NewRectangle(String tx) {
			rectangle = new Rectangle(40, 20);
			rectangle.setFill(Color.WHITE);
			setMaxWidth(40);
			setMaxHeight(20);
			label.setText(tx);
			setStyle("-fx-border-width: 1px;-fx-border-color: black;");
			getChildren().addAll(rectangle, label);
		}

		public void setLabel(String tx) {
			label.setText(tx);
		}
	}

	class Node extends HBox {
		private Rectangle rectangleRight;
		private NewRectangle rectangleLeft;

		public Node(String value) {
			rectangleRight = new Rectangle(20,20);
			rectangleRight.setFill(Color.WHITE);
			rectangleLeft = new NewRectangle(value);
			setMaxHeight(20);
			setMaxWidth(20);
			setStyle("-fx-border-color: black;-fx-border-width: 1px;");
			getChildren().addAll(rectangleLeft, rectangleRight);

			setAlignment(Pos.CENTER);
		}

		public NewRectangle getRectangleLeft() {
			return rectangleLeft;
		}
	}

	class EachNode extends VBox {
		private Node node;
		private Label labelHead;
		private Label labelTail;

		public EachNode(String value) {
			node = new Node(value);
			labelHead = new Label("head");
			labelTail = new Label("tail");
			getChildren().addAll(labelHead, labelTail, node);	// ��ӽ��
			setSpacing(10);	// �������¼��
		}

		public void setLabelTail(String text) {
			labelTail.setText(text);
		}

		/**
		 * ���label Head���ַ���
		 */
		public void setLabelHead(String tx) {
			labelHead.setText(tx);
		}

		/**
		 * ���ý������Ķ���ֵ
		 * @param tx
		 */
		public void setNode(String tx) {
			node.getRectangleLeft().setLabel(tx);
		}
	}
}

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
		// 创建一个BorderPane的面板
		BorderPane borPane = new BorderPane();
		FlowPane flowPaneCenter = new FlowPane(0,20);	// 面板中存放结点的面板
		flowPaneCenter.setAlignment(Pos.CENTER_LEFT);	// 设置flow Pane的结点的对齐方式
		flowPaneCenter.setPadding(new Insets(0, 10, 0, 10));	// 设置内边距

		// 最后的一句话
		Text txValue = new Text("Enter a value: ");	// 输入值的提示语
		txValue.setStyle("-fx-font-size:20px;");	// 设置字体大小为20px
		TextField tfValue = new TextField();		// 输入值的输入框
		Button btEnqueue = new Button("Enqueue");		// 插入按钮 Insert
		Button btDequeue = new Button("Dequeue");		// 删除按钮 Delete
		HBox hboxBottom = new HBox(8);		// 创建一个HBox面板，初始化节点与节点之间的距离为8
		// 把所有的节点添加进hboxBottom中
		hboxBottom.getChildren().addAll(txValue, tfValue, btEnqueue, btDequeue);
		hboxBottom.setAlignment(Pos.BOTTOM_RIGHT);	// 设置面板中节点的对齐方式为向右对齐
		hboxBottom.setPadding(new Insets(0, 20, 20, 0));
		borPane.setBottom(hboxBottom);

		// 各个按钮的注册事件
		btEnqueue.setOnAction(event -> {
			if (!tfIsEmpty(tfValue) && isNumOfTf(tfValue)) {	// 当输入框不为空并且输入都是数字
				int value = Integer.parseInt(tfValue.getText());
				EachNode node = new EachNode(value + "");
				if (nodes.size() == 0) {	// 如果加入之前一个结点都没有
					nodes.add(node);
					flowPaneCenter.getChildren().add(node);
				}else {	// 加入之前已经有结点存在了
					Pane pane = new Pane();
					pane.getChildren().addAll(new Line(0, 60, 30, 60),
							new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// 箭头
					nodes.get(nodes.size() - 1).setLabelTail("");
					node.setLabelHead("");
					nodes.add(node);
					flowPaneCenter.getChildren().addAll(pane, node);
				}
			} else
				alert(AlertType.WARNING, "警告：输入为空或者输入的不是数字!");
		});

		btDequeue.setOnAction(event -> {
			if (nodes.size() > 0) {
				flowPaneCenter.getChildren().clear();	// 清空flow Pane里面的所有结点
				nodes.removeFirst();
				for (int i = 0; i < nodes.size(); i++) {
					if (i == 0) {
						flowPaneCenter.getChildren().add(nodes.get(i));
						nodes.get(i).setLabelTail("head");
					}
					else {
						Pane pane = new Pane();
						pane.getChildren().addAll(new Line(0, 60, 30, 60),
								new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// 箭头
						flowPaneCenter.getChildren().addAll(pane, nodes.get(i));
					}
				}
			} else
				alert(AlertType.WARNING, "警告：队列为空!");
		});

		borPane.setCenter(flowPaneCenter);

		primaryStage.setTitle("LinkedList动画演示");
		primaryStage.setScene(new Scene(borPane, 1200, 400));
		primaryStage.show();
	}

	/**
	 * 弹出框的方法
	 * @param type
	 * @param tx
	 */
	public void alert(Alert.AlertType type, String tx) {
		Alert alert = new Alert(type);
		alert.setHeaderText(tx);
		alert.showAndWait();
	}

	/**
	 * 检查输入框中的是否为数字
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
	 * 判断两个输入框是否输入了值
	 * 为true则表示输入框是空的
	 * 为false表示输入框不是空的
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
			getChildren().addAll(labelHead, labelTail, node);	// 添加结点
			setSpacing(10);	// 设置上下间距
		}

		public void setLabelTail(String text) {
			labelTail.setText(text);
		}

		/**
		 * 清空label Head的字符串
		 */
		public void setLabelHead(String tx) {
			labelHead.setText(tx);
		}

		/**
		 * 重置结点里面的额数值
		 * @param tx
		 */
		public void setNode(String tx) {
			node.getRectangleLeft().setLabel(tx);
		}
	}
}

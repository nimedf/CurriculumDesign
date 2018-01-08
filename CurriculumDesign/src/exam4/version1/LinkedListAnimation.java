package exam4.version1;

import java.util.ArrayList;
import java.util.LinkedList;

import exam4.version1.ArrayListAnimation.NewPane;
import exam4.version1.ArrayListAnimation.NewRectangle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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

public class LinkedListAnimation extends Application{
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private ArrayList<EachNode> nodes = new ArrayList<EachNode>();

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
		Text txIndex = new Text(" Enter a index: ");	// 下标的输入提示语
		txIndex.setStyle("-fx-font-size:20px;");	// 设置字体大小为20px
		TextField tfIndex = new TextField();		//输入下标的输入框
		Button btSearch = new Button("Search");		// 查询按钮 Search
		Button btInsert = new Button("Insert");		// 插入按钮 Insert
		Button btDelete = new Button("Delete");		// 删除按钮 Delete
		HBox hboxBottom = new HBox(8);		// 创建一个HBox面板，初始化节点与节点之间的距离为8
		// 把所有的节点添加进hboxBottom中
		hboxBottom.getChildren().addAll(txValue, tfValue, txIndex, tfIndex, btSearch, btInsert, btDelete);
		hboxBottom.setAlignment(Pos.BOTTOM_RIGHT);	// 设置面板中节点的对齐方式为向右对齐
		hboxBottom.setPadding(new Insets(0, 20, 20, 0));
		borPane.setBottom(hboxBottom);

		// 各个按钮的注册事件
		btSearch.setOnAction(event -> {
			if (!tfIsEmpty(tfIndex) || !tfIsEmpty(tfValue)) {	// 当两个输入框中有一个不为空，就可一查询
				if (!tfIsEmpty(tfIndex) && !tfIsEmpty(tfValue)) {	// 两个输入空都不为空
					if (!isNumOfTf(tfIndex) && !isNumOfTf(tfValue)) {	// 两个输入框是否都是数字
						int value = Integer.parseInt(tfValue.getText());
						int index = Integer.parseInt(tfIndex.getText());
						if (list.get(index) == value)
							alert(AlertType.INFORMATION, "该元素为链表中的第" + index + "处的");
						else
							alert(AlertType.WARNING, "报告：链表中没有这个元素!");
					} else
						alert(AlertType.WARNING, "警告：输入值必须是数字");
				} else if (!tfIsEmpty(tfValue)){	// 只有输入值不为空
					if (!isNumOfTf(tfValue)) {	// tfValue输入框中只能是数字
						int value = Integer.parseInt(tfValue.getText());
						if (list.contains(value))
							alert(AlertType.INFORMATION, "该元素为链表中的第" + list.indexOf(value) + "处的");
						else
							alert(AlertType.WARNING, "报告：链表中没有这个元素!");
					} else
						alert(AlertType.WARNING, "警告：输入值必须是数字");
				} else if (!tfIsEmpty(tfIndex)) {
					if (!isNumOfTf(tfIndex)) {    // tfIndex输入框中只能是数字
						int index = Integer.parseInt(tfIndex.getText());
						if (index >= 0 && index < list.size())
							alert(AlertType.INFORMATION, "该元素为" + list.get(index));
						else
							alert(AlertType.WARNING, "报告：链表中没有这个元素!");
					} else
						alert(AlertType.WARNING, "警告：输入值必须是数字");
				}
			} else {	// 否则就弹出警告框
				alert(AlertType.WARNING, "警告：输入值为空");
			}
		});

		btInsert.setOnAction(event -> {
			if ((!tfIsEmpty(tfIndex) && !tfIsEmpty(tfValue))) {	// 当输入框都不为空时，或者两个输入框都不为空
				if (isNumOfTf(tfValue) && isNumOfTf(tfIndex)) {    // 判断两个输入框的值是否都是数字
					int value = Integer.parseInt(tfValue.getText());
					int index = Integer.parseInt(tfIndex.getText());
					EachNode eachNode = new EachNode(value + "");

					if (index <= list.size()) {	// 判断下标是否大于线性标的大小
						if (index == list.size() && list.size() != 0) {		// 如果下标等于线性表的大小,并且线性表的大小不为0,就追加在最后
							list.addLast(value);		// 把该value加入线性表中
							nodes.get(nodes.size() - 1).setLabelTail("");	// 移动tail
							eachNode.setLabelHead("");	// 清空head的label值
							nodes.add(eachNode);	// 将each Node加入nodes链表中
							Pane pane = new Pane();
							pane.getChildren().addAll(new Line(0, 60, 30, 60),
									new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// 箭头
							flowPaneCenter.getChildren().addAll(pane, eachNode);
						} else if (index == list.size() && list.size() == 0) {	// 下标等于线性表长度，并且线性表中没有一个元素
							nodes.add(eachNode);
							list.add(value);
							flowPaneCenter.getChildren().add(eachNode);
						} else if(index == 0 && list.size() != 0) {	// 线性表不为空，且下标始终等于0
							flowPaneCenter.getChildren().clear();	// 清空容器
							nodes.add(0, eachNode);
							list.add(0, value);
							for (int i = 0; i < nodes.size(); i++) {	// 清空所有的head和tail
								nodes.get(i).setLabelTail("");
								nodes.get(i).setLabelHead("");
								flowPaneCenter.getChildren().add(nodes.get(i));
							}
							nodes.get(0).setLabelTail("head");
							nodes.get(nodes.size() - 1).setLabelTail("tail");
						} else {		//	在线性表长度以内的任意下标
							eachNode.setLabelHead("");	// 清空head标签
							eachNode.setLabelTail("");	// 清空tail标签
							nodes.add(index, eachNode);
							list.add(index, value);
							flowPaneCenter.getChildren().clear();	// 清空flow Pane这个容器
							flowPaneCenter.getChildren().add(nodes.get(0));
							for (int i = 1; i < nodes.size(); i++) {
								Pane pane = new Pane();
								pane.getChildren().addAll(new Line(0, 60, 30, 60),
										new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// 箭头
								flowPaneCenter.getChildren().addAll(pane, nodes.get(i));
							}
						}
					} else
						alert(AlertType.WARNING, "警告：下标值超出线性表长度");
				} else
					alert(AlertType.WARNING, "警告：输入值必须是数字");
			}else if ((!tfIsEmpty(tfValue) && tfIsEmpty(tfIndex))) {	// 如果输入下标的输入框为空，但是输入值的输入框不为空
				if (isNumOfTf(tfValue)) {	// 判断两个输入框的值是否都是数字
					int value = Integer.parseInt(tfValue.getText());	// 获取tfValue输入框中的值
					list.add(value);
					EachNode eachNode = new EachNode(value + "");

					if (list.size() - 1 == 0) {    // 表明这是链表中的第一个元素
						nodes.add(eachNode);
						flowPaneCenter.getChildren().add(eachNode);    // 这个时候头，尾结点都在第一个
					}
					else {
						nodes.get(nodes.size() - 1).setLabelTail("");	// 移动tail
						eachNode.setLabelHead("");
						nodes.add(eachNode);
						Pane pane = new Pane();
						pane.getChildren().addAll(new Line(0, 60, 30, 60),
								new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));
						flowPaneCenter.getChildren().addAll(pane, eachNode);
					}
				}else
					alert(AlertType.WARNING, "警告：输入值必须是数字");
			}else {	// 否则就弹出警告框
				alert(AlertType.WARNING, "警告：输入值为空");
			}
		});

		btDelete.setOnAction(event -> {
			if (tfIsEmpty(tfValue) && !tfIsEmpty(tfIndex)) {	// 当index下标框不为空时
				if (isNumOfTf(tfIndex)) {	// 判断输入框是否时数字
					int index = Integer.parseInt(tfIndex.getText());
					if (index >= 0 && index < list.size()) {	// 如果index在list的大小范围
						if (index != 0) {
							flowPaneCenter.getChildren().remove(nodes.get(index));
							nodes.remove(index);
							list.remove(index);
						}else {
							flowPaneCenter.getChildren().remove(nodes.get(index));
							nodes.remove(index);
							nodes.get(index).setLabelTail("head");
							list.remove(index);
						}
					} else
						alert(AlertType.WARNING, "警告：下标不合法!");
				} else
					alert(AlertType.WARNING, "警告：输入值只能是数字!");
			} else if (tfIsEmpty(tfValue) && tfIsEmpty(tfIndex)) {	// 两个下标框都为空的时候
				if (list.size() > 0) { 	// 链表不为空
					flowPaneCenter.getChildren().remove(nodes.get(0));
					nodes.remove(0);
					nodes.get(0).setLabelTail("head");
					list.remove();
				}
				else
					alert(AlertType.WARNING, "警告：链表为空");
			}
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

package exam4.version1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class StackAnimation extends Application {
	private LinkedList<NewRectangle> nodes = new LinkedList<NewRectangle>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		BorderPane borderPane = new BorderPane();	// 总的面板容器
		VBox vBox = new VBox(0);	// 中间添加结点的容器
		HBox hBox = new HBox(10);	// 在总面板容器底部的面板容器
		HBox hBoxArrow = new HBox(0);	// 保存最上面的矩形的箭头和矩形
		vBox.setAlignment(Pos.BOTTOM_CENTER);	// 设置vbox中的结点对齐方式为底部居中
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox);

		hBox.setAlignment(Pos.CENTER);	// 面板中结点对齐方式为居中对齐
		Text text = new Text("Enter a value:");
		TextField tfValue = new TextField();
		NewButton btPush = new NewButton("Push");
		NewButton btPop = new NewButton("Pop");
		hBox.getChildren().addAll(text, tfValue, btPush, btPop);
		hBox.setPadding(new Insets(0, 10, 10, 10));	// 设置内边距

		Pane paneArrow = new Pane();	// 保存箭头的线条的面板容器
		paneArrow.getChildren().addAll(new Line(110, 0, 172, 0),
				new Line(172,0, 146,-10), new Line(172,0, 146,10));

		// 各个按钮的注册事件
		btPush.setOnAction(event -> {
			if (isSafety(tfValue)) {
				vBox.getChildren().clear();	// 清空vBox面板容器中的结点
				hBoxArrow.getChildren().clear();
				int value = Integer.parseInt(tfValue.getText());
				NewRectangle rectangle = new NewRectangle(value + "");
				hBoxArrow.getChildren().addAll(paneArrow, rectangle);
				nodes.add(rectangle);	// 向链表里面添加元素
				for (int i = nodes.size() - 1; i >= 0; i--) {
					if (i == nodes.size() - 1) {
						vBox.getChildren().add(hBoxArrow);
						continue;
					}
					vBox.getChildren().add(nodes.get(i));
				}
			}
		});

		btPop.setOnAction(event -> {
			if (nodes.size() > 0) {
				vBox.getChildren().clear();
				hBoxArrow.getChildren().clear();
				nodes.removeLast();	// 栈的后进先出

				for (int i = nodes.size() - 1; i >= 0; i--) {
					if (i == nodes.size() - 1) {
						hBoxArrow.getChildren().addAll(paneArrow, nodes.get(i));
						vBox.getChildren().add(hBoxArrow);
						continue;
					}
					vBox.getChildren().add(nodes.get(i));
				}
			} else
				alert(Alert.AlertType.WARNING, "警告：栈为空！");
		});

		primaryStage.setTitle("栈");
		primaryStage.setScene(new Scene(borderPane, 400, 500));
		primaryStage.show();
	}

	/**
	 * 判断一个输入框是否合法,是否全是数字，是否为空
	 * @param tf
	 * @return
	 */
	public static boolean isSafety(TextField tf) {
		if (tf.getText().isEmpty()) {
			alert(Alert.AlertType.WARNING, "警告：输入框为空!");
			return false;
		} else {
			char[] ch = tf.getText().toCharArray();
			for (int i = 0; i < ch.length; i++) {
				if (ch[i] < '0' && ch[i] > '9') {
					alert(Alert.AlertType.WARNING, "警告：输入框中不是数字!");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 一个弹出框的方法
	 * @param type
	 * @param text
	 */
	public static void alert(Alert.AlertType type, String text) {
		Alert alert = new Alert(type);
		alert.setHeaderText(text);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * 每一个结点的矩形类
	 */
	class NewRectangle extends StackPane{
		private Rectangle rectangle;
		private Label label;

		public NewRectangle(String value) {
			rectangle = new Rectangle(50,25);
			rectangle.setFill(Color.WHITE);
			setMaxWidth(50);
			setMaxHeight(25);	// 设置newRectangle的最大宽度和高度
			setStyle("-fx-border-width: 1px;-fx-border-color: black;");
			label = new Label(value);
			getChildren().addAll(rectangle, label);
		}
	}

	/**
	 * 重置一个button类
	 */
	class NewButton extends Button {
		public NewButton(String text) {
			super(text);
			setMinWidth(50);
			setMinHeight(30);
		}
	}
}
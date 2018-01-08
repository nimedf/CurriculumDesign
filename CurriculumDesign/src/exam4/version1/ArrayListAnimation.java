package exam4.version1;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ArrayListAnimation extends Application{

	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		// 创建一个ArrayList列表
		MyArrayList<Integer> list = new MyArrayList<Integer>();
		// 创建一个BorderPane的面板
		BorderPane borPane = new BorderPane();
		
		// 排头的一句话
		Text txTop = new Text("array is empty, size = " + list.size + " and capacity is " + list.DEFAULT_LENGTH);
		txTop.setStyle("-fx-font-size:20px;");
		
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
		Button btTrimToSize = new Button("TrimToSize");	// 清楚空格的按钮 TrimToSize
		HBox hboxBottom = new HBox(8);		// 创建一个HBox面板，初始化节点与节点之间的距离为8
		// 把所有的节点添加进hboxBottom中
		hboxBottom.getChildren().addAll(txValue, tfValue, txIndex, tfIndex, btSearch, btInsert, btDelete, btTrimToSize);
		hboxBottom.setAlignment(Pos.BOTTOM_RIGHT);	// 设置面板中节点的对齐方式为向右对齐
		hboxBottom.setPadding(new Insets(0, 20, 20, 0));
		
		// 创建一个FlowPane ，初始化面板为水平放置，垂直间距为20
		FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 0, 20);
		flowPane.setPadding(new Insets(0, 0, 0, 20));	// 设置flowPane与左侧的距离为20
		
		// 初始化界面初始时有16个
		ArrayList<NewRectangle> rectangles = new ArrayList<NewRectangle>();
		for (int i = 0; i < MyArrayList.DEFAULT_LENGTH; i++) {
			rectangles.add(new NewRectangle(60, 30));	// 以长为60，宽慰30的初始值创建一个NewRectangle对象
			flowPane.getChildren().add(new NewPane(rectangles.get(i), rectangles.get(i).getLabel()));
		}
		flowPane.setAlignment(Pos.CENTER_LEFT);	// flowPane中节点的对齐方式为居中向左对齐
		
		// 查询按钮的注册事件
		btSearch.setOnAction(event -> {			
			// 判断是否为空
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			// tfValue框不为空，tfIndex框为空,输入了值，但是没有输入下标
			if (!tfValue.getText().isEmpty() && tfIndex.getText().isEmpty()) {
				if (list.contain(Integer.parseInt(tfValue.getText())) && 
						rectangles.contains(Integer.parseInt(tfValue.getText()))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("查找到对应元素");
					alert.setContentText("其对应下标为：" + list.indexOf(Integer.parseInt(tfValue.getText())));
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("未查找到对应元素");
					alert.showAndWait();
				}
			}
			// tfValue框为空，tfIndex框不为空,只输入了下标，但是没有输入值
			else if (tfValue.getText().isEmpty() && !tfIndex.getText().isEmpty()) {
				int index = Integer.parseInt(tfIndex.getText());
				if (index < 0 || index > list.size) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("下标大于数组链表中的元素的个数");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("查找到对应元素");
					alert.setContentText("在" + index + "处的元素为：" + list.get(index));
					alert.showAndWait();
				}
			}
			// tfValue框不为空，tfIndex框不为空
			else {
				int index = Integer.parseInt(tfIndex.getText());
				if (index < 0 || index > list.size) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("下标大于数组链表中的元素的个数");
					alert.showAndWait();
				}
				else if (list.get(index).equals(Integer.parseInt(tfValue.getText()))){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("查找到对应元素");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("未查找到对应元素");
					alert.showAndWait();
				}
			}
		});
		
		// 插入按钮的注册事件
		btInsert.setOnAction(event -> {
			// 判断是否为空
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			// 当元素个数超过默认的16个时，就在这儿默认的添加一个矩形到链表
			if (list.size + 1 > rectangles.size()) {
				rectangles.add(new NewRectangle(60, 30));
				flowPane.getChildren().add(new NewPane(rectangles.get(list.size), rectangles.get(list.size).getLabel()));
			}
			
			// tfValue框不为空，tfIndex框为空
			if (!tfValue.getText().isEmpty() && tfIndex.getText().isEmpty()) {
				list.add(Integer.parseInt(tfValue.getText())); // 数组链表里添加一个元素
				rectangles.get(list.size - 1).setLabel(tfValue.getText());
				txTop.setText("array is empty, size = " + list.size + " and capacity is " + list.getSize());
			}
			// tfValue框为空，tfIndex框不为空
			else if (tfValue.getText().isEmpty() && !tfIndex.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("提示");
				alert.setHeaderText("请输入要插入的值，不能只有下标");
				alert.showAndWait();
			}
			// tfValue框不为空，tfIndex框不为空
			else {
				if (Integer.parseInt(tfIndex.getText()) > rectangles.size() - 1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("下标不合法");
					alert.showAndWait();
					return;
				}
				list.set(Integer.parseInt(tfIndex.getText()), Integer.parseInt(tfValue.getText()));
				rectangles.get(Integer.parseInt(tfIndex.getText())).setLabel(tfValue.getText());
				txTop.setText("array is empty, size = " + list.size + " and capacity is " + list.getSize());
			}
		});
		
		// 删除按钮的注册事件
		btDelete.setOnAction(event -> {
			// 判断是否为空
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			// tfValue框不为空，tfIndex框为空
			if (!tfValue.getText().isEmpty() && tfIndex.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("请输入要删除元素的下标");
				alert.showAndWait();
			}
			// tfValue框为空，tfIndex框不为空
			else if (tfValue.getText().isEmpty() && !tfIndex.getText().isEmpty()) {
				if (list.size <= 0) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setHeaderText("已经没有元素可删了，亲");
					alert1.showAndWait();
					return;
				}
				if (Integer.parseInt(tfIndex.getText()) < 0 || Integer.parseInt(tfIndex.getText()) >= list.size) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setHeaderText("下标不合法");
					alert1.showAndWait();
					return;
				}
				list.remove(Integer.parseInt(tfIndex.getText())); // 数组链表里删除一个元素
//				rectangles.get(Integer.parseInt(tfIndex.getText())).setLabel("null");
				rectangles.remove(Integer.parseInt(tfIndex.getText()));
				flowPane.getChildren().remove(Integer.parseInt(tfIndex.getText()));
				txTop.setText("array is empty, size = " + list.size + " and capacity is " + list.getSize());
			}
			// tfValue框不为空，tfIndex框不为空
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("请输入要删除元素的下标");
				alert.showAndWait();
			}
		});
		
		// TrimToSize的注册事件
		btTrimToSize.setOnAction(event -> {
			// 判断是否为空
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			Alert alert = new Alert(AlertType.INFORMATION);
			list.trimToSize();
			alert.setHeaderText(list.toString());
			alert.showAndWait();
		});
		
		borPane.setCenter(flowPane);
		
		borPane.setTop(txTop);
		borPane.setBottom(hboxBottom);
		
		primaryStage.setTitle("数组列表动画演示");
		primaryStage.setScene(new Scene(borPane, 1200, 400));
		primaryStage.show();
	}
	
	/**
	 * 判断两个输入框是否输入了值
	 * 为true则表示输入框是空的
	 * 为false表示输入框不是空的
	 * @param tfIndex
	 * @param tfValue
	 * @return
	 */
	public boolean tfIsEmpty(TextField tfIndex, TextField tfValue) {
		if (tfIndex.getText().isEmpty() && tfValue.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("提示");
			alert.setHeaderText("警告：输入值为空");
			alert.showAndWait();
			return true;
		}
		
		return false;
	}
	
	// 可以将矩形和label绑定在一起
	class NewRectangle extends Rectangle {
		Label label = new Label("null");
		
		public NewRectangle(double width, double height) {
			super(width, height);
			setFill(Color.WHITE);		// 设置填充色为白色
			setStroke(Color.BLACK);	// 设置边线颜色为黑色
		}
		
		public Label getLabel() {
			return label;
		}
		
		public void setLabel(String s) {
			label.setText(s);
		}
	}
	
	// 将label放在矩形里面
	class NewPane extends StackPane {
		public NewPane(NewRectangle re, Label label) {
			getChildren().addAll(re, label);
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

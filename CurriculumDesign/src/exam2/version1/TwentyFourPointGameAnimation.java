package exam2.version1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TwentyFourPointGameAnimation extends Application{

	public void start(Stage primaryStage) throws Exception {
		// 总面板顶部
		HBox hbTop = new HBox(20);	// 创建top的面板容器.节点和节点之间的间隙为20
		Button btRefresh = new Button("Refresh");	// 创建刷新面板中部扑克牌的按钮refresh
		btRefresh.setMinHeight(30);		// 设置btRefresh的最小高度为30
		btRefresh.setMinWidth(100); 	// 设置btRefresh的最下宽度为100
		btRefresh.setStyle("-fx-font-size:15px;");	// 设置btRefresh中的字体大小为15px
		Text txTop = new Text("J，Q，K的值分别为11,12,13，A的值为1");	// 设置规则提示语
		txTop.setStyle("-fx-font-size:13px;");		// 设置txTop的字体大小为11px
		hbTop.getChildren().addAll(txTop, btRefresh);	// 将这两个节点加入hbTop容器中
		hbTop.setPadding(new Insets(10, 30, 0, 0));		// 设置hbTop容器的内边距
		hbTop.setAlignment(Pos.CENTER_RIGHT);		// 设置hbTop中节点的对齐方式
		
		// 面板中部的图片
		HBox hbCenter = new HBox(30);	// 创建center的面板容器.节点和节点之间的间隙为30
		ArrayList<ImageView> images = new ArrayList<ImageView>();// 保存54张扑克牌
		for (int i = 1; i <= 52; i++) {
			String s = "card/" + i + ".png";	// 拼接图片的字符串
			images.add(new ImageView(new Image(s)));	// 创建图片的对象，并加入images中
		}
		
		// 得到随机抽取扑克牌的下标
		Iterator<Integer> iterator = getFourCard(images);
		int[] index = new int[4];	// 保存扑克牌的下标
		int k = 0;
		while (iterator.hasNext()) {	// 把扑克牌放入面板hbCenter中
			int temp = iterator.next();		//保存下标值
			hbCenter.getChildren().add(images.get(temp));	// 将图片节点放入hbCenter面板容器中
			index[k++] = temp + 1;	// 下标保存在index中,加一是为了保证下标值和图片的名字数值一样
		}
		hbCenter.setAlignment(Pos.CENTER);	// 设置hbCenter容器中间节点的对齐方式为居中对齐
		
		// 面板上的底部部分
		Text tx = new Text("Enter an expression: ");	// 设置底部的提示语
		tx.setStyle("-fx-font-size:18px;");		// 设置提示语的字体大小为18px
		TextField tfExpression = new TextField();	// 创建一个输入框
		Button btVerity = new Button("Verity");		// 创建一个验证的按钮btVerity
		btVerity.setMinHeight(20);		// 设置btVerity的最小高度为20
		btVerity.setMinWidth(40);		// 设置btVerity的最小宽度为40
		btVerity.setStyle("-fx-font-size:15px;");	// 设置btVerity中的字体大小为15px
		HBox hbBottom = new HBox(5);		// 创建底部的容器，节点与节点间的距离为5
		hbBottom.getChildren().addAll(tx, tfExpression, btVerity);	// 将底部各节点加入底部容器中
		hbBottom.setPadding(new Insets(0, 0, 10, 0));	// 设置内边距
		
		// btRefresh的注册事件,点击这个按钮就刷新中间的图片
		btRefresh.setOnAction(event -> {
			hbCenter.getChildren().clear();	// 清楚原来面板中的节点
			tfExpression.setText("");
			// 得到随机抽取扑克牌的下标
			Iterator<Integer> iterator1 = getFourCard(images);
			int i = 0;
			while (iterator1.hasNext()) {	// 把扑克牌放入面板hbCenter中
				int temp = iterator1.next();		//保存下标值
				hbCenter.getChildren().add(images.get(temp));	// 将图片节点放入hbCenter面板容器中
				index[i++] = temp + 1;	// 下标保存在index中
			}
			hbCenter.setAlignment(Pos.CENTER);	// 设置hbCenter容器中间节点的对齐方式为居中对齐
		});
		
		// btVerity的注册事件，点击该按钮，就验证输入的表达式是否等于24
		btVerity.setOnAction(event -> {
			String expression = tfExpression.getText();	// 获取输入框中输入的字符串
			
			int[] num = new int[4];	// 保存输入字符串中的字符
			// 分解字符串
			StringTokenizer st = new StringTokenizer(expression, " ()（）+-*/");
			boolean isRight = false;
			int i = 0;
			// 遍历，并且验证输入的数和牌面上的数是否相等
			while (st.hasMoreElements()) {
				num[i] = Integer.parseInt((String)(st.nextElement()));
				for (int j = 0; j < index.length; j++) {
					// 因为图片保存的方式是按照花色顺序排列，13个一循环，所以根据下标来判断数值
					if ((num[i] % 13) == (index[j] % 13)) {
						isRight = true;
						break;	// 只要有一个数字相同，就说明当前的数字合法就跳出循环，判断下一个
					}
				}
				i++;
			}
			
			// 用户输入的数字不是扑克牌上的，就弹出框
			if (!isRight) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("The numbers in expression don't match the numbers in the set");
				alert.showAndWait();
			}
			else {		// 判断用户输入的表达式的结果是否是24
				Main s = new Main();	// 处理用户输入表达式，转换为运算表达式，并计算结果的		
				int result = s.getExpressionValue(expression);
				if (result == 24) {		// 结果是24就弹出Correct
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Correct");
					alert.showAndWait();
				}
				else {		// 结果不是24就弹出Incorrect result
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Incorrect result");
					alert.showAndWait();
				}
			}
		});
		
		// 总面板
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(hbTop);
		borderPane.setCenter(hbCenter);
		borderPane.setBottom(hbBottom);
		
		// 设置场景和舞台
		primaryStage.setScene(new Scene(borderPane, 420, 300));
		primaryStage.setTitle("24-Point-Card-Game");
		primaryStage.show();
	}
	
	/**
	 * 随机从54章牌中抽取四张出来，返回一个迭代器
	 * @param images
	 * @return
	 */
	public Iterator<Integer> getFourCard(ArrayList<ImageView> images) {
		Set<Integer> setIndex = new HashSet<Integer>();	// 存放被抽出的扑克牌的下标
		for (int i = 0; setIndex.size() < 4; i++) {
			setIndex.add((int)(1 + Math.random() * 51));	// 获取随机的下标
		}
		return setIndex.iterator();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

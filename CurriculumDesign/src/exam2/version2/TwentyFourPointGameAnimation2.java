package exam2.version2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import exam2.version1.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TwentyFourPointGameAnimation2 extends Application {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@SuppressWarnings("rawtypes")
	public void start(Stage primaryStage) throws Exception {
		// 总面板
		BorderPane borderPane = new BorderPane();
		// 设置面板的背景图片
		borderPane
				.setStyle("-fx-background-image:url('/exam2/version2/background.jpg');-fx-background-size:500px;");

		// 总面板顶部
		HBox hbTop = new HBox(20); // 创建top的面板容器.节点和节点之间的间隙为20
		Button btRefresh = new Button("Refresh"); // 创建刷新面板中部扑克牌的按钮refresh
		btRefresh.setMinHeight(30); // 设置btRefresh的最小高度为30
		btRefresh.setMinWidth(100); // 设置btRefresh的最下宽度为100
		btRefresh.setTextFill(Color.WHITE);
		btRefresh.setStyle("-fx-font-size:15px;-fx-background-color:rgb(0, 162, 262);"); // 设置btRefresh中的字体大小为15px
		Button btFindSolution = new Button("Find a Solution"); // 设置一个寻找解的按钮
		btFindSolution.setMinHeight(30); // 设置btFindSolution的最小高度为30
		btFindSolution.setMinWidth(150); // 设置btFindSolution的最下宽度为100
		effect(btFindSolution); // 调用方法，使按钮呈现阴影
		btFindSolution.setStyle("-fx-font-size:15px;"); // 设置btFindSolution中的字体大小为15px
		TextField tfSolution = new TextField("解会在这里显示"); // 设置一个用来显示各种解的框
		tfSolution.setMinHeight(30); // 设置tfSolution的最小高度
		tfSolution.setDisable(true); // 设置解的显示框不可编辑
		hbTop.getChildren().addAll(btFindSolution, tfSolution, btRefresh); // 将这两个节点加入hbTop容器中
		hbTop.setPadding(new Insets(10, 30, 0, 0)); // 设置hbTop容器的内边距
		hbTop.setAlignment(Pos.CENTER_RIGHT); // 设置hbTop中节点的对齐方式

		// 面板中部的图片
		HBox hbCenter = new HBox(30); // 创建center的面板容器.节点和节点之间的间隙为30
		ArrayList<ImageView> images = new ArrayList<ImageView>();// 保存54张扑克牌
		for (int i = 1; i <= 52; i++) {
			String s = "card/" + i + ".png"; // 拼接图片的字符串
			images.add(new ImageView(new Image(s))); // 创建图片的对象，并加入images中
		}

		// 得到随机抽取扑克牌的下标
		Iterator<Integer> iterator = getFourCard(images);
		int[] index = new int[4]; // 保存扑克牌的下标
		int k = 0;
		while (iterator.hasNext()) { // 把扑克牌放入面板hbCenter中
			int temp = iterator.next(); // 保存下标值
			hbCenter.getChildren().add(images.get(temp)); // 将图片节点放入hbCenter面板容器中
			imageToBig(images.get(temp));
			index[k++] = temp + 1; // 下标保存在index中,加一是为了保证下标值和图片的名字数值一样
		}
		hbCenter.setAlignment(Pos.CENTER); // 设置hbCenter容器中间节点的对齐方式为居中对齐

		// 面板上的底部部分
		Text tx = new Text("Enter an expression: "); // 设置底部的提示语
		tx.setStyle("-fx-font-size:18px;"); // 设置提示语的字体大小为18px
		TextField tfExpression = new TextField(); // 创建一个输入框
		Button btVerity = new Button("Verity"); // 创建一个验证的按钮btVerity
		btVerity.setMinHeight(20); // 设置btVerity的最小高度为20
		btVerity.setMinWidth(40); // 设置btVerity的最小宽度为40
		effect(btVerity);
		btVerity.setStyle("-fx-font-size:15px;"); // 设置btVerity中的字体大小为15px
		HBox hbBottom = new HBox(5); // 创建底部的容器，节点与节点间的距离为5
		Button btChange = new Button("Change");
		btChange.setMinHeight(20); // 设置btChange的最小高度为20
		btChange.setMinWidth(40); // 设置btChange的最小宽度为40
		effect(btChange);
		btChange.setStyle("-fx-font-size:15px;"); // 设置btChange中的字体大小为15px
		hbBottom.getChildren().addAll(tx, tfExpression, btVerity, btChange); // 将底部各节点加入底部容器中
		hbBottom.setPadding(new Insets(0, 0, 10, 0)); // 设置内边距
		hbBottom.setAlignment(Pos.CENTER);

		// 设置总面板中的节点
		borderPane.setTop(hbTop);
		borderPane.setCenter(hbCenter);
		borderPane.setBottom(hbBottom);

		// 设置音乐的目录，在对应的class目录树文件夹下
		String url1 = getClass().getResource("BGM.mp3").toString();// 第一个界面的音乐
		Media media1 = new Media(url1); // 创建一个Media对象
		MediaPlayer player1 = new MediaPlayer(media1); // 设置音频播放器
		player1.setAutoPlay(true);
		String url2 = getClass().getResource("赌神.mp3").toString();// 第二个界面的音乐
		Media media2 = new Media(url2); // 创建一个Media对象
		MediaPlayer player2 = new MediaPlayer(media2); // 设置音频播放器
		
		double[] num5 = new	double[4];
		for (int i = 0; i < index.length; i++) {
			if (index[i] % 13 != 0)
				num5[i] = index[i] % 13;
			else {
				num5[i] = 13;
			}
		}
		ArrayList<String> list = FindVarietySolution.findVaritySolution(num5);
		System.out.println(list.toString());
//		String[] exp = new String[list.size()];
//		for (int i = 0; i < list.size(); i++)
//			exp[i] = list.get(i);

		// btRefresh的注册事件,点击这个按钮就刷新中间的图片
		btRefresh.setOnAction(event -> {
			hbCenter.getChildren().clear(); // 清除原来面板中的节点
				tfExpression.setText(""); // 初始化输入表达式的输入框
				tfSolution.setText("解会在这里显示"); // 初始化显示表达式的输入框

				// 得到随机抽取扑克牌的下标
				Iterator<Integer> iterator1 = getFourCard(images);
				int j = 0;
				while (iterator1.hasNext()) { // 把扑克牌放入面板hbCenter中
					int temp = iterator1.next(); // 保存下标值
					hbCenter.getChildren().add(images.get(temp)); // 将图片节点放入hbCenter面板容器中
					imageToBig(images.get(temp));
					index[j++] = temp + 1; // 下标保存在index中
				}
				
				for (int i = 0; i < index.length; i++) {
					if (index[i] % 13 != 0)
						num5[i] = index[i] % 13;
					else {
						num5[i] = 13;
					}
				}
				list.clear();
				ArrayList<String> list1 = FindVarietySolution.findVaritySolution(num5);
				for (int i = 0; i < list1.size(); i++)
					list.add(list1.get(i));										
				
				hbCenter.setAlignment(Pos.CENTER); // 设置hbCenter容器中间节点的对齐方式为居中对齐
			});

		// btVerity的注册事件，点击该按钮，就验证输入的表达式是否等于24
		btVerity.setOnAction(event -> {
			String expression = tfExpression.getText(); // 获取输入框中输入的字符串

			int[] num = new int[4]; // 保存输入字符串中的字符
			// 分解字符串
			StringTokenizer st = new StringTokenizer(expression, " ()（）+-*/");
			boolean isRight = false;
			boolean isContinue = true;
			int i = 0;
			// 遍历，并且验证输入的数和牌面上的数是否相等
			while (st.hasMoreElements()) {
				String str = (String) (st.nextElement()); // 获取字符串
				char[] ch = str.toCharArray(); // 将字符串转换为字符数组
				for (int m = 0; m < ch.length; m++) { // 判断字符是否是数字
					if (ch[m] < '0' || ch[m] > '9') {
						isContinue = false; // 如果字符不是数字就不能进行后面的操作，循环也就不用再继续了
						break; // 退出检测是否是数字的循环
					}
				}
				if (!isContinue) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("输入不合法!!!");
					alert.showAndWait();
					break; // 退出对数字进行操作的循环
				}

				num[i] = Integer.parseInt(str);
				for (int j = 0; j < index.length; j++) {
					// 因为图片保存的方式是按照花色顺序排列，13个一循环，所以根据下标来判断数值
					if ((num[i] % 13) == (index[j] % 13)) {
						isRight = true;
						break; // 只要有一个数字相同，就说明当前的数字合法就跳出循环，判断下一个
					}
				}
				i++;
			}

			// 用户输入的数字不是扑克牌上的，就弹出框
			if (!isRight && isContinue) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("The numbers in expression don't match the numbers in the set");
				alert.showAndWait();
			}
			if (isContinue && !tfExpression.getText().isEmpty()) {
				// 判断用户输入的表达式的结果是否是24
				Main s = new Main(); // 处理用户输入表达式，转换为运算表达式，并计算结果的
				int result = s.getExpressionValue(expression);
				if (result == 24) { // 结果是24就弹出Correct
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Correct");
					alert.showAndWait();
				} else { // 结果不是24就弹出Incorrect result
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Incorrect result");
					alert.showAndWait();
				}
			}
		});

		// btFindSolution的注册事件
		btFindSolution.setOnAction(event -> {
			int a = (int)(Math.random() * (list.size() - 1));
			tfSolution.setText(list.get(a));
		});

		// btChange的注册事件
		btChange.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("点击第一个输入框会初始化所有输入框的值");
			Optional result = alert.showAndWait(); // 获取用户是点击的确定还是取消
			if (result.get() == ButtonType.OK) { // 当用户点击确定时才能跳转页面
				player1.stop(); // 让背景音乐暂停
				borderPane.getChildren().clear(); // 这个方法就可以总面板中的节点删除
				HBox newHbTop = new HBox(30); // 设置总面板上面部分的面板
				HBox newHbCenter = new HBox(10); // 设置总面板中部的面板，并初始化节点与节点间的间隙为10
				HBox newHbBottom = new HBox(20); // 设置总面板底部的面板,并初始化节点与节点间的间隙为20

				// 新的面板中顶部的提示话语
				Text txPrompting = new Text("Input for numbers between 1 to 13");
				txPrompting.setFill(Color.WHITE);
				txPrompting.setStyle("-fx-font-size:20px;");
				Button btExit = new Button("Exit"); // 设置返回上一界面的按钮
				btExit.setMinHeight(30); // 为btFindSolution按钮设置新的高度为50
				btExit.setMinWidth(50); // 为btFindSolution按钮设置新的宽度为100
				btExit.setStyle("-fx-font-size:20px;");
				newHbTop.getChildren().addAll(btExit, txPrompting); // 将btExit,txPrompting添加进上部
				newHbTop.setAlignment(Pos.CENTER); // 设置面板中的节点对齐方式为居中对齐
				newHbTop.setPadding(new Insets(10, 10, 0, 10)); // 设置newHbTop的内边距

				// 新的面板中的中部的节点
				TextField tfValue1 = new TextField(); // 设置4个最小宽度和高度都为80的输入框
				tfValue1.setMinHeight(80);
				tfValue1.setMinWidth(80);
				checkIsNum(tfValue1); // 检查输入的是否是数字
				tfValue1.setStyle("-fx-font-size:40px;"); // 设置这个输入框中的字体大小为40px
				TextField tfValue2 = new TextField(); // 设置4个最小宽度和高度都为80的输入框
				tfValue2.setMinHeight(80);
				tfValue2.setMinWidth(80);
				checkIsNum(tfValue2); // 检查输入的是否是数字
				tfValue2.setStyle("-fx-font-size:40px;"); // 设置这个输入框中的字体大小为40px
				TextField tfValue3 = new TextField(); // 设置4个最小宽度和高度都为80的输入框
				tfValue3.setMinHeight(80);
				tfValue3.setMinWidth(80);
				checkIsNum(tfValue3); // 检查输入的是否是数字
				tfValue3.setStyle("-fx-font-size:40px;"); // 设置这个输入框中的字体大小为40px
				TextField tfValue4 = new TextField(); // 设置4个最小宽度和高度都为80的输入框
				tfValue4.setMinHeight(80);
				tfValue4.setMinWidth(80);
				checkIsNum(tfValue4); // 检查输入的是否是数字
				tfValue4.setStyle("-fx-font-size:40px;"); // 设置这个输入框中的字体大小为40px
				// 将4个输入框都添加在中间面板中
				newHbCenter.getChildren().addAll(tfValue1, tfValue2, tfValue3,
						tfValue4);
				newHbCenter.setPadding(new Insets(0, 10, 0, 10)); // 设置newHbCenter的内边距
				newHbCenter.setAlignment(Pos.CENTER); // 设置面板中的节点对齐方式为居中对齐

				// 新的面板中底部的各节点
				TextField tfLook = new TextField("解会在这里显示"); // 一个显示出解的输入框
				tfLook.setDisable(true); // 设置该输入框不可输入，也就是只读属性
				tfLook.setMinHeight(50); // 设置该输入框的最小高度为50
				tfLook.setMinWidth(100); // 设置该输入框的最小宽度为100
				tfLook.setStyle("-fx-font-size:20px;"); // 设置输入框中的字体大小为20px
				Button btSolution = new Button("Find a Solution"); // 设置一个查询solution的按钮
				btSolution.setMinHeight(50); // 为btFindSolution按钮设置新的高度为50
				btSolution.setMinWidth(100); // 为btFindSolution按钮设置新的宽度为100
				btSolution.setStyle("-fx-font-size:20px;");
				newHbBottom.getChildren().addAll(tfLook, btSolution); // 将新的底部节点添加进底部的面板中
				newHbBottom.setAlignment(Pos.CENTER); // 设置面板中的节点对齐方式为居中对齐
				newHbBottom.setPadding(new Insets(0, 10, 10, 10)); // 设置newHbBottom的内边距

				// 对btExit按钮进行注册事件
				btExit.setOnAction(e -> {
					Alert alert1 = new Alert(AlertType.CONFIRMATION);
					alert1.setHeaderText("确定退出?");
					Optional newResult = alert1.showAndWait();
					if (newResult.get() == ButtonType.OK) {
						borderPane.setTop(hbTop);
						borderPane.setCenter(hbCenter);
						borderPane.setBottom(hbBottom);
						borderPane.setStyle("-fx-background-image:url('/exam2/version2/background.jpg');-fx-background-size:500px;");
						player2.stop();
						player1.play();
					}
				});

				// btSolution按钮的注册事件
				btSolution.setOnAction(e -> {
					if (!isUnderThirteen(tfValue1, tfValue2, tfValue3, tfValue4)) { // 检查输入的值是否超出13
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setHeaderText("输入框为空，或者输入的数超过13");
						alert1.showAndWait();
					}
					else {
						double[] num1 = new double[4];
						num1[0] = Integer.parseInt(tfValue1.getText());
						num1[1] = Integer.parseInt(tfValue2.getText());
						num1[2] = Integer.parseInt(tfValue3.getText());
						num1[3] = Integer.parseInt(tfValue4.getText());
						ArrayList<String> list2 = FindVarietySolution.findVaritySolution(num1);
						int a = (int)(Math.random() * (list2.size() - 1));
						tfLook.setText(list2.get(a));
					}
				});

				// tfValue1的注册事件，单击一次这个输入框就会初始化tfLook框,并且初始化输入框
				// 设置tfValue1的监听器，当在这个区域输入值得时候就初始化tfLook框
				tfValue1.setOnMouseClicked(e -> {
					tfValue1.setText(""); // 让四个输入框的值变为空
					tfValue2.setText("");
					tfValue3.setText("");
					tfValue4.setText("");
					tfLook.setText("解会在这里显示");
				});

				// 重置面板的背景图片
				borderPane.setStyle("-fx-background-image:url('/exam2/version2/background2.jpg');-fx-background-size:500px;");
				// 将newHbBottom， newHbCenter，newHbTop添加进总面板
				borderPane.setTop(newHbTop);
				borderPane.setCenter(newHbCenter);
				borderPane.setBottom(newHbBottom);
				player2.setAutoPlay(true);
			}
		});

		// 设置场景和舞台
		primaryStage.setScene(new Scene(borderPane, 500, 300));
		primaryStage.setTitle("24-Point-Card-Game");
		primaryStage.show();
	}

	/**
	 * 图片会放大
	 * @param img
	 */
	public void imageToBig(ImageView img) {
		// Adding the shadow when the mouse cursor is on
		img.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			img.setFitWidth(110);
			img.setFitHeight(146);
		});

		// Removing the shadow when the mouse cursor is off
		img.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			img.setFitWidth(72);
			img.setFitHeight(96);
		});
	}

	/**
	 * 当鼠标放在按钮上时，按钮呈现阴影
	 * 
	 * @param bt
	 */
	public void effect(Button bt) {

		DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		bt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			bt.setEffect(shadow);
			bt.setTextFill(Color.WHITE);
			bt.setStyle("-fx-background-color:red;-fx-font-size:15px;");
		});

		// Removing the shadow when the mouse cursor is off
		bt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			bt.setEffect(null);
			bt.setTextFill(Color.BLACK);
			bt.setStyle("-fx-background-color:#DDDDDD;");
		});
	}

	/**
	 * 将四个输入框传进来看是否为空，以及判断输入的数值是否正确
	 * 
	 * @param tf1
	 * @param tf2
	 * @param tf3
	 * @param tf4
	 */
	public boolean isUnderThirteen(TextField tf1, TextField tf2, TextField tf3,	TextField tf4) {
		 // 判断这四个输入框是否为空
		if (!tf1.getText().isEmpty() && !tf2.getText().isEmpty() && !tf3.getText().isEmpty() && !tf4.getText().isEmpty()) {
			if (Integer.parseInt(tf1.getText()) > 13
					|| Integer.parseInt(tf2.getText()) > 13
					|| Integer.parseInt(tf3.getText()) > 13
					|| Integer.parseInt(tf4.getText()) > 13) { // 判断这四个框中的值是否大于13
				
				return false;
			}
			else	// 表明数是小于13的
				return true;
		} else	// 表明输入框是空的
			return false;
	}

	/**
	 * 判断用户输入的是否是数字，如果不是数字就弹出提示
	 * 
	 * @param tfValue
	 */
	public void checkIsNum(TextField tfValue) {

		// 监听键盘事件，控制用户只能输入数字，而不能输入其他的
		tfValue.setOnKeyPressed(e -> {
			if (e.getCode() != KeyCode.NUMPAD0
					&& e.getCode() != KeyCode.NUMPAD1
					&& e.getCode() != KeyCode.NUMPAD2
					&& e.getCode() != KeyCode.NUMPAD3
					&& e.getCode() != KeyCode.NUMPAD4
					&& e.getCode() != KeyCode.NUMPAD5
					&& e.getCode() != KeyCode.NUMPAD6
					&& e.getCode() != KeyCode.NUMPAD7
					&& e.getCode() != KeyCode.NUMPAD8
					&& e.getCode() != KeyCode.NUMPAD9
					&& e.getCode() != KeyCode.BACK_SPACE
					&& e.getCode() != KeyCode.SHIFT
					&& e.getCode() != KeyCode.ENTER
					&& e.getCode() != KeyCode.DIGIT0
					&& e.getCode() != KeyCode.DIGIT1
					&& e.getCode() != KeyCode.DIGIT2
					&& e.getCode() != KeyCode.DIGIT3
					&& e.getCode() != KeyCode.DIGIT4
					&& e.getCode() != KeyCode.DIGIT5
					&& e.getCode() != KeyCode.DIGIT6
					&& e.getCode() != KeyCode.DIGIT7
					&& e.getCode() != KeyCode.DIGIT8
					&& e.getCode() != KeyCode.DIGIT9
					&& e.getCode() != KeyCode.TAB) {

				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setHeaderText("请输入数字!!");
				alert1.showAndWait();
			}
		});
	}

	/**
	 * 随机从54章牌中抽取四张出来，返回一个迭代器
	 * 
	 * @param images
	 * @return
	 */
	public Iterator<Integer> getFourCard(ArrayList<ImageView> images) {
		Set<Integer> setIndex = new HashSet<Integer>(); // 存放被抽出的扑克牌的下标
		for (int i = 0; setIndex.size() < 4; i++) {
			setIndex.add((int) (1 + Math.random() * 51)); // 获取随机的下标
		}
		return setIndex.iterator();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
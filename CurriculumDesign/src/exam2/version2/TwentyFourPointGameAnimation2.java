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
		// �����
		BorderPane borderPane = new BorderPane();
		// �������ı���ͼƬ
		borderPane
				.setStyle("-fx-background-image:url('/exam2/version2/background.jpg');-fx-background-size:500px;");

		// ����嶥��
		HBox hbTop = new HBox(20); // ����top���������.�ڵ�ͽڵ�֮��ļ�϶Ϊ20
		Button btRefresh = new Button("Refresh"); // ����ˢ������в��˿��Ƶİ�ťrefresh
		btRefresh.setMinHeight(30); // ����btRefresh����С�߶�Ϊ30
		btRefresh.setMinWidth(100); // ����btRefresh�����¿��Ϊ100
		btRefresh.setTextFill(Color.WHITE);
		btRefresh.setStyle("-fx-font-size:15px;-fx-background-color:rgb(0, 162, 262);"); // ����btRefresh�е������СΪ15px
		Button btFindSolution = new Button("Find a Solution"); // ����һ��Ѱ�ҽ�İ�ť
		btFindSolution.setMinHeight(30); // ����btFindSolution����С�߶�Ϊ30
		btFindSolution.setMinWidth(150); // ����btFindSolution�����¿��Ϊ100
		effect(btFindSolution); // ���÷�����ʹ��ť������Ӱ
		btFindSolution.setStyle("-fx-font-size:15px;"); // ����btFindSolution�е������СΪ15px
		TextField tfSolution = new TextField("�����������ʾ"); // ����һ��������ʾ���ֽ�Ŀ�
		tfSolution.setMinHeight(30); // ����tfSolution����С�߶�
		tfSolution.setDisable(true); // ���ý����ʾ�򲻿ɱ༭
		hbTop.getChildren().addAll(btFindSolution, tfSolution, btRefresh); // ���������ڵ����hbTop������
		hbTop.setPadding(new Insets(10, 30, 0, 0)); // ����hbTop�������ڱ߾�
		hbTop.setAlignment(Pos.CENTER_RIGHT); // ����hbTop�нڵ�Ķ��뷽ʽ

		// ����в���ͼƬ
		HBox hbCenter = new HBox(30); // ����center���������.�ڵ�ͽڵ�֮��ļ�϶Ϊ30
		ArrayList<ImageView> images = new ArrayList<ImageView>();// ����54���˿���
		for (int i = 1; i <= 52; i++) {
			String s = "card/" + i + ".png"; // ƴ��ͼƬ���ַ���
			images.add(new ImageView(new Image(s))); // ����ͼƬ�Ķ��󣬲�����images��
		}

		// �õ������ȡ�˿��Ƶ��±�
		Iterator<Integer> iterator = getFourCard(images);
		int[] index = new int[4]; // �����˿��Ƶ��±�
		int k = 0;
		while (iterator.hasNext()) { // ���˿��Ʒ������hbCenter��
			int temp = iterator.next(); // �����±�ֵ
			hbCenter.getChildren().add(images.get(temp)); // ��ͼƬ�ڵ����hbCenter���������
			imageToBig(images.get(temp));
			index[k++] = temp + 1; // �±걣����index��,��һ��Ϊ�˱�֤�±�ֵ��ͼƬ��������ֵһ��
		}
		hbCenter.setAlignment(Pos.CENTER); // ����hbCenter�����м�ڵ�Ķ��뷽ʽΪ���ж���

		// ����ϵĵײ�����
		Text tx = new Text("Enter an expression: "); // ���õײ�����ʾ��
		tx.setStyle("-fx-font-size:18px;"); // ������ʾ��������СΪ18px
		TextField tfExpression = new TextField(); // ����һ�������
		Button btVerity = new Button("Verity"); // ����һ����֤�İ�ťbtVerity
		btVerity.setMinHeight(20); // ����btVerity����С�߶�Ϊ20
		btVerity.setMinWidth(40); // ����btVerity����С���Ϊ40
		effect(btVerity);
		btVerity.setStyle("-fx-font-size:15px;"); // ����btVerity�е������СΪ15px
		HBox hbBottom = new HBox(5); // �����ײ����������ڵ���ڵ��ľ���Ϊ5
		Button btChange = new Button("Change");
		btChange.setMinHeight(20); // ����btChange����С�߶�Ϊ20
		btChange.setMinWidth(40); // ����btChange����С���Ϊ40
		effect(btChange);
		btChange.setStyle("-fx-font-size:15px;"); // ����btChange�е������СΪ15px
		hbBottom.getChildren().addAll(tx, tfExpression, btVerity, btChange); // ���ײ����ڵ����ײ�������
		hbBottom.setPadding(new Insets(0, 0, 10, 0)); // �����ڱ߾�
		hbBottom.setAlignment(Pos.CENTER);

		// ����������еĽڵ�
		borderPane.setTop(hbTop);
		borderPane.setCenter(hbCenter);
		borderPane.setBottom(hbBottom);

		// �������ֵ�Ŀ¼���ڶ�Ӧ��classĿ¼���ļ�����
		String url1 = getClass().getResource("BGM.mp3").toString();// ��һ�����������
		Media media1 = new Media(url1); // ����һ��Media����
		MediaPlayer player1 = new MediaPlayer(media1); // ������Ƶ������
		player1.setAutoPlay(true);
		String url2 = getClass().getResource("����.mp3").toString();// �ڶ������������
		Media media2 = new Media(url2); // ����һ��Media����
		MediaPlayer player2 = new MediaPlayer(media2); // ������Ƶ������
		
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

		// btRefresh��ע���¼�,��������ť��ˢ���м��ͼƬ
		btRefresh.setOnAction(event -> {
			hbCenter.getChildren().clear(); // ���ԭ������еĽڵ�
				tfExpression.setText(""); // ��ʼ��������ʽ�������
				tfSolution.setText("�����������ʾ"); // ��ʼ����ʾ���ʽ�������

				// �õ������ȡ�˿��Ƶ��±�
				Iterator<Integer> iterator1 = getFourCard(images);
				int j = 0;
				while (iterator1.hasNext()) { // ���˿��Ʒ������hbCenter��
					int temp = iterator1.next(); // �����±�ֵ
					hbCenter.getChildren().add(images.get(temp)); // ��ͼƬ�ڵ����hbCenter���������
					imageToBig(images.get(temp));
					index[j++] = temp + 1; // �±걣����index��
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
				
				hbCenter.setAlignment(Pos.CENTER); // ����hbCenter�����м�ڵ�Ķ��뷽ʽΪ���ж���
			});

		// btVerity��ע���¼�������ð�ť������֤����ı��ʽ�Ƿ����24
		btVerity.setOnAction(event -> {
			String expression = tfExpression.getText(); // ��ȡ�������������ַ���

			int[] num = new int[4]; // ���������ַ����е��ַ�
			// �ֽ��ַ���
			StringTokenizer st = new StringTokenizer(expression, " ()����+-*/");
			boolean isRight = false;
			boolean isContinue = true;
			int i = 0;
			// ������������֤��������������ϵ����Ƿ����
			while (st.hasMoreElements()) {
				String str = (String) (st.nextElement()); // ��ȡ�ַ���
				char[] ch = str.toCharArray(); // ���ַ���ת��Ϊ�ַ�����
				for (int m = 0; m < ch.length; m++) { // �ж��ַ��Ƿ�������
					if (ch[m] < '0' || ch[m] > '9') {
						isContinue = false; // ����ַ��������־Ͳ��ܽ��к���Ĳ�����ѭ��Ҳ�Ͳ����ټ�����
						break; // �˳�����Ƿ������ֵ�ѭ��
					}
				}
				if (!isContinue) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("���벻�Ϸ�!!!");
					alert.showAndWait();
					break; // �˳������ֽ��в�����ѭ��
				}

				num[i] = Integer.parseInt(str);
				for (int j = 0; j < index.length; j++) {
					// ��ΪͼƬ����ķ�ʽ�ǰ��ջ�ɫ˳�����У�13��һѭ�������Ը����±����ж���ֵ
					if ((num[i] % 13) == (index[j] % 13)) {
						isRight = true;
						break; // ֻҪ��һ��������ͬ����˵����ǰ�����ֺϷ�������ѭ�����ж���һ��
					}
				}
				i++;
			}

			// �û���������ֲ����˿����ϵģ��͵�����
			if (!isRight && isContinue) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("The numbers in expression don't match the numbers in the set");
				alert.showAndWait();
			}
			if (isContinue && !tfExpression.getText().isEmpty()) {
				// �ж��û�����ı��ʽ�Ľ���Ƿ���24
				Main s = new Main(); // �����û�������ʽ��ת��Ϊ������ʽ������������
				int result = s.getExpressionValue(expression);
				if (result == 24) { // �����24�͵���Correct
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Correct");
					alert.showAndWait();
				} else { // �������24�͵���Incorrect result
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Incorrect result");
					alert.showAndWait();
				}
			}
		});

		// btFindSolution��ע���¼�
		btFindSolution.setOnAction(event -> {
			int a = (int)(Math.random() * (list.size() - 1));
			tfSolution.setText(list.get(a));
		});

		// btChange��ע���¼�
		btChange.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("�����һ���������ʼ������������ֵ");
			Optional result = alert.showAndWait(); // ��ȡ�û��ǵ����ȷ������ȡ��
			if (result.get() == ButtonType.OK) { // ���û����ȷ��ʱ������תҳ��
				player1.stop(); // �ñ���������ͣ
				borderPane.getChildren().clear(); // ��������Ϳ���������еĽڵ�ɾ��
				HBox newHbTop = new HBox(30); // ������������沿�ֵ����
				HBox newHbCenter = new HBox(10); // ����������в�����壬����ʼ���ڵ���ڵ��ļ�϶Ϊ10
				HBox newHbBottom = new HBox(20); // ���������ײ������,����ʼ���ڵ���ڵ��ļ�϶Ϊ20

				// �µ�����ж�������ʾ����
				Text txPrompting = new Text("Input for numbers between 1 to 13");
				txPrompting.setFill(Color.WHITE);
				txPrompting.setStyle("-fx-font-size:20px;");
				Button btExit = new Button("Exit"); // ���÷�����һ����İ�ť
				btExit.setMinHeight(30); // ΪbtFindSolution��ť�����µĸ߶�Ϊ50
				btExit.setMinWidth(50); // ΪbtFindSolution��ť�����µĿ��Ϊ100
				btExit.setStyle("-fx-font-size:20px;");
				newHbTop.getChildren().addAll(btExit, txPrompting); // ��btExit,txPrompting��ӽ��ϲ�
				newHbTop.setAlignment(Pos.CENTER); // ��������еĽڵ���뷽ʽΪ���ж���
				newHbTop.setPadding(new Insets(10, 10, 0, 10)); // ����newHbTop���ڱ߾�

				// �µ�����е��в��Ľڵ�
				TextField tfValue1 = new TextField(); // ����4����С��Ⱥ͸߶ȶ�Ϊ80�������
				tfValue1.setMinHeight(80);
				tfValue1.setMinWidth(80);
				checkIsNum(tfValue1); // ���������Ƿ�������
				tfValue1.setStyle("-fx-font-size:40px;"); // �������������е������СΪ40px
				TextField tfValue2 = new TextField(); // ����4����С��Ⱥ͸߶ȶ�Ϊ80�������
				tfValue2.setMinHeight(80);
				tfValue2.setMinWidth(80);
				checkIsNum(tfValue2); // ���������Ƿ�������
				tfValue2.setStyle("-fx-font-size:40px;"); // �������������е������СΪ40px
				TextField tfValue3 = new TextField(); // ����4����С��Ⱥ͸߶ȶ�Ϊ80�������
				tfValue3.setMinHeight(80);
				tfValue3.setMinWidth(80);
				checkIsNum(tfValue3); // ���������Ƿ�������
				tfValue3.setStyle("-fx-font-size:40px;"); // �������������е������СΪ40px
				TextField tfValue4 = new TextField(); // ����4����С��Ⱥ͸߶ȶ�Ϊ80�������
				tfValue4.setMinHeight(80);
				tfValue4.setMinWidth(80);
				checkIsNum(tfValue4); // ���������Ƿ�������
				tfValue4.setStyle("-fx-font-size:40px;"); // �������������е������СΪ40px
				// ��4�������������м������
				newHbCenter.getChildren().addAll(tfValue1, tfValue2, tfValue3,
						tfValue4);
				newHbCenter.setPadding(new Insets(0, 10, 0, 10)); // ����newHbCenter���ڱ߾�
				newHbCenter.setAlignment(Pos.CENTER); // ��������еĽڵ���뷽ʽΪ���ж���

				// �µ�����еײ��ĸ��ڵ�
				TextField tfLook = new TextField("�����������ʾ"); // һ����ʾ����������
				tfLook.setDisable(true); // ���ø�����򲻿����룬Ҳ����ֻ������
				tfLook.setMinHeight(50); // ���ø���������С�߶�Ϊ50
				tfLook.setMinWidth(100); // ���ø���������С���Ϊ100
				tfLook.setStyle("-fx-font-size:20px;"); // ����������е������СΪ20px
				Button btSolution = new Button("Find a Solution"); // ����һ����ѯsolution�İ�ť
				btSolution.setMinHeight(50); // ΪbtFindSolution��ť�����µĸ߶�Ϊ50
				btSolution.setMinWidth(100); // ΪbtFindSolution��ť�����µĿ��Ϊ100
				btSolution.setStyle("-fx-font-size:20px;");
				newHbBottom.getChildren().addAll(tfLook, btSolution); // ���µĵײ��ڵ���ӽ��ײ��������
				newHbBottom.setAlignment(Pos.CENTER); // ��������еĽڵ���뷽ʽΪ���ж���
				newHbBottom.setPadding(new Insets(0, 10, 10, 10)); // ����newHbBottom���ڱ߾�

				// ��btExit��ť����ע���¼�
				btExit.setOnAction(e -> {
					Alert alert1 = new Alert(AlertType.CONFIRMATION);
					alert1.setHeaderText("ȷ���˳�?");
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

				// btSolution��ť��ע���¼�
				btSolution.setOnAction(e -> {
					if (!isUnderThirteen(tfValue1, tfValue2, tfValue3, tfValue4)) { // ��������ֵ�Ƿ񳬳�13
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setHeaderText("�����Ϊ�գ����������������13");
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

				// tfValue1��ע���¼�������һ����������ͻ��ʼ��tfLook��,���ҳ�ʼ�������
				// ����tfValue1�ļ����������������������ֵ��ʱ��ͳ�ʼ��tfLook��
				tfValue1.setOnMouseClicked(e -> {
					tfValue1.setText(""); // ���ĸ�������ֵ��Ϊ��
					tfValue2.setText("");
					tfValue3.setText("");
					tfValue4.setText("");
					tfLook.setText("�����������ʾ");
				});

				// �������ı���ͼƬ
				borderPane.setStyle("-fx-background-image:url('/exam2/version2/background2.jpg');-fx-background-size:500px;");
				// ��newHbBottom�� newHbCenter��newHbTop��ӽ������
				borderPane.setTop(newHbTop);
				borderPane.setCenter(newHbCenter);
				borderPane.setBottom(newHbBottom);
				player2.setAutoPlay(true);
			}
		});

		// ���ó�������̨
		primaryStage.setScene(new Scene(borderPane, 500, 300));
		primaryStage.setTitle("24-Point-Card-Game");
		primaryStage.show();
	}

	/**
	 * ͼƬ��Ŵ�
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
	 * �������ڰ�ť��ʱ����ť������Ӱ
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
	 * ���ĸ�����򴫽������Ƿ�Ϊ�գ��Լ��ж��������ֵ�Ƿ���ȷ
	 * 
	 * @param tf1
	 * @param tf2
	 * @param tf3
	 * @param tf4
	 */
	public boolean isUnderThirteen(TextField tf1, TextField tf2, TextField tf3,	TextField tf4) {
		 // �ж����ĸ�������Ƿ�Ϊ��
		if (!tf1.getText().isEmpty() && !tf2.getText().isEmpty() && !tf3.getText().isEmpty() && !tf4.getText().isEmpty()) {
			if (Integer.parseInt(tf1.getText()) > 13
					|| Integer.parseInt(tf2.getText()) > 13
					|| Integer.parseInt(tf3.getText()) > 13
					|| Integer.parseInt(tf4.getText()) > 13) { // �ж����ĸ����е�ֵ�Ƿ����13
				
				return false;
			}
			else	// ��������С��13��
				return true;
		} else	// ����������ǿյ�
			return false;
	}

	/**
	 * �ж��û�������Ƿ������֣�����������־͵�����ʾ
	 * 
	 * @param tfValue
	 */
	public void checkIsNum(TextField tfValue) {

		// ���������¼��������û�ֻ���������֣�����������������
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
				alert1.setHeaderText("����������!!");
				alert1.showAndWait();
			}
		});
	}

	/**
	 * �����54�����г�ȡ���ų���������һ��������
	 * 
	 * @param images
	 * @return
	 */
	public Iterator<Integer> getFourCard(ArrayList<ImageView> images) {
		Set<Integer> setIndex = new HashSet<Integer>(); // ��ű�������˿��Ƶ��±�
		for (int i = 0; setIndex.size() < 4; i++) {
			setIndex.add((int) (1 + Math.random() * 51)); // ��ȡ������±�
		}
		return setIndex.iterator();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
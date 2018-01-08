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
		BorderPane borderPane = new BorderPane();	// �ܵ��������
		VBox vBox = new VBox(0);	// �м���ӽ�������
		HBox hBox = new HBox(10);	// ������������ײ����������
		HBox hBoxArrow = new HBox(0);	// ����������ľ��εļ�ͷ�;���
		vBox.setAlignment(Pos.BOTTOM_CENTER);	// ����vbox�еĽ����뷽ʽΪ�ײ�����
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox);

		hBox.setAlignment(Pos.CENTER);	// ����н����뷽ʽΪ���ж���
		Text text = new Text("Enter a value:");
		TextField tfValue = new TextField();
		NewButton btPush = new NewButton("Push");
		NewButton btPop = new NewButton("Pop");
		hBox.getChildren().addAll(text, tfValue, btPush, btPop);
		hBox.setPadding(new Insets(0, 10, 10, 10));	// �����ڱ߾�

		Pane paneArrow = new Pane();	// �����ͷ���������������
		paneArrow.getChildren().addAll(new Line(110, 0, 172, 0),
				new Line(172,0, 146,-10), new Line(172,0, 146,10));

		// ������ť��ע���¼�
		btPush.setOnAction(event -> {
			if (isSafety(tfValue)) {
				vBox.getChildren().clear();	// ���vBox��������еĽ��
				hBoxArrow.getChildren().clear();
				int value = Integer.parseInt(tfValue.getText());
				NewRectangle rectangle = new NewRectangle(value + "");
				hBoxArrow.getChildren().addAll(paneArrow, rectangle);
				nodes.add(rectangle);	// �������������Ԫ��
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
				nodes.removeLast();	// ջ�ĺ���ȳ�

				for (int i = nodes.size() - 1; i >= 0; i--) {
					if (i == nodes.size() - 1) {
						hBoxArrow.getChildren().addAll(paneArrow, nodes.get(i));
						vBox.getChildren().add(hBoxArrow);
						continue;
					}
					vBox.getChildren().add(nodes.get(i));
				}
			} else
				alert(Alert.AlertType.WARNING, "���棺ջΪ�գ�");
		});

		primaryStage.setTitle("ջ");
		primaryStage.setScene(new Scene(borderPane, 400, 500));
		primaryStage.show();
	}

	/**
	 * �ж�һ��������Ƿ�Ϸ�,�Ƿ�ȫ�����֣��Ƿ�Ϊ��
	 * @param tf
	 * @return
	 */
	public static boolean isSafety(TextField tf) {
		if (tf.getText().isEmpty()) {
			alert(Alert.AlertType.WARNING, "���棺�����Ϊ��!");
			return false;
		} else {
			char[] ch = tf.getText().toCharArray();
			for (int i = 0; i < ch.length; i++) {
				if (ch[i] < '0' && ch[i] > '9') {
					alert(Alert.AlertType.WARNING, "���棺������в�������!");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * һ��������ķ���
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
	 * ÿһ�����ľ�����
	 */
	class NewRectangle extends StackPane{
		private Rectangle rectangle;
		private Label label;

		public NewRectangle(String value) {
			rectangle = new Rectangle(50,25);
			rectangle.setFill(Color.WHITE);
			setMaxWidth(50);
			setMaxHeight(25);	// ����newRectangle������Ⱥ͸߶�
			setStyle("-fx-border-width: 1px;-fx-border-color: black;");
			label = new Label(value);
			getChildren().addAll(rectangle, label);
		}
	}

	/**
	 * ����һ��button��
	 */
	class NewButton extends Button {
		public NewButton(String text) {
			super(text);
			setMinWidth(50);
			setMinHeight(30);
		}
	}
}
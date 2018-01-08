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
		// ����һ��ArrayList�б�
		MyArrayList<Integer> list = new MyArrayList<Integer>();
		// ����һ��BorderPane�����
		BorderPane borPane = new BorderPane();
		
		// ��ͷ��һ�仰
		Text txTop = new Text("array is empty, size = " + list.size + " and capacity is " + list.DEFAULT_LENGTH);
		txTop.setStyle("-fx-font-size:20px;");
		
		// ����һ�仰
		Text txValue = new Text("Enter a value: ");	// ����ֵ����ʾ��
		txValue.setStyle("-fx-font-size:20px;");	// ���������СΪ20px
		TextField tfValue = new TextField();		// ����ֵ�������
		Text txIndex = new Text(" Enter a index: ");	// �±��������ʾ��
		txIndex.setStyle("-fx-font-size:20px;");	// ���������СΪ20px
		TextField tfIndex = new TextField();		//�����±�������
		Button btSearch = new Button("Search");		// ��ѯ��ť Search
		Button btInsert = new Button("Insert");		// ���밴ť Insert
		Button btDelete = new Button("Delete");		// ɾ����ť Delete
		Button btTrimToSize = new Button("TrimToSize");	// ����ո�İ�ť TrimToSize
		HBox hboxBottom = new HBox(8);		// ����һ��HBox��壬��ʼ���ڵ���ڵ�֮��ľ���Ϊ8
		// �����еĽڵ���ӽ�hboxBottom��
		hboxBottom.getChildren().addAll(txValue, tfValue, txIndex, tfIndex, btSearch, btInsert, btDelete, btTrimToSize);
		hboxBottom.setAlignment(Pos.BOTTOM_RIGHT);	// ��������нڵ�Ķ��뷽ʽΪ���Ҷ���
		hboxBottom.setPadding(new Insets(0, 20, 20, 0));
		
		// ����һ��FlowPane ����ʼ�����Ϊˮƽ���ã���ֱ���Ϊ20
		FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 0, 20);
		flowPane.setPadding(new Insets(0, 0, 0, 20));	// ����flowPane�����ľ���Ϊ20
		
		// ��ʼ�������ʼʱ��16��
		ArrayList<NewRectangle> rectangles = new ArrayList<NewRectangle>();
		for (int i = 0; i < MyArrayList.DEFAULT_LENGTH; i++) {
			rectangles.add(new NewRectangle(60, 30));	// �Գ�Ϊ60����ο30�ĳ�ʼֵ����һ��NewRectangle����
			flowPane.getChildren().add(new NewPane(rectangles.get(i), rectangles.get(i).getLabel()));
		}
		flowPane.setAlignment(Pos.CENTER_LEFT);	// flowPane�нڵ�Ķ��뷽ʽΪ�����������
		
		// ��ѯ��ť��ע���¼�
		btSearch.setOnAction(event -> {			
			// �ж��Ƿ�Ϊ��
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			// tfValue��Ϊ�գ�tfIndex��Ϊ��,������ֵ������û�������±�
			if (!tfValue.getText().isEmpty() && tfIndex.getText().isEmpty()) {
				if (list.contain(Integer.parseInt(tfValue.getText())) && 
						rectangles.contains(Integer.parseInt(tfValue.getText()))) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("���ҵ���ӦԪ��");
					alert.setContentText("���Ӧ�±�Ϊ��" + list.indexOf(Integer.parseInt(tfValue.getText())));
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("δ���ҵ���ӦԪ��");
					alert.showAndWait();
				}
			}
			// tfValue��Ϊ�գ�tfIndex��Ϊ��,ֻ�������±꣬����û������ֵ
			else if (tfValue.getText().isEmpty() && !tfIndex.getText().isEmpty()) {
				int index = Integer.parseInt(tfIndex.getText());
				if (index < 0 || index > list.size) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("�±�������������е�Ԫ�صĸ���");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("���ҵ���ӦԪ��");
					alert.setContentText("��" + index + "����Ԫ��Ϊ��" + list.get(index));
					alert.showAndWait();
				}
			}
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			else {
				int index = Integer.parseInt(tfIndex.getText());
				if (index < 0 || index > list.size) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("�±�������������е�Ԫ�صĸ���");
					alert.showAndWait();
				}
				else if (list.get(index).equals(Integer.parseInt(tfValue.getText()))){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("���ҵ���ӦԪ��");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("δ���ҵ���ӦԪ��");
					alert.showAndWait();
				}
			}
		});
		
		// ���밴ť��ע���¼�
		btInsert.setOnAction(event -> {
			// �ж��Ƿ�Ϊ��
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			// ��Ԫ�ظ�������Ĭ�ϵ�16��ʱ���������Ĭ�ϵ����һ�����ε�����
			if (list.size + 1 > rectangles.size()) {
				rectangles.add(new NewRectangle(60, 30));
				flowPane.getChildren().add(new NewPane(rectangles.get(list.size), rectangles.get(list.size).getLabel()));
			}
			
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			if (!tfValue.getText().isEmpty() && tfIndex.getText().isEmpty()) {
				list.add(Integer.parseInt(tfValue.getText())); // �������������һ��Ԫ��
				rectangles.get(list.size - 1).setLabel(tfValue.getText());
				txTop.setText("array is empty, size = " + list.size + " and capacity is " + list.getSize());
			}
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			else if (tfValue.getText().isEmpty() && !tfIndex.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("��ʾ");
				alert.setHeaderText("������Ҫ�����ֵ������ֻ���±�");
				alert.showAndWait();
			}
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			else {
				if (Integer.parseInt(tfIndex.getText()) > rectangles.size() - 1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("�±겻�Ϸ�");
					alert.showAndWait();
					return;
				}
				list.set(Integer.parseInt(tfIndex.getText()), Integer.parseInt(tfValue.getText()));
				rectangles.get(Integer.parseInt(tfIndex.getText())).setLabel(tfValue.getText());
				txTop.setText("array is empty, size = " + list.size + " and capacity is " + list.getSize());
			}
		});
		
		// ɾ����ť��ע���¼�
		btDelete.setOnAction(event -> {
			// �ж��Ƿ�Ϊ��
			if (tfIsEmpty(tfIndex, tfValue))
				return;
			
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			if (!tfValue.getText().isEmpty() && tfIndex.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("������Ҫɾ��Ԫ�ص��±�");
				alert.showAndWait();
			}
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			else if (tfValue.getText().isEmpty() && !tfIndex.getText().isEmpty()) {
				if (list.size <= 0) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setHeaderText("�Ѿ�û��Ԫ�ؿ�ɾ�ˣ���");
					alert1.showAndWait();
					return;
				}
				if (Integer.parseInt(tfIndex.getText()) < 0 || Integer.parseInt(tfIndex.getText()) >= list.size) {
					Alert alert1 = new Alert(AlertType.INFORMATION);
					alert1.setHeaderText("�±겻�Ϸ�");
					alert1.showAndWait();
					return;
				}
				list.remove(Integer.parseInt(tfIndex.getText())); // ����������ɾ��һ��Ԫ��
//				rectangles.get(Integer.parseInt(tfIndex.getText())).setLabel("null");
				rectangles.remove(Integer.parseInt(tfIndex.getText()));
				flowPane.getChildren().remove(Integer.parseInt(tfIndex.getText()));
				txTop.setText("array is empty, size = " + list.size + " and capacity is " + list.getSize());
			}
			// tfValue��Ϊ�գ�tfIndex��Ϊ��
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("������Ҫɾ��Ԫ�ص��±�");
				alert.showAndWait();
			}
		});
		
		// TrimToSize��ע���¼�
		btTrimToSize.setOnAction(event -> {
			// �ж��Ƿ�Ϊ��
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
		
		primaryStage.setTitle("�����б�����ʾ");
		primaryStage.setScene(new Scene(borPane, 1200, 400));
		primaryStage.show();
	}
	
	/**
	 * �ж�����������Ƿ�������ֵ
	 * Ϊtrue���ʾ������ǿյ�
	 * Ϊfalse��ʾ������ǿյ�
	 * @param tfIndex
	 * @param tfValue
	 * @return
	 */
	public boolean tfIsEmpty(TextField tfIndex, TextField tfValue) {
		if (tfIndex.getText().isEmpty() && tfValue.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("��ʾ");
			alert.setHeaderText("���棺����ֵΪ��");
			alert.showAndWait();
			return true;
		}
		
		return false;
	}
	
	// ���Խ����κ�label����һ��
	class NewRectangle extends Rectangle {
		Label label = new Label("null");
		
		public NewRectangle(double width, double height) {
			super(width, height);
			setFill(Color.WHITE);		// �������ɫΪ��ɫ
			setStroke(Color.BLACK);	// ���ñ�����ɫΪ��ɫ
		}
		
		public Label getLabel() {
			return label;
		}
		
		public void setLabel(String s) {
			label.setText(s);
		}
	}
	
	// ��label���ھ�������
	class NewPane extends StackPane {
		public NewPane(NewRectangle re, Label label) {
			getChildren().addAll(re, label);
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

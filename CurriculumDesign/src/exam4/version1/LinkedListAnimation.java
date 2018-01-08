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
		// ����һ��BorderPane�����
		BorderPane borPane = new BorderPane();
		FlowPane flowPaneCenter = new FlowPane(0,20);	// ����д�Ž������
		flowPaneCenter.setAlignment(Pos.CENTER_LEFT);	// ����flow Pane�Ľ��Ķ��뷽ʽ
		flowPaneCenter.setPadding(new Insets(0, 10, 0, 10));	// �����ڱ߾�

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
		HBox hboxBottom = new HBox(8);		// ����һ��HBox��壬��ʼ���ڵ���ڵ�֮��ľ���Ϊ8
		// �����еĽڵ���ӽ�hboxBottom��
		hboxBottom.getChildren().addAll(txValue, tfValue, txIndex, tfIndex, btSearch, btInsert, btDelete);
		hboxBottom.setAlignment(Pos.BOTTOM_RIGHT);	// ��������нڵ�Ķ��뷽ʽΪ���Ҷ���
		hboxBottom.setPadding(new Insets(0, 20, 20, 0));
		borPane.setBottom(hboxBottom);

		// ������ť��ע���¼�
		btSearch.setOnAction(event -> {
			if (!tfIsEmpty(tfIndex) || !tfIsEmpty(tfValue)) {	// ���������������һ����Ϊ�գ��Ϳ�һ��ѯ
				if (!tfIsEmpty(tfIndex) && !tfIsEmpty(tfValue)) {	// ��������ն���Ϊ��
					if (!isNumOfTf(tfIndex) && !isNumOfTf(tfValue)) {	// ����������Ƿ�������
						int value = Integer.parseInt(tfValue.getText());
						int index = Integer.parseInt(tfIndex.getText());
						if (list.get(index) == value)
							alert(AlertType.INFORMATION, "��Ԫ��Ϊ�����еĵ�" + index + "����");
						else
							alert(AlertType.WARNING, "���棺������û�����Ԫ��!");
					} else
						alert(AlertType.WARNING, "���棺����ֵ����������");
				} else if (!tfIsEmpty(tfValue)){	// ֻ������ֵ��Ϊ��
					if (!isNumOfTf(tfValue)) {	// tfValue�������ֻ��������
						int value = Integer.parseInt(tfValue.getText());
						if (list.contains(value))
							alert(AlertType.INFORMATION, "��Ԫ��Ϊ�����еĵ�" + list.indexOf(value) + "����");
						else
							alert(AlertType.WARNING, "���棺������û�����Ԫ��!");
					} else
						alert(AlertType.WARNING, "���棺����ֵ����������");
				} else if (!tfIsEmpty(tfIndex)) {
					if (!isNumOfTf(tfIndex)) {    // tfIndex�������ֻ��������
						int index = Integer.parseInt(tfIndex.getText());
						if (index >= 0 && index < list.size())
							alert(AlertType.INFORMATION, "��Ԫ��Ϊ" + list.get(index));
						else
							alert(AlertType.WARNING, "���棺������û�����Ԫ��!");
					} else
						alert(AlertType.WARNING, "���棺����ֵ����������");
				}
			} else {	// ����͵��������
				alert(AlertType.WARNING, "���棺����ֵΪ��");
			}
		});

		btInsert.setOnAction(event -> {
			if ((!tfIsEmpty(tfIndex) && !tfIsEmpty(tfValue))) {	// ������򶼲�Ϊ��ʱ��������������򶼲�Ϊ��
				if (isNumOfTf(tfValue) && isNumOfTf(tfIndex)) {    // �ж�����������ֵ�Ƿ�������
					int value = Integer.parseInt(tfValue.getText());
					int index = Integer.parseInt(tfIndex.getText());
					EachNode eachNode = new EachNode(value + "");

					if (index <= list.size()) {	// �ж��±��Ƿ�������Ա�Ĵ�С
						if (index == list.size() && list.size() != 0) {		// ����±�������Ա�Ĵ�С,�������Ա�Ĵ�С��Ϊ0,��׷�������
							list.addLast(value);		// �Ѹ�value�������Ա���
							nodes.get(nodes.size() - 1).setLabelTail("");	// �ƶ�tail
							eachNode.setLabelHead("");	// ���head��labelֵ
							nodes.add(eachNode);	// ��each Node����nodes������
							Pane pane = new Pane();
							pane.getChildren().addAll(new Line(0, 60, 30, 60),
									new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// ��ͷ
							flowPaneCenter.getChildren().addAll(pane, eachNode);
						} else if (index == list.size() && list.size() == 0) {	// �±�������Ա��ȣ��������Ա���û��һ��Ԫ��
							nodes.add(eachNode);
							list.add(value);
							flowPaneCenter.getChildren().add(eachNode);
						} else if(index == 0 && list.size() != 0) {	// ���Ա�Ϊ�գ����±�ʼ�յ���0
							flowPaneCenter.getChildren().clear();	// �������
							nodes.add(0, eachNode);
							list.add(0, value);
							for (int i = 0; i < nodes.size(); i++) {	// ������е�head��tail
								nodes.get(i).setLabelTail("");
								nodes.get(i).setLabelHead("");
								flowPaneCenter.getChildren().add(nodes.get(i));
							}
							nodes.get(0).setLabelTail("head");
							nodes.get(nodes.size() - 1).setLabelTail("tail");
						} else {		//	�����Ա������ڵ������±�
							eachNode.setLabelHead("");	// ���head��ǩ
							eachNode.setLabelTail("");	// ���tail��ǩ
							nodes.add(index, eachNode);
							list.add(index, value);
							flowPaneCenter.getChildren().clear();	// ���flow Pane�������
							flowPaneCenter.getChildren().add(nodes.get(0));
							for (int i = 1; i < nodes.size(); i++) {
								Pane pane = new Pane();
								pane.getChildren().addAll(new Line(0, 60, 30, 60),
										new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));	// ��ͷ
								flowPaneCenter.getChildren().addAll(pane, nodes.get(i));
							}
						}
					} else
						alert(AlertType.WARNING, "���棺�±�ֵ�������Ա���");
				} else
					alert(AlertType.WARNING, "���棺����ֵ����������");
			}else if ((!tfIsEmpty(tfValue) && tfIsEmpty(tfIndex))) {	// ��������±�������Ϊ�գ���������ֵ�������Ϊ��
				if (isNumOfTf(tfValue)) {	// �ж�����������ֵ�Ƿ�������
					int value = Integer.parseInt(tfValue.getText());	// ��ȡtfValue������е�ֵ
					list.add(value);
					EachNode eachNode = new EachNode(value + "");

					if (list.size() - 1 == 0) {    // �������������еĵ�һ��Ԫ��
						nodes.add(eachNode);
						flowPaneCenter.getChildren().add(eachNode);    // ���ʱ��ͷ��β��㶼�ڵ�һ��
					}
					else {
						nodes.get(nodes.size() - 1).setLabelTail("");	// �ƶ�tail
						eachNode.setLabelHead("");
						nodes.add(eachNode);
						Pane pane = new Pane();
						pane.getChildren().addAll(new Line(0, 60, 30, 60),
								new Line(30, 60, 20, 50), new Line(30, 60, 20, 70));
						flowPaneCenter.getChildren().addAll(pane, eachNode);
					}
				}else
					alert(AlertType.WARNING, "���棺����ֵ����������");
			}else {	// ����͵��������
				alert(AlertType.WARNING, "���棺����ֵΪ��");
			}
		});

		btDelete.setOnAction(event -> {
			if (tfIsEmpty(tfValue) && !tfIsEmpty(tfIndex)) {	// ��index�±��Ϊ��ʱ
				if (isNumOfTf(tfIndex)) {	// �ж�������Ƿ�ʱ����
					int index = Integer.parseInt(tfIndex.getText());
					if (index >= 0 && index < list.size()) {	// ���index��list�Ĵ�С��Χ
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
						alert(AlertType.WARNING, "���棺�±겻�Ϸ�!");
				} else
					alert(AlertType.WARNING, "���棺����ֵֻ��������!");
			} else if (tfIsEmpty(tfValue) && tfIsEmpty(tfIndex)) {	// �����±��Ϊ�յ�ʱ��
				if (list.size() > 0) { 	// ����Ϊ��
					flowPaneCenter.getChildren().remove(nodes.get(0));
					nodes.remove(0);
					nodes.get(0).setLabelTail("head");
					list.remove();
				}
				else
					alert(AlertType.WARNING, "���棺����Ϊ��");
			}
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

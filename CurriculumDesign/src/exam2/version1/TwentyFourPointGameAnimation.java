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
		// ����嶥��
		HBox hbTop = new HBox(20);	// ����top���������.�ڵ�ͽڵ�֮��ļ�϶Ϊ20
		Button btRefresh = new Button("Refresh");	// ����ˢ������в��˿��Ƶİ�ťrefresh
		btRefresh.setMinHeight(30);		// ����btRefresh����С�߶�Ϊ30
		btRefresh.setMinWidth(100); 	// ����btRefresh�����¿��Ϊ100
		btRefresh.setStyle("-fx-font-size:15px;");	// ����btRefresh�е������СΪ15px
		Text txTop = new Text("J��Q��K��ֵ�ֱ�Ϊ11,12,13��A��ֵΪ1");	// ���ù�����ʾ��
		txTop.setStyle("-fx-font-size:13px;");		// ����txTop�������СΪ11px
		hbTop.getChildren().addAll(txTop, btRefresh);	// ���������ڵ����hbTop������
		hbTop.setPadding(new Insets(10, 30, 0, 0));		// ����hbTop�������ڱ߾�
		hbTop.setAlignment(Pos.CENTER_RIGHT);		// ����hbTop�нڵ�Ķ��뷽ʽ
		
		// ����в���ͼƬ
		HBox hbCenter = new HBox(30);	// ����center���������.�ڵ�ͽڵ�֮��ļ�϶Ϊ30
		ArrayList<ImageView> images = new ArrayList<ImageView>();// ����54���˿���
		for (int i = 1; i <= 52; i++) {
			String s = "card/" + i + ".png";	// ƴ��ͼƬ���ַ���
			images.add(new ImageView(new Image(s)));	// ����ͼƬ�Ķ��󣬲�����images��
		}
		
		// �õ������ȡ�˿��Ƶ��±�
		Iterator<Integer> iterator = getFourCard(images);
		int[] index = new int[4];	// �����˿��Ƶ��±�
		int k = 0;
		while (iterator.hasNext()) {	// ���˿��Ʒ������hbCenter��
			int temp = iterator.next();		//�����±�ֵ
			hbCenter.getChildren().add(images.get(temp));	// ��ͼƬ�ڵ����hbCenter���������
			index[k++] = temp + 1;	// �±걣����index��,��һ��Ϊ�˱�֤�±�ֵ��ͼƬ��������ֵһ��
		}
		hbCenter.setAlignment(Pos.CENTER);	// ����hbCenter�����м�ڵ�Ķ��뷽ʽΪ���ж���
		
		// ����ϵĵײ�����
		Text tx = new Text("Enter an expression: ");	// ���õײ�����ʾ��
		tx.setStyle("-fx-font-size:18px;");		// ������ʾ��������СΪ18px
		TextField tfExpression = new TextField();	// ����һ�������
		Button btVerity = new Button("Verity");		// ����һ����֤�İ�ťbtVerity
		btVerity.setMinHeight(20);		// ����btVerity����С�߶�Ϊ20
		btVerity.setMinWidth(40);		// ����btVerity����С���Ϊ40
		btVerity.setStyle("-fx-font-size:15px;");	// ����btVerity�е������СΪ15px
		HBox hbBottom = new HBox(5);		// �����ײ����������ڵ���ڵ��ľ���Ϊ5
		hbBottom.getChildren().addAll(tx, tfExpression, btVerity);	// ���ײ����ڵ����ײ�������
		hbBottom.setPadding(new Insets(0, 0, 10, 0));	// �����ڱ߾�
		
		// btRefresh��ע���¼�,��������ť��ˢ���м��ͼƬ
		btRefresh.setOnAction(event -> {
			hbCenter.getChildren().clear();	// ���ԭ������еĽڵ�
			tfExpression.setText("");
			// �õ������ȡ�˿��Ƶ��±�
			Iterator<Integer> iterator1 = getFourCard(images);
			int i = 0;
			while (iterator1.hasNext()) {	// ���˿��Ʒ������hbCenter��
				int temp = iterator1.next();		//�����±�ֵ
				hbCenter.getChildren().add(images.get(temp));	// ��ͼƬ�ڵ����hbCenter���������
				index[i++] = temp + 1;	// �±걣����index��
			}
			hbCenter.setAlignment(Pos.CENTER);	// ����hbCenter�����м�ڵ�Ķ��뷽ʽΪ���ж���
		});
		
		// btVerity��ע���¼�������ð�ť������֤����ı��ʽ�Ƿ����24
		btVerity.setOnAction(event -> {
			String expression = tfExpression.getText();	// ��ȡ�������������ַ���
			
			int[] num = new int[4];	// ���������ַ����е��ַ�
			// �ֽ��ַ���
			StringTokenizer st = new StringTokenizer(expression, " ()����+-*/");
			boolean isRight = false;
			int i = 0;
			// ������������֤��������������ϵ����Ƿ����
			while (st.hasMoreElements()) {
				num[i] = Integer.parseInt((String)(st.nextElement()));
				for (int j = 0; j < index.length; j++) {
					// ��ΪͼƬ����ķ�ʽ�ǰ��ջ�ɫ˳�����У�13��һѭ�������Ը����±����ж���ֵ
					if ((num[i] % 13) == (index[j] % 13)) {
						isRight = true;
						break;	// ֻҪ��һ��������ͬ����˵����ǰ�����ֺϷ�������ѭ�����ж���һ��
					}
				}
				i++;
			}
			
			// �û���������ֲ����˿����ϵģ��͵�����
			if (!isRight) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("The numbers in expression don't match the numbers in the set");
				alert.showAndWait();
			}
			else {		// �ж��û�����ı��ʽ�Ľ���Ƿ���24
				Main s = new Main();	// �����û�������ʽ��ת��Ϊ������ʽ������������		
				int result = s.getExpressionValue(expression);
				if (result == 24) {		// �����24�͵���Correct
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Correct");
					alert.showAndWait();
				}
				else {		// �������24�͵���Incorrect result
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Incorrect result");
					alert.showAndWait();
				}
			}
		});
		
		// �����
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(hbTop);
		borderPane.setCenter(hbCenter);
		borderPane.setBottom(hbBottom);
		
		// ���ó�������̨
		primaryStage.setScene(new Scene(borderPane, 420, 300));
		primaryStage.setTitle("24-Point-Card-Game");
		primaryStage.show();
	}
	
	/**
	 * �����54�����г�ȡ���ų���������һ��������
	 * @param images
	 * @return
	 */
	public Iterator<Integer> getFourCard(ArrayList<ImageView> images) {
		Set<Integer> setIndex = new HashSet<Integer>();	// ��ű�������˿��Ƶ��±�
		for (int i = 0; setIndex.size() < 4; i++) {
			setIndex.add((int)(1 + Math.random() * 51));	// ��ȡ������±�
		}
		return setIndex.iterator();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

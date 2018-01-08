package version2;



import javafx.scene.paint.Color;

import java.util.Scanner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Histogram extends Application {
	int[] num = new int[26];
	Scanner input = new Scanner(System.in);

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.print("请输入一个字符串：");
		String str1 = input.next();
		String str = str1.toUpperCase();

		for (int i = 0; i < str.length(); i++){
			char ch = str.charAt(i);
			if (ch >= 'A' && ch <= 'Z')
				num[ch - 'A']++;
			else
				continue;
		}

		// TODO Auto-generated method stub
		BorderPane pane = new BorderPane();

		double height = 500;

		Rectangle rectangle1 = new Rectangle(20,height - num[0] * 10 - 50,10,num[0] * 10);
		Line line1 = new Line(10,450,30,450);
		line1.setStrokeWidth(3);
		line1.setStroke(Color.BLACK);
		rectangle1.setFill(Color.BLACK);
		Text text1 = new Text(20,463,"A");
		pane.getChildren().addAll(rectangle1,line1,text1);

		Rectangle rectangle2 = new Rectangle(40,height - num[1] * 10 - 50,10,num[1] * 10);
		Line line2 = new Line(30,450,50,450);
		line2.setStrokeWidth(3);
		line2.setStroke(Color.BLACK);
		rectangle2.setFill(Color.BLACK);
		Text text2 = new Text(40,463,"B");
		pane.getChildren().addAll(rectangle2,line2,text2);

		Rectangle rectangle3 = new Rectangle(60,height - num[1] * 10 - 50,10,num[2] * 10);
		Line line3 = new Line(50,450,70,450);
		line3.setStrokeWidth(3);
		line3.setStroke(Color.BLACK);
		rectangle3.setFill(Color.BLACK);
		Text text3 = new Text(60,463,"C");
		pane.getChildren().addAll(rectangle3,line3,text3);

		Rectangle rectangle4 = new Rectangle(80,height - num[3] * 10 - 50,10,num[3] * 10);
		Line line4 = new Line(70,450,90,450);
		line4.setStrokeWidth(3);
		line4.setStroke(Color.BLACK);
		rectangle4.setFill(Color.BLACK);
		Text text4 = new Text(80,463,"D");
		pane.getChildren().addAll(rectangle4,line4,text4);

		Rectangle rectangle5 = new Rectangle(100,height - num[4] * 10 - 50,10,num[4] * 10);
		Line line5 = new Line(90,450,110,450);
		line5.setStrokeWidth(3);
		line5.setStroke(Color.BLACK);
		rectangle5.setFill(Color.BLACK);
		Text text5 = new Text(100,463,"E");
		pane.getChildren().addAll(rectangle5,line5,text5);

		Rectangle rectangle6 = new Rectangle(120,height - num[5] * 10 - 50,10,num[5] * 10);
		Line line6 = new Line(110,450,130,450);
		line6.setStrokeWidth(3);
		line6.setStroke(Color.BLACK);
		rectangle6.setFill(Color.BLACK);
		Text text6 = new Text(120,463,"F");
		pane.getChildren().addAll(rectangle6,line6,text6);

		Rectangle rectangle7 = new Rectangle(140,height - num[6] * 10 - 50,10,num[6] * 10);
		Line line7 = new Line(130,450,150,450);
		line7.setStrokeWidth(3);
		line7.setStroke(Color.BLACK);
		rectangle7.setFill(Color.BLACK);
		Text text7 = new Text(140,463,"G");
		pane.getChildren().addAll(rectangle7,line7,text7);

		Rectangle rectangle8 = new Rectangle(160,height - num[7] * 10 - 50,10,num[7] * 10);
		Line line8 = new Line(150,450,170,450);
		line8.setStrokeWidth(3);
		line8.setStroke(Color.BLACK);
		rectangle8.setFill(Color.BLACK);
		Text text8 = new Text(160,463,"H");
		pane.getChildren().addAll(rectangle8,line8,text8);

		Rectangle rectangle9 = new Rectangle(180,height - num[8] * 10 - 50,10,num[8] * 10);
		Line line9 = new Line(170,450,190,450);
		line9.setStrokeWidth(3);
		line9.setStroke(Color.BLACK);
		rectangle9.setFill(Color.BLACK);
		Text text9 = new Text(180,463,"I");
		pane.getChildren().addAll(rectangle9,line9,text9);

		Rectangle rectangle10 = new Rectangle(200,height - num[9] * 10 - 50,10,num[9] * 10);
		Line line10 = new Line(190,450,210,450);
		line10.setStrokeWidth(3);
		line10.setStroke(Color.BLACK);
		rectangle10.setFill(Color.BLACK);
		Text text10 = new Text(200,463,"J");
		pane.getChildren().addAll(rectangle10,line10,text10);

		Rectangle rectangle11 = new Rectangle(220,height - num[10] * 10 - 50,10,num[10] * 10);
		Line line11 = new Line(210,450,230,450);
		line11.setStrokeWidth(3);
		line11.setStroke(Color.BLACK);
		rectangle11.setFill(Color.BLACK);
		Text text11 = new Text(220,463,"K");
		pane.getChildren().addAll(rectangle11,line11,text11);

		Rectangle rectangle12 = new Rectangle(240,height - num[11] * 10 - 50,10,num[11] * 10);
		Line line12 = new Line(230,450,250,450);
		line12.setStrokeWidth(3);
		line12.setStroke(Color.BLACK);
		rectangle12.setFill(Color.BLACK);
		Text text12 = new Text(240,463,"L");
		pane.getChildren().addAll(rectangle12,line12,text12);

		Rectangle rectangle13 = new Rectangle(260,height - num[12] * 10 - 50,10,num[12] * 10);
		Line line13 = new Line(250,450,270,450);
		line13.setStrokeWidth(3);
		line13.setStroke(Color.BLACK);
		rectangle13.setFill(Color.BLACK);
		Text text13 = new Text(260,463,"M");
		pane.getChildren().addAll(rectangle13,line13,text13);

		Rectangle rectangle14 = new Rectangle(280,height - num[13] * 10 - 50,10,num[13] * 10);
		Line line14 = new Line(270,450,290,450);
		line14.setStrokeWidth(3);
		line14.setStroke(Color.BLACK);
		rectangle14.setFill(Color.BLACK);
		Text text14= new Text(280,463,"N");
		pane.getChildren().addAll(rectangle14,line14,text14);

		Rectangle rectangle15 = new Rectangle(300,height - num[14] * 10 - 50,10,num[14] * 10);
		Line line15 = new Line(290,450,310,450);
		line15.setStrokeWidth(3);
		line15.setStroke(Color.BLACK);
		rectangle15.setFill(Color.BLACK);
		Text text15 = new Text(300,463,"O");
		pane.getChildren().addAll(rectangle15,line15,text15);

		Rectangle rectangle16 = new Rectangle(320,height - num[15] * 10 - 50,10,num[15] * 10);
		Line line16 = new Line(310,450,330,450);
		line16.setStrokeWidth(3);
		line16.setStroke(Color.BLACK);
		rectangle16.setFill(Color.BLACK);
		Text text16 = new Text(320,463,"P");
		pane.getChildren().addAll(rectangle16,line16,text16);

		Rectangle rectangle17 = new Rectangle(340,height - num[16] * 10 - 50,10,num[16] * 10);
		Line line17 = new Line(330,450,350,450);
		line17.setStrokeWidth(3);
		line17.setStroke(Color.BLACK);
		rectangle17.setFill(Color.BLACK);
		Text text17 = new Text(340,463,"Q");
		pane.getChildren().addAll(rectangle17,line17,text17);

		Rectangle rectangle18 = new Rectangle(360,height - num[17] * 10 - 50,10,num[17] * 10);
		Line line18 = new Line(350,450,370,450);
		line18.setStrokeWidth(3);
		line18.setStroke(Color.BLACK);
		rectangle18.setFill(Color.BLACK);
		Text text18 = new Text(360,463,"R");
		pane.getChildren().addAll(rectangle18,line18,text18);

		Rectangle rectangle19 = new Rectangle(380,height - num[18] * 10 - 50,10,num[18] * 10);
		Line line19 = new Line(370,450,390,450);
		line19.setStrokeWidth(3);
		line19.setStroke(Color.BLACK);
		rectangle19.setFill(Color.BLACK);
		Text text19 = new Text(380,463,"S");
		pane.getChildren().addAll(rectangle19,line19,text19);

		Rectangle rectangle20 = new Rectangle(400,height - num[19] * 10 - 50,10,num[19] * 10);
		Line line20 = new Line(390,450,410,450);
		line20.setStrokeWidth(3);
		line20.setStroke(Color.BLACK);
		rectangle20.setFill(Color.BLACK);
		Text text20 = new Text(400,463,"T");
		pane.getChildren().addAll(rectangle20,line20,text20);

		Rectangle rectangle21 = new Rectangle(420,height - num[20] * 10 - 50,10,num[20] * 10);
		Line line21 = new Line(410,450,430,450);
		line21.setStrokeWidth(3);
		line21.setStroke(Color.BLACK);
		rectangle21.setFill(Color.BLACK);
		Text text21 = new Text(420,463,"U");
		pane.getChildren().addAll(rectangle21,line21,text21);

		Rectangle rectangle22 = new Rectangle(440,height - num[21] * 10 - 50,10,num[21] * 10);
		Line line22 = new Line(430,450,450,450);
		line22.setStrokeWidth(3);
		line22.setStroke(Color.BLACK);
		rectangle22.setFill(Color.BLACK);
		Text text22 = new Text(440,463,"V");
		pane.getChildren().addAll(rectangle22,line22,text22);

		Rectangle rectangle23 = new Rectangle(460,height - num[22] * 10 - 50,10,num[22] * 10);
		Line line23 = new Line(450,450,470,450);
		line23.setStrokeWidth(3);
		line23.setStroke(Color.BLACK);
		rectangle23.setFill(Color.BLACK);
		Text text23 = new Text(460,463,"W");
		pane.getChildren().addAll(rectangle23,line23,text23);

		Rectangle rectangle24 = new Rectangle(480,height - num[23] * 10 - 50,10,num[23] * 10);
		Line line24 = new Line(470,450,490,450);
		line24.setStrokeWidth(3);
		line24.setStroke(Color.BLACK);
		rectangle24.setFill(Color.BLACK);
		Text text24 = new Text(480,463,"X");
		pane.getChildren().addAll(rectangle24,line24,text24);

		Rectangle rectangle25 = new Rectangle(500,height - num[24] * 10 - 50,10,num[23] * 10);
		Line line25 = new Line(490,450,510,450);
		line25.setStrokeWidth(3);
		line25.setStroke(Color.BLACK);
		rectangle25.setFill(Color.BLACK);
		Text text25 = new Text(500,463,"Y");
		pane.getChildren().addAll(rectangle25,line25,text25);

		Rectangle rectangle26 = new Rectangle(520,height - num[25] * 10 - 50,10,num[23] * 10);
		Line line26 = new Line(510,450,530,450);
		line26.setStrokeWidth(3);
		line26.setStroke(Color.BLACK);
		rectangle26.setFill(Color.BLACK);
		Text text26 = new Text(520,463,"Z");
		pane.getChildren().addAll(rectangle26,line26,text26);

		Scene scene = new Scene(pane,550,600);
		primaryStage.setTitle("Histogram");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args){
		Application.launch(args);
	}
}
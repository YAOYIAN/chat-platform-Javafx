package com.ql.view.client;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NoticeView extends Stage {
    public NoticeView(String str) {
        StackPane stackPane = new StackPane();
        Label label = new Label(str);
        label.setFont(new Font("宋体", 18));
        stackPane.setPrefSize(220,128);
        stackPane.getChildren().add(label);
        Scene scene = new Scene(stackPane);
        this.setScene(scene);
        this.setTitle("提示");
        this.show();
    }
}

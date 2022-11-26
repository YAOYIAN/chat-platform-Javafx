package com.ql.view.client;


import com.ql.entity.client.Client;
import com.ql.util.client.Md5Util;
import com.ql.util.client.PropertiesUtil_Client;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;


public class LoginView2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        TextField textField_Name = new TextField();
        PasswordField passwordField = new PasswordField();
        Label label_Name = new Label("用户名:");
        Label label_PassWord = new Label("密码:");
        HBox hBox_picture = new HBox();
        HBox hBox_picture2 = new HBox();
        HBox hBox_Name = new HBox();
        HBox hBox_Button = new HBox();
        HBox hBox_Password = new HBox();
        File file = new File("C:/Users/admin/IdeaProjects/Eleven/9.png");
        ImageView start_view = new ImageView(new Image(file.toURL().toString()));
        start_view.setFitHeight(240);
        start_view.setFitWidth(300);
        hBox_picture.getChildren().addAll(start_view);

        hBox_Name.getChildren().addAll(label_Name,textField_Name);
        hBox_Password.getChildren().addAll(label_PassWord,passwordField);
        Button login_button = new Button("登录");
        login_button.setFont(new Font("宋体", 18));
        login_button.setOnAction(e->{
            Client client = new Client(PropertiesUtil_Client.getPropertiesUtil().getValue("ip").trim(),textField_Name.getText(),Md5Util.md5(passwordField.getText()));
            if(client.login_ask_sever(textField_Name.getText(),Md5Util.md5(passwordField.getText()))){
                new FirstView(client);
                new NoticeView("登录成功");
                primaryStage.close();
            }
            else{
                new NoticeView("账号或密码错误！");
            }
        });
        Button register_button = new Button("注册");
        register_button.setFont(new Font("宋体", 18));
        register_button.setOnAction(e->{
                    Client client = new Client(PropertiesUtil_Client.getPropertiesUtil().getValue("ip").trim(),textField_Name.getText(), Md5Util.md5(passwordField.getText()));
                    if(client.register_ask_sever(textField_Name.getText(),Md5Util.md5(passwordField.getText()))){
                        new NoticeView("成功注册！");
                    }
                    else{
                        new NoticeView("不能注册！");
                    }
                }
        );
        Button exit_button = new Button("取消");
        exit_button.setFont(new Font("宋体", 18));
        exit_button.setOnAction(e->
                primaryStage.close()
        );
        hBox_Button.getChildren().addAll(login_button,register_button,exit_button);
        hBox_Button.setSpacing(28);
        vBox.getChildren().addAll(hBox_picture,hBox_picture2,hBox_Name,hBox_Password,hBox_Button);
        vBox.setPrefWidth(245);
        vBox.setPrefHeight(160);
        hBox_Button.setAlignment(Pos.CENTER);
        hBox_picture.setAlignment(Pos.CENTER);
        hBox_Name.setAlignment(Pos.CENTER);
        hBox_Password.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

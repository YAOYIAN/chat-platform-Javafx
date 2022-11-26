package com.ql.view.client;

import com.ql.entity.client.Client;

import com.ql.entity.client.ClientReader;
import com.ql.entity.client.File_third;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Objects;


public class FirstView extends Stage {
    ListView<String> onLineUsers = new ListView<>();
    ListView<String> onLineUsers2 = new ListView<>();
    CheckBox isPrivateBn = new CheckBox("私聊");
    CheckBox isattentionBn = new CheckBox("提醒");
    HBox hBox_p = new HBox();
    TextArea textArea = new TextArea();
    TextArea textArea_write = new TextArea();
    ComboBox<String> comboBox1= new ComboBox<>();
    String filepath = "";
    ObservableList<File_third> cellData = FXCollections.observableArrayList();
    TableView<File_third> tableView = new TableView<>();
    TableColumn<File_third, String> tc1 = new TableColumn<File_third, String>("文件名");
    TableColumn<File_third, String> tc2 = new TableColumn<File_third, String>("文件大小");
    TableColumn<File_third, String> tc3 = new TableColumn<File_third, String>("文件上次修改时间");


    public FirstView(Client client) {
        System.out.println("进入页面了");
        Platform.runLater(()->{
            new ClientReader(this,client.getSocket()).start();
        });
        Label label1 = new Label("你的小组空间：");
        Label label2 = new Label("加入空间：");
        Label label3 = new Label("创建空间：");
        TextField textField_goin = new TextField();
        TextField textField_create = new TextField();
        client.qun_i_have_in();
        Button button_exit = new Button("关闭软件");
        button_exit.setOnAction(e->{
            try {
                client.getSocket().close();
                this.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Button button_comfirm = new Button("进入空间");
        button_comfirm.setOnAction(e->{
            client.i_will_change(comboBox1.getSelectionModel().getSelectedItem());
        });
        Button button_delete= new Button("退出空间");
        button_delete.setOnAction(e->{
            client.go_out_qun_ask_sever(comboBox1.getSelectionModel().getSelectedItem());
        });
        Button button_goin = new Button("加入空间");
        button_goin.setOnAction(e->{
            client.goin_qun_ask_sever(textField_goin.getText().trim());
        });
        Button button_create = new Button("创建空间");
        button_create.setOnAction(e->{
           client.create_qun_ask_sever(textField_create.getText().trim());
        });
        Button button_choosefile = new Button("选择要上传的文件");
        button_choosefile.setOnAction(e->{
            FileChooser chooser = new FileChooser();
            filepath = chooser.showOpenDialog(null).getAbsolutePath();
            System.out.println(filepath);
        });
        Button button_upload = new Button("上传文件");
        button_upload.setOnAction(e->{
            if(!Objects.equals(filepath, ""))
                client.uploadfile(filepath);
        });
        Button button_download = new Button("下载文件");
        button_download.setOnAction(e->{
            if(tableView.getSelectionModel().getSelectedItem().getFile_name().get()!=null){
                String filename = tableView.getSelectionModel().getSelectedItem().getFile_name().get();
                client.downloadfile(filename);
            }
        });
        Button button_delete_file = new Button("删除文件");
        button_delete_file.setOnAction(e->{
            String filename = tableView.getSelectionModel().getSelectedItem().getFile_name().get();
            client.delete_file(filename);
        });
        Button sendBn = new Button("发送");
        sendBn.setOnAction(e->{
            String msgSend = textArea_write.getText();
            if(!msgSend.trim().equals("")){
                try{
                    Label label0 = new Label("");
                    label0.textProperty().bind(onLineUsers.getSelectionModel().selectedItemProperty());
                    String selectName = label0.getText();
                    int flag = 2;
                    if(selectName != null && isattentionBn.isSelected()){
                        msgSend = ("@" + selectName + "!!:" + msgSend);
                    }
                    if(isPrivateBn.isSelected() && selectName !=null){
                        flag = 3;
                    }
                    DataOutputStream dos = new DataOutputStream(client.getSocket().getOutputStream());
                    dos.writeInt(flag);
                    dos.writeUTF(msgSend);
                    if(flag == 3){
                        dos.writeUTF(selectName.trim());
                    }
                    dos.flush();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
            textArea_write.setText(null);
        });
        HBox hBox_upload_download = new HBox();
        hBox_upload_download.getChildren().addAll(button_choosefile,button_upload,button_download,button_delete_file);
        hBox_upload_download.setSpacing(30);
        hBox_upload_download.setPrefSize(400,80);
        HBox hBox_main = new HBox();
        HBox hBox_choose = new HBox();
        HBox hBox_choose2 = new HBox();
        HBox hBox_goin = new HBox();
        HBox hBox_create = new HBox();
        VBox vBox_low = new VBox();
        VBox vBox_middle = new VBox();
        hBox_choose.getChildren().addAll(label1,comboBox1);
        hBox_choose.setSpacing(30);
        hBox_choose.setPrefSize(400,50);
        hBox_choose2.getChildren().addAll(button_comfirm,button_delete);
        hBox_choose2.setSpacing(30);
        hBox_choose2.setPrefSize(400,80);
        hBox_goin.getChildren().addAll(label2,textField_goin,button_goin);
        hBox_goin.setSpacing(30);
        hBox_goin.setPrefSize(400,50);
        hBox_create.getChildren().addAll(label3,textField_create,button_create);
        hBox_create.setSpacing(30);
        hBox_create.setPrefSize(400,50);
        vBox_low.getChildren().addAll(hBox_choose,hBox_choose2,hBox_goin,hBox_create);
        tableView.setPrefHeight(800);
        tableView.setPrefWidth(800);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(tc1, tc2, tc3);

        ArrayList<File_third>  File_third_list = new ArrayList<File_third>();
        cellData.addAll(File_third_list);
        tableView.setItems(cellData);
        Label label_information = new Label("操作提示:");
        Button button_information = new Button("?");
        button_information.setOnAction(e->{
            File file = new File("C:/Users/admin/IdeaProjects/Eleven/notice.png");
            ImageView imageView = null;
            try {
                imageView = new ImageView(new Image(file.toURL().toString()));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            imageView.setFitWidth(750);
            imageView.setFitHeight(450);
            Pane pane = new Pane();
            pane.getChildren().addAll(imageView);
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });
        HBox hBox_bottom = new HBox();
        hBox_bottom.getChildren().addAll(label_information,button_information);
        hBox_bottom.setSpacing(20);
        textArea.setPrefSize(400,300);
        textArea.setEditable(false);
        Label labelsize = new Label("字体大小:");
        ComboBox<Integer> comboBox_choosesize = new ComboBox<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i=9;i<24;i++)
            arrayList.add(i);
        ObservableList<Integer> observableList=  FXCollections.observableArrayList(arrayList);
        comboBox_choosesize.setItems(observableList);
        comboBox_choosesize.setOnAction(e->{
            textArea.setFont(new Font(comboBox_choosesize.getSelectionModel().getSelectedItem()));
        });
        textArea_write.setPrefSize(400,100);
        hBox_p.getChildren().addAll(sendBn,isattentionBn,isPrivateBn,labelsize,comboBox_choosesize);
        hBox_p.setSpacing(30);
        hBox_p.setPrefSize(400,80);
        vBox_middle.getChildren().addAll(textArea,textArea_write,hBox_p,hBox_upload_download,vBox_low,hBox_bottom);
        onLineUsers.setPrefSize(200,600);
        onLineUsers2.setPrefSize(200,180);
        VBox vBox_right = new VBox();
        HBox hBox_right_top = new HBox();
        Label label_online1 = new Label("团队空间内在线成员:");
        Label label_online2 = new Label("团队空间内所有成员:");
        hBox_right_top.getChildren().addAll(label_online1,button_exit);
        hBox_right_top.setSpacing(50);
        vBox_right.getChildren().addAll(hBox_right_top,onLineUsers,label_online2,onLineUsers2);
        hBox_main.getChildren().addAll(tableView,vBox_middle,vBox_right);
        Scene scene = new Scene(hBox_main);
        this.setScene(scene);
        this.setTitle(client.getName());
        this.show();
    }
    public ListView<String> getOnLineUsers() {
        return onLineUsers;
    }

    public ListView<String> getOnLineUsers2() {
        return onLineUsers2;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public ComboBox<String> getComboBox1() {
        return comboBox1;
    }

    public TableView<File_third> getTableView() {
        return tableView;
    }
    public ObservableList<File_third> getCellData() {
        return cellData;
    }

    public TableColumn<File_third, String> getTc1() {
        return tc1;
    }

    public TableColumn<File_third, String> getTc2() {
        return tc2;
    }

    public TableColumn<File_third, String> getTc3() {
        return tc3;
    }
}

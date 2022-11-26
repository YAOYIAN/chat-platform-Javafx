package com.ql.entity.client;
import com.ql.view.client.NoticeView;
import javafx.application.Platform;
import com.ql.entity.client.Constants;
import com.ql.view.client.FirstView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientReader extends Thread {
    private final Socket socket;
    FirstView firstView;
    public ClientReader(FirstView firstView, Socket socket) {
        this.firstView = firstView;
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true){
                int flag = dis.readInt();
                if(flag == 1){
                    String nameDatas =dis.readUTF();
                    System.out.println(nameDatas);
                    String[] names = nameDatas.split(Constants.SPILIT);
                    System.out.println(Arrays.toString(names));
                    ObservableList<String> strList = FXCollections.observableArrayList(names);
                    Platform.runLater(() -> firstView.getOnLineUsers().setItems(strList));
                }
                else if(flag == 2){
                    String msg = dis.readUTF();
                    Platform.runLater(() -> {
                        firstView.getTextArea().appendText(msg);
                        firstView.getTextArea().positionCaret(firstView.getTextArea().getText().length());
                    });
                }
                else if(flag==4){
                    int ff = dis.readInt();
                    if(ff==1){
                        Platform.runLater(() -> new NoticeView("成功建群"));
                        ArrayList<String> qun_name_has_in = new ArrayList<>();
                        String qunname = "";
                        qunname =  dis.readUTF();
                        while (!qunname.equals("mei_you_la_ha_ha")) {
                            try {
                                qun_name_has_in.add(qunname);
                                qunname =  dis.readUTF();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        ObservableList<String> observableList = FXCollections.observableArrayList(qun_name_has_in);
                        Platform.runLater(() -> firstView.getComboBox1().setItems(observableList));
                    }
                    else
                        Platform.runLater(() -> new NoticeView("建群失败"));
                }
                else if(flag==5){
                    int ff = dis.readInt();
                    if(ff==1){
                        Platform.runLater(() -> new NoticeView("成功加入"));
                        ArrayList<String> qun_name_has_in = new ArrayList<>();
                        String qunname = "";
                        qunname =  dis.readUTF();
                        while (!qunname.equals("mei_you_la_ha_ha")) {
                            try {
                                qun_name_has_in.add(qunname);
                                qunname =  dis.readUTF();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        ObservableList<String> observableList = FXCollections.observableArrayList(qun_name_has_in);
                        Platform.runLater(() -> firstView.getComboBox1().setItems(observableList));
                    }
                    else
                        Platform.runLater(() -> new NoticeView("加入失败"));

                }
                else if(flag==6){
                    ArrayList<String> qun_name_has_in = new ArrayList<>();
                    String qunname = "";
                    qunname =  dis.readUTF();
                    while (!qunname.equals("mei_you_la_ha_ha")) {
                        try {
                            qun_name_has_in.add(qunname);
                            qunname =  dis.readUTF();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList(qun_name_has_in);
                    Platform.runLater(() -> firstView.getComboBox1().setItems(observableList));
                }
                else if(flag==8){
                    ArrayList<String> qun_name_has_in = new ArrayList<>();
                    String qunname = "";
                    qunname =  dis.readUTF();
                    while (!qunname.equals("mei_you_la_ha_ha")) {
                        try {
                            qun_name_has_in.add(qunname);
                            qunname =  dis.readUTF();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    ObservableList<String> observableList = FXCollections.observableArrayList(qun_name_has_in);
                    Platform.runLater(() -> {
                        firstView.getComboBox1().setItems(observableList);
                        new NoticeView("退群成功！");
                    });
                }
                else if(flag==21){
                    ArrayList<File_third>  File_third_list = new ArrayList<File_third>();
                    String str = dis.readUTF();
                    System.out.println("更新文件列表中");
                    while (!str.equals("mei_you_la_ha_ha")){
                        File_third file_third = new File_third();
                        file_third.setFile_name(new SimpleStringProperty(dis.readUTF()));
                        file_third.setFile_length(new SimpleStringProperty(dis.readUTF()));
                        file_third.setFile_modify_time(new SimpleStringProperty(dis.readUTF()));
                        File_third_list.add(file_third);
                        str = dis.readUTF();
                    }
                    System.out.println("更新完毕");
                    Platform.runLater(() -> {
                        firstView.getCellData().clear();
                        for (File_third file_third : File_third_list) {
                            firstView.getCellData().add(file_third);
                            firstView.getTc1().setCellValueFactory(cellData -> cellData.getValue().getFile_name());
                            firstView.getTc2().setCellValueFactory(cellData -> cellData.getValue().getFile_length());
                            firstView.getTc3().setCellValueFactory(cellData -> cellData.getValue().getFile_modify_time());
                        }
                        firstView.getTableView().setItems( firstView.getCellData());
                    });
                }
                else if(flag==22){
                    String filename = dis.readUTF();
                    OutputStream os = new FileOutputStream(filename);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = dis.read(buffer)) > 0){
                        os.write(buffer,0,len);
                        if(len<1024)
                            break;
                    }
                    os.close();
                    Platform.runLater(() -> new NoticeView("下载完成"));
                }
                else if(flag==25){
                    String nameDatas =dis.readUTF();
                    System.out.println(nameDatas);
                    String[] names = nameDatas.split(Constants.SPILIT);
                    System.out.println(Arrays.toString(names));
                    ObservableList<String> strList = FXCollections.observableArrayList(names);
                    Platform.runLater(() -> firstView.getOnLineUsers2().setItems(strList));
                }
                else if(flag==26){
                    String msg_record = dis.readUTF();
                    Platform.runLater(() ->  firstView.getTextArea().setText(msg_record));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    };
}
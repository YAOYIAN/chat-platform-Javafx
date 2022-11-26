package com.ql.dao.server;

import com.ql.util.server.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    public static boolean login(String name,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement = connection.prepareStatement("select * from user where name = ? and password = ?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean register(String name,String password){
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from user where name = ? ");
            preparedStatement1.setString(1,name);
            resultSet1 = preparedStatement1.executeQuery();
            if(resultSet1.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Connection connection3 = null;
        PreparedStatement preparedStatement3 = null;
        try {
            connection3 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement3 = connection3.prepareStatement("create table "+name+" (qun varchar(255))");
            preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(null,preparedStatement3,connection3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Connection connection4 = null;
        PreparedStatement preparedStatement4 = null;
        try {
            connection4 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement4 = connection4.prepareStatement("insert into realtime(user) values (?)");
            preparedStatement4.setString(1,name);
            preparedStatement4.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(null,preparedStatement4,connection4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Connection connection2 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet2 = null;
        try {
            connection2 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement2 = connection2.prepareStatement("insert into user(name,password) values (?,?)");
            preparedStatement2.setString(1,name);
            preparedStatement2.setString(2,password);
            int count = preparedStatement2.executeUpdate();
            if(count != -1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet2,preparedStatement2,connection2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean new_qun(String qun_name,String jiaqun_name){
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from shuo_you_qun where qun = ? ");
            preparedStatement1.setString(1,qun_name);
            resultSet1 = preparedStatement1.executeQuery();
            if(resultSet1.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




        Connection connection3 = null;
        PreparedStatement preparedStatement3 = null;
        try {
            connection3 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement3 = connection3.prepareStatement("insert into "+jiaqun_name+"(qun) values (?)");
            preparedStatement3.setString(1,qun_name);
            preparedStatement3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(null,preparedStatement3,connection3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Connection connection4 = null;
        PreparedStatement preparedStatement4 = null;
        try {
            connection4 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement4 = connection4.prepareStatement("insert into shuo_you_qun(qun) values (?)");
            preparedStatement4.setString(1,qun_name);
            preparedStatement4.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(null,preparedStatement4,connection4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static boolean goin_qun(String qun_name,String goin_name){
        boolean has_this_qun = false;
        boolean has_already_in = false;
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from shuo_you_qun where qun = ? ");
            preparedStatement1.setString(1,qun_name.trim());
            resultSet1 = preparedStatement1.executeQuery();
            System.out.println("正在查看是否有这个群");
            if(resultSet1.next()){
                has_this_qun = true;
            }
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(!has_this_qun){
            System.out.println("没有这个群");
        }

        if(has_this_qun){
            Connection connection2 = null;
            PreparedStatement preparedStatement2 = null;
            ResultSet resultSet2 = null;
            try {
                connection2 = JdbcUtil.getJdbcUtil().getConnection();
                preparedStatement2 = connection2.prepareStatement("select * from " +goin_name+ " where qun = ? ");
                preparedStatement2.setString(1,qun_name);
                resultSet2 = preparedStatement2.executeQuery();
                System.out.println("正在查看是否已经加过了这个群");
                if(resultSet2.next()){
                    has_already_in = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }finally {
                try {
               JdbcUtil.getJdbcUtil().closeConnection(resultSet2,preparedStatement2,connection2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(!has_already_in){
                Connection connection3 = null;
                PreparedStatement preparedStatement3 = null;
                try {
                connection3 = JdbcUtil.getJdbcUtil().getConnection();
                preparedStatement3 = connection3.prepareStatement("insert into "+goin_name+"(qun) values (?)");
                preparedStatement3.setString(1,qun_name);
                preparedStatement3.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }finally {
                    try {
                        JdbcUtil.getJdbcUtil().closeConnection(null,preparedStatement3,connection3);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        }
        return false;
    }
    public static ArrayList<String> get_all_qunname(){
        ArrayList<String> all_qunname = new ArrayList<>();
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from shuo_you_qun");
            resultSet1 = preparedStatement1.executeQuery();
            if(resultSet1.next()){
                all_qunname.add(resultSet1.getString("qun"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  all_qunname;
    }
    public static ArrayList<String> qun_name_has_in(String name){
        ArrayList<String> qun_name_has_in = new ArrayList<>();
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from "+name);
            resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                qun_name_has_in.add(resultSet1.getString("qun"));
                System.out.println(resultSet1.getString("qun"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return qun_name_has_in;
    }
    public static ArrayList<String> qun_name_online(String qun_name){
        ArrayList<String> qun_name_has_in = new ArrayList<>();
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from realtime where qun = ?");
            preparedStatement1.setString(1,qun_name);
            resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                qun_name_has_in.add(resultSet1.getString("user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return qun_name_has_in;
    }
    public static void change_to_new_qun(String name,String new_qun){
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("update realtime set qun = ? where user = ?");
            preparedStatement1.setString(1,new_qun);
            preparedStatement1.setString(2,name);
            preparedStatement1.executeUpdate();
            System.out.println("更换完成 ");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void tuiqun(String name,String tui_qunname){
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("delete from "+name+" where qun = ?");
            preparedStatement1.setString(1,tui_qunname);
            preparedStatement1.executeUpdate();
            System.out.println("更新完成 ");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static String xia_xian(String name){
        String ans = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement = connection.prepareStatement("select * from realtime where user = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                ans = resultSet.getString("qun");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("update realtime set qun = null where user = ?");
            preparedStatement1.setString(1,name);
            preparedStatement1.executeUpdate();
            System.out.println("更新完成 ");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }
    public static String this_man_in(String name){
        String ans = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement = connection.prepareStatement("select * from realtime where user = ?");
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                ans = resultSet.getString("qun");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ans;
    }
    public static ArrayList<String> with_this_man_in_qun(String name){
        ArrayList<String> with_this_man_in_qun = new ArrayList<>();
        String qunname = "";
        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from realtime where user = ?");
            preparedStatement1.setString(1,name);
            resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                qunname=resultSet1.getString("qun");
                System.out.println(resultSet1.getString("qun"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        Connection connection2 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet2 = null;
        try {
            connection2 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement2 = connection2.prepareStatement("select * from realtime where qun = ?");
            preparedStatement2.setString(1,qunname);
            resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                with_this_man_in_qun.add(resultSet2.getString("user"));
                System.out.println(resultSet2.getString("user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet2,preparedStatement2,connection2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return with_this_man_in_qun;
    }
    public static ArrayList<String> permit_newqun(String qun_name){
        ArrayList<String> permit_newqun_name = new ArrayList<>();
        ArrayList<String> all_name = new ArrayList<>();

        Connection connection1 = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet1 = null;
        try {
            connection1 = JdbcUtil.getJdbcUtil().getConnection();
            preparedStatement1 = connection1.prepareStatement("select * from user");
            resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()){
                all_name.add(resultSet1.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcUtil.getJdbcUtil().closeConnection(resultSet1,preparedStatement1,connection1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("all_user_scanning:");
        for (String name: all_name) {
            Connection connection2 = null;
            PreparedStatement preparedStatement2 = null;
            ResultSet resultSet2 = null;
            try {
                connection2 = JdbcUtil.getJdbcUtil().getConnection();
                String SQL = new String("select * from "+name +" where qun = '"+qun_name+"'");
                preparedStatement2 = connection2.prepareStatement(SQL);
                resultSet2 = preparedStatement2.executeQuery();
                if (resultSet2.next()){
                    permit_newqun_name.add(name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    JdbcUtil.getJdbcUtil().closeConnection(resultSet2,preparedStatement2,connection2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return permit_newqun_name;
    }
}



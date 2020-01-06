package com.example;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;

/**
 * Hello world!
 */
public class App {
    public static Connection getConnection() {
        Connection conn = null;

        try {
            String url = "jdbc:oracle:thin:@//192.168.230.155:1521/fxcms";
            String user = "test";
            String password = "test";

            Class.forName("oracle.jdbc.driver.OracleDriver");//加载数据驱动
            conn = DriverManager.getConnection(url, user, password);// 连接数据库

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }
        return conn;
    }

    public static Connection getConnection2() {
        Connection conn = null;

        try {
            String url = "jdbc:oracle:thin:@//192.168.230.155:1521/fxcms2";
            String user = "test";
            String password = "test";

            Class.forName("oracle.jdbc.driver.OracleDriver");//加载数据驱动
            conn = DriverManager.getConnection(url, user, password);// 连接数据库

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }
        return conn;
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String query(String sql) {
        PreparedStatement pstmt;

        try {
            pstmt = getConnection().prepareStatement(sql);
            //建立一个结果集，用来保存查询出来的结果
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                String address = rs.getString("address");
                System.out.println(address);
                return address;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "xx";
    }

    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {

                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) throws Exception {
        //String sql="select userenv('language') xx from dual";
        //query(sql);
        // String sqlstr="select * from tbs01";
        //query(sqlstr);
       /* String xx=new String("中国人".getBytes(),"UTF-8");
        byte[] bytes="中国人".getBytes("UTF-8");
        System.out.println(xx);
        System.out.println(byte2Hex(bytes));*/
        Connection conn2 = getConnection2();

        String sqlstr = "select * from tbs01";
        String result = query(sqlstr);
        System.out.println("result=" + result);

        String insertx = "insert into tbs01(id,address) values(?,?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(insertx);
        pstmt.setInt(1, 2);
        String ss = new String(result.getBytes(), "UTF-8");
        pstmt.setString(2, result);
        pstmt.executeUpdate();
        //conn.commit();
        conn.close();


    }
}

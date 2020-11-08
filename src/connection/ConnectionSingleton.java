package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private Connection c;
    private static ConnectionSingleton s;

    private ConnectionSingleton(){
        try{
            String url = "jdbc:mysql://185.163.127.113:3306/enzocist_movielens";
            c = DriverManager.getConnection(url,"enzocist_any","Togsfbchzd1");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ConnectionSingleton getInstance(){
        if (s == null){
            s = new ConnectionSingleton();
        }else{
            try{
                if(s.c != null && s.c.isClosed()){
                    s = new ConnectionSingleton();
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return s;
    }

    public Connection getConnection(){
        return c;
    }
}

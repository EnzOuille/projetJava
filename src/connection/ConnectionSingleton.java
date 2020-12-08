package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Connection singleton.
 */
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

    /**
     * Crées une instance de connexion si il n'y en a pas en cours
     * Sinon retourne celle courante
     * @return the connection singleton
     */
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

    /**
     * Get connection connection.
     *
     * @return the connection
     */
    public Connection getConnection(){
        return c;
    }
}

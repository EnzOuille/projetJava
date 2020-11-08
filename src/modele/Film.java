package modele;

import connection.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

public class Film extends Observable {

    private static int currentMovie = 1;
    private InstanceFilm film = null;

    public Film(){
        this.film = getFilm(currentMovie);
        System.out.println(this.film.toString());
    }

    public static InstanceFilm getFilm(int num) {
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE mov_id = ?");
            ps.setInt(1, num);
            ResultSet res = ps.executeQuery();
            if (res.first()){
                return constructeurInstanceFilm(res);
            }else{
                return getFilm(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InstanceFilm getNext(){
        currentMovie += 1;
        return getFilm(currentMovie);
    }

    public static InstanceFilm previous(){
        if (currentMovie>=1) currentMovie --;
        return getFilm(currentMovie);
    }

    public static InstanceFilm constructeurInstanceFilm(ResultSet res) {
        try {
            InstanceFilm ret = new InstanceFilm(res.getInt("mov_id"), res.getString("imdb_id"), res.getString("title"), res.getString("poster"), res.getString("genres"), res.getString("release_date"), res.getString("type"), res.getString("synopsis"), res.getDouble("rating"), res.getInt("year"), res.getBoolean("active"));
            return ret;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static double cosinus_sim(int mov_id1, int mov_id2){
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try {
            PreparedStatement ps1 = c.prepareStatement("SELECT * FROM tag_relevance WHERE mov_id = ?");
            ps1.setInt(1, mov_id1);
            ResultSet res1 = ps1.executeQuery();
            PreparedStatement ps2 = c.prepareStatement("SELECT * FROM tag_relevance WHERE mov_id = ?");
            ps2.setInt(1,mov_id2);
            ResultSet res2 = ps2.executeQuery();
            ArrayList<Double> tags1 = new ArrayList<>();
            ArrayList<Double> tags2 = new ArrayList<>();
            double somme = 0;
            while (res1.next()) {
                tags1.add(res1.getInt("tag_id"),(double)res1.getDouble("relevance"));
            }
            while (res2.next()){
                tags2.add(res2.getInt("tag_id"),(double)res2.getDouble("relevance"));
            }
            if (tags1.size() ==0 || tags2.size() == 0){
                return 0;
            }
            for (int i=0; i<1128;i++){
                if (tags1.get(i) != null && tags2.get(i) != null){
                    somme += tags1.get(i) * tags2.get(i);
                }else{
                    tags1.remove(i);
                    tags2.remove(i);
                }
            }
            double quotient1;
            double quotient2;
            quotient1 = tags1.stream().mapToDouble(x -> x * x).sum();
            quotient2 = tags2.stream().mapToDouble(x -> x * x).sum();
            return somme/(quotient1 * quotient2);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getCurrentMovie(){
        return currentMovie;
    }

    public InstanceFilm getFilm(){
        return film;
    }
    public void update(){
        this.setChanged();
        this.notifyObservers();
    }
}

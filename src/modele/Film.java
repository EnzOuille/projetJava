package modele;

import connection.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Film.
 */
public class Film extends Observable {

    private static int currentMovie = 1;
    private InstanceFilm film = null;
    private ArrayList<InstanceFilm> recherches = null;
    private ArrayList<Integer> liste_algo = null;
    private ArrayList<Integer> liste_memoire = null;
    private HashMap<Integer,ArrayList<Double>> all;
    private boolean collectionGeneree = false;
    /**
     * Film retourné lors de l'algorithme
     */
    static int res_mov = -1;
    /**
     * Cosinus minimum lors de l'algorithme
     */
    static double min = 100;

    /**
     * Instantiates a new Film.
     */
    public Film(){
        this.film = getFilm(currentMovie);
    }

    /**
     * Retourne un film de la base de données
     *
     * @param num le mov_id du film demandé
     * @return the film
     */
    public InstanceFilm getFilm(int num) {
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM movie WHERE mov_id = ?");
            ps.setInt(1, num);
            ResultSet res = ps.executeQuery();
            if (res.first()){
                return constructeurInstanceFilm(res);
            }else{
                currentMovie = 1;
                return getFilm(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Modifies le film courant pour être le suivant
     */
    public void getNext(){
        currentMovie += 1;
        film = getFilm(currentMovie);
    }

    /**
     * Modifies le film courant pour être le précédent
     */
    public void previous(){
        if (currentMovie>=1) currentMovie --;
        film = getFilm(currentMovie);
    }

    /**
     * à partir du mov_id d'un film, modifie le film courant pour cet id précisément
     *
     * @param id le mov_id du film demandé
     */
    public void getSpecialFilm(int id){
        currentMovie = id;
        film = getFilm(currentMovie);
    }

    /**
     * Retourne la liste des films quel la recherche a retournée
     *
     * @return the recherches
     */
    public ArrayList<InstanceFilm> getRecherches() {
        return recherches;
    }

    /**
     * Construit une instanceFilm à partir d'un résultat d'une requête
     *
     * @param res le résultat de la requête
     * @return l'instance retournée
     */
    public InstanceFilm constructeurInstanceFilm(ResultSet res) {
        try {
            return new InstanceFilm(res.getInt("mov_id"), res.getString("imdb_id"), res.getString("title"), res.getString("poster"), res.getString("genres"), res.getString("release_date"), res.getString("type"), res.getString("synopsis"), res.getDouble("rating"), res.getInt("year"), res.getBoolean("active"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Construit une instanceActeur à partir d'un résultat d'une requête
     *
     * @param res le résultat de la requête
     * @return l'instance retournée
     */
    public InstanceFilmActeur constructeurInstanceFilmActeur(ResultSet res){
        try{
            return new InstanceFilmActeur(res.getString("poster"),res.getString("title"),res.getInt("year"),res.getString("rol_name"),res.getInt("mov_id"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * retourne le film courant
     *
     * @return the instance film
     */
    public InstanceFilm getFilm(){
        return film;
    }

    /**
     * Retourne une liste d'objet Participant correspondant à tous les acteurs d'un film
     *
     * @return the participants
     */
    public ArrayList<Participant> getParticipants() {
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM role INNER JOIN people on people.peo_id = role.peo_id WHERE mov_id = ?");
            ps.setInt(1,currentMovie);
            ResultSet res = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            if (res.first()){
                Participant temp = new Participant(res.getInt("role.peo_id"),res.getInt("mov_id"),res.getString("peo_name"),res.getString("rol_name"),res.getInt("rol_id"));
                participants.add(temp);
                while(res.next()){
                    temp = new Participant(res.getInt("role.peo_id"),res.getInt("mov_id"),res.getString("peo_name"),res.getString("rol_name"),res.getInt("rol_id"));
                    participants.add(temp);
                }
            }else{
                return null;
            }
            return participants;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retourne toutes les instancesFilmActeur qui correspondent au rôle d'un acteur dans un film
     *
     * @param nomActeur le nom de l'acteur
     * @return l'ensemble des instances
     */
    public ArrayList<InstanceFilmActeur> getFilmActeur(String nomActeur){
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT role.mov_id,title,poster,year,rol_name FROM movie INNER JOIN role on movie.mov_id = role.mov_id\n" +
                    "INNER JOIN people on role.peo_id = people.peo_id\n" +
                    "WHERE people.peo_name = ?;");
            ps.setString(1,nomActeur);
            ResultSet res  = ps.executeQuery();
            ArrayList<InstanceFilmActeur> instances = new ArrayList<>();
            if (res.first()){
                instances.add(constructeurInstanceFilmActeur(res));
                while(res.next()){
                    instances.add(constructeurInstanceFilmActeur(res));
                }
                return instances;
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes une requête en recherchant dans la base de données le titre d'un film
     * Stocke le résultat dans l'attribut "recherches"
     *
     * @param textfield la recherche
     */
    public void rechercheFilm(String textfield){
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM movie where title LIKE ?");
            ps.setString(1,"%"+textfield.toLowerCase()+"%");
            ResultSet res = ps.executeQuery();
            ArrayList<InstanceFilm> instances = new ArrayList<>();
            if (res.first()) {
                instances.add(constructeurInstanceFilm(res));
                while (res.next()){
                    instances.add(constructeurInstanceFilm(res));
                }
            }else{
                this.recherches = null;
            }
            this.recherches =  instances;
        }catch (SQLException e){
            e.printStackTrace();
            this.recherches =  null;
        }
    }

    /**
     * Executes une requête en recherchant dans la base de données plusieurs attributs
     * Stocke le résultat dans l'attribut "recherches"
     *
     * @param all the all
     */
    public void rechercheAll (String all){
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * from movie inner join role on movie.mov_id = role.mov_id\n" +
                    "                    inner join people on role.peo_id = people.peo_id\n" +
                    "WHERE\n" +
                    "    title like ?\n" +
                    "OR\n" +
                    "    genres like ?\n" +
                    "OR\n" +
                    "    synopsis like ?\n" +
                    "OR\n" +
                    "    peo_name like ? GROUP BY movie.mov_id");
            ps.setString(1, "%"+all.toLowerCase()+"%");
            ps.setString(2, "%"+all.toLowerCase()+"%");
            ps.setString(3, "%"+all.toLowerCase()+"%");
            ps.setString(4,"%"+all.toLowerCase()+"%");
            ResultSet res = ps.executeQuery();
            ArrayList<InstanceFilm> instances = new ArrayList<>();
            if (res.first()) {
                instances.add(constructeurInstanceFilm(res));
                while (res.next()){
                    instances.add(constructeurInstanceFilm(res));
                }
            }else{
                this.recherches = null;
            }
            this.recherches =  instances;
        }catch (SQLException e){
            e.printStackTrace();
            this.recherches =  null;
        }
    }

    /**
     * Retourne une liste de tous les participants qui ont partcipé à un film donné
     *
     * @param mov_id le mov_id du film
     * @return l'ensemble des participants
     */
    public ArrayList<Participant> getSpecialParticipants(int mov_id){
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT * FROM role INNER JOIN people on people.peo_id = role.peo_id WHERE mov_id = ?");
            ps.setInt(1,mov_id);
            ResultSet res = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            if (res.first()){
                Participant temp = new Participant(res.getInt("role.peo_id"),res.getInt("mov_id"),res.getString("peo_name"),res.getString("rol_name"),res.getInt("rol_id"));
                participants.add(temp);
                while(res.next()){
                    temp = new Participant(res.getInt("role.peo_id"),res.getInt("mov_id"),res.getString("peo_name"),res.getString("rol_name"),res.getInt("rol_id"));
                    participants.add(temp);
                }
            }else{
                return null;
            }
            return participants;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private HashMap<Integer,Double> liste_simil;

    /**
     * Permet de réduire la liste sur laquelle l'algorithme travaille lors du choix d'un film
     *
     * @param mov_id l'id du film courant
     */
    public void reduireListe(int mov_id) {
        //Parcours de la liste pour recalculer les similarités
        liste_simil.forEach((k, v) -> {
            if (k == mov_id) {
                liste_simil.replace(mov_id, 1.0);
            } else {
                liste_simil.replace(k, cosinus_sim(mov_id, k));
            }
        });
        //Tri de la liste par similarité
        liste_simil = liste_simil.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        HashMap<Integer,Double> temp_map = new HashMap<>();
        int i = 0;
        int total = 0;
        //Calcul de l'indice à partir duquel il faut s'arreter pour couper la liste
        if (liste_simil.size()%2==0) total = liste_simil.size()/2;
        if (liste_simil.size()%2!=0) total = (int) ((liste_simil.size()/2)-0.5);
        //Création d'une liste simil temporaire
        for(Map.Entry<Integer, Double> e : liste_simil.entrySet()){
            if(i < total){
                temp_map.put(e.getKey(),e.getValue());
            }
            i++;
        }
        //Mise à jour de liste simil
        liste_simil = temp_map;
        liste_algo = new ArrayList<>();
        //Mise à jour de liste algo
        liste_algo.addAll(liste_simil.keySet());
    }

    /**
     * Algorithme qui gérè le choix et les préférences d'un film
     *
     * @return Retourne les films à afficher dans la vue
     */
    public ArrayList<Integer> finalAlgorithm(){
        liste_simil = new HashMap<>();
        ArrayList<Integer> res = new ArrayList<>();
        if (liste_algo.size() == 0){
            // Zéro élément je retourne le film de base
            res.add(1);
        }else if (liste_algo.size() == 1){
            // Un seul élément je ne retourne que celui-ci
            res.addAll(liste_algo);
        }else if (liste_algo.size() == 2){
            // Deux éléments, je les retourne et j'ajoute un troisième élément pour différencier du dernier cas
            res.addAll(liste_algo);
            res.add(-1);
        }else{
            // Plusieurs éléments, alors je lance l'algorithme précisé dans le sujet
            // Je choisis un film au hasard, calcule toutes les similarités et retourne le film aléatoire et le film le moins similaire
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(liste_algo.size());
            int temp_mov = liste_algo.get(randomInt);
            liste_algo.forEach(x -> {
                if (x != temp_mov){
                    double temp_cos = cosinus_sim(temp_mov,x);
                    liste_simil.put(x,temp_cos);
                    if (temp_cos < min){
                        min = temp_cos;
                        res_mov = x;
                    }
                }
            });
            res.add(temp_mov);
            res.add(res_mov);
            min = 5;
            res_mov = -1;
        }
        return res;
    }

    /**
     * Méthode qui génére all qui contient tout les mov_id, leurs tag et leur relevance
     * Cette Map est utilisée tout du long pour l'algorithme
     */
    public void genererCollection(){
        HashMap<Integer,ArrayList<Double>> all = new HashMap<>();
        liste_algo = new ArrayList<>();
        ConnectionSingleton cs = ConnectionSingleton.getInstance();
        Connection c = cs.getConnection();
        try{
            PreparedStatement ps = c.prepareStatement("SELECT mov_id,tag_id,relevance from tag_relevance;");
            ResultSet res = ps.executeQuery();
            while(res.next()){
                try{
                    liste_algo.get(res.getInt("mov_id"));
                }catch( IndexOutOfBoundsException e){
                    liste_algo.add(res.getInt("mov_id"));
                }
                if (all.get(res.getInt("mov_id")) == null){
                    ArrayList<Double> add = new ArrayList<>();
                    add.add(res.getInt("tag_id"),res.getDouble("relevance"));
                    all.put(res.getInt("mov_id"),add);
                }else{
                    all.get(res.getInt("mov_id")).add(res.getInt("tag_id"),res.getDouble("relevance"));
                }
            }
            this.all = all;
            Set<Integer> mySet = new HashSet<>(liste_algo);
            liste_algo = new ArrayList<Integer>(mySet);
            this.collectionGeneree = true;
            this.liste_memoire = liste_algo;
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void rerechercher(){
        this.liste_algo = this.liste_memoire;
    }

    /**
     * Calcules la similarité entre deux films à partir de leur tag comme précisé dans le sujet
     *
     * @param mov_id1 l'id du premier film
     * @param mov_id2 l'id du deuxième film
     * @return the double
     */
    public double cosinus_sim(int mov_id1, int mov_id2){
        ArrayList<Double> tags1 = all.get(mov_id1);
        ArrayList<Double> tags2 = all.get(mov_id2);
        double somme = 0;
        if (tags1.size() ==0 || tags2.size() == 0) {
            return 0;
        }
        int min = 0;
        if (tags1.size() <= tags2.size()) min = tags1.size();
        if (tags2.size() <= tags1.size()) min = tags2.size();
        double quotient1 = 0;
        double quotient2 = 0;
        for (int i=0; i<min;i++){
            if (tags1.get(i) != null && tags2.get(i) != null){
                somme += tags1.get(i) * tags2.get(i);
                quotient1+=tags1.get(i)*tags1.get(i);
                quotient2+=tags2.get(i)*tags2.get(i);
            }
        }
        return somme/(quotient1 * quotient2);
    }

    /**
     * Retourne l'attribut collectionGeneree
     * @return collectionGeneree
     */
    public boolean isCollectionGeneree() {
        return collectionGeneree;
    }

    /**
     * Update.
     */
    public void update(){
        this.setChanged();
        this.notifyObservers();
    }
}

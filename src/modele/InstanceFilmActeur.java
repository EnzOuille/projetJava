package modele;

/**
 * The type Instance film acteur.
 */
public class InstanceFilmActeur {

    private String poster;
    private String title;
    private int annee;
    private String role;
    private int mov_id;

    /**
     * Instantiates a new Instance film acteur.
     *
     * @param poster the poster
     * @param title  the title
     * @param annee  the annee
     * @param role   the role
     * @param mov_id the mov id
     */
    public InstanceFilmActeur(String poster, String title, int annee, String role, int mov_id){
        this.poster = poster;
        this.title = title;
        this.annee = annee;
        this.role = role;
        this.mov_id = mov_id;
    }

    /**
     * Gets poster.
     *
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets annee.
     *
     * @return the annee
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets mov id.
     *
     * @return the mov id
     */
    public int getMov_id() {
        return mov_id;
    }
}

package modele;

/**
 * The type Instance film.
 */
public class InstanceFilm {

    private int mov_id;
    private String imdb_id;
    private String title;
    private String poster;
    private String genres;
    private String release_date;
    private String type;
    private String synopsis;
    private double note;
    private int annee;

    @Override
    public String toString() {
        return "modele.InstanceFilm{" +
                "mov_id=" + mov_id +
                ", imdb_id='" + imdb_id + '\'' +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", genres='" + genres + '\'' +
                ", release_date='" + release_date + '\'' +
                ", type='" + type + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", note=" + note +
                ", annee=" + annee +
                ", active=" + active +
                '}';
    }

    private boolean active;

    /**
     * Instantiates a new Instance film.
     *
     * @param mov_id       the mov id
     * @param imdb_id      the imdb id
     * @param title        the title
     * @param poster       the poster
     * @param genres       the genres
     * @param release_date the release date
     * @param type         the type
     * @param synopsis     the synopsis
     * @param note         the note
     * @param annee        the annee
     * @param active       the active
     */
    public InstanceFilm(int mov_id, String imdb_id, String title, String poster, String genres, String release_date, String type, String synopsis, double note, int annee, boolean active){
        this.mov_id = mov_id;
        this.imdb_id = imdb_id;
        this.title = title;
        this.poster = poster;
        this.genres = genres;
        this.release_date = release_date;
        this.type = type;
        this.synopsis = synopsis;
        this.note = note;
        this.annee = annee;
        this.active = active;
    }

    /**
     * Gets mov id.
     *
     * @return the mov id
     */
    public int getMov_id() {
        return mov_id;
    }

    /**
     * Gets imdb id.
     *
     * @return the imdb id
     */
    public String getImdb_id() {
        return imdb_id;
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
     * Gets poster.
     *
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Gets genres.
     *
     * @return the genres
     */
    public String getGenres() {
        return genres;
    }

    /**
     * Gets release date.
     *
     * @return the release date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets synopsis.
     *
     * @return the synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Gets note.
     *
     * @return the note
     */
    public double getNote() {
        return note;
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
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }
}

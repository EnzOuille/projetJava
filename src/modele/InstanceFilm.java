package modele;

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

    public int getMov_id() {
        return mov_id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getGenres() {
        return genres;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getType() {
        return type;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getNote() {
        return note;
    }

    public int getAnnee() {
        return annee;
    }

    public boolean isActive() {
        return active;
    }
}

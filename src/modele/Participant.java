package modele;

public class Participant {
    private int peo_id;
    private int mov_id;
    private String peo_name;
    private String rol_name;
    private int rol_id;

    public Participant(int peo_id, int mov_id, String peo_name, String rol_name, int rol_id){
        this.peo_id = peo_id;
        this.mov_id = mov_id;
        this.peo_name = peo_name;
        this.rol_name = rol_name;
        this.rol_id = rol_id;
    }

    public int getPeo_id() {
        return peo_id;
    }

    public int getMov_id() {
        return mov_id;
    }

    public String getPeo_name() {
        return peo_name;
    }

    public String getRol_name() {
        return rol_name;
    }

    public int getRol_id() {
        return rol_id;
    }
}

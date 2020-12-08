package modele;

/**
 * The type Participant.
 */
public class Participant {
    private int peo_id;
    private int mov_id;
    private String peo_name;
    private String rol_name;
    private int rol_id;

    /**
     * Instantiates a new Participant.
     *
     * @param peo_id   the peo id
     * @param mov_id   the mov id
     * @param peo_name the peo name
     * @param rol_name the rol name
     * @param rol_id   the rol id
     */
    public Participant(int peo_id, int mov_id, String peo_name, String rol_name, int rol_id){
        this.peo_id = peo_id;
        this.mov_id = mov_id;
        this.peo_name = peo_name;
        this.rol_name = rol_name;
        this.rol_id = rol_id;
    }

    /**
     * Gets peo id.
     *
     * @return the peo id
     */
    public int getPeo_id() {
        return peo_id;
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
     * Gets peo name.
     *
     * @return the peo name
     */
    public String getPeo_name() {
        return peo_name;
    }

    /**
     * Gets rol name.
     *
     * @return the rol name
     */
    public String getRol_name() {
        return rol_name;
    }

    /**
     * Gets rol id.
     *
     * @return the rol id
     */
    public int getRol_id() {
        return rol_id;
    }
}

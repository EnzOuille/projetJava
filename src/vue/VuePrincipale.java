package vue;

import controlleur.ControlleurAffichageFilm;
import modele.Film;
import vue.VueAffichage;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * The type Vue principale.
 */
public class VuePrincipale extends JFrame {

    private VueAffichage vAffi;
    private VueActeur vActeur;
    private ControlleurAffichageFilm cont;

    /**
     * Instantiates a new Vue principale.
     *
     * @param modele the modele
     */
    public VuePrincipale(Film modele){
        super("--- MovieLens ---");
        this.setIconImage(new ImageIcon("img/movielens.png").getImage());
        vAffi = new VueAffichage(modele,this);
        vActeur = new VueActeur(modele,this);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(vAffi);
        this.setVisible(true);
//        this.setLocationRelativeTo(null);
        this.pack();
    }

    /**
     * Get vue affichage vue affichage.
     *
     * @return the vue affichage
     */
    public VueAffichage getVueAffichage(){
        return vAffi;
    }

    /**
     * Get vue acteur vue acteur.
     *
     * @return the vue acteur
     */
    public VueActeur getVueActeur(){
        return vActeur;
    }

    /**
     * Sets cont.
     *
     * @param cont the cont
     */
    public void setCont(ControlleurAffichageFilm cont) {
        this.cont = cont;
    }

    /**
     * Gets cont.
     *
     * @return the cont
     */
    public ControlleurAffichageFilm getCont() {
        return cont;
    }
}

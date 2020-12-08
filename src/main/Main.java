package main;

import controlleur.ControlleurAffichageFilm;
import modele.Film;
import vue.VueAffichage;
import vue.VuePrincipale;

import javax.swing.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main (String[] args) {
        //Création du modèle
        Film modele = new Film();
        // Création de la JFrame
        VuePrincipale swing1 = new VuePrincipale(modele);
        //Création du controlleur
        ControlleurAffichageFilm cont = new ControlleurAffichageFilm(modele,
                swing1.getVueAffichage(),swing1,swing1.getVueActeur());
        //Ajout des listeners aux différents composants
        swing1.getVueAffichage().prec.addActionListener(cont);
        swing1.getVueAffichage().suiv.addActionListener(cont);
        swing1.getVueAffichage().num.addActionListener(cont);
        swing1.getVueAffichage().go1.addActionListener(cont);
        swing1.getVueAffichage().go2.addActionListener(cont);
        swing1.getVueAffichage().lancer_recherche.addActionListener(cont);
        swing1.setCont(cont);
        //Première mise à jour de l'affichage
        cont.premierUpdate();
    }
}

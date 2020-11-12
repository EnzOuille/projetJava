package main;

import controlleur.ControlleurAffichageFilm;
import modele.Film;
import vue.VueAffichage;
import vue.VuePrincipale;

import javax.swing.*;

public class Main {

    public static void main (String[] args) throws UnsupportedLookAndFeelException {
        Film modele = new Film();
        VuePrincipale swing1 = new VuePrincipale(modele);
        VueAffichage vaffi = swing1.getVueAffichage();
        ControlleurAffichageFilm cont = new ControlleurAffichageFilm(modele,swing1.getVueAffichage());
        vaffi.prec.addActionListener(cont);
        vaffi.suiv.addActionListener(cont);
        vaffi.num.addActionListener(cont);
    }
}

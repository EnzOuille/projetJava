package main;

import modele.Film;
import vue.VuePrincipale;

import javax.swing.*;

public class Main {

    public static void main (String[] args) throws UnsupportedLookAndFeelException {
        Film modele = new Film();
        VuePrincipale swing1 = new VuePrincipale(modele);
    }
}

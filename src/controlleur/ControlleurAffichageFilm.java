package controlleur;

import modele.Film;
import vue.VueAffichage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlleurAffichageFilm implements ActionListener {

    private Film film;
    private VueAffichage vue;

    public ControlleurAffichageFilm(Film film, VueAffichage vue){
        this.film = film;
        this.vue = vue;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.prec) film.previous();
        if (e.getSource() == vue.suiv) film.getNext();
        if (e.getSource() == vue.num) film.getSpecialFilm(Integer.parseInt(vue.num.getText()));
        film.update();
    }
}

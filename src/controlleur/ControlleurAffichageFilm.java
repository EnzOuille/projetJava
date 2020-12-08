package controlleur;

import modele.Film;
import modele.InstanceFilm;
import vue.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Controlleur affichage film.
 */
public class ControlleurAffichageFilm implements ActionListener {

    private Film film;
    private VueAffichage vue;
    private VuePrincipale vueP;
    private VueActeur vueA;

    /**
     * Premier update.
     */
    public void premierUpdate(){
        film.update();
    }

    /**
     * Instantiates a new Controlleur affichage film.
     *
     * @param film Le modèle (Film)
     * @param vue  La vueAffichage
     * @param vp   La vuePrincipale
     * @param va   La vueActeur
     */
    public ControlleurAffichageFilm(Film film, VueAffichage vue, VuePrincipale vp, VueActeur va){
        this.film = film;
        this.vue = vue;
        this.vueP = vp;
        this.vueA = va;
    }

    /**
     * Remplace la vue courante par la vue Acteur
     */
    public void putVueActeur(){
        this.vueP.setContentPane(vueA);
        this.vueP.validate();
    }

    /**
     * Remplace la vue courante par la vue Affichage
     */
    public void putVueAffichage(){
        this.vueP.setContentPane(vue);
        this.vueP.validate();
        this.vueP.pack();
    }

    /**
     * Remplace la vue courante par la vue Choix
     *
     * @param vue Vue à ajouter
     */
    public void putVueChoix(VueChoix vue){
        this.vueP.setContentPane(vue);
        this.vueP.validate();
    }

    /**
     * Put vue preferences.
     *
     * @param film1   Premier film
     * @param film2   Deuxième Film
     * @param boutons Booleen pour savoir si il faut désactiver les boutons "Préférer"
     */
    public void putVuePreferences(InstanceFilm film1, InstanceFilm film2, Boolean boutons){
        JPanel temp = new JPanel();
        temp.setLayout(new BorderLayout());
        VueSimple vue1 = new VueSimple(this,film,film1,boutons);
        VueSimple vue2 = new VueSimple(this,film,film2,boutons);
        JButton retour = new JButton("Retour à l'interface principale");
        retour.setActionCommand("Retour");
        retour.addActionListener(this);
        JPanel flow = new JPanel();
        flow.setLayout(new BoxLayout(flow,BoxLayout.X_AXIS));
        flow.add(vue1);
        flow.add(vue2);
        JScrollPane scroll = new JScrollPane(flow);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        temp.add(scroll,BorderLayout.CENTER);
        temp.add(retour,BorderLayout.NORTH);
        this.vueP.setContentPane(temp);
        this.vueP.validate();
        this.vueP.pack();
    }

    /**
     * Ce controlleur va écouter toutes les actions de l'utilisateur et effectuer les opérations correspondantes
     * @param e Action écoutée
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Appui sur le bouton précédent
        if (e.getSource() == vue.prec) film.previous();
        // Appui sur le bouton suivant
        if (e.getSource() == vue.suiv) film.getNext();
        // Appui de la touche entrée sur le champ de recherche par ID
        if (e.getSource() == vue.num){
            try{
                int temp = Integer.parseInt(vue.num.getText());
                film.getSpecialFilm(temp);
            }catch (NumberFormatException ignored){
            }
        }
        // Appui sur le bouton Go qui lance la recherche par titre
        if (e.getSource() == vue.go1){
            film.rechercheFilm(vue.titre_search.getText());
            if (film.getRecherches().size() == 1) film.getSpecialFilm(film.getRecherches().get(0).getMov_id()); putVueAffichage();
            if (film.getRecherches() != null && film.getRecherches().size() > 1){
                VueChoix vueChoix = new VueChoix(film.getRecherches(),this);
                putVueChoix(vueChoix);
            }
        }
        // Appui sur le bouton Go qui lance la recherche sur différents champs
        if (e.getSource() == vue.go2){
            film.rechercheAll(vue.nom_search.getText());
            if (film.getRecherches().size() == 1) film.getSpecialFilm(film.getRecherches().get(0).getMov_id()); putVueAffichage();
            if (film.getRecherches() != null && film.getRecherches().size() > 1){
                VueChoix vueChoix = new VueChoix(film.getRecherches(),this);
                putVueChoix(vueChoix);
            }
        }
        try{
        // Appui sur le bouton d'un participant et qui lance la vue de l'acteur en question
        if (((JButton) e.getSource()).getActionCommand().startsWith("0")){
            JButton temp = (JButton)e.getSource();
            String command = temp.getActionCommand().substring(1);
            vueA = new VueActeur(film,vueP);
            vueA.initialiserComposants(command);
            putVueActeur();
        }
        // Appui sur le bouton dans la vue Acteur qui renvoie vers un film
        if (((JButton) e.getSource()).getActionCommand().startsWith("1_")){
            JButton temp = (JButton) e.getSource();
            String command = temp.getActionCommand().substring(2);
            film.getSpecialFilm(Integer.parseInt(command));
            putVueAffichage();
        }
        // Appui sur le bouton lors du choix d'un film avec une recherche retournant plusieurs résultats
        if (((JButton) e.getSource()).getActionCommand().startsWith("recherche_")){
                JButton temp = (JButton) e.getSource();
                String command = temp.getActionCommand().substring(10);
                film.getSpecialFilm(Integer.parseInt(command));
                putVueAffichage();
        }
        // Appui sur le bouton retour lors de l'algorithme du choix d'un film
        if (((JButton) e.getSource()).getActionCommand().startsWith("Retour")){
            film.getSpecialFilm(1);
            putVueAffichage();
        }
        // Appui sur le bouton lancer_recherche qui lance l'algorithme qui permet de choisir des films
        if (e.getSource() == vue.lancer_recherche){
            if (!film.isCollectionGeneree())film.genererCollection();
            if (film.isCollectionGeneree())film.rerechercher();
            ArrayList<Integer> temp = film.finalAlgorithm();
            if (temp.size() == 1) film.getSpecialFilm(temp.get(0));putVueAffichage();
            if (temp.size() == 3) putVuePreferences(film.getFilm(temp.get(0)),film.getFilm(temp.get(1)),false);
            if (temp.size() == 2) putVuePreferences(film.getFilm(temp.get(0)),film.getFilm(temp.get(1)),true);
        }
        // Appui sur le bouton Je choisis lors du choix d'un film
        if (((JButton) e.getSource()).getActionCommand().startsWith("c_")){
            JButton temp = (JButton) e.getSource();
            String command = temp.getActionCommand().substring(2);
            film.getSpecialFilm(Integer.parseInt(command));
            putVueAffichage();
        }
        // Appui sur le bouton Je préfère lors du choix d'un film
        if (((JButton) e.getSource()).getActionCommand().startsWith("p_")){
            JButton temp = (JButton) e.getSource();
            String command = temp.getActionCommand().substring(2);
            film.reduireListe(Integer.parseInt(command));
            ArrayList<Integer> temp2 = film.finalAlgorithm();
            if (temp2.size() == 1) film.getSpecialFilm(temp2.get(0));putVueAffichage();
            if (temp2.size() == 3) putVuePreferences(film.getFilm(temp2.get(0)),film.getFilm(temp2.get(1)),false);
            if (temp2.size() == 2) putVuePreferences(film.getFilm(temp2.get(0)),film.getFilm(temp2.get(1)),true);
        }
        }catch (Exception ignored){
        }
        film.update();
    }
}

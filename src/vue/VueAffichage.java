package vue;

import modele.Film;
import modele.Participant;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VueAffichage extends JPanel implements Observer {

    public Film modele;
    public JLabel lbl_titre;
    public JLabel ic_film;
    public JLabel lbl_annee;
    public JLabel lbl_note;
    public JLabel lbl_participant;
    public JLabel[] lbl_participants;
    public JLabel lbl_genres;
    public JTextArea lbl_synopsis;
    public JPanel participants;
    public JTextField titre_search;
    public JTextField nom_search;
    public JButton go1;
    public JButton go2;
    public JButton prec;
    public JButton suiv;
    public JTextField num;

    public VueAffichage(Film mod){
        modele = mod;
        modele.addObserver(this);
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());
        initialiserComposants();
        this.positionnementFilm();
        this.setBorder(new EmptyBorder(10,10,10,10));
        mod.update();
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    public void initialiserComposants(){
        lbl_titre = new JLabel("Titre du film",SwingConstants.CENTER);
        lbl_titre.setOpaque(true);
        lbl_titre.setBackground(Color.BLUE);
        lbl_annee = new JLabel("Année de production",SwingConstants.CENTER);
        lbl_annee.setOpaque(true);
        lbl_annee.setBackground(Color.BLUE);
        lbl_note = new JLabel("Note",SwingConstants.CENTER);
        lbl_note.setOpaque(true);
        lbl_note.setBackground(Color.BLUE);
        lbl_participant = new JLabel("Participants",SwingConstants.CENTER);
        lbl_genres = new JLabel("Genres",SwingConstants.CENTER);
        lbl_genres.setOpaque(true);
        lbl_genres.setBackground(Color.BLUE);
        lbl_synopsis = new JTextArea("Synopsis");
        lbl_synopsis.setLineWrap(true);
        lbl_synopsis.setWrapStyleWord(true);
        lbl_synopsis.setEditable(false);
        lbl_synopsis.setPreferredSize(new Dimension(this.getWidth(),300));
        ic_film = new JLabel( new ImageIcon("img/zero_two.png"));
        titre_search = new JTextField("Recherche par titre");
        nom_search = new JTextField("Recherche par nom (acteur, ...)");
        go1 = new JButton("Go");
        go1.setOpaque(true);
        go1.setBackground(Color.BLACK);
        go1.setForeground(Color.WHITE);
        go2 = new JButton("Go");
        go2.setOpaque(true);
        go2.setBackground(Color.BLACK);
        go2.setForeground(Color.WHITE);
        prec = new JButton("Précédent");
        prec.setOpaque(true);
        prec.setBackground(Color.BLACK);
        prec.setForeground(Color.WHITE);
        Dimension d = prec.getPreferredSize();
        d.width = d.width + 20;
        prec.setPreferredSize(d);
        suiv = new JButton("Suivant");
        suiv.setOpaque(true);
        suiv.setBackground(Color.BLACK);
        suiv.setForeground(Color.WHITE);
        suiv.setPreferredSize(d);
        num = new JTextField("ID film");
        num.setHorizontalAlignment(JTextField.CENTER);
    }

    public void positionnementFilm(){
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2,2,2,2);
        c.fill = GridBagConstraints.HORIZONTAL;
        //Recherche Titre
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 100;
        this.add(titre_search,c);
        //GO1
        c.gridwidth = 1;
        c.gridx = 2;
        c.weightx = 0;
        this.add(go1,c);
        // Recherche par nom
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 100;
        this.add(nom_search,c);
        //GO2
        c.gridx = 2;
        c.gridwidth = 1;
        c.weightx = 0;
        this.add(go2,c);
        //Précédent, Suivant, Numéro Film
        prec_suiv_num(c);
        //Titre du film
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        this.add(lbl_titre,c);
        //Image
        c.gridx= 0;
        c.gridy= 4;
        c.gridheight = 3;
        c.gridwidth = 1;
        this.add(ic_film,c);
        //Année
        c.gridx=2;
        c.gridy=4;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(lbl_annee,c);
        //Note
        c.gridx=2;
        c.gridy=5;
        this.add(lbl_note,c);
        //Participants
        participants(c);
        //Genres
        c.gridx=0;
        c.gridy=7;
        c.gridwidth = 3;
        this.add(lbl_genres,c);
        //Synopsis
        c.gridy=8;
        c.gridheight = 2;
        c.weightx = 0;
        JScrollPane jp = new JScrollPane(lbl_synopsis);
        this.add(jp,c);
    }

    public void prec_suiv_num(GridBagConstraints c){
        JPanel ensemble = new JPanel();
        ensemble.setBackground(Color.GRAY);
        ensemble.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(2,2,2,2);
        c2.fill = GridBagConstraints.HORIZONTAL;
        //Préédent
        c2.gridx = 0;
        c2.gridy = 2;
        c2.weightx = 0;
        ensemble.add(prec,c2);
        // Champ numéro
        c2.weightx = 80;
        c2.gridx = 1;
        ensemble.add(num,c2);
        //Suivant
        c2.weightx = 0;
        c2.gridx = 2;
        ensemble.add(suiv,c2);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.weightx = 100;
        this.add(ensemble,c);
    }

    public void participants(GridBagConstraints c){
        participants = new JPanel();
        participants.setLayout(new GridBagLayout());
        participants.add(lbl_participant);
        c.gridx = 2;
        c.gridy = 6;
        this.add(participants,c);
    }

    public void affichageParticipants(){

        ArrayList<Participant> part = modele.getParticipants();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        part.forEach(x ->{
            JButton temp = new JButton(x.getPeo_name() + " : " + x.getRol_name());
            participants.add(temp,c);
            c.gridy++;
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        lbl_titre.setText(modele.getFilm().getTitle());
        lbl_note.setText(String.valueOf(modele.getFilm().getNote()));
        lbl_annee.setText(String.valueOf(modele.getFilm().getAnnee()));
        lbl_genres.setText(modele.getFilm().getGenres());
        lbl_synopsis.setText(modele.getFilm().getSynopsis());
        num.setText(String.valueOf(modele.getFilm().getMov_id()));
        affichageParticipants();
        try {
            URL url = new URL(modele.getFilm().getPoster());
            BufferedImage image = ImageIO.read(url);
            ic_film.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

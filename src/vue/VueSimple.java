package vue;

import controlleur.ControlleurAffichageFilm;
import modele.Film;
import modele.InstanceFilm;
import modele.Participant;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Vue simple.
 */
public class VueSimple extends JPanel{

    public JLabel lbl_titre;
    public JLabel ic_film;
    public JLabel lbl_annee;
    public JLabel lbl_note;
    public JLabel lbl_participant;
    public ArrayList<JButton> boutons_participants;
    public JLabel lbl_genres;
    public JTextArea lbl_synopsis;
    public JPanel participants;
    public JButton choisir;
    public JButton prefere;
    public InstanceFilm inst;
    public Film mod;
    public ControlleurAffichageFilm cont;
    public boolean bouton;

    /**
     * Instantiates a new Vue simple.
     *
     * @param cont   the cont
     * @param mod    the mod
     * @param film   the film
     * @param bouton the bouton
     */
    public VueSimple(ControlleurAffichageFilm cont, Film mod, InstanceFilm film, Boolean bouton){
        this.bouton = bouton;
        this.inst = film;
        this.mod = mod;
        this.cont = cont;
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());
        initialiserComposants();
        positionnementFilm();
        this.setBorder(new EmptyBorder(10,10,10,10));
    }

    /**
     * Initialiser composants.
     */
    public void initialiserComposants(){
        lbl_titre = new JLabel(inst.getTitle(),SwingConstants.CENTER);
        lbl_titre.setOpaque(true);
        lbl_annee = new JLabel(String.valueOf(inst.getAnnee()),SwingConstants.CENTER);
        lbl_annee.setOpaque(true);
        lbl_note = new JLabel(String.valueOf(inst.getNote()),SwingConstants.CENTER);
        lbl_note.setOpaque(true);
        lbl_participant = new JLabel("Participants",SwingConstants.CENTER);
        lbl_genres = new JLabel(inst.getGenres(),SwingConstants.CENTER);
        lbl_genres.setOpaque(true);
        lbl_synopsis = new JTextArea(5,5);
        lbl_synopsis.setText(inst.getSynopsis());
        lbl_synopsis.setLineWrap(true);
        lbl_synopsis.setWrapStyleWord(true);
        lbl_synopsis.setEditable(false);
        lbl_synopsis.setPreferredSize(new Dimension(this.getWidth(),100));
        try {
            URL url = new URL(inst.getPoster());
            BufferedImage image = ImageIO.read(url);
            ic_film = new JLabel(new ImageIcon(image.getScaledInstance(200,298, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            ic_film = new JLabel(new ImageIcon(new ImageIcon("img/missing.png").getImage().getScaledInstance(200,298,Image.SCALE_SMOOTH)));
        }
        if (bouton) {
            prefere = new JButton("Je préfère ce film");
            prefere.setOpaque(true);
            prefere.setBackground(Color.BLACK);
            prefere.setForeground(Color.WHITE);
            prefere.addActionListener(cont);
            prefere.setActionCommand("p_" + String.valueOf(inst.getMov_id()));
        }
        choisir = new JButton("Je choisis ce film");
        choisir.setOpaque(true);
        choisir.setBackground(Color.BLACK);
        choisir.setForeground(Color.WHITE);
        choisir.addActionListener(cont);
        choisir.setActionCommand("c_"+String.valueOf(inst.getMov_id()));
    }

    /**
     * Positionnement film.
     */
    public void positionnementFilm(){
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2,2,2,2);
        c.gridwidth=2;
        c.fill = GridBagConstraints.HORIZONTAL;
        //Titre
        this.add(lbl_titre,c);
        //Image
        c.gridx= 0;
        c.gridy= 1;
        this.add(ic_film,c);
        //Participants
        participants(c);
        affichageParticipants();
        //Année
        c.weightx=0;
        c.weighty=0;
        c.gridx=0;
        c.gridy=3;
        this.add(lbl_annee,c);
        //Note
        c.gridx=0;
        c.gridy=4;
        this.add(lbl_note,c);
        //Genres
        c.weighty=0;
        c.gridx=0;
        c.gridy=5;
        this.add(lbl_genres,c);
        //Synopsis
        c.gridy=6;
        c.weighty=25;
        JScrollPane jp = new JScrollPane(lbl_synopsis);
        this.add(jp,c);
        c.weighty=0;
        c.gridwidth=1;
        c.gridy=7;
        this.add(choisir,c);
        if (bouton){
            c.gridx++;
            this.add(prefere,c);
        }
    }

    /**
     * Participants.
     *
     * @param c the c
     */
    public void participants(GridBagConstraints c){
        participants = new JPanel();
        participants.setLayout(new GridBagLayout());
        participants.add(lbl_participant);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth=2;
        c.weightx = 100;
        c.weighty=0;
        JScrollPane temp = new JScrollPane(participants);
        temp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        temp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        temp.setPreferredSize(new Dimension(this.getWidth(),200));
        this.add(temp,c);
    }

    /**
     * Affichage participants.
     */
    public void affichageParticipants(){
        boutons_participants = new ArrayList<>();
        ArrayList<Participant> part = mod.getSpecialParticipants(inst.getMov_id());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 100;
        part.forEach(x ->{
            JButton temp = new JButton(x.getPeo_name() + " : " + x.getRol_name());
            temp.setActionCommand("0"+x.getPeo_name());
            boutons_participants.add(temp);
            participants.add(temp,c);
            c.gridy++;
        });
    }
}

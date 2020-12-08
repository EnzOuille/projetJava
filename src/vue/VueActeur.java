package vue;

import modele.Film;
import modele.InstanceFilmActeur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The type Vue acteur.
 */
public class VueActeur extends JPanel implements Observer {

    private Film modele;
    private JLabel lbl_personne;
    public ArrayList<JButton> boutons_films = new ArrayList<>();
    private VuePrincipale vue;

    /**
     * Crées une nouvelle vue
     *
     * @param mod le modèle
     * @param vue la vuePrincipale
     */
    public VueActeur(Film mod, VuePrincipale vue){
        this.vue = vue;
        modele = mod;
        modele.addObserver(this);
        this.setBackground(Color.GRAY);
        this.setBorder(new EmptyBorder(10,10,10,10));
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    /**
     * Initialise tout les composants et leur valeurs textuelles et leur position
     * @param command the command
     */
    public void initialiserComposants(String command){
        this.boutons_films = null;
        JPanel ensemble = new JPanel();
        this.boutons_films = new ArrayList<>();
        ensemble.setLayout(new BoxLayout(ensemble,BoxLayout.X_AXIS));
        ensemble.setBackground(Color.GRAY );
        lbl_personne = new JLabel(command);
        lbl_personne.setBackground(Color.WHITE);
        lbl_personne.setOpaque(true);
        lbl_personne.setHorizontalAlignment(JLabel.CENTER);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100;
        this.add(lbl_personne,c);
        ArrayList<InstanceFilmActeur> all = modele.getFilmActeur(command);
        all.forEach(x -> {
            JPanel temp = new JPanel();
            temp.setLayout(new BoxLayout(temp,BoxLayout.Y_AXIS));
            JLabel temp_poster = null;
            try {
                URL url = new URL(x.getPoster());
                BufferedImage image = ImageIO.read(url);
                temp_poster = new JLabel(new ImageIcon(image.getScaledInstance(200,298, Image.SCALE_SMOOTH)));
            } catch (IOException e) {
                temp_poster = new JLabel(new ImageIcon(new ImageIcon("img/missing.png").getImage().getScaledInstance(200,298,Image.SCALE_SMOOTH)));
            }
            temp.setBorder(new EmptyBorder(0,10,0,0));
            temp_poster.setBorder(new EmptyBorder(0,0,10,0));
            JButton temp_title = new JButton(x.getTitle());
            temp_title.setHorizontalAlignment(JButton.CENTER);
            temp_title.setHorizontalAlignment(JLabel.CENTER);
            temp_title.setActionCommand("1_" + String.valueOf(x.getMov_id()));
            boutons_films.add(temp_title);
            JLabel temp_annee = new JLabel(String.valueOf(x.getAnnee()));
            temp_annee.setBorder(new EmptyBorder(10,0,10,0));
            temp_annee.setHorizontalAlignment(JLabel.CENTER);
            JLabel temp_role = new JLabel(x.getRole());
            temp_role.setBorder(new EmptyBorder(0,0,0,0));
            temp_role.setHorizontalAlignment(JLabel.CENTER);
            //Ajout au GridBagLayout
            temp_title.setMaximumSize(new Dimension(200, (int) temp_title.getPreferredSize().getHeight()));
            temp_annee.setMaximumSize(new Dimension(200, (int) temp_annee.getPreferredSize().getHeight()));
            temp_role.setMaximumSize(new Dimension(200, (int) temp_role.getPreferredSize().getHeight()));
            temp.add(temp_poster);
            temp.add(temp_title);
            temp.add(temp_annee);
            temp.add(temp_role);
            ensemble.add(temp);
            temp.setBackground(Color.GRAY);
        });
        boutons_films.forEach(x -> {
            x.addActionListener(vue.getCont());
        });
        JScrollPane jpane = new JScrollPane(ensemble);
        jpane.setPreferredSize(new Dimension());
        jpane.setBackground(Color.GRAY);
        jpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        c.gridy=1;
        c.weighty=70;
        this.add(jpane,c);
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

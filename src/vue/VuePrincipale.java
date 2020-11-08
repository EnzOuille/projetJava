package vue;

import modele.Film;
import vue.VueAffichage;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class VuePrincipale extends JFrame {

    public VuePrincipale(Film modele) throws UnsupportedLookAndFeelException {
        super("--- MovieLens ---");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        this.setSize(500,700);
        this.setIconImage(new ImageIcon("img/zero_two.png").getImage());
        VueAffichage vAffi = new VueAffichage(modele);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(vAffi);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

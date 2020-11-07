import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class VuePrincipale extends JFrame {

    public VuePrincipale() throws UnsupportedLookAndFeelException {
        super("--- MovieLens ---");
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        this.setSize(500,700);
        this.setIconImage(new ImageIcon("img/zero_two.png").getImage());
        this.setVisible(true);
        this.setContentPane(new VueAffichage());
    }
}

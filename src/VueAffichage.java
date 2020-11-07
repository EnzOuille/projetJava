import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VueAffichage extends JPanel {

    private JLabel lbl_titre;
    private JLabel ic_film;
    private JLabel lbl_annee;
    private JLabel lbl_note;
    private JLabel lbl_participant;
    private JLabel[] lbl_participants;
    private JLabel lbl_genres;
    private JLabel lbl_synopsis;
    private JPanel participants;

    public VueAffichage(){
        initialiserComposants();
        this.setBackground(Color.GRAY);
        this.setLayout(new BorderLayout());
        this.positionnementFilm();
        this.setBorder(new EmptyBorder(10,10,10,10));
    }

    public void initialiserComposants(){
        lbl_titre = new JLabel("Titre du film");
        lbl_annee = new JLabel("Année de production");
        lbl_note = new JLabel("Note");
        lbl_participant = new JLabel("Participants");
        lbl_genres = new JLabel("Participants");
        lbl_synopsis = new JLabel("Synopsis");
        ic_film = new JLabel(new ImageIcon("img/zero_two.png"));
    }

    public void positionnementFilm(){
        lbl_titre.setBounds(0,0,this.getWidth(),30);
        lbl_titre.setOpaque(true);
        lbl_titre.setBackground(Color.BLUE);
        lbl_titre.setHorizontalAlignment(SwingConstants.CENTER);
        ic_film.setPreferredSize(new Dimension((int)Math.round(this.getWidth()*0.7 - 5), (int)Math.round(this.getHeight()*0.5)));
        ic_film.setBounds(0,35, (int)Math.round(this.getWidth()*0.7 - 5), (int)Math.round(this.getHeight()*0.5));
        ic_film.setOpaque(true);
        ic_film.setBackground(Color.YELLOW);

        this.add(lbl_titre);
        this.add(ic_film);
    }
}

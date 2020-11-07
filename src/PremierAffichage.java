import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class PremierAffichage {

    private JLabel lbl_titre;
    private JLabel ic_film;
    private JLabel lbl_annee;
    private JLabel lbl_note;
    private JLabel lbl_participant;
    private JLabel[] lbl_participants;
    private JLabel lbl_genres;
    private JLabel lbl_synopsis;

    public PremierAffichage(){
        JFrame MainFrame = new JFrame("--- Affichage 1 ---");
        ImageIcon icone = new ImageIcon("img/zero_two.png");
        MainFrame.setIconImage(icone.getImage());
        MainFrame.setSize(500,700);
        affichageFilm(MainFrame);
        MainFrame.setVisible(true);
    }

    public void affichageFilm(JFrame frame){
        JPanel affichageFilm = new JPanel();
        affichageFilm.setBackground(Color.GRAY);
        affichageFilm.setLayout(new BorderLayout());
        initFilm();
        positionnementFilm(affichageFilm);
        JPanel test = new JPanel();
        test.setBackground(Color.cyan);
        test.setPreferredSize(new Dimension(480,680));
        affichageFilm.setBorder(new EmptyBorder(10,10,10,10));
        test.add(lbl_annee);
        affichageFilm.add(test,BorderLayout.CENTER);
        frame.setContentPane(affichageFilm);

    }

    public void initFilm(){
        lbl_titre = new JLabel("Titre du film");
        lbl_annee = new JLabel("Année de production");
        lbl_note = new JLabel("Note");
        lbl_participant = new JLabel("Participants");
        lbl_genres = new JLabel("Participants");
        lbl_synopsis = new JLabel("Synopsis");
        ic_film = new JLabel(new ImageIcon("img/zero_two.png"));
    }

    public void positionnementFilm(JPanel panel){

    }
}

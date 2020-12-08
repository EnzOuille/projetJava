package vue;

import controlleur.ControlleurAffichageFilm;
import modele.InstanceFilm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The type Vue choix.
 */
public class VueChoix extends JPanel {

    /**
     * Instantiates a new Vue choix.
     *
     * @param inst the inst
     * @param cont the cont
     */
    public VueChoix(ArrayList<InstanceFilm> inst, ControlleurAffichageFilm cont){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        inst.forEach(x -> {
            JButton temp = new JButton(x.getMov_id() + " : " + x.getTitle());
            temp.setActionCommand("recherche_"+String.valueOf(x.getMov_id()));
            temp.addActionListener(cont);
            temp.setMinimumSize(new Dimension(this.getWidth(), (int) temp.getPreferredSize().getHeight()));
            this.add(temp);
        });
    }

}

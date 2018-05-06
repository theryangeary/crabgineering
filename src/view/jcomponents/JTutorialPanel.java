package view.jcomponents;

import controller.requests.RequestQueue;
import view.estuaryenums.EstuaryFont;
import view.estuaryenums.EstuaryImage;

import javax.swing.*;
import java.awt.*;

public class JTutorialPanel extends JPanel {

    private Font font;

    public JTutorialPanel(RequestQueue requestQueue){
        font = new Font(EstuaryFont.MINECRAFT.name(), Font.PLAIN, 12);

        JLabel intro = new JLabel("The estuary needs you help! Place the");
        JLabel recyclingText = new JLabel("recyclables");
        JLabel recyclingImage = new JLabel(new ImageIcon(EstuaryImage.RECYCLING.getImage()));
        JLabel trash = new JLabel("");
    }
}

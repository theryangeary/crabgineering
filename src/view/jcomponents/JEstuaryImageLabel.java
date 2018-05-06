package view.jcomponents;

import view.estuaryenums.EstuaryImage;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for displaying and scaling EstuaryImages in swing components
 */
public class JEstuaryImageLabel extends JLabel {

    /**
     * Creates a JLabel with an ImageIcon that scales to fit the label's size
     * @param image The EstuaryImage that should be displayed in the label
     */
    public JEstuaryImageLabel (EstuaryImage image){
        final JLabel parent = this;

        ImageIcon imageIcon = new ImageIcon(image.getImage()){
            @Override
            public void paintIcon(Component component, Graphics g, int x, int y){
                g.drawImage(getImage(),
                        0,
                        0,
                        parent.getWidth(),
                        parent.getHeight(),
                        null);
            }
        };

        setIcon(imageIcon);
    }
}

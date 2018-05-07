package view.jcomponents;

import controller.requests.RequestQueue;
import org.omg.CORBA.Request;
import view.View;
import view.estuaryenums.EstuaryFont;
import view.estuaryenums.EstuaryImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Displays a quick tutorial at the start of the game
 */
public class JTutorialPanel extends JPanel {

    private Font font;
    private static final Color SEA_BLUE = new Color(0x3399ff);

    /**
     * Sets up the quick tutorial to be displayed
     * @param requestQueue
     */
    public JTutorialPanel(ActionListener onButtonPress){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(SEA_BLUE);

        font = new Font(EstuaryFont.MINECRAFT.name(), Font.PLAIN, 32);

        JLabel intro = new JLabel("The estuary needs your help!\n Place");
        intro.setFont(font);
        add(intro);

        JPanel trashTable = createTrashTable();
        add(trashTable);

        JLabel mid = new JLabel("into the");
        mid.setFont(font);
        add(mid);

        JPanel bargeTable = createBargeTable();
        add(bargeTable);

        JButton okayButton = new JButton("okay");
        okayButton.addActionListener(onButtonPress);
        add(okayButton);
    }

    /**
     * Layout out all the different types of trash and recycling
     * @return a JPanel containing the trash images and labels
     */
    private JPanel createTrashTable(){
        JPanel trashTable = new JPanel(new GridBagLayout());
        trashTable.setBackground(SEA_BLUE);

        //set up the constraints for the recyclables column
        GridBagConstraints recyclableConstraints = new GridBagConstraints();
        recyclableConstraints.weighty = 1;
        recyclableConstraints.weightx = 1;
        recyclableConstraints.gridx = 0;
        //trashConstraints.fill = GridBagConstraints.HORIZONTAL;

        //create label for the column of recyclables
        JLabel recyclingHeader = new JLabel("recyclables", JLabel.CENTER);
        recyclingHeader.setFont(font);
        recyclableConstraints.gridy = 0;
        trashTable.add(recyclingHeader, recyclableConstraints);

        //add all the recyclable images
        recyclableConstraints.gridy = 1;
        trashTable.add(new JEstuaryImageLabel(EstuaryImage.SODA_CAN), recyclableConstraints);
        recyclableConstraints.gridy = 2;
        trashTable.add(new JEstuaryImageLabel(EstuaryImage.MILK_JUG), recyclableConstraints);

        //set up the constraints for the trash column
        GridBagConstraints trashConstraints = new GridBagConstraints();
        trashConstraints.weighty = 1;
        trashConstraints.weightx = 1;
        trashConstraints.gridx = 1;
        //trashConstraints.fill = GridBagConstraints.HORIZONTAL;

        //create label for the column of trash
        JLabel trashHeader = new JLabel("trash", JLabel.CENTER);
        trashHeader.setFont(font);
        trashConstraints.gridy = 0;
        trashTable.add(trashHeader, trashConstraints);

        //add all the trash images
        trashConstraints.gridy = 1;
        trashTable.add(new JEstuaryImageLabel(EstuaryImage.SNACK_BAG), trashConstraints);
        trashConstraints.gridy = 2;
        trashTable.add(new JEstuaryImageLabel(EstuaryImage.STYROFOAM_CUP), trashConstraints);

        return trashTable;
    }

    /**
     * lays out the different barges
     * @return a JPanel containing the barge images and labels
     */
    private JPanel createBargeTable(){
        JPanel bargeTable = new JPanel(new GridBagLayout());
        bargeTable.setBackground(SEA_BLUE);

        //set up the constraints for the recyclables column
        GridBagConstraints recyclableConstraints = new GridBagConstraints();
        recyclableConstraints.weighty = 1;
        recyclableConstraints.weightx = 1;
        recyclableConstraints.gridx = 0;
        //trashConstraints.fill = GridBagConstraints.HORIZONTAL;

        //create label for the column of recyclables
        JLabel recyclingLabel = new JLabel("recycling barge", JLabel.CENTER);
        recyclingLabel.setFont(font);
        recyclableConstraints.gridy = 0;
        bargeTable.add(recyclingLabel, recyclableConstraints);

        //add the barge image
        recyclableConstraints.gridy = 1;
        bargeTable.add(new JEstuaryImageLabel(EstuaryImage.RECYCLING_BARGE), recyclableConstraints);

        //set up the constraints for the trash column
        GridBagConstraints trashConstraints = new GridBagConstraints();
        trashConstraints.weighty = 1;
        trashConstraints.weightx = 1;
        trashConstraints.gridx = 1;
        //trashConstraints.fill = GridBagConstraints.HORIZONTAL;

        //create label for the column of trash
        JLabel trashLabel = new JLabel("trash barge", JLabel.CENTER);
        trashLabel.setFont(font);
        trashConstraints.gridy = 0;
        bargeTable.add(trashLabel, trashConstraints);

        //add the barge image
        trashConstraints.gridy = 1;
        bargeTable.add(new JEstuaryImageLabel(EstuaryImage.TRASH_BARGE), trashConstraints);

        return bargeTable;
    }
}

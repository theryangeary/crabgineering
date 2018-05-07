package view.jcomponents;

import controller.requests.RequestQueue;
import view.estuaryenums.EstuaryFont;
import view.estuaryenums.EstuaryImage;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a quick tutorial at the start of the game
 */
public class JTutorialPanel extends JPanel {

    private Font font;

    /**
     * Sets up the quick tutorial to be displayed
     * @param requestQueue
     */
    public JTutorialPanel(RequestQueue requestQueue){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        font = new Font(EstuaryFont.MINECRAFT.name(), Font.PLAIN, 32);

        JLabel intro = new JLabel("The estuary needs your help! Place");
        intro.setFont(font);
        add(intro);

        JPanel trashTable = createTrashTable();
        add(trashTable);

        JLabel mid = new JLabel("into the", JLabel.CENTER);
        mid.setFont(font);
        add(mid);

        JPanel bargeTable = createBargeTable();
        add(bargeTable);

        JButton okayButton = new JButton("okay");
        add(okayButton);
    }

    private JPanel createTrashTable(){
        JPanel trashTable = new JPanel(new GridBagLayout());

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

    private JPanel createBargeTable(){
        JPanel bargeTable = new JPanel(new GridBagLayout());

        //set up the constraints for the recyclables column
        GridBagConstraints recyclableConstraints = new GridBagConstraints();
        recyclableConstraints.weighty = 1;
        recyclableConstraints.weightx = 1;
        recyclableConstraints.gridx = 0;
        //trashConstraints.fill = GridBagConstraints.HORIZONTAL;

        //create label for the column of recyclables
        JLabel recyclingLabel = new JLabel("recycling barge", JLabel.CENTER);
        recyclingLabel.setFont(font);
        bargeTable.add(recyclingLabel);

        //create label for the column of trash
        JLabel trashLabel = new JLabel("trash barge", JLabel.CENTER);
        trashLabel.setFont(font);
        bargeTable.add(trashLabel);

        //add all the images
        bargeTable.add(new JEstuaryImageLabel(EstuaryImage.RECYCLING_BARGE));
        bargeTable.add(new JEstuaryImageLabel(EstuaryImage.TRASH_BARGE));

        return bargeTable;
    }
}

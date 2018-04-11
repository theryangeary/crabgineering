import javax.swing.JPanel;                                                                     
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;

public class View extends JPanel {
  // define size of game
  final static int frameWidth = 1000;
  final static int frameHeight = 1000;

  private JFrame frame;

  public View() {
	initJFrame();

  }

  private void initJFrame() {
	frame = new JFrame();
	frame.getContentPane().add(this);
	frame.getContentPane().setBackground(Color.blue);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(frameWidth, frameHeight);
	frame.setVisible(true);
  }
}

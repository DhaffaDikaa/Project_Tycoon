import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {

        setTitle("Resto Tycoon");
        setSize(1280,720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // BACKGROUND
        ImageIcon bg = new ImageIcon(getClass().getResource("/assets/openingmenu.png"));
        JLabel background = new JLabel(bg);

        background.setLayout(new GridBagLayout());
        setContentPane(background);

        GridBagConstraints gbc = new GridBagConstraints();


        // ICON START
        ImageIcon startIcon = new ImageIcon(getClass().getResource("/assets/startButton.png"));
        Image img = startIcon.getImage();
        Image resize = img.getScaledInstance(350, 120, Image.SCALE_SMOOTH);

        ImageIcon newIcon = new ImageIcon(resize);
        JButton startButton = new JButton(newIcon);

        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);

        gbc.insets = new Insets(200,0,10,0);
        gbc.gridy = 0;
        background.add(startButton, gbc);

        startButton.addActionListener(e -> {
            new PersiapanGUI();
            dispose();
        });


        // SAVE
        ImageIcon saveIcon = new ImageIcon(getClass().getResource("/assets/saveButton.png"));
        Image img2 = saveIcon.getImage();
        Image resize2 = img2.getScaledInstance(350, 120, Image.SCALE_SMOOTH);

        ImageIcon newIcon2 = new ImageIcon(resize2);
        JButton saveButton = new JButton(newIcon2);

        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);

        gbc.insets = new Insets(10,0,10,0);
        gbc.gridy = 1;
        background.add(saveButton, gbc);

        // EXIT START
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/assets/exitButton.png"));
        Image img3 = exitIcon.getImage();
        Image resize3 = img3.getScaledInstance(350, 120, Image.SCALE_SMOOTH);

        ImageIcon newIcon3 = new ImageIcon(resize3);
        JButton exitButton = new JButton(newIcon3);

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);

        gbc.gridy = 2;
        background.add(exitButton, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        new PersiapanGUI();
        //new PersiapanGUI();
    }
}
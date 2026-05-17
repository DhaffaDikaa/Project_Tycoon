import javax.swing.*;
import java.awt.*;

public class PersiapanGUI extends JFrame {

    public PersiapanGUI() {

        setTitle("Resto Tycoon");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //BACKGROUND
        ImageIcon bg = new ImageIcon(getClass().getResource("/assets/mainmalam.png"));
        JLabel background = new JLabel(bg);
        background.setLayout(null);
        setContentPane(background);


        //Persiapan Border
        ImageIcon persiapan = new ImageIcon(getClass().getResource("/assets/persiapan.png"));
        Image img4 = persiapan.getImage();

        Image resize4 = img4.getScaledInstance(220,80, Image.SCALE_SMOOTH);
        ImageIcon icon4 = new ImageIcon(resize4);
        JLabel persiapanBorder = new JLabel(icon4);
        persiapanBorder.setBounds(630,20,220,80);

        background.add(persiapanBorder);

        Restoran r = new Restoran();
        HUDPanel hud = new HUDPanel(r);
        hud.setBounds(0,0,1600,100);
        background.add(hud);

        //Restoran
        JButton restoran = new JButton();
        restoran.setBounds(205,500,100,50);
        background.add(restoran);

        restoran.addActionListener(e ->{
            new MenuGUI();
            dispose();
        });

        //Sembako
        JButton sembako = new JButton();
        sembako.setBounds(840,490,100,50);
        background.add(sembako);

        sembako.addActionListener(e -> {
            new SembakoGUI();
            dispose();
        });

        //Jimat
        JButton jimat = new JButton();
        jimat.setBounds(1165,490,100,50);

        jimat.addActionListener(e -> {
            new JimatGUI();
            dispose();
        });

        background.add(jimat);

        setVisible(true);
    }
}
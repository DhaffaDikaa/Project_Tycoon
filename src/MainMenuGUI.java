
import java.awt.*;
import javax.swing.*;

public class MainMenuGUI extends JFrame {

    private Restoran restoran;

    public MainMenuGUI(Restoran restoran) {
        this.restoran = restoran;

        setTitle("Game Presto - Main Menu");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("SELAMAT DATANG DI GAME PRESTO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32)); 
        lblTitle.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15)); 

        JButton btnPersiapan = new JButton("PERSIAPAN");
        JButton btnBukaRestoran = new JButton("BUKA RESTORAN");
        JButton btnTutorial = new JButton("TUTORIAL BERMAIN");
        JButton btnSaveExit = new JButton("SAVE & EXIT");

        Dimension btnSize = new Dimension(300, 60);
        btnPersiapan.setPreferredSize(btnSize);
        btnBukaRestoran.setPreferredSize(btnSize);
        btnSaveExit.setPreferredSize(btnSize);
        btnTutorial.setPreferredSize(btnSize);

        buttonPanel.add(btnPersiapan);
        buttonPanel.add(btnBukaRestoran);
        buttonPanel.add(btnSaveExit);
        buttonPanel.add(btnTutorial);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonPanel);
        add(centerWrapper, BorderLayout.CENTER);



        btnBukaRestoran.addActionListener(e -> {

            new BukaRestoranGUI(restoran).setVisible(true);

           
        });
        btnSaveExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Simpan dan keluar dari permainan?", "Pengesahan", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                GameGenerateGUI.simpanGame(restoran);

                JOptionPane.showMessageDialog(this, "Permainan Berjaya Disimpan!");
                System.exit(0);
            }
        });

        btnPersiapan.addActionListener(e -> {
          
            new MenuPersiapanGUI(restoran).setVisible(true);

    
        });
        btnTutorial.addActionListener(e -> {
          
            new TutorialGUI(restoran).setVisible(true);
        });
    }

}

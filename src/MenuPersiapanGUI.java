
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MenuPersiapanGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;

    public MenuPersiapanGUI(Restoran restoran) {
        this.restoran = restoran;

        setTitle("Game Presto - Fase Persiapan");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainPanel = new BackgroundPanel("image/FasePersiapan.png");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(245, 245, 245, 220)); 

        JLabel lblTitle = new JLabel("FASE PERSIAPAN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblLevel = new JLabel("", SwingConstants.CENTER);
        lblLevel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLevel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblUang = new JLabel("", SwingConstants.CENTER);
        lblUang.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUang.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblKapasitas = new JLabel("", SwingConstants.CENTER);
        lblKapasitas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblKapasitas.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        topPanel.add(lblTitle);
        topPanel.add(lblLevel);
        topPanel.add(lblUang);
        topPanel.add(lblKapasitas);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomContainer = new JPanel();
        bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.Y_AXIS));
        bottomContainer.setOpaque(false);
        bottomContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10)); 

        JPanel buttonRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonRowPanel.setOpaque(false);

        JButton btnPasar = new JButton("KE PASAR");
        JButton btnDapur = new JButton("KE DAPUR");
        JButton btnJimat = new JButton("TOKO JIMAT");
        JButton btnGudang = new JButton("KE GUDANG");
        
        Dimension btnNavSize = new Dimension(190, 50);
        styleMenuButton(btnPasar, new Color(230, 126, 34), btnNavSize);
        styleMenuButton(btnDapur, new Color(231, 76, 60), btnNavSize);
        styleMenuButton(btnJimat, new Color(52, 73, 94), btnNavSize);
        styleMenuButton(btnGudang, new Color(155, 89, 182), btnNavSize);

        buttonRowPanel.add(btnPasar);
        buttonRowPanel.add(btnDapur);
        buttonRowPanel.add(btnJimat);
        buttonRowPanel.add(btnGudang);
 

        JPanel backRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        backRowPanel.setOpaque(false);

        JButton btnKembali = new JButton("KEMBALI KE MAIN MENU");
        styleMenuButton(btnKembali, new Color(127, 140, 141), new Dimension(350, 45));

        backRowPanel.add(btnKembali);

        bottomContainer.add(buttonRowPanel);
        bottomContainer.add(backRowPanel);

        mainPanel.add(bottomContainer, BorderLayout.SOUTH);

        btnKembali.addActionListener(e -> {
            new MainMenuGUI(restoran).setVisible(true);
            this.dispose();
        });

        btnJimat.addActionListener(e -> {
            TokoJimatGUI guiJimat = new TokoJimatGUI(restoran);
            guiJimat.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    updateStatusBar();
                }
            });
            guiJimat.setVisible(true);
        });

        btnPasar.addActionListener(e -> {
            PasarGUI guiPasar = new PasarGUI(restoran);
            guiPasar.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    updateStatusBar();
                }
            });
            guiPasar.setVisible(true);
        });

        btnDapur.addActionListener(e -> {
            DapurGUI guiDapur = new DapurGUI(restoran);
            guiDapur.setVisible(true);
        });

        btnGudang.addActionListener(e -> {
            GudangGUI guiGudang = new GudangGUI(restoran);
            guiGudang.setVisible(true);
        });

        updateStatusBar();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }


    private void styleMenuButton(JButton btn, Color bgColor, Dimension size) {
        btn.setPreferredSize(size);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15)); 
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2));
    }

   
    class BackgroundPanel extends JPanel {

        private Image imgBackground;

        public BackgroundPanel(String path) {
            this.imgBackground = new ImageIcon(path).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imgBackground != null) {
                g.drawImage(imgBackground, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}

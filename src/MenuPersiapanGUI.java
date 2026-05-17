
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
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("FASE PERSIAPAN", SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblLevel = new JLabel("", SwingConstants.CENTER);
        lblLevel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblUang = new JLabel("", SwingConstants.CENTER);
        lblUang.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        lblKapasitas = new JLabel("", SwingConstants.CENTER);
        lblKapasitas.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        topPanel.add(lblTitle);
        topPanel.add(lblLevel);
        topPanel.add(lblUang);
        topPanel.add(lblKapasitas);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);

        JButton btnPasar = new JButton("TOMBOL KE PASAR");
        JButton btnDapur = new JButton("TOMBOL KE DAPUR");
        JButton btnGudang = new JButton("TOMBOL KE GUDANG");
        JButton btnJimat = new JButton("TOMBOL KE TOKO JIMAT");
        JButton btnKembali = new JButton("KEMBALI KE MAIN MENU");

        btnPasar.setBounds(80, 350, 180, 40);    
        btnDapur.setBounds(350, 300, 180, 40);   
        btnGudang.setBounds(650, 180, 180, 40);  
        btnJimat.setBounds(650, 380, 180, 40);   
        btnKembali.setBounds(20, 20, 180, 30);  

        centerPanel.add(btnPasar);
        centerPanel.add(btnDapur);
        centerPanel.add(btnGudang);
        centerPanel.add(btnJimat);
        centerPanel.add(btnKembali);

        add(centerPanel, BorderLayout.CENTER);

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

            guiPasar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
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

    
    
}

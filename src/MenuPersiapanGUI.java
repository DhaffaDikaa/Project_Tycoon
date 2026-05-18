
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MenuPersiapanGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;

    public MenuPersiapanGUI(Restoran restoran) {
        this.restoran = restoran;

        // Pengaturan dasar Frame
        setTitle("Game Presto - Fase Persiapan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        // --- 1. PANEL ATAS (Status Bar) ---
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

        //PANEL TENGAH (Peta Kota & Tombol)
        //Bg
        ImageIcon bg = new ImageIcon(getClass().getResource("/asset/bg3.png"));
        Image background = bg.getImage();

        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // gambar full panel
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        centerPanel.setLayout(null);

        ImageIcon tranparan = new ImageIcon(getClass().getResource("/asset/tranparan.png"));

        // Membuat tombol-tombol
        JButton btnPasar = new JButton(tranparan);
        JButton btnDapur = new JButton(tranparan);
        JButton btnGudang = new JButton(tranparan);
        JButton btnJimat = new JButton(tranparan);
        JButton btnKembali = new JButton(tranparan);

        // Mengatur posisi (X, Y) dan ukuran (Lebar, Tinggi) dari masing-masing tombol
        btnPasar.setBounds(140, 160, 200, 150);
        btnDapur.setBounds(460, 160, 200, 150);
        btnGudang.setBounds(1160, 180, 180, 170);
        btnJimat.setBounds(140, 450, 180, 170);
        btnKembali.setBounds(640, 680, 200, 60);

        //transparan
        JButton[] btn = {btnPasar, btnDapur,btnGudang,btnJimat,btnKembali};
        for(JButton b : btn) {
            b.setBorderPainted(false);
            b.setContentAreaFilled(false);
            b.setFocusPainted(false);
            b.setOpaque(false);
        }


        // Memasukkan tombol ke panel peta
        centerPanel.add(btnPasar);
        centerPanel.add(btnDapur);
        centerPanel.add(btnGudang);
        centerPanel.add(btnJimat);
        centerPanel.add(btnKembali);

        add(centerPanel, BorderLayout.CENTER);

        // --- 3. ACTION LISTENER (Menghubungkan Halaman) ---
        // Tombol kembali ke Main Menu
        btnKembali.addActionListener(e -> {
            new MainMenuGUI(restoran).setVisible(true);
            this.dispose(); // Menutup halaman persiapan
        });

        // Tombol ke Toko Jimat (Menghubungkan ke GUI sebelumnya)
        btnJimat.addActionListener(e -> {
            TokoJimatGUI guiJimat = new TokoJimatGUI(restoran);

            // Trik khusus: Saat jendela Jimat ditutup, otomatis perbarui teks Uang di halaman ini
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
            // Listener ini berguna agar saat kita tutup Pasar, teks uang di Peta langsung terupdate
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
        // Memanggil data pertama kali
        updateStatusBar();
    }

    // Method untuk sinkronisasi teks status dengan data asli restoran
    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }


}




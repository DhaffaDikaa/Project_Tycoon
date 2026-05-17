
import java.awt.*;
import javax.swing.*;

public class MainMenuGUI extends JFrame {

    private Restoran restoran;
    private Image backgroundImage;

    private void fixMacTransparency() {
        System.setProperty("apple.awt.application.appearance", "system");
        UIManager.put("ScrollPane.background", new Color(0, 0, 0, 0));
        UIManager.put("Viewport.background",   new Color(0, 0, 0, 0));
        UIManager.put("ScrollBar.thumb",       new Color(100, 100, 100));
        UIManager.put("ScrollBar.track",       new Color(0, 0, 0, 0));
    }

    public MainMenuGUI(Restoran restoran) {
        this.restoran = restoran;

        //IconBg
        ImageIcon bg = new ImageIcon(getClass().getResource("/asset/openingmenu.png"));
        Image background = bg.getImage();
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        // Pengaturan dasar Window/Frame
        setTitle("Game Presto - Main Menu");
        setSize(800, 600); // Ukuran window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Agar window muncul persis di tengah layar
        setLayout(new BorderLayout());

        // --- BAGIAN ATAS: JUDUL ---
        JLabel lblTitle = new JLabel("SELAMAT DATANG DI GAME PRESTO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32)); // Font sementara
        lblTitle.setBorder(BorderFactory.createEmptyBorder(80, 0, 50, 0)); // Memberi jarak atas dan bawah
        bgPanel.add(lblTitle, BorderLayout.NORTH);

        // --- BAGIAN TENGAH: TOMBOL-TOMBOL ---
        // Membuat panel khusus untuk menampung tombol agar bisa diatur jaraknya
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 15)); // 3 Baris, 1 Kolom, jarak vertikal 25px

        JButton btnPersiapan = new JButton("PERSIAPAN");
        JButton btnBukaRestoran = new JButton("BUKA RESTORAN");
        JButton btnTutorial = new JButton("TUTORIAL BERMAIN");
        JButton btnSaveExit = new JButton("SAVE & EXIT");

        // Mengatur ukuran tombol agar seragam dan cukup besar
        Dimension btnSize = new Dimension(300, 60);
        btnPersiapan.setPreferredSize(btnSize);
        btnBukaRestoran.setPreferredSize(btnSize);
        btnSaveExit.setPreferredSize(btnSize);
        btnTutorial.setPreferredSize(btnSize);

        // Memasukkan tombol ke dalam buttonPanel
        buttonPanel.add(btnPersiapan);
        buttonPanel.add(btnBukaRestoran);
        buttonPanel.add(btnSaveExit);
        buttonPanel.add(btnTutorial);


        // Membuat panel pembungkus (wrapper) menggunakan GridBagLayout 
        // Ini adalah trik rahasia agar buttonPanel posisinya persis di TENGAH layar
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonPanel);
        bgPanel.add(centerWrapper, BorderLayout.CENTER);


        lblTitle.setForeground(Color.WHITE);
        buttonPanel.setOpaque(false);
        centerWrapper.setOpaque(false);

        // --- ACTION LISTENER (Fungsi Tombol) ---


        btnBukaRestoran.addActionListener(e -> {
            // Membuka halaman simulasi restoran
            new BukaRestoranGUI(restoran).setVisible(true);

            // Menutup main menu
            this.dispose();
        });
        btnSaveExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Simpan dan keluar dari permainan?", "Pengesahan", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Memanggil kaedah simpan dari fail GameGenerateGUI
                GameGenerateGUI.simpanGame(restoran);

                JOptionPane.showMessageDialog(this, "Permainan Berjaya Disimpan!");
                System.exit(0);
            }
        });

        btnPersiapan.addActionListener(e -> {
            // Membuka halaman persiapan dan membawa data restoran
            new MenuPersiapanGUI(restoran).setVisible(true);

            // Menutup halaman main menu saat ini
            this.dispose();
        });
        btnTutorial.addActionListener(e -> {
            // Membuka frame tutorial berbasis langkah yang baru saja kita buat
            new TutorialGUI(restoran).setVisible(true);
        });

        //Icon
        ImageIcon iconPersiapan = new ImageIcon(getClass().getResource("/asset/opening/persiapan.png"));
        ImageIcon iconBuka = new ImageIcon(getClass().getResource("/asset/opening/open.png"));
        ImageIcon iconTutorial = new ImageIcon(getClass().getResource("/asset/opening/tutorial.png"));
        ImageIcon iconSave = new ImageIcon(getClass().getResource("/asset/opening/save.png"));

        JButton[] semuaTombol = {
                btnPersiapan,
                btnBukaRestoran,
                btnTutorial,
                btnSaveExit
        };

        for (JButton btn : semuaTombol) {
            btn.setBorderPainted(false);
            //btn.setContentAreaFilled(false);
            //btn.setFocusPainted(false);
            //btn.setOpaque(false);
        }

        Image img = iconPersiapan.getImage();
        Image resize = img.getScaledInstance(300, 60, Image.SCALE_SMOOTH);
        iconPersiapan = new ImageIcon(resize);

        Image img2 = iconBuka.getImage();
        Image resize2 = img2.getScaledInstance(300, 60, Image.SCALE_SMOOTH);
        iconBuka = new ImageIcon(resize2);

        Image img3 = iconTutorial.getImage();
        Image resize3 = img3.getScaledInstance(300, 60, Image.SCALE_SMOOTH);
        iconTutorial = new ImageIcon(resize3);

        Image img4 = iconSave.getImage();
        Image resize4 = img4.getScaledInstance(300, 60, Image.SCALE_SMOOTH);
        iconSave = new ImageIcon(resize4);

        btnPersiapan.setIcon(iconPersiapan);
        btnBukaRestoran.setIcon(iconBuka);
        btnTutorial.setIcon(iconTutorial);
        btnSaveExit.setIcon(iconSave);

        fixMacTransparency();
    }

    // Main Method untuk testing halaman ini
   
}

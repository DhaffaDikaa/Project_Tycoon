
import java.awt.*;
import javax.swing.*;

public class MainMenuGUI extends JFrame {

    private Restoran restoran;

    public MainMenuGUI(Restoran restoran) {
        this.restoran = restoran;

        // Pengaturan dasar Window/Frame
        setTitle("Game Presto - Main Menu");
        setSize(800, 600); // Ukuran window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Agar window muncul persis di tengah layar
        setLayout(new BorderLayout());

        // --- BAGIAN ATAS: JUDUL ---
        JLabel lblTitle = new JLabel("SELAMAT DATANG DI GAME PRESTO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32)); // Font sementara
        lblTitle.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0)); // Memberi jarak atas dan bawah
        add(lblTitle, BorderLayout.NORTH);

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
        add(centerWrapper, BorderLayout.CENTER);

        // --- ACTION LISTENER (Fungsi Tombol) ---


        btnBukaRestoran.addActionListener(e -> {
            // Membuka halaman simulasi restoran
            new BukaRestoranGUI(restoran).setVisible(true);

            // Menutup main menu
           
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
    
        });
        btnTutorial.addActionListener(e -> {
            // Membuka frame tutorial berbasis langkah yang baru saja kita buat
            new TutorialGUI(restoran).setVisible(true);
        });
    }

    // Main Method untuk testing halaman ini
   
}

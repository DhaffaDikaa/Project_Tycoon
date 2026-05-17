
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PasarGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;
    private List<BahanBaku> bahanTersediaSaatIni; // Menyimpan daftar bahan yang sudah unlock

    public PasarGUI(Restoran restoran) {
        this.restoran = restoran;
        this.bahanTersediaSaatIni = new ArrayList<>();

        //Background
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

        // Pengaturan dasar Frame
        setTitle("Game Presto - Pasar (Beli Bahan Baku)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── 1. PANEL ATAS (Status Bar) ─────────────────────────────────────────
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("SIMULASI BUKA RESTORAN", SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblTitle.setForeground(Color.WHITE);

        lblLevel = new JLabel("", SwingConstants.CENTER);
        lblLevel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblLevel.setForeground(Color.WHITE);

        lblUang = new JLabel("", SwingConstants.CENTER);
        lblUang.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblUang.setForeground(Color.WHITE);

        lblKapasitas = new JLabel("", SwingConstants.CENTER);
        lblKapasitas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblKapasitas.setForeground(Color.WHITE);

        topPanel.add(lblTitle);
        topPanel.add(lblLevel);
        topPanel.add(lblUang);
        topPanel.add(lblKapasitas);
        add(topPanel, BorderLayout.NORTH);

        // --- 2. PANEL TENGAH (Ilustrasi & Terminal) ---
        JPanel centerPanel = new JPanel(null);
        centerPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/asset/tokosembako.png"));
        Image img = icon.getImage();
        Image resize = img.getScaledInstance(700, 425, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resize);

        JLabel lblIlustrasi = new JLabel(icon);
        lblIlustrasi.setBounds(10, 15, 475, 425);
        lblIlustrasi.setForeground(Color.WHITE);

        // TERMINAL IMAGE
        ImageIcon iconTerminal = new ImageIcon(getClass().getResource("/asset/terminal.png"));

        Image img2 = iconTerminal.getImage();
        Image resize2 = img2.getScaledInstance(445, 425, Image.SCALE_SMOOTH);

        iconTerminal = new ImageIcon(resize2);

        JLabel terminalBg = new JLabel(iconTerminal);
        terminalBg.setLayout(null);

        terminalBg.setBounds(500, 25, 445, 425);

        // TEXT AREA
        terminalArea = new JTextArea();

        terminalArea.setEditable(false);
        terminalArea.setOpaque(false);

        terminalArea.setForeground(Color.GREEN);

        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 10));

        // posisi isi terminal
        terminalArea.setBounds(40, 60, 350, 300);
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBounds(40, 60, 350, 300);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);

        terminalArea.setOpaque(false);


        terminalBg.add(scrollTerminal);


        centerPanel.add(lblIlustrasi);
        centerPanel.add(terminalBg);

        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (2 Input Box & Tombol) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 80));

        JTextField txtNomorBahan = new JTextField(5);
        JTextField txtJumlahBahan = new JTextField(5);


        ImageIcon iconField1 = new ImageIcon(getClass().getResource("/asset/dapur/kosong.png"));

        Image imgField1 = iconField1.getImage();
        Image resizeField1 = imgField1.getScaledInstance(165, 45, Image.SCALE_SMOOTH);
        iconField1 = new ImageIcon(resizeField1);
        JLabel labelNomor = new JLabel(iconField1);
        labelNomor.setLayout(null);

        JTextField NomorBahan = new JTextField();
        NomorBahan.setBounds(20, 5, 120, 35);

        NomorBahan.setForeground(Color.WHITE);
        NomorBahan.setOpaque(false);
        NomorBahan.setBorder(null);
        NomorBahan.setCaretColor(Color.WHITE);

        NomorBahan.setHorizontalAlignment(JTextField.CENTER);

        labelNomor.add(NomorBahan);

// ======================================
// FIELD JUMLAH BAHAN
// ======================================

        ImageIcon iconField2 = new ImageIcon(getClass().getResource("/asset/dapur/kosong.png"));

        Image imgField2 = iconField2.getImage();
        Image resizeField2 = imgField2.getScaledInstance(165, 45, Image.SCALE_SMOOTH);

        iconField2 = new ImageIcon(resizeField2);

        JLabel labelJumlah = new JLabel(iconField2);
        labelJumlah.setLayout(null);

        JTextField JumlahBahan = new JTextField();

        JumlahBahan.setBounds(20, 5, 120, 35);

        JumlahBahan.setForeground(Color.WHITE);
        JumlahBahan.setOpaque(false);
        JumlahBahan.setBorder(null);
        JumlahBahan.setCaretColor(Color.WHITE);

        JumlahBahan.setHorizontalAlignment(JTextField.CENTER);

        labelJumlah.add(JumlahBahan);

        //beli
        ImageIcon iconBeli = new ImageIcon(getClass().getResource("/asset/beli.png"));

        Image img3 = iconBeli.getImage();
        Image resize3 = img3.getScaledInstance(135, 40, Image.SCALE_SMOOTH);

        iconBeli = new ImageIcon(resize3);

        JButton btnBeli = new JButton(iconBeli);

        btnBeli.setBorderPainted(false);
        btnBeli.setContentAreaFilled(false);
        btnBeli.setFocusPainted(false);
        btnBeli.setOpaque(false);

        //IconBack
        ImageIcon iconBack = new ImageIcon(getClass().getResource("/asset/back.png"));
        Image img5 = iconBack.getImage();
        Image resize5 = img5.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        iconBack = new ImageIcon(resize5);

        JButton btnKembali = new JButton(iconBack);

        btnKembali.setPreferredSize(new Dimension(150, 40));

        topPanel.setOpaque(false);
        centerPanel.setOpaque(false);
        bottomPanel.setOpaque(false);
        terminalArea.setOpaque(false);

        // ========================================================
        // LOGIKA ASLI DARI beliBahan() SEKARANG PINDAH KE SINI
        // ========================================================
        btnBeli.addActionListener(e -> {
            try {
                int inputNomor = Integer.parseInt(NomorBahan.getText());
                int jumlahBeli = Integer.parseInt(JumlahBahan.getText());

                // Validasi Nomor Pilihan
                if (inputNomor > 0 && inputNomor <= bahanTersediaSaatIni.size()) {
                    BahanBaku bahanDipilih = bahanTersediaSaatIni.get(inputNomor - 1);
                    int totalHarga = jumlahBeli * bahanDipilih.getHarga();

                    // Cek Uang
                    if (restoran.getUang() >= totalHarga) {
                        restoran.kurangiUang(totalHarga); // Potong uang

                        // Tambah ke stok Gudang
                        restoran.stok.put(bahanDipilih, restoran.stok.getOrDefault(bahanDipilih, 0) + jumlahBeli);

                        terminalArea.append("✅ Berhasil membeli " + jumlahBeli + " " + bahanDipilih.getNama() + " (Total: Rp " + totalHarga + ")\n");
                        updateStatusBar(); // Perbarui tampilan Uang di atas
                    } else {
                        terminalArea.append("❌ Uang tidak cukup! Butuh Rp " + totalHarga + "\n");
                    }
                } else {
                    terminalArea.append("❌ Nomor bahan tidak tersedia di daftar!\n");
                }

                NomorBahan.setText("");
                JumlahBahan.setText("");

            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan input berupa angka!\n");
            }
        });

        btnKembali.addActionListener(e -> {
            this.dispose();
        });

        bottomPanel.add(labelNomor);
        bottomPanel.add(labelJumlah);
        bottomPanel.add(btnBeli);
        bottomPanel.add(btnKembali);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. TAMPILKAN DATA AWAL ---
        updateStatusBar();
        tampilkanDaftarBahanBaku();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    // ========================================================
    // LOGIKA MENAMPILKAN DATABASE BAHAN SESUAI LEVEL
    // ========================================================
    private void tampilkanDaftarBahanBaku() {
        terminalArea.setText("=== PASAR BAHAN BAKU ===\n");
        terminalArea.append("Uang Anda: Rp " + restoran.getUang() + "\n\n");

        bahanTersediaSaatIni.clear(); // Kosongkan list bantuan

        // Mengambil data dari GameGenerateGUI (Database Utama)
        for (BahanBaku b : GameGenerateGUI.MASTER_BAHAN) {
            if (restoran.getLevel() >= b.getLevelMinimal()) {
                bahanTersediaSaatIni.add(b);
                terminalArea.append(bahanTersediaSaatIni.size() + ". " + b.getNama() + " (Rp " + b.getHarga() + ")\n");
            } else {
                terminalArea.append("X. [Terkunci - Butuh Lv." + b.getLevelMinimal() + "]\n");
            }
        }
        terminalArea.append("Pilih item dengan mengisi kolom pertama\n");
        terminalArea.append("Masukkan jumlah item dengan mengisi kolom kedua\n");
        terminalArea.append("\n");
    }
}

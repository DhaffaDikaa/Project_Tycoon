
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class TokoJimatGUI extends JFrame {

    private Restoran restoran;
    private JPanel bottomCardPanel;
    private CardLayout cardLayout;
    private JTextArea terminalArea;
    private JLabel lblLevel, lblUang, lblKapasitas;

    public TokoJimatGUI(Restoran restoran) {
        this.restoran = restoran;

        setTitle("Resto Tycoon - Toko Jimat");
        setSize(950, 650); // Dilebarkan sedikit agar tombol muat
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL ATAS (Status Bar) ---
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblTitle = new JLabel("TOKO JIMAT", SwingConstants.CENTER);
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

        // --- 2. PANEL TENGAH (Ilustrasi Responsif & Terminal) ---
        JPanel centerPanel = new JPanel(new BorderLayout(15, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Custom JLabel untuk Gambar agar responsif (ikut membesar)
        JLabel lblIlustrasi = new JLabel("", SwingConstants.CENTER) {
            private Image imgAsli;

            {
                try {
                    // Sesuaikan nama gambar untuk toko jimat Anda
                    imgAsli = new ImageIcon("image/TokoJimat.png").getImage();
                } catch (Exception e) {
                    System.out.println("Gambar jimat tidak ditemukan.");
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imgAsli != null) {
                    int panelW = getWidth();
                    int panelH = getHeight();
                    int imgW = imgAsli.getWidth(this);
                    int imgH = imgAsli.getHeight(this);

                    if (imgW > 0 && imgH > 0) {
                        double ratio = Math.min((double) panelW / imgW, (double) panelH / imgH);
                        int newW = (int) (imgW * ratio);
                        int newH = (int) (imgH * ratio);
                        int x = (panelW - newW) / 2;
                        int y = (panelH - newH) / 2;
                        g.drawImage(imgAsli, x, y, newW, newH, this);
                    }
                } else {
                    setText("GAMBAR TOKO JIMAT TIDAK DITEMUKAN");
                }
            }
        };
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Terminal JTextArea
        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminalArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Kunci lebar terminal ke 350px
        scrollTerminal.setPreferredSize(new Dimension(380, 0));

        centerPanel.add(lblIlustrasi, BorderLayout.CENTER);
        centerPanel.add(scrollTerminal, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (Navigasi CardLayout) ---
        cardLayout = new CardLayout();
        bottomCardPanel = new JPanel(cardLayout);
        bottomCardPanel.setPreferredSize(new Dimension(0, 80));

        // -> Card 1: Main Menu (Ditambah Tombol Jual)
        JPanel mainTokoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JButton btnBeliJimat = new JButton("1. BELI JIMAT");
        JButton btnJualJimat = new JButton("2. JUAL JIMAT (50%)"); // TOMBOL JUAL KEMBALI
        JButton btnGunakanJimat = new JButton("3. GUNAKAN JIMAT");
        JButton btnKeluar = new JButton("4. KEMBALI KE MENU");

        Dimension btnSize = new Dimension(180, 45); // Dikecilkan sedikit agar muat 4 tombol
        Font btnFont = new Font("Segoe UI", Font.BOLD, 13);
        setupButton(btnBeliJimat, btnSize, btnFont, new Color(52, 152, 219));    // Biru
        setupButton(btnJualJimat, btnSize, btnFont, new Color(243, 156, 18));    // Orange
        setupButton(btnGunakanJimat, btnSize, btnFont, new Color(46, 204, 113)); // Hijau
        setupButton(btnKeluar, btnSize, btnFont, new Color(231, 76, 60));        // Merah

        mainTokoPanel.add(btnBeliJimat);
        mainTokoPanel.add(btnJualJimat);
        mainTokoPanel.add(btnGunakanJimat);
        mainTokoPanel.add(btnKeluar);

        // -> Card 2: Form Menu Beli
        JPanel beliPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JLabel lblBeli = new JLabel("MASUKKAN NOMOR JIMAT UNTUK DIBELI:");
        JTextField txtBeliBox = new JTextField(5);
        JButton btnProsesBeli = new JButton("BELI");
        JButton btnBackBeli = new JButton("KEMBALI");

        setupButton(btnProsesBeli, new Dimension(100, 40), btnFont, new Color(52, 152, 219));
        setupButton(btnBackBeli, new Dimension(120, 40), btnFont, Color.GRAY);

        beliPanel.add(lblBeli);
        beliPanel.add(txtBeliBox);
        beliPanel.add(btnProsesBeli);
        beliPanel.add(btnBackBeli);

        // -> Card 3: Form Menu Jual (BARU DIKEMBALIKAN)
        JPanel jualPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JLabel lblJual = new JLabel("MASUKKAN NOMOR JIMAT UNTUK DIJUAL (50%):");
        JTextField txtJualBox = new JTextField(5);
        JButton btnProsesJual = new JButton("JUAL");
        JButton btnBackJual = new JButton("KEMBALI");

        setupButton(btnProsesJual, new Dimension(100, 40), btnFont, new Color(243, 156, 18));
        setupButton(btnBackJual, new Dimension(120, 40), btnFont, Color.GRAY);

        jualPanel.add(lblJual);
        jualPanel.add(txtJualBox);
        jualPanel.add(btnProsesJual);
        jualPanel.add(btnBackJual);

        // -> Card 4: Form Menu Gunakan
        JPanel gunakanPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JLabel lblGunakan = new JLabel("MASUKKAN NOMOR JIMAT UNTUK DIPAKAI:");
        JTextField txtGunakanBox = new JTextField(5);
        JButton btnProsesGunakan = new JButton("GUNAKAN");
        JButton btnBackGunakan = new JButton("KEMBALI");

        setupButton(btnProsesGunakan, new Dimension(120, 40), btnFont, new Color(46, 204, 113));
        setupButton(btnBackGunakan, new Dimension(120, 40), btnFont, Color.GRAY);

        gunakanPanel.add(lblGunakan);
        gunakanPanel.add(txtGunakanBox);
        gunakanPanel.add(btnProsesGunakan);
        gunakanPanel.add(btnBackGunakan);

        // Masukkan semua card ke panel pembungkus bawah
        bottomCardPanel.add(mainTokoPanel, "MAIN_MENU");
        bottomCardPanel.add(beliPanel, "MENU_BELI");
        bottomCardPanel.add(jualPanel, "MENU_JUAL");
        bottomCardPanel.add(gunakanPanel, "MENU_GUNAKAN");
        add(bottomCardPanel, BorderLayout.SOUTH);

        // --- ACTION LISTENERS NAVIGASI ---
        btnKeluar.addActionListener(e -> this.dispose());

        btnBeliJimat.addActionListener(e -> {
            cardLayout.show(bottomCardPanel, "MENU_BELI");
            tampilkanDaftarShop();
        });

        btnJualJimat.addActionListener(e -> {
            cardLayout.show(bottomCardPanel, "MENU_JUAL");
            tampilkanDaftarInventaris("MENU JUAL JIMAT (Harga 50%)");
        });

        btnGunakanJimat.addActionListener(e -> {
            cardLayout.show(bottomCardPanel, "MENU_GUNAKAN");
            tampilkanDaftarInventaris("MENU GUNAKAN JIMAT");
        });

        btnBackBeli.addActionListener(e -> {
            cardLayout.show(bottomCardPanel, "MAIN_MENU");
            tampilkanDaftarShop();
        });

        btnBackJual.addActionListener(e -> {
            cardLayout.show(bottomCardPanel, "MAIN_MENU");
            tampilkanDaftarShop();
        });

        btnBackGunakan.addActionListener(e -> {
            cardLayout.show(bottomCardPanel, "MAIN_MENU");
            tampilkanDaftarShop();
        });

        // ========================================================
        // LOGIKA TRANSAKSI BELI JIMAT
        // ========================================================
        btnProsesBeli.addActionListener(e -> {
            try {
                int nomor = Integer.parseInt(txtBeliBox.getText());
                int harga = 0;
                Jimat jimatBaru = null;

                if (nomor == 1) {
                    jimatBaru = new JimatCharming();
                    harga = 150000;
                } else if (nomor == 2) {
                    jimatBaru = new JimatSecurity();
                    harga = 100000;
                } else if (nomor == 3) {
                    jimatBaru = new JimatCleaner();
                    harga = 75000;
                } else {
                    terminalArea.append("❌ Nomor jimat tidak valid!\n");
                    return;
                }

                if (restoran.getUang() >= harga) {
                    restoran.kurangiUang(harga);
                    restoran.getInventarisJimat().add(jimatBaru);
                    terminalArea.append("✅ " + jimatBaru.getNama() + " berhasil dibeli!\n");
                } else {
                    terminalArea.append("❌ Uang kas tidak cukup!\n");
                }

                txtBeliBox.setText("");
                updateStatusBar();
                tampilkanDaftarShop();

            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan nomor berupa angka!\n");
            }
        });

        // ========================================================
        // LOGIKA TRANSAKSI JUAL JIMAT (DIKEMBALIKAN DENGAN HARGA 50%)
        // ========================================================
        btnProsesJual.addActionListener(e -> {
            try {
                int idx = Integer.parseInt(txtJualBox.getText()) - 1;
                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {
                    // Ambil jimat yang akan dijual dari inventaris
                    Jimat dijatuhin = inventaris.remove(idx);
                    int refund = 0;

                    // Tentukan harga jual (50% dari harga beli)
                    if (dijatuhin.getNama().toLowerCase().contains("charming")) {
                        refund = 150000 / 2;
                    } else if (dijatuhin.getNama().toLowerCase().contains("security")) {
                        refund = 100000 / 2;
                    } else if (dijatuhin.getNama().toLowerCase().contains("cleaner")) {
                        refund = 75000 / 2;
                    }

                    restoran.tambahUang(refund);

                    // Jika jimat yang dijual kebetulan sedang dipakai, copot!
                    if (restoran.getJimatAktif() != null && restoran.getJimatAktif().equals(dijatuhin)) {
                        restoran.pasangJimat(null);
                        terminalArea.append("⚠️ Jimat yang Anda jual sedang aktif. Jimat telah dicopot otomatis.\n");
                    }

                    terminalArea.append("💰 " + dijatuhin.getNama() + " berhasil dijual! Anda mendapat Rp " + refund + "\n");

                    txtJualBox.setText("");
                    updateStatusBar();
                    tampilkanDaftarInventaris("MENU JUAL JIMAT (Harga 50%)");
                } else {
                    terminalArea.append("❌ Nomor jimat tidak ditemukan di dalam tas!\n");
                }
            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan angka!\n");
            }
        });

        // ========================================================
        // LOGIKA MENGGUNAKAN JIMAT
        // ========================================================
        btnProsesGunakan.addActionListener(e -> {
            try {
                int idx = Integer.parseInt(txtGunakanBox.getText()) - 1;
                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {
                    restoran.pasangJimat(inventaris.get(idx));
                    if (restoran.getJimatAktif() != null) {
                        terminalArea.append("✨ " + restoran.getJimatAktif().aktifkanEfek() + "\n");
                    }
                    txtGunakanBox.setText("");
                    tampilkanDaftarInventaris("MENU GUNAKAN JIMAT");
                } else {
                    terminalArea.append("❌ Nomor jimat tidak ditemukan!\n");
                }
            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan angka!\n");
            }
        });

        // Initialize display
        updateStatusBar();
        tampilkanDaftarShop();
    }

    private void setupButton(JButton btn, Dimension size, Font font, Color bgColor) {
        btn.setPreferredSize(size);
        btn.setFont(font);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void tampilkanDaftarShop() {
        terminalArea.setText("=== 🔮 TOKO JIMAT PERMANEN RESTORAN ===\n\n");
        terminalArea.append("Status Jimat Saat Ini: "
                + (restoran.getJimatAktif() != null ? restoran.getJimatAktif().getNama() : "Tidak ada jimat aktif") + "\n\n");
        terminalArea.append("1. JIMAT CHARMING (Meningkatkan tips tamu)  -> Rp 150.000\n");
        terminalArea.append("2. JIMAT SECURITY (Mencegah pencurian kas)   -> Rp 100.000\n");
        terminalArea.append("3. JIMAT CLEANER  (Mencegah tikus pemakan bahan baku)  -> Rp  75.000\n\n");
        terminalArea.append("Silakan klik salah satu menu di bawah untuk melakukan aksi.");
    }

    private void tampilkanDaftarInventaris(String header) {
        terminalArea.setText("=== 🎒 " + header + " ===\n\n");
        List<Jimat> inventaris = restoran.getInventarisJimat();
        if (inventaris.isEmpty()) {
            terminalArea.append("Tas Anda kosong! Anda belum memiliki jimat apapun.\n");
        } else {
            int no = 1;
            for (Jimat j : inventaris) {
                // Beri tanda khusus jika jimat di inventaris adalah jimat yang sedang aktif dipakai
                String status = (restoran.getJimatAktif() != null && restoran.getJimatAktif().equals(j)) ? " [SEDANG DIPAKAI]" : "";
                terminalArea.append(no + ". " + j.getNama() + status + "\n");
                no++;
            }
        }
    }
}

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

        // ── Background Panel (sama seperti DapurGUI) ──────────────────────────
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

        setTitle("Resto Tycoon - Toko Jimat");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── 1. PANEL ATAS (Status Bar) ─────────────────────────────────────────
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("TOKO JIMAT", SwingConstants.CENTER);
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

        // ── 2. PANEL TENGAH (Ilustrasi & Terminal) ────────────────────────────
        JPanel centerPanel = new JPanel(null);
        centerPanel.setOpaque(false);

        // Label ilustrasi kiri
        ImageIcon icon = new ImageIcon(getClass().getResource("/asset/tokojimat.png"));
        JLabel lblIlustrasi = new JLabel(icon);
        lblIlustrasi.setBounds(10, 30, 425, 425);
        lblIlustrasi.setForeground(null);
        lblIlustrasi.setOpaque(false);

        // Terminal background image
        ImageIcon iconTerminal = new ImageIcon(getClass().getResource("/asset/terminal.png"));
        Image imgTerminal = iconTerminal.getImage();
        Image resizeTerminal = imgTerminal.getScaledInstance(425, 425, Image.SCALE_SMOOTH);
        iconTerminal = new ImageIcon(resizeTerminal);

        JLabel terminalBg = new JLabel(iconTerminal);
        terminalBg.setLayout(null);
        terminalBg.setBounds(440, 25, 425, 425);

        // Text area di dalam terminal
        terminalArea = new JTextArea("TERMINAL :\n\nSelamat datang di Toko Jimat!\nSilakan pilih menu di bawah.\n");
        terminalArea.setEditable(false);
        terminalArea.setOpaque(false);
        terminalArea.setForeground(Color.GREEN);
        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 10));
        terminalArea.setBounds(40, 60, 330, 300);

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBounds(40, 60, 330, 280);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);

        terminalBg.add(scrollTerminal);

        centerPanel.add(lblIlustrasi);
        centerPanel.add(terminalBg);
        add(centerPanel, BorderLayout.CENTER);

        // ── 3. PANEL BAWAH (Card Layout: Menu Utama / Beli / Jual / Gunakan) ──
        cardLayout = new CardLayout();
        bottomCardPanel = new JPanel(cardLayout);
        bottomCardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomCardPanel.setOpaque(false);

        bottomCardPanel.add(createMainMenuPanel(), "MAIN_MENU");
        bottomCardPanel.add(createBeliPanel(),     "BELI_MENU");
        bottomCardPanel.add(createJualPanel(),     "JUAL_MENU");
        bottomCardPanel.add(createGunakanPanel(),  "GUNAKAN_MENU");

        add(bottomCardPanel, BorderLayout.SOUTH);

        updateStatusBar();
    }

    // ── Helper: buat tombol dengan ikon (fallback ke teks jika aset tidak ada) ──
    private JButton buatTombolIkon(String pathAset, int lebar, int tinggi, String teksBackup) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(pathAset));
            Image img = icon.getImage().getScaledInstance(lebar, tinggi, Image.SCALE_SMOOTH);
            JButton btn = new JButton(new ImageIcon(img));
            btn.setPreferredSize(new Dimension(lebar, tinggi));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            return btn;
        } catch (Exception ex) {
            // Fallback teks biasa jika file aset tidak ditemukan
            JButton btn = new JButton(teksBackup);
            btn.setPreferredSize(new Dimension(lebar, tinggi));
            return btn;
        }
    }

    // ── Helper: buat label field berikon dengan JTextField di dalamnya ─────────
    private JLabel buatLabelField(JTextField txtField) {
        ImageIcon iconField;
        try {
            iconField = new ImageIcon(getClass().getResource("/asset/dapur/kosong.png"));
            Image img = iconField.getImage().getScaledInstance(165, 45, Image.SCALE_SMOOTH);
            iconField = new ImageIcon(img);
        } catch (Exception ex) {
            iconField = null;
        }

        JLabel labelField = iconField != null ? new JLabel(iconField) : new JLabel();
        labelField.setLayout(null);
        labelField.setPreferredSize(new Dimension(165, 45));

        txtField.setBounds(20, 5, 120, 35);
        txtField.setForeground(Color.WHITE);
        txtField.setOpaque(false);
        txtField.setBorder(null);
        txtField.setHorizontalAlignment(JTextField.CENTER);
        labelField.add(txtField);

        return labelField;
    }

    // ─────────────────────────────────────────────────────────────────────────
    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void tampilkanDaftarInventaris(String judulMenu) {
        terminalArea.setText("=== " + judulMenu + " ===\n\n");
        List<Jimat> inventaris = restoran.getInventarisJimat();

        if (inventaris.isEmpty()) {
            terminalArea.append("Inventaris jimat Anda saat ini kosong.\n");
        } else {
            for (int i = 0; i < inventaris.size(); i++) {
                Jimat j = inventaris.get(i);
                String statusPakai = (restoran.getJimatAktif() != null
                        && restoran.getJimatAktif().equals(j)) ? " [SEDANG DIGUNAKAN]" : "";
                terminalArea.append((i + 1) + ". " + j.getNama() + statusPakai + "\n");
            }
        }
    }

    private void prosesBeliJimat(Jimat jimat, int harga) {
        if (restoran.getUang() >= harga) {
            restoran.kurangiUang(harga);
            restoran.getInventarisJimat().add(jimat);
            terminalArea.append("✅ Berhasil membeli " + jimat.getNama() + "!\n");
            updateStatusBar();
        } else {
            terminalArea.append("❌ Uang tidak cukup! Butuh Rp " + harga + "\n");
        }
    }

    // ── Panel: Menu Utama ──────────────────────────────────────────────────────
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setOpaque(false);

        JButton btnBeli     = buatTombolIkon("/asset/beli.png",    140, 45, "BELI JIMAT");
        JButton btnJual     = buatTombolIkon("/asset/jual.png",    140, 45, "JUAL JIMAT");
        JButton btnGunakan  = buatTombolIkon("/asset/gunakan.png", 140, 45, "GUNAKAN JIMAT");
        JButton btnKembali  = buatTombolIkon("/asset/back.png",              150, 40, "KEMBALI");

        btnBeli.addActionListener(e -> {
            terminalArea.setText("=== MENU BELI JIMAT ===\n\n");
            terminalArea.append("1. Jimat Charming  (Rp " + GameGenerateGUI.HARGA_CHARMING  + ")\n");
            terminalArea.append("2. Jimat Security  (Rp " + GameGenerateGUI.HARGA_SECURITY  + ")\n");
            terminalArea.append("3. Jimat Cleaner   (Rp " + GameGenerateGUI.HARGA_CLEANER   + ")\n");
            cardLayout.show(bottomCardPanel, "BELI_MENU");
        });

        btnJual.addActionListener(e -> {
            tampilkanDaftarInventaris("MENU JUAL JIMAT (Harga Jual 50%)");
            cardLayout.show(bottomCardPanel, "JUAL_MENU");
        });

        btnGunakan.addActionListener(e -> {
            tampilkanDaftarInventaris("MENU GUNAKAN JIMAT");
            cardLayout.show(bottomCardPanel, "GUNAKAN_MENU");
        });

        btnKembali.addActionListener(e -> this.dispose());

        panel.add(btnBeli);
        panel.add(btnJual);
        panel.add(btnGunakan);
        panel.add(btnKembali);
        return panel;
    }

    // ── Panel: Beli ────────────────────────────────────────────────────────────
    private JPanel createBeliPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setOpaque(false);

        JButton btnCharming = buatTombolIkon("/asset/jimat/charming.png", 130, 40, "CHARMING");
        JButton btnSecurity = buatTombolIkon("/asset/jimat/security.png", 130, 40, "SECURITY");
        JButton btnCleaner  = buatTombolIkon("/asset/jimat/cleaner.png",  130, 40, "CLEANER");
        JButton btnKembali  = buatTombolIkon("/asset/back.png",               130, 40, "KEMBALI");

        btnCharming.addActionListener(e -> prosesBeliJimat(new JimatCharming(), GameGenerateGUI.HARGA_CHARMING));
        btnSecurity.addActionListener(e -> prosesBeliJimat(new JimatSecurity(), GameGenerateGUI.HARGA_SECURITY));
        btnCleaner.addActionListener(e  -> prosesBeliJimat(new JimatCleaner(),  GameGenerateGUI.HARGA_CLEANER));

        btnKembali.addActionListener(e -> cardLayout.show(bottomCardPanel, "MAIN_MENU"));

        panel.add(btnCharming);
        panel.add(btnSecurity);
        panel.add(btnCleaner);
        panel.add(btnKembali);
        return panel;
    }

    // ── Panel: Jual ────────────────────────────────────────────────────────────
    private JPanel createJualPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setOpaque(false);

        JTextField txtFieldBox = new JTextField();
        JLabel labelField = buatLabelField(txtFieldBox);

        JButton btnJual    = buatTombolIkon("/asset/jual.png", 130, 40, "JUAL");
        JButton btnKembali = buatTombolIkon("/asset/back.png",            150, 40, "KEMBALI");

        btnJual.addActionListener(e -> {
            try {
                int idx = Integer.parseInt(txtFieldBox.getText()) - 1;
                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {
                    Jimat dijatuhin = inventaris.get(idx);
                    int hargaJual = 50000;

                    inventaris.remove(idx);
                    restoran.tambahUang(hargaJual);

                    if (restoran.getJimatAktif() != null && restoran.getJimatAktif().equals(dijatuhin)) {
                        restoran.pasangJimat(null);
                    }

                    terminalArea.append("💰 Jual berhasil! Anda mendapat Rp " + hargaJual + "\n");
                    txtFieldBox.setText("");
                    tampilkanDaftarInventaris("MENU JUAL JIMAT");
                    updateStatusBar();
                } else {
                    terminalArea.append("❌ Nomor jimat tidak valid!\n");
                }
            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan angka!\n");
            }
        });

        btnKembali.addActionListener(e -> cardLayout.show(bottomCardPanel, "MAIN_MENU"));

        panel.add(labelField);
        panel.add(btnJual);
        panel.add(btnKembali);
        return panel;
    }

    // ── Panel: Gunakan ─────────────────────────────────────────────────────────
    private JPanel createGunakanPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setOpaque(false);

        JTextField txtFieldBox = new JTextField();
        JLabel labelField = buatLabelField(txtFieldBox);

        JButton btnGunakan = buatTombolIkon("/asset/dapur/pilihMenu.png", 165, 45, "GUNAKAN");
        JButton btnKembali = buatTombolIkon("/asset/back.png",            150, 40, "KEMBALI");

        btnGunakan.addActionListener(e -> {
            try {
                int idx = Integer.parseInt(txtFieldBox.getText()) - 1;
                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {
                    restoran.pasangJimat(inventaris.get(idx));

                    if (restoran.getJimatAktif() != null) {
                        terminalArea.append("✨ " + restoran.getJimatAktif().aktifkanEfek() + "\n");
                    }
                    txtFieldBox.setText("");
                    tampilkanDaftarInventaris("MENU GUNAKAN JIMAT");
                } else {
                    terminalArea.append("❌ Nomor jimat tidak ditemukan!\n");
                }
            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan angka!\n");
            }
        });

        btnKembali.addActionListener(e -> cardLayout.show(bottomCardPanel, "MAIN_MENU"));

        panel.add(labelField);
        panel.add(btnGunakan);
        panel.add(btnKembali);
        return panel;
    }
}
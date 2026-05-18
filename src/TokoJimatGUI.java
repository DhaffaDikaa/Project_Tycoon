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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

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

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        Image imgIlustrasiRaw = new ImageIcon(getClass().getResource("/asset/tokojimat.png")).getImage();

        JPanel ilustrasiPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgIlustrasiRaw, 0, 0, getWidth(), getHeight(), this);
            }
        };

        ilustrasiPanel.setOpaque(false);

        Image imgTerminalRaw = new ImageIcon(getClass().getResource("/asset/terminal.png")).getImage();

        JPanel terminalPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgTerminalRaw, 0, 0, getWidth(), getHeight(), this);
            }
        };

        terminalPanel.setOpaque(false);
        terminalPanel.setBorder(BorderFactory.createEmptyBorder(55, 45, 60, 45));

        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setOpaque(false);
        terminalArea.setForeground(Color.GREEN);
        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 10));

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);
        scrollTerminal.setBorder(null);

        terminalPanel.add(scrollTerminal, BorderLayout.CENTER);

        centerPanel.add(ilustrasiPanel);
        centerPanel.add(terminalPanel);

        add(centerPanel, BorderLayout.CENTER);

        cardLayout = new CardLayout();

        bottomCardPanel = new JPanel(cardLayout);
        bottomCardPanel.setOpaque(false);
        bottomCardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        bottomCardPanel.add(createMainMenuPanel(), "MAIN_MENU");
        bottomCardPanel.add(createBeliPanel(), "BELI_MENU");
        bottomCardPanel.add(createJualPanel(), "JUAL_MENU");
        bottomCardPanel.add(createGunakanPanel(), "GUNAKAN_MENU");

        add(bottomCardPanel, BorderLayout.SOUTH);

        updateStatusBar();
        tampilkanDaftarShop();
    }

    private JButton buatTombolIkon(String path, int w, int h, String backup) {

        try {

            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);

            JButton btn = new JButton(new ImageIcon(img));

            btn.setPreferredSize(new Dimension(w, h));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);

            return btn;

        } catch (Exception ex) {

            JButton btn = new JButton(backup);
            btn.setPreferredSize(new Dimension(w, h));

            return btn;
        }
    }

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
        txtField.setCaretColor(Color.WHITE);
        txtField.setOpaque(false);
        txtField.setBorder(null);
        txtField.setHorizontalAlignment(JTextField.CENTER);

        labelField.add(txtField);

        return labelField;
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void tampilkanDaftarShop() {

        terminalArea.setText("=== 🔮 TOKO JIMAT PERMANEN RESTORAN ===\n\n");

        terminalArea.append("Status Jimat Aktif : " + (restoran.getJimatAktif() != null ? restoran.getJimatAktif().getNama() : "Tidak Ada") + "\n\n");

        terminalArea.append("1. JIMAT CHARMING -> Rp " + GameGenerateGUI.HARGA_CHARMING + "\n");
        terminalArea.append("2. JIMAT SECURITY -> Rp " + GameGenerateGUI.HARGA_SECURITY + "\n");
        terminalArea.append("3. JIMAT CLEANER -> Rp " + GameGenerateGUI.HARGA_CLEANER + "\n\n");

        terminalArea.append("Silakan pilih menu di bawah.\n");
    }

    private void tampilkanDaftarInventaris(String title) {

        terminalArea.setText("=== " + title + " ===\n\n");

        List<Jimat> inventaris = restoran.getInventarisJimat();

        if (inventaris.isEmpty()) {

            terminalArea.append("Tas jimat Anda kosong.\n");

        } else {

            int no = 1;

            for (Jimat j : inventaris) {

                String status = "";

                if (restoran.getJimatAktif() != null && restoran.getJimatAktif().equals(j)) status = " [SEDANG DIPAKAI]";

                terminalArea.append(no + ". " + j.getNama() + status + "\n");

                no++;
            }
        }
    }

    private void prosesBeliJimat(Jimat jimat, int harga) {

        if (restoran.getUang() >= harga) {

            restoran.kurangiUang(harga);
            restoran.getInventarisJimat().add(jimat);

            terminalArea.append("✅ " + jimat.getNama() + " berhasil dibeli!\n");
            terminalArea.append("💸 Uang berkurang Rp " + harga + "\n");

            updateStatusBar();

        } else terminalArea.append("❌ Uang kas tidak cukup!\n");
    }

    private JPanel createMainMenuPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        panel.setOpaque(false);

        JButton btnBeli = buatTombolIkon("/asset/beli.png", 140, 45, "BELI");
        JButton btnJual = buatTombolIkon("/asset/jual.png", 140, 45, "JUAL");
        JButton btnGunakan = buatTombolIkon("/asset/gunakan.png", 140, 45, "GUNAKAN");
        JButton btnBack = buatTombolIkon("/asset/back.png", 150, 40, "KEMBALI");

        btnBeli.addActionListener(e -> {
            tampilkanDaftarShop();
            cardLayout.show(bottomCardPanel, "BELI_MENU");
        });

        btnJual.addActionListener(e -> {
            tampilkanDaftarInventaris("MENU JUAL JIMAT");
            cardLayout.show(bottomCardPanel, "JUAL_MENU");
        });

        btnGunakan.addActionListener(e -> {
            tampilkanDaftarInventaris("MENU GUNAKAN JIMAT");
            cardLayout.show(bottomCardPanel, "GUNAKAN_MENU");
        });

        btnBack.addActionListener(e -> dispose());

        panel.add(btnBeli);
        panel.add(btnJual);
        panel.add(btnGunakan);
        panel.add(btnBack);

        return panel;
    }

    private JPanel createBeliPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        panel.setOpaque(false);

        JButton btnCharming = buatTombolIkon("/asset/jimat/charming.png", 130, 40, "CHARMING");
        JButton btnSecurity = buatTombolIkon("/asset/jimat/security.png", 130, 40, "SECURITY");
        JButton btnCleaner = buatTombolIkon("/asset/jimat/cleaner.png", 130, 40, "CLEANER");
        JButton btnBack = buatTombolIkon("/asset/back.png", 130, 40, "KEMBALI");

        btnCharming.addActionListener(e -> prosesBeliJimat(new JimatCharming(), GameGenerateGUI.HARGA_CHARMING));
        btnSecurity.addActionListener(e -> prosesBeliJimat(new JimatSecurity(), GameGenerateGUI.HARGA_SECURITY));
        btnCleaner.addActionListener(e -> prosesBeliJimat(new JimatCleaner(), GameGenerateGUI.HARGA_CLEANER));

        btnBack.addActionListener(e -> {
            tampilkanDaftarShop();
            cardLayout.show(bottomCardPanel, "MAIN_MENU");
        });

        panel.add(btnCharming);
        panel.add(btnSecurity);
        panel.add(btnCleaner);
        panel.add(btnBack);

        return panel;
    }

    private JPanel createJualPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        panel.setOpaque(false);

        JLabel lbl = new JLabel("MASUKAN NOMOR JIMAT");
        lbl.setForeground(Color.WHITE);

        JTextField txtField = new JTextField();

        JLabel field = buatLabelField(txtField);

        JButton btnJual = buatTombolIkon("/asset/jual.png", 130, 40, "JUAL");
        JButton btnBack = buatTombolIkon("/asset/back.png", 150, 40, "KEMBALI");

        btnJual.addActionListener(e -> {

            try {

                int idx = Integer.parseInt(txtField.getText()) - 1;

                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {

                    Jimat dijual = inventaris.remove(idx);

                    int refund = 0;

                    if (dijual instanceof JimatCharming) refund = GameGenerateGUI.HARGA_CHARMING / 2;
                    else if (dijual instanceof JimatSecurity) refund = GameGenerateGUI.HARGA_SECURITY / 2;
                    else if (dijual instanceof JimatCleaner) refund = GameGenerateGUI.HARGA_CLEANER / 2;

                    restoran.tambahUang(refund);

                    if (restoran.getJimatAktif() != null && restoran.getJimatAktif().equals(dijual)) {
                        restoran.pasangJimat(null);
                        terminalArea.append("⚠️ Jimat aktif otomatis dilepas!\n");
                    }

                    terminalArea.append("💰 " + dijual.getNama() + " berhasil dijual!\n");
                    terminalArea.append("💵 Anda mendapat Rp " + refund + "\n");

                    txtField.setText("");

                    tampilkanDaftarInventaris("MENU JUAL JIMAT");

                    updateStatusBar();

                } else terminalArea.append("❌ Nomor jimat tidak valid!\n");

            } catch (NumberFormatException ex) {

                terminalArea.append("❌ Harap masukkan angka!\n");
            }
        });

        btnBack.addActionListener(e -> {
            tampilkanDaftarShop();
            cardLayout.show(bottomCardPanel, "MAIN_MENU");
        });

        panel.add(lbl);
        panel.add(field);
        panel.add(btnJual);
        panel.add(btnBack);

        return panel;
    }

    private JPanel createGunakanPanel() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        panel.setOpaque(false);

        JLabel lbl = new JLabel("MASUKAN NOMOR JIMAT");
        lbl.setForeground(Color.WHITE);

        JTextField txtField = new JTextField();

        JLabel field = buatLabelField(txtField);

        JButton btnGunakan = buatTombolIkon("/asset/dapur/pilihMenu.png", 165, 45, "GUNAKAN");
        JButton btnBack = buatTombolIkon("/asset/back.png", 150, 40, "KEMBALI");

        btnGunakan.addActionListener(e -> {

            try {

                int idx = Integer.parseInt(txtField.getText()) - 1;

                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {

                    restoran.pasangJimat(inventaris.get(idx));

                    terminalArea.append("✨ " + restoran.getJimatAktif().aktifkanEfek() + "\n");

                    txtField.setText("");

                    tampilkanDaftarInventaris("MENU GUNAKAN JIMAT");

                } else terminalArea.append("❌ Nomor jimat tidak ditemukan!\n");

            } catch (NumberFormatException ex) {

                terminalArea.append("❌ Harap masukkan angka!\n");
            }
        });

        btnBack.addActionListener(e -> {
            tampilkanDaftarShop();
            cardLayout.show(bottomCardPanel, "MAIN_MENU");
        });

        panel.add(lbl);
        panel.add(field);
        panel.add(btnGunakan);
        panel.add(btnBack);

        return panel;
    }
}
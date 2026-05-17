
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
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

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

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblIlustrasi = new JLabel("GAMBAR ILUSTRASI TOKO JIMAT", SwingConstants.CENTER);
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        terminalArea = new JTextArea("TERMINAL :\n\nSelamat datang di Toko Jimat! Silakan pilih menu di bawah.\n");
        terminalArea.setEditable(false);
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        centerPanel.add(lblIlustrasi);
        centerPanel.add(scrollTerminal);
        add(centerPanel, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        bottomCardPanel = new JPanel(cardLayout);
        bottomCardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        bottomCardPanel.add(createMainMenuPanel(), "MAIN_MENU");
        bottomCardPanel.add(createBeliPanel(), "BELI_MENU");
        bottomCardPanel.add(createJualPanel(), "JUAL_MENU");
        bottomCardPanel.add(createGunakanPanel(), "GUNAKAN_MENU");

        add(bottomCardPanel, BorderLayout.SOUTH);

        updateStatusBar();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void tampilkanDaftarInventaris(String judulMenu) {
        terminalArea.setText("=== " + judulMenu + " ===\n");
        List<Jimat> inventaris = restoran.getInventarisJimat();

        if (inventaris.isEmpty()) {
            terminalArea.append("Inventaris jimat Anda saat ini kosong.\n");
        } else {
            for (int i = 0; i < inventaris.size(); i++) {
                Jimat j = inventaris.get(i);
                // Memeriksa jika jimat ini sedang dipakai
                String statusPakai = (restoran.getJimatAktif() != null && restoran.getJimatAktif().equals(j)) ? " [SEDANG DIGUNAKAN]" : "";
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
            terminalArea.append("❌ Uang tidak cukup untuk membeli jimat ini! Butuh Rp " + harga + "\n");
        }
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setPreferredSize(new Dimension(0, 60));

        JButton btnBeli = new JButton("BELI JIMAT");
        JButton btnJual = new JButton("JUAL JIMAT");
        JButton btnGunakan = new JButton("GUNAKAN JIMAT");
        JButton btnKembali = new JButton("KEMBALI");

        btnBeli.addActionListener(e -> {
            terminalArea.setText("=== MENU BELI JIMAT ===\n");
            terminalArea.append("1. Jimat Charming (Rp " + GameGenerateGUI.HARGA_CHARMING + ")\n");
            terminalArea.append("2. Jimat Security (Rp " + GameGenerateGUI.HARGA_SECURITY + ")\n");
            terminalArea.append("3. Jimat Cleaner (Rp " + GameGenerateGUI.HARGA_CLEANER + ")\n");
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

    private JPanel createBeliPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setPreferredSize(new Dimension(0, 60));

        JButton btnCharming = new JButton("CHARMING");
        JButton btnSecurity = new JButton("SECURITY");
        JButton btnCleaner = new JButton("CLEANER");
        JButton btnKembali = new JButton("KEMBALI");

        // Menggunakan harga resmi dari GameGenerateGUI
        btnCharming.addActionListener(e -> prosesBeliJimat(new JimatCharming(), GameGenerateGUI.HARGA_CHARMING));
        btnSecurity.addActionListener(e -> prosesBeliJimat(new JimatSecurity(), GameGenerateGUI.HARGA_SECURITY));
        btnCleaner.addActionListener(e -> prosesBeliJimat(new JimatCleaner(), GameGenerateGUI.HARGA_CLEANER));

        btnKembali.addActionListener(e -> cardLayout.show(bottomCardPanel, "MAIN_MENU"));

        panel.add(btnCharming);
        panel.add(btnSecurity);
        panel.add(btnCleaner);
        panel.add(btnKembali);
        return panel;
    }

    private JPanel createJualPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setPreferredSize(new Dimension(0, 60));

        JLabel lblInstruksi = new JLabel("MASUKAN NOMOR JIMAT YANG MAU DIJUAL");
        JTextField txtFieldBox = new JTextField(5);
        JButton btnJual = new JButton("JUAL");
        JButton btnKembali = new JButton("KEMBALI");

        btnJual.addActionListener(e -> {
            try {
                int idx = Integer.parseInt(txtFieldBox.getText()) - 1;
                List<Jimat> inventaris = restoran.getInventarisJimat();

                if (idx >= 0 && idx < inventaris.size()) {
                    Jimat dijatuhin = inventaris.get(idx);

                    // Asumsi refund pukul rata 50.000, atau sesuaikan dengan mekanik aslimu
                    int hargaJual = 50000;

                    inventaris.remove(idx);
                    restoran.tambahUang(hargaJual);

                    // Jika jimat yang dijual sedang dipakai, copot
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

        panel.add(lblInstruksi);
        panel.add(txtFieldBox);
        panel.add(btnJual);
        panel.add(btnKembali);
        return panel;
    }

    private JPanel createGunakanPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setPreferredSize(new Dimension(0, 60));

        JLabel lblInstruksi = new JLabel("MASUKAN NOMOR JIMAT YANG MAU DIGUNAKAN");
        JTextField txtFieldBox = new JTextField(5);
        JButton btnGunakan = new JButton("GUNAKAN");
        JButton btnKembali = new JButton("KEMBALI");

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

        panel.add(lblInstruksi);
        panel.add(txtFieldBox);
        panel.add(btnGunakan);
        panel.add(btnKembali);
        return panel;
    }
}

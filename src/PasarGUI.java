import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PasarGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;
    private List<BahanBaku> bahanTersediaSaatIni;

    public PasarGUI(Restoran restoran) {
        this.restoran = restoran;
        this.bahanTersediaSaatIni = new ArrayList<>();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Background
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

        setTitle("Game Presto - Pasar (Beli Bahan Baku)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel atas
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("PASAR BAHAN BAKU", SwingConstants.CENTER);
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

        // Panel tengah
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        // Panel ilustrasi
        Image imgIlustrasiRaw = new ImageIcon(
                getClass().getResource("/asset/tokosembako.png")).getImage();

        JPanel ilustrasiPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgIlustrasiRaw, 0, 0, getWidth(), getHeight(), this);
            }
        };
        ilustrasiPanel.setOpaque(false);

        // Panel terminal
        Image imgTerminalRaw = new ImageIcon(
                getClass().getResource("/asset/terminal.png")).getImage();

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

        // Panel bawah
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 80));
        bottomPanel.setOpaque(false);

        // Input nomor bahan
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

        // Input jumlah bahan
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

        // Tombol beli
        ImageIcon iconBeli = new ImageIcon(getClass().getResource("/asset/beli.png"));
        Image img3 = iconBeli.getImage();
        Image resize3 = img3.getScaledInstance(135, 40, Image.SCALE_SMOOTH);
        iconBeli = new ImageIcon(resize3);
        JButton btnBeli = new JButton(iconBeli);
        btnBeli.setBorderPainted(false);
        btnBeli.setContentAreaFilled(false);
        btnBeli.setFocusPainted(false);
        btnBeli.setOpaque(false);

        // Tombol kembali
        ImageIcon iconBack = new ImageIcon(getClass().getResource("/asset/back.png"));
        Image img5 = iconBack.getImage();
        Image resize5 = img5.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        iconBack = new ImageIcon(resize5);
        JButton btnKembali = new JButton(iconBack);
        btnKembali.setPreferredSize(new Dimension(150, 40));
        btnKembali.setBorderPainted(false);
        btnKembali.setContentAreaFilled(false);
        btnKembali.setFocusPainted(false);

        // Logika beli bahan
        btnBeli.addActionListener(e -> {
            try {
                int inputNomor = Integer.parseInt(NomorBahan.getText());
                int jumlahBeli = Integer.parseInt(JumlahBahan.getText());

                if (inputNomor > 0 && inputNomor <= bahanTersediaSaatIni.size()) {
                    BahanBaku bahanDipilih = bahanTersediaSaatIni.get(inputNomor - 1);
                    int totalHarga = jumlahBeli * bahanDipilih.getHarga();

                    if (restoran.getUang() >= totalHarga) {
                        restoran.kurangiUang(totalHarga);

                        restoran.stok.put(bahanDipilih, restoran.stok.getOrDefault(bahanDipilih, 0) + jumlahBeli);

                        terminalArea.append("✅ Berhasil membeli " + jumlahBeli + " " + bahanDipilih.getNama() + " (Total: Rp " + totalHarga + ")\n");
                        updateStatusBar();
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

        btnKembali.addActionListener(e -> this.dispose());

        bottomPanel.add(labelNomor);
        bottomPanel.add(labelJumlah);
        bottomPanel.add(btnBeli);
        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        updateStatusBar();
        tampilkanDaftarBahanBaku();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    // Logika database bahan
    private void tampilkanDaftarBahanBaku() {
        terminalArea.setText("=== PASAR BAHAN BAKU ===\n");
        terminalArea.append("Uang Anda: Rp " + restoran.getUang() + "\n\n");

        bahanTersediaSaatIni.clear();
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
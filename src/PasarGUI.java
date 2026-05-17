
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

        setTitle("Game Presto - Pasar (Beli Bahan Baku)");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblTitle = new JLabel("PASAR", SwingConstants.CENTER);
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

        JPanel centerPanel = new JPanel(new BorderLayout(15, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblIlustrasi = new JLabel("", SwingConstants.CENTER) {
            private Image imgAsli;

            {
                try {
                    imgAsli = new ImageIcon("image/Pasar.png").getImage();
                } catch (Exception e) {
                    System.out.println("Gambar tidak ditemukan.");
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
                    setText("GAMBAR TIDAK DITEMUKAN");
                }
            }
        };
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminalArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        scrollTerminal.setPreferredSize(new Dimension(350, 0));

        centerPanel.add(lblIlustrasi, BorderLayout.CENTER);
        centerPanel.add(scrollTerminal, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 100));

        JLabel lblInstruksiNomor = new JLabel("<html><center>MASUKAN NOMOR BAHAN<br>BAKU YANG HENDAK<br>DIBELI</center></html>");
        JTextField txtNomorBahan = new JTextField(5);

        JLabel lblInstruksiJumlah = new JLabel("<html><center>MASUKAN JUMLAH BAHAN<br>BAKU YANG HENDAK<br>DIBELI</center></html>");
        JTextField txtJumlahBahan = new JTextField(5);

        JButton btnBeli = new JButton("BELI");
        JButton btnKembali = new JButton("KEMBALI");

        btnBeli.setPreferredSize(new Dimension(120, 45));
        btnBeli.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBeli.setBackground(new Color(46, 204, 113));
        btnBeli.setForeground(Color.WHITE);
        btnBeli.setFocusPainted(false);
        btnBeli.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnKembali.setPreferredSize(new Dimension(120, 45));
        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKembali.setBackground(new Color(231, 76, 60));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBeli.addActionListener(e -> {
            try {
                int inputNomor = Integer.parseInt(txtNomorBahan.getText());
                int jumlahBeli = Integer.parseInt(txtJumlahBahan.getText());

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

                txtNomorBahan.setText("");
                txtJumlahBahan.setText("");

            } catch (NumberFormatException ex) {
                terminalArea.append("❌ Harap masukkan input berupa angka!\n");
            }
        });

        btnKembali.addActionListener(e -> {
            this.dispose();
        });

        bottomPanel.add(lblInstruksiNomor);
        bottomPanel.add(txtNomorBahan);
        bottomPanel.add(lblInstruksiJumlah);
        bottomPanel.add(txtJumlahBahan);
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
        terminalArea.append("\n");
    }
}

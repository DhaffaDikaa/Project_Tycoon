
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

        // Pengaturan dasar Frame
        setTitle("Game Presto - Pasar (Beli Bahan Baku)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL ATAS (Status Bar) ---
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

        // --- 2. PANEL TENGAH (Ilustrasi & Terminal) ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblIlustrasi = new JLabel("GAMBAR ILUSTRASI PASAR", SwingConstants.CENTER);
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        centerPanel.add(lblIlustrasi);
        centerPanel.add(scrollTerminal);
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (2 Input Box & Tombol) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 100));

        JLabel lblInstruksiNomor = new JLabel("<html><center>MASUKAN NOMOR BAHAN<br>BAKU YANG HENDAK<br>DIBELI</center></html>");
        JTextField txtNomorBahan = new JTextField(5);

        JLabel lblInstruksiJumlah = new JLabel("<html><center>MASUKAN JUMLAH BAHAN<br>BAKU YANG HENDAK<br>DIBELI</center></html>");
        JTextField txtJumlahBahan = new JTextField(5);

        JButton btnBeli = new JButton("BELI");
        JButton btnKembali = new JButton("KEMBALI");

        btnBeli.setPreferredSize(new Dimension(100, 50));
        btnKembali.setPreferredSize(new Dimension(100, 50));

        // ========================================================
        // LOGIKA ASLI DARI beliBahan() SEKARANG PINDAH KE SINI
        // ========================================================
        btnBeli.addActionListener(e -> {
            try {
                int inputNomor = Integer.parseInt(txtNomorBahan.getText());
                int jumlahBeli = Integer.parseInt(txtJumlahBahan.getText());

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
        terminalArea.append("\n");
    }
}

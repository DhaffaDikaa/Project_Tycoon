
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DapurGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;
    private List<Menu> menuTersediaSaatIni; // Menyimpan daftar resep yang sudah terbuka

    public DapurGUI(Restoran restoran) {
        this.restoran = restoran;
        this.menuTersediaSaatIni = new ArrayList<>();

        setTitle("Game Presto - Dapur (Set Menu)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL ATAS (Status Bar) ---
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblTitle = new JLabel("DAPUR : SET MENU", SwingConstants.CENTER);
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

        JLabel lblIlustrasi = new JLabel("GAMBAR ILUSTRASI DAPUR", SwingConstants.CENTER);
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        centerPanel.add(lblIlustrasi);
        centerPanel.add(scrollTerminal);
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (Input Box & Tombol) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 80));

        JLabel lblInstruksi = new JLabel("<html><center>MASUKAN NOMOR MENU<br>YANG HENDAK DI AKTIF/NON-AKTIFKAN</center></html>");
        JTextField txtFieldBox = new JTextField(5);
        JButton btnPilihMenu = new JButton("PILIH MENU");
        JButton btnKembali = new JButton("KEMBALI");

        btnPilihMenu.setPreferredSize(new Dimension(150, 40));
        btnKembali.setPreferredSize(new Dimension(150, 40));

        // ========================================================
        // LOGIKA ASLI setMenuRestoran() PINDAH KE SINI
        // ========================================================
        btnPilihMenu.addActionListener(e -> {
            try {
                int inputNomor = Integer.parseInt(txtFieldBox.getText());

                if (inputNomor > 0 && inputNomor <= menuTersediaSaatIni.size()) {
                    Menu m = menuTersediaSaatIni.get(inputNomor - 1);

                    if (restoran.getMenu().containsKey(m)) {
                        restoran.getMenu().remove(m); // Non-aktifkan
                        JOptionPane.showMessageDialog(this, "➖ " + m.getNama() + " dihapus dari daftar menu hari ini.");
                    } else {
                        restoran.getMenu().put(m, true); // Aktifkan
                        JOptionPane.showMessageDialog(this, "➕ " + m.getNama() + " sekarang tersedia untuk pelanggan.");
                    }
                    tampilkanDaftarMenu(); // Refresh daftar setelah status berubah
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Nomor menu tidak tersedia di daftar!");
                }
                txtFieldBox.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Harap masukkan angka yang valid!");
            }
        });

        btnKembali.addActionListener(e -> this.dispose());

        bottomPanel.add(lblInstruksi);
        bottomPanel.add(txtFieldBox);
        bottomPanel.add(btnPilihMenu);
        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. TAMPILKAN DATA AWAL ---
        updateStatusBar();
        tampilkanDaftarMenu();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void tampilkanDaftarMenu() {
        terminalArea.setText("=== BUKU RESEP (Unlock by Level) ===\n\n");
        menuTersediaSaatIni.clear();

        for (Menu m : GameGenerateGUI.MASTER_MENU) {
            if (restoran.getLevel() >= m.getLevelMinimal()) {
                menuTersediaSaatIni.add(m);
                String status = restoran.getMenu().containsKey(m) ? "[AKTIF]" : "[NON-AKTIF]";
                terminalArea.append(menuTersediaSaatIni.size() + ". " + status + " " + m.getNama() + "\n");
            } else {
                terminalArea.append("X. [Resep Terkunci - Butuh Lv." + m.getLevelMinimal() + "]\n");
            }
        }
    }
}

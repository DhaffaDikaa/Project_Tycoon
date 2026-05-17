
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class GudangGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;

    public GudangGUI(Restoran restoran) {
        this.restoran = restoran;

        setTitle("Resto Tycoon - Gudang");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL ATAS (Status Bar) ---
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblTitle = new JLabel("GUDANG : CEK STOK", SwingConstants.CENTER);
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

        JLabel lblIlustrasi = new JLabel("GAMBAR ILUSTRASI GUDANG", SwingConstants.CENTER);
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        centerPanel.add(lblIlustrasi);
        centerPanel.add(scrollTerminal);
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (Tombol Kembali) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 60));

        JButton btnKembali = new JButton("KEMBALI");
        btnKembali.setPreferredSize(new Dimension(200, 40));
        btnKembali.addActionListener(e -> this.dispose());

        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. TAMPILKAN DATA ASLI ---
        updateStatusBar();
        tampilkanStok();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    // ========================================================
    // LOGIKA MEMBACA DATA STOK ASLI DARI OBJEK RESTORAN
    // ========================================================
    private void tampilkanStok() {
        terminalArea.setText("=== TERMINAL : STOK GUDANG SAAT INI ===\n\n");

        // Membaca map stok dari class Restoran
        if (restoran.stok == null || restoran.stok.isEmpty()) {
            terminalArea.append("Gudang kosong! Anda belum membeli bahan baku apapun di Pasar.\n");
        } else {
            int no = 1;
            for (Map.Entry<BahanBaku, Integer> entry : restoran.stok.entrySet()) {
                terminalArea.append(no + ". " + entry.getKey().getNama() + " : " + entry.getValue() + " unit\n");
                no++;
            }
        }
    }
}

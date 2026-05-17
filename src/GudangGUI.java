
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class GudangGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;

    public GudangGUI(Restoran restoran) {
        this.restoran = restoran;

        //IconBg
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

        setTitle("Resto Tycoon - Gudang");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 15));

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
        // PANEL TENGAH
        JPanel centerPanel = new JPanel(null);
        centerPanel.setOpaque(false);


        // ILUSTRASI KIRI
        ImageIcon icon = new ImageIcon(getClass().getResource("/asset/gudang.png"));
        JLabel lblIlustrasi = new JLabel(icon);
        lblIlustrasi.setBounds(10, 30, 425, 425);
        lblIlustrasi.setForeground(null);
        lblIlustrasi.setOpaque(false);


        // TERMINAL IMAGE
        ImageIcon iconTerminal = new ImageIcon(getClass().getResource("/asset/terminal.png"));

        Image img2 = iconTerminal.getImage();
        Image resize2 = img2.getScaledInstance(425, 425, Image.SCALE_SMOOTH);

        iconTerminal = new ImageIcon(resize2);

        JLabel terminalBg = new JLabel(iconTerminal);
        terminalBg.setLayout(null);

        terminalBg.setBounds(440, 25, 425, 425);

        // TEXT AREA
        terminalArea = new JTextArea();

        terminalArea.setEditable(false);
        terminalArea.setOpaque(false);

        terminalArea.setForeground(Color.GREEN);

        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 14));

        // posisi isi terminal
        terminalArea.setBounds(40, 60, 330, 180);

        terminalBg.add(terminalArea);


        centerPanel.add(lblIlustrasi);
        centerPanel.add(terminalBg);

        add(centerPanel, BorderLayout.CENTER);


        // --- 3. PANEL BAWAH (Tombol Kembali) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 60));

        //icon
        ImageIcon iconBack = new ImageIcon(getClass().getResource("/asset/back.png"));
        Image img = iconBack.getImage();
        Image resize = img.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        iconBack = new ImageIcon(resize);

        JButton btnKembali = new JButton(iconBack);
        btnKembali.setPreferredSize(new Dimension(150, 40));
        btnKembali.addActionListener(e -> this.dispose());

        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. TAMPILKAN DATA ASLI ---
        updateStatusBar();
        tampilkanStok();

        topPanel.setOpaque(false);
        centerPanel.setOpaque(false);
        terminalArea.setOpaque(false);
        bottomPanel.setOpaque(false);
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

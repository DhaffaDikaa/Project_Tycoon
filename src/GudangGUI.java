import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class GudangGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;

    private void fixMacTransparency() {
        System.setProperty("apple.awt.application.appearance", "system");
        UIManager.put("ScrollPane.background", new Color(0, 0, 0, 0));
        UIManager.put("Viewport.background",   new Color(0, 0, 0, 0));
        UIManager.put("ScrollBar.thumb",       new Color(100, 100, 100));
        UIManager.put("ScrollBar.track",       new Color(0, 0, 0, 0));
    }

    public GudangGUI(Restoran restoran) {
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

        setTitle("Resto Tycoon - Gudang");
        setSize(900, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 15));

        // ── 1. PANEL ATAS (Status Bar) ─────────────────────────────────────────
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("GUDANG", SwingConstants.CENTER);
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

        // ── 2. PANEL TENGAH — GridLayout agar scale saat MAXIMIZED_BOTH ────────
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        // ── Kiri: Panel ilustrasi gudang — skala via paintComponent ─────────────
        Image imgIlustrasiRaw = new ImageIcon(
                getClass().getResource("/asset/gudang.png")).getImage();

        JPanel ilustrasiPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgIlustrasiRaw, 0, 0, getWidth(), getHeight(), this);
            }
        };
        ilustrasiPanel.setOpaque(false);

        // ── Kanan: Panel terminal — frame terminal.png skala via paintComponent ──
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
        // Padding dalam agar teks tidak menempel bingkai gambar terminal
        terminalPanel.setBorder(BorderFactory.createEmptyBorder(55, 45, 60, 45));

        // TEXT AREA
        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setOpaque(false);
        terminalArea.setForeground(Color.GREEN);
        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 14));

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);
        scrollTerminal.setBorder(null);

        terminalPanel.add(scrollTerminal, BorderLayout.CENTER);

        centerPanel.add(ilustrasiPanel);
        centerPanel.add(terminalPanel);
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (Tombol Kembali) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 60));
        bottomPanel.setOpaque(false);

        ImageIcon iconBack = new ImageIcon(getClass().getResource("/asset/back.png"));
        Image img = iconBack.getImage();
        Image resize = img.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        iconBack = new ImageIcon(resize);

        JButton btnKembali = new JButton(iconBack);
        btnKembali.setPreferredSize(new Dimension(150, 40));
        btnKembali.setBorderPainted(false);
        btnKembali.setContentAreaFilled(false);
        btnKembali.setFocusPainted(false);
        btnKembali.addActionListener(e -> this.dispose());

        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. TAMPILKAN DATA ASLI ---
        updateStatusBar();
        tampilkanStok();
        fixMacTransparency();
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
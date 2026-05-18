import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class GudangGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;

    public GudangGUI(Restoran restoran) {
        this.restoran = restoran;

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

        setTitle("Resto Tycoon - Gudang");
        setSize(900, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 15));

        // Panel atas
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

        // Panel tengah
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        // Panel ilustrasi
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
        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 14));

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);
        scrollTerminal.setBorder(null);

        terminalPanel.add(scrollTerminal, BorderLayout.CENTER);

        centerPanel.add(ilustrasiPanel);
        centerPanel.add(terminalPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Panel bawah
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

        updateStatusBar();
        tampilkanStok();
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    // Logika stok
    private void tampilkanStok() {
        terminalArea.setText("=== TERMINAL : STOK GUDANG SAAT INI ===\n\n");

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
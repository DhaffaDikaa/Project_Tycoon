
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

        JPanel centerPanel = new JPanel(new BorderLayout(15, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblIlustrasi = new JLabel("", SwingConstants.CENTER) {
            private Image imgAsli;

            {
                try {
                    imgAsli = new ImageIcon("image/Gudang.png").getImage();
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 70));

        JButton btnKembali = new JButton("KEMBALI");
        btnKembali.setPreferredSize(new Dimension(200, 45));

        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnKembali.setBackground(new Color(231, 76, 60));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private void tampilkanStok() {
        terminalArea.setText("STOK GUDANG SAAT INI\n\n");

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

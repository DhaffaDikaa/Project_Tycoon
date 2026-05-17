
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DapurGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;
    private List<Menu> menuTersediaSaatIni;

    public DapurGUI(Restoran restoran) {
        this.restoran = restoran;
        this.menuTersediaSaatIni = new ArrayList<>();

        setTitle("Game Presto - Dapur (Set Menu)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

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

        JPanel centerPanel = new JPanel(new BorderLayout(15, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblIlustrasi = new JLabel("", SwingConstants.CENTER) {
            private Image imgAsli;

            {
                try {
            
                    imgAsli = new ImageIcon("image/Dapur.png").getImage();
                } catch (Exception e) {
                    System.out.println("Gambar dapur tidak ditemukan.");
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
                    setText("GAMBAR ILUSTRASI DAPUR TIDAK DITEMUKAN");
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 80));

        JLabel lblInstruksi = new JLabel("<html><center>MASUKAN NOMOR MENU<br>YANG HENDAK DI AKTIF/NON-AKTIFKAN</center></html>");
        lblInstruksi.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTextField txtFieldBox = new JTextField(5);
        txtFieldBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnPilihMenu = new JButton("PILIH MENU");
        JButton btnKembali = new JButton("KEMBALI");

        btnPilihMenu.setPreferredSize(new Dimension(150, 40));
        btnPilihMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPilihMenu.setBackground(new Color(46, 204, 113)); 
        btnPilihMenu.setForeground(Color.WHITE);
        btnPilihMenu.setFocusPainted(false);
        btnPilihMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnKembali.setPreferredSize(new Dimension(150, 40));
        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnKembali.setBackground(new Color(231, 76, 60)); 
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnPilihMenu.addActionListener(e -> {
            try {
                int inputNomor = Integer.parseInt(txtFieldBox.getText());

                if (inputNomor > 0 && inputNomor <= menuTersediaSaatIni.size()) {
                    Menu m = menuTersediaSaatIni.get(inputNomor - 1);

                    if (restoran.getMenu().containsKey(m)) {
                        restoran.getMenu().remove(m);
                        JOptionPane.showMessageDialog(this, "➖ " + m.getNama() + " dihapus dari daftar menu hari ini.");
                    } else {
                        restoran.getMenu().put(m, true);
                        JOptionPane.showMessageDialog(this, "➕ " + m.getNama() + " sekarang tersedia untuk pelanggan.");
                    }
                    tampilkanDaftarMenu();
                } else {
                    JOptionPane.showMessageDialog(this, "Nomor menu tidak tersedia di daftar!");
                }
                txtFieldBox.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harap masukkan angka yang valid!");
            }
        });

        btnKembali.addActionListener(e -> this.dispose());

        bottomPanel.add(lblInstruksi);
        bottomPanel.add(txtFieldBox);
        bottomPanel.add(btnPilihMenu);
        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

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

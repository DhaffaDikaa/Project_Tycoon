
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

        setTitle("Game Presto - Dapur (Set Menu)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

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
        ImageIcon ilustrasi = new ImageIcon(getClass().getResource("/asset/dapur.png"));
        JLabel lblIlustrasi = new JLabel(ilustrasi);
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

        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 10));

        // posisi isi terminal
        terminalArea.setBounds(40, 60, 330, 300);
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBounds(40, 60, 330, 280);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);

        terminalArea.setOpaque(false);

        terminalBg.add(scrollTerminal);


        centerPanel.add(lblIlustrasi);
        centerPanel.add(terminalBg);

        add(centerPanel, BorderLayout.CENTER);


        // --- 3. PANEL BAWAH (Input Box & Tombol) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 80));

        //JLabel lblInstruksi = new JLabel("<html><center>MASUKAN NOMOR MENU<br>YANG HENDAK DI AKTIF/NON-AKTIFKAN</center></html>");

        //Textfield
        ImageIcon iconField = new ImageIcon(getClass().getResource("/asset/dapur/kosong.png"));

        Image img4 = iconField.getImage();
        Image resize4 = img4.getScaledInstance(165, 45, Image.SCALE_SMOOTH);

        iconField = new ImageIcon(resize4);

        JLabel labelField = new JLabel(iconField);
        labelField.setLayout(null);

        JTextField txtFieldBox = new JTextField();

        txtFieldBox.setBounds(20, 5, 120, 40);

        txtFieldBox.setForeground(Color.WHITE);
        txtFieldBox.setOpaque(false);
        txtFieldBox.setBorder(null);
        txtFieldBox.setHorizontalAlignment(JTextField.CENTER);

        labelField.add(txtFieldBox);

        //IconPilih
        ImageIcon iconPilih = new ImageIcon(getClass().getResource("/asset/dapur/pilihMenu.png"));
        Image img3 = iconPilih.getImage();
        Image resize3 = img3.getScaledInstance(165, 40, Image.SCALE_SMOOTH);
        iconPilih = new ImageIcon(resize3);
        JButton btnPilihMenu = new JButton(iconPilih);

        //IconBack
        ImageIcon iconBack = new ImageIcon(getClass().getResource("/asset/back.png"));
        Image img = iconBack.getImage();
        Image resize = img.getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        iconBack = new ImageIcon(resize);

        JButton btnKembali = new JButton(iconBack);


        btnPilihMenu.setPreferredSize(new Dimension(150, 40));
        btnKembali.setPreferredSize(new Dimension(150, 40));
        //labelField.setPreferredSize(new Dimension(150, 40));

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

        bottomPanel.add(labelField);
        bottomPanel.add(btnPilihMenu);
        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. TAMPILKAN DATA AWAL ---
        updateStatusBar();
        tampilkanDaftarMenu();

        //Transparan
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

        terminalArea.append("MASUKAN NOMOR MENU YANG HENDAK\nDI AKTIF/NON-AKTIFKAN");
    }
}

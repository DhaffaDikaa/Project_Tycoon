
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class BukaRestoranGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextPane terminalPane;
    private PrintStream originalSystemOut;

    private Color currentColor = Color.BLACK;
    private final Color COLOR_BLUE = new Color(0, 102, 204);
    private final Color COLOR_RED = new Color(204, 0, 0);
    private final Color COLOR_GREEN = new Color(0, 153, 0);
    private final Color COLOR_ORANGE = new Color(230, 115, 0);
    private final Color COLOR_BLACK = new Color(30, 30, 30);

    public BukaRestoranGUI(Restoran restoran) {
        this.restoran = restoran;

        setTitle("Game Presto - Simulasi Buka Restoran");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblTitle = new JLabel("SIMULASI BUKA RESTORAN", SwingConstants.CENTER);
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
                    setText("GAMBAR SIMULASI TIDAK DITEMUKAN");
                }
            }
        };
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(new Color(250, 250, 250));
        terminalPane.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollTerminal = new JScrollPane(terminalPane);
        scrollTerminal.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        scrollTerminal.setPreferredSize(new Dimension(450, 0));

        centerPanel.add(lblIlustrasi, BorderLayout.CENTER);
        centerPanel.add(scrollTerminal, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 70));

        JButton btnKembali = new JButton("KEMBALI");
        btnKembali.setPreferredSize(new Dimension(200, 45));
        btnKembali.setEnabled(false);
        btnKembali.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnKembali.setBackground(new Color(231, 76, 60));
        btnKembali.setForeground(Color.WHITE);
        btnKembali.setFocusPainted(false);
        btnKembali.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnKembali.addActionListener(e -> {
            System.setOut(originalSystemOut);
            this.dispose();
        });

        bottomPanel.add(btnKembali);
        add(bottomPanel, BorderLayout.SOUTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.setOut(originalSystemOut);
                dispose();
            }
        });

        originalSystemOut = System.out;
        OutputStream out = new OutputStream() {
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                if (b == '\r') {
                    return;
                }
                buffer.append((char) b);
                if (b == '\n') {
                    flushBuffer();
                }
            }

            private void flushBuffer() {
                if (buffer.length() > 0) {
                    String text = buffer.toString();
                    Color colorToUse = currentColor;

                    String teksKecil = text.toLowerCase();

                    if (teksKecil.contains("alarm") || teksKecil.contains("kabur")
                            || teksKecil.contains("rugi") || teksKecil.contains("kecewa")
                            || teksKecil.contains("penuh") || teksKecil.contains("nasib buruk") || teksKecil.contains("gawat")) {
                        colorToUse = COLOR_RED;
                    } else if (teksKecil.contains("berhasil") || teksKecil.contains("tips")
                            || teksKecil.contains("efek jimat") || teksKecil.contains("diusir")) {
                        colorToUse = COLOR_GREEN;
                    }

                    appendToTerminal(text, colorToUse);
                    buffer.setLength(0);
                }
            }

            @Override
            public void flush() {
                flushBuffer();
            }
        };
        System.setOut(new PrintStream(out, true));

        updateStatusBar();
        jalankanSimulasiRestoran(btnKembali);
    }

    private void changeColor(Color c) {
        System.out.flush();
        currentColor = c;
    }

    private void appendToTerminal(String text, Color c) {
        SwingUtilities.invokeLater(() -> {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Segoe UI");
            aset = sc.addAttribute(aset, StyleConstants.FontSize, 14);

            try {
                int len = terminalPane.getDocument().getLength();
                terminalPane.getDocument().insertString(len, text, aset);
            } catch (Exception e) {
            }
            terminalPane.setCaretPosition(terminalPane.getDocument().getLength());
        });
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void jalankanSimulasiRestoran(JButton btnKembali) {
        new Thread(() -> {

            changeColor(COLOR_ORANGE);
            System.out.println("=== RESTORAN RESMI DIBUKA ===");
            System.out.println("=== (Kapasitas Maksimal: " + restoran.getKapasitas() + ") ===");

            changeColor(COLOR_BLACK);
            Jimat jimatAktif = restoran.getJimatAktif();
            boolean hasCleaner = (jimatAktif instanceof JimatCleaner);

            if (jimatAktif != null) {
                System.out.println("\n[INFO] Jimat Aktif Hari Ini: " + jimatAktif.getNama());
            }

            System.out.println("Menunggu pelanggan datang...\n");

            Random ran = new Random();
            int totalTamu = 0;
            int kursiTerisi = 0;

            for (int i = 0; i < 5; i++) {
                try {
                    int waktuTunggu = ran.nextInt(2001) + 2000;
                    Thread.sleep(waktuTunggu);
                } catch (InterruptedException e) {
                }

                if (ran.nextInt(100) < 20) {
                    if (hasCleaner) {
                        changeColor(COLOR_GREEN);
                        System.out.println("[CLEANER AKTIF] Ada tikus mengendus gudang, tapi langsung diusir!");
                    } else {
                        changeColor(COLOR_RED);
                        System.out.println("GAWAT! Seekor tikus masuk ke gudang!");
                        if (!restoran.stok.isEmpty()) {
                            List<BahanBaku> daftarBahan = new ArrayList<>(restoran.stok.keySet());
                            BahanBaku bahanDimakan = daftarBahan.get(ran.nextInt(daftarBahan.size()));
                            int sisaStok = restoran.stok.get(bahanDimakan);

                            if (sisaStok > 0) {
                                restoran.stok.put(bahanDimakan, sisaStok - 1);
                                System.out.println("-> Tikus memakan 1 unit " + bahanDimakan.getNama() + " Anda!");
                            }
                        } else {
                            System.out.println("-> Gudang kosong, tikus pergi mencari tempat lain.");
                        }
                    }
                    System.out.println("");
                }

                int maxRandom = Math.max(1, restoran.getKapasitas() - 3);
                int jml = ran.nextInt(maxRandom) + 1;

                changeColor(COLOR_BLUE);
                System.out.println("--------------------------------------------------");
                System.out.println("[Rombongan " + (i + 1) + "] " + jml + " orang datang...");

                if (kursiTerisi + jml <= restoran.getKapasitas()) {
                    kursiTerisi += jml;
                    System.out.println("Tamu duduk. (Kursi terpakai: " + kursiTerisi + "/" + restoran.getKapasitas() + ")");
                    System.out.println("");

                    changeColor(COLOR_BLACK);
                    totalTamu += jml;

                    Pelanggan p = new Pelanggan(jml, restoran);
                    p.pilihMenu(restoran);
                    p.selesaikanTransaksi(restoran);
                   

                    changeColor(COLOR_BLUE);
                    kursiTerisi -= jml;
                    System.out.println("");
                    System.out.println("Tamu selesai dan pergi. Kursi kosong kembali.");
                    System.out.println("--------------------------------------------------\n");
                    SwingUtilities.invokeLater(this::updateStatusBar);

                } else {
                    changeColor(COLOR_RED);
                    System.out.println("Restoran penuh! " + jml + " tamu batal pesan dan pergi.");
                    System.out.println("--------------------------------------------------\n");
                }
            }

            changeColor(COLOR_ORANGE);
            restoran.tambahExp(totalTamu * 20);
            System.out.println("=== HARI BERAKHIR ===");
            System.out.println("Total tamu yang dilayani: " + totalTamu);
            System.out.println("\nSILAKAN KLIK TOMBOL KEMBALI.");

            SwingUtilities.invokeLater(() -> {
                updateStatusBar();
                btnKembali.setEnabled(true);
                btnKembali.addActionListener(e -> this.dispose());

            });
        }).start();
    }
}

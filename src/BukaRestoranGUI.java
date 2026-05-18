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

    private Color currentColor = Color.GREEN;
    private final Color COLOR_BLUE   = new Color(100, 180, 255);
    private final Color COLOR_RED    = new Color(255,  80,  80);
    private final Color COLOR_GREEN  = new Color( 80, 220,  80);
    private final Color COLOR_ORANGE = new Color(255, 180,  50);
    private final Color COLOR_BLACK  = new Color(200, 200, 200);

    public BukaRestoranGUI(Restoran restoran) {
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

        setTitle("Game Presto - Simulasi Buka Restoran");
        setSize(900, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel atas
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

        // Panel tengah
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        // Panel ilustrasi
        Image imgIlustrasiRaw = new ImageIcon(
                getClass().getResource("/asset/dapur1.png")).getImage();

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

        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setOpaque(false);

        JScrollPane scrollTerminal = new JScrollPane(terminalPane);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);
        scrollTerminal.setBorder(null);

        terminalPanel.add(scrollTerminal, BorderLayout.CENTER);

        centerPanel.add(ilustrasiPanel);
        centerPanel.add(terminalPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Panel bawah
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setPreferredSize(new Dimension(0, 70));
        buttonPanel.setOpaque(false);

        JButton btnKembali;
        try {
            ImageIcon iconBack = new ImageIcon(getClass().getResource("/asset/back.png"));
            Image imgBack = iconBack.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
            btnKembali = new JButton(new ImageIcon(imgBack));
            btnKembali.setBorderPainted(false);
            btnKembali.setContentAreaFilled(false);
            btnKembali.setFocusPainted(false);
            btnKembali.setPreferredSize(new Dimension(150, 40));
        } catch (Exception ex) {
            btnKembali = new JButton("KEMBALI KE MENU");
            btnKembali.setPreferredSize(new Dimension(250, 50));
        }

        btnKembali.setEnabled(false);
        buttonPanel.add(btnKembali);
        add(buttonPanel, BorderLayout.SOUTH);

        btnKembali.addActionListener(e -> {
            System.setOut(originalSystemOut);
            new MainMenuGUI(restoran).setVisible(true);
            this.dispose();
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.setOut(originalSystemOut);
                dispose();
            }
        });

        updateStatusBar();
        mulaiSimulasiThread(btnKembali);
    }

    private void changeColor(Color c) {
        System.out.flush();
        currentColor = c;
    }

    private void appendToTerminal(String text, Color c) {
        SwingUtilities.invokeLater(() -> {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Monospaced");
            aset = sc.addAttribute(aset, StyleConstants.Bold, true);
            aset = sc.addAttribute(aset, StyleConstants.FontSize, 12);

            try {
                int len = terminalPane.getDocument().getLength();
                terminalPane.getDocument().insertString(len, text, aset);
            } catch (Exception e) { }
            terminalPane.setCaretPosition(terminalPane.getDocument().getLength());
        });
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    private void mulaiSimulasiThread(JButton btnKembali) {
        if (restoran.getMenu().isEmpty()) {
            appendToTerminal("❌ Gagal buka! Anda belum menentukan menu di Dapur.\n", COLOR_RED);
            btnKembali.setEnabled(true);
            return;
        }

        originalSystemOut = System.out;

        OutputStream out = new OutputStream() {
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                if (b == '\r') return;
                buffer.append((char) b);
                if (b == '\n') flushBuffer();
            }

            private void flushBuffer() {
                if (buffer.length() == 0) return;
                String text = buffer.toString();
                Color colorToUse = currentColor;

                String teksKecil = text.toLowerCase();
                if (teksKecil.contains("alarm")    || teksKecil.contains("kabur")
                        || teksKecil.contains("rugi")     || teksKecil.contains("kecewa")
                        || teksKecil.contains("penuh")    || teksKecil.contains("nasib buruk")
                        || teksKecil.contains("gawat")    || teksKecil.contains("❌")) {
                    colorToUse = COLOR_RED;
                } else if (teksKecil.contains("berhasil") || teksKecil.contains("tips")
                        || teksKecil.contains("efek jimat") || teksKecil.contains("diusir")
                        || teksKecil.contains("cleaner aktif")) {
                    colorToUse = COLOR_GREEN;
                }

                appendToTerminal(text, colorToUse);
                buffer.setLength(0);
            }

            @Override
            public void flush() { flushBuffer(); }
        };
        System.setOut(new PrintStream(out, true));


        new Thread(() -> {

            changeColor(COLOR_ORANGE);
            System.out.println("=== RESTORAN DIBUKA (Kapasitas Maksimal: " + restoran.getKapasitas() + ") ===");

            changeColor(COLOR_BLACK);
            Jimat jimatAktif = restoran.getJimatAktif();
            boolean hasCleaner = (jimatAktif instanceof JimatCleaner);

            if (jimatAktif != null) {
                System.out.println("[INFO] Jimat Aktif Hari Ini: " + jimatAktif.getNama());
            }

            System.out.println("⏳ Menunggu pelanggan datang...\n");

            Random ran = new Random();
            int totalTamu = 0;
            int kursiTerisi = 0;

            for (int i = 0; i < 5; i++) {
                try {
                    int waktuTunggu = ran.nextInt(2001) + 2000;
                    Thread.sleep(waktuTunggu);
                } catch (InterruptedException e) {
                    System.out.println("Waktu tunggu terganggu!");
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
                    System.out.println("Restoran penuh! " + jml + " tamu tidak jadi pesan dan pergi.");
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
            });

        }).start();
    }
}
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import javax.swing.*;

public class BukaRestoranGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JTextArea terminalArea;
    private PrintStream originalSystemOut; // Untuk menyimpan System.out asli

    public BukaRestoranGUI(Restoran restoran) {
        this.restoran = restoran;

        // ── Background Panel ───────────────────────────────────────────────────
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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Buka langsung penuh
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Cegah tutup saat simulasi jalan
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

        // ── 2. PANEL TENGAH — GridLayout agar scale saat MAXIMIZED_BOTH ────────
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        // ── Kiri: Panel ilustrasi — gambar skala mengikuti ukuran panel ─────────
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

        // Text area di dalam terminal
        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setOpaque(false);
        terminalArea.setForeground(Color.GREEN);
        terminalArea.setFont(new Font("Monospaced", Font.BOLD, 10));

        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setOpaque(false);
        scrollTerminal.getViewport().setOpaque(false);
        scrollTerminal.setBorder(null);

        terminalPanel.add(scrollTerminal, BorderLayout.CENTER);

        centerPanel.add(ilustrasiPanel);
        centerPanel.add(terminalPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ── 3. PANEL BAWAH (Tombol Kembali) ───────────────────────────────────
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setPreferredSize(new Dimension(0, 70));
        buttonPanel.setOpaque(false);

        // Tombol kembali berikon (fallback teks jika aset tidak ada)
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

        btnKembali.setEnabled(false); // Dimatikan sementara sampai simulasi selesai

        buttonPanel.add(btnKembali);
        add(buttonPanel, BorderLayout.SOUTH);

        // ── Logika tombol kembali ──────────────────────────────────────────────
        btnKembali.addActionListener(e -> {
            new MainMenuGUI(restoran).setVisible(true);
            this.dispose();
        });

        updateStatusBar();
        mulaiSimulasiThread(btnKembali);
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    // ========================================================
    // LOGIKA SIMULASI MENGGUNAKAN THREAD AGAR GUI TIDAK FREEZE
    // ========================================================
    private void mulaiSimulasiThread(JButton btnKembali) {
        if (restoran.getMenu().isEmpty()) {
            terminalArea.setText("❌ Gagal buka! Anda belum menentukan menu di Dapur.\n");
            btnKembali.setEnabled(true);
            return;
        }

        // Trik: Mengalihkan System.out.println agar masuk ke terminalArea GUI
        originalSystemOut = System.out;
        PrintStream customOut = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Diarahkan secara thread-safe ke TextArea GUI
                SwingUtilities.invokeLater(() -> {
                    terminalArea.append(String.valueOf((char) b));
                    // Otomatis scroll ke bawah
                    terminalArea.setCaretPosition(terminalArea.getDocument().getLength());
                });
            }
        });
        System.setOut(customOut); // Pasang alat penyadap print

        // Membuat proses latar belakang agar jeda (sleep) tidak membuat aplikasi macet
        new Thread(() -> {
            System.out.println("=== RESTORAN DIBUKA (Kapasitas Maksimal: " + restoran.getKapasitas() + ") ===");
            System.out.println("⏳ Menunggu pelanggan datang...\n");

            Random ran = new Random();
            int totalTamu = 0;
            int kursiTerisi = 0;

            for (int i = 0; i < 5; i++) {
                try {
                    int waktuTunggu = ran.nextInt(2001) + 2000;
                    Thread.sleep(waktuTunggu); // Delay 2-4 detik
                } catch (InterruptedException e) {
                    System.out.println("Waktu tunggu terganggu!");
                }

                // Cek aman untuk Random nextInt (jika kapasitas terlalu kecil)
                int maxRandom = Math.max(1, restoran.getKapasitas() - 3);
                int jml = ran.nextInt(maxRandom) + 1;
                System.out.println("[Rombongan " + (i + 1) + "] " + jml + " orang datang...");

                if (kursiTerisi + jml <= restoran.getKapasitas()) {
                    kursiTerisi += jml;
                    System.out.println("Tamu duduk. (Kursi terpakai: " + kursiTerisi + "/" + restoran.getKapasitas() + ")");

                    totalTamu += jml;
                    Pelanggan p = new Pelanggan(jml, restoran);
                    p.pilihMenu(restoran); // Output diprint otomatis ke GUI
                    p.selesaikanTransaksi(restoran); // Uang dll bertambah

                    kursiTerisi -= jml;
                    System.out.println("Tamu selesai dan pergi. Kursi kosong kembali.\n");
                } else {
                    System.out.println("Restoran penuh! " + jml + " tamu tidak jadi pesan dan pergi.\n");
                }
            }

            restoran.tambahExp(totalTamu * 20);
            System.out.println("=== HARI BERAKHIR ===");
            System.out.println("Total tamu yang dilayani: " + totalTamu);
            System.out.println("\nSILAKAN KLIK TOMBOL KEMBALI.");

            // Kembalikan System.out ke aslinya
            System.setOut(originalSystemOut);

            // Perbarui tampilan status bar dengan data terbaru dan nyalakan tombol kembali
            SwingUtilities.invokeLater(() -> {
                updateStatusBar();
                btnKembali.setEnabled(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            });
        }).start();
    }
}
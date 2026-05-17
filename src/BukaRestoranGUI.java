
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

        setTitle("Game Presto - Simulasi Buka Restoran");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Cegah tutup saat simulasi jalan
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

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        JLabel lblIlustrasi = new JLabel("GAMBAR ILUSTRASI RESTORAN", SwingConstants.CENTER);
        lblIlustrasi.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnKembali = new JButton("KEMBALI KE MENU");
        btnKembali.setPreferredSize(new Dimension(250, 50));
        btnKembali.setEnabled(false); // Dimatikan sementara sampai simulasi selesai
        buttonPanel.add(btnKembali);

        leftPanel.add(lblIlustrasi, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollTerminal = new JScrollPane(terminalArea);
        scrollTerminal.setBorder(BorderFactory.createTitledBorder("TERMINAL : PESAN DARI PROGRAM"));

        centerPanel.add(leftPanel);
        centerPanel.add(scrollTerminal);
        add(centerPanel, BorderLayout.CENTER);

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

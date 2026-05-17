import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TutorialGUI extends JFrame {

    private Restoran restoran;
    private JLabel lblLevel, lblUang, lblKapasitas;
    private JLabel lblIlustrasi, lblStepTitle;
    private JTextArea txtDesc;
    private JButton btnPrev, btnNext;

    // Data Tutorial Berbasis Langkah
    private List<TutorialStep> tutorialSteps;
    private int currentStepIndex = 0;

    // Inner class untuk menampung data satu langkah
    private class TutorialStep {

        String title;
        String description;
        String imagePlaceholderText; // Placeholder untuk gambar (sementara)

        TutorialStep(String title, String description, String imagePlaceholderText) {
            this.title = title;
            this.description = description;
            this.imagePlaceholderText = imagePlaceholderText;
        }
    }

    public TutorialGUI(Restoran restoran) {
        this.restoran = restoran;
        this.tutorialSteps = new ArrayList<>();
        initTutorialData(); // Isi data tutorial lengkap

        // ── Background Panel (sama seperti DapurGUI) ──────────────────────────
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

        // Pengaturan dasar Frame Tutorial
        setTitle("Resto Tycoon - Panduan Tutorial Bermain");
        setSize(950, 650);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Buka langsung maksimal
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. PANEL ATAS (Status Bar) ---
        JPanel topPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        topPanel.setOpaque(false);

        // Membuat slot pertama menjadi teks kustom "TUTORIAL"
        JLabel lblTuto = new JLabel("TUTORIAL", SwingConstants.CENTER);
        lblTuto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblTuto.setForeground(Color.WHITE);

        lblLevel = new JLabel("", SwingConstants.CENTER);
        lblLevel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblLevel.setForeground(Color.WHITE);

        lblUang = new JLabel("", SwingConstants.CENTER);
        lblUang.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblUang.setForeground(Color.WHITE);

        lblKapasitas = new JLabel("", SwingConstants.CENTER);
        lblKapasitas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblKapasitas.setForeground(Color.WHITE);

        topPanel.add(lblTuto); // Slot 1 diganti
        topPanel.add(lblLevel);
        topPanel.add(lblUang);
        topPanel.add(lblKapasitas);
        add(topPanel, BorderLayout.NORTH);

        // --- 2. PANEL TENGAH (Ilustrasi & Terminal) ---
        // GridLayout agar kedua sisi ikut resize saat MAXIMIZED_BOTH
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        centerPanel.setOpaque(false);

        // Kiri: Panel ilustrasi — label mengisi penuh via BorderLayout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        lblIlustrasi = new JLabel("", SwingConstants.CENTER);
        lblIlustrasi.setHorizontalAlignment(SwingConstants.CENTER);
        lblIlustrasi.setVerticalAlignment(SwingConstants.CENTER);
        lblIlustrasi.setFont(new Font("Arial", Font.ITALIC, 13));
        lblIlustrasi.setForeground(Color.WHITE);
        leftPanel.add(lblIlustrasi, BorderLayout.CENTER);

        // Kanan: Panel terminal — terminal.png digambar via paintComponent agar scale ikut ukuran panel
        Image imgTerminalRaw = new ImageIcon(getClass().getResource("/asset/terminal.png")).getImage();

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

        // Text area deskripsi di dalam terminal
        txtDesc = new JTextArea();
        txtDesc.setEditable(false);
        txtDesc.setOpaque(false);
        txtDesc.setForeground(Color.GREEN);
        txtDesc.setFont(new Font("Monospaced", Font.BOLD, 10));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);

        JScrollPane scrollDesc = new JScrollPane(txtDesc);
        scrollDesc.setOpaque(false);
        scrollDesc.getViewport().setOpaque(false);
        scrollDesc.setBorder(null);

        terminalPanel.add(scrollDesc, BorderLayout.CENTER);

        centerPanel.add(leftPanel);
        centerPanel.add(terminalPanel);
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. PANEL BAWAH (Navigasi Berbasis Desain Ralat) ---
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 80));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomPanel.setOpaque(false);

        // Kiri Bawah: Tombol Kembali
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
            btnKembali = new JButton("KEMBALI");
            btnKembali.setPreferredSize(new Dimension(150, 40));
        }

        JPanel panelKembali = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelKembali.setOpaque(false);
        panelKembali.add(btnKembali);
        bottomPanel.add(panelKembali, BorderLayout.WEST);

        // Tengah Bawah: Navigasi (Panah - Teks - Panah)
        JPanel navWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navWrapper.setOpaque(false);

        // Membuat tombol panah menggunakan ikon kiri.png dan kanan.png

            ImageIcon iconKiri = new ImageIcon(getClass().getResource("/asset/tutorial/kiri.png"));
            Image imgKiri = iconKiri.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            btnPrev = new JButton(new ImageIcon(imgKiri));

        btnPrev.setPreferredSize(new Dimension(80, 50));
        btnPrev.setContentAreaFilled(false);
        btnPrev.setBorderPainted(false);
        btnPrev.setFocusPainted(false);


            ImageIcon iconKanan = new ImageIcon(getClass().getResource("/asset/tutorial/kanan.png"));
            Image imgKanan = iconKanan.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            btnNext = new JButton(new ImageIcon(imgKanan));

        btnNext.setPreferredSize(new Dimension(80, 50));
        btnNext.setContentAreaFilled(false);
        btnNext.setBorderPainted(false);
        btnNext.setFocusPainted(false);

        // Label untuk Judul Step
        lblStepTitle = new JLabel("NAMA STEP TUTORIAL", SwingConstants.CENTER);
        lblStepTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblStepTitle.setForeground(Color.WHITE);
        lblStepTitle.setPreferredSize(new Dimension(450, 30));

        // Menambah ke panel nav (Kiri ke Kanan)
        navWrapper.add(btnPrev);
        navWrapper.add(lblStepTitle);
        navWrapper.add(btnNext);
        bottomPanel.add(navWrapper, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- 4. ACTION LISTENERS ---
        btnPrev.addActionListener(e -> navigateTutorial(-1));
        btnNext.addActionListener(e -> navigateTutorial(1));
        btnKembali.addActionListener(e -> this.dispose()); // Tutup tutorial

        // --- 5. INITIALIZE DISPLAY ---
        updateStatusBar();
        displayCurrentStep(); // Tampilkan langkah pertama
    }

    private void updateStatusBar() {
        lblLevel.setText("LEVEL : " + restoran.getLevel());
        lblUang.setText("UANG : Rp " + restoran.getUang());
        lblKapasitas.setText("KAPASITAS : " + restoran.getKapasitas());
    }

    // Method untuk menangani navigasi (panah kiri/kanan)
    private void navigateTutorial(int direction) {
        currentStepIndex += direction;

        // Loop kembali jika di luar batas
        if (currentStepIndex < 0) {
            currentStepIndex = tutorialSteps.size() - 1;
        } else if (currentStepIndex >= tutorialSteps.size()) {
            currentStepIndex = 0;
        }

        displayCurrentStep();
    }

    // Method untuk menampilkan langkah tutorial saat ini (Judul, Teks, Gambar Placeholder)
    private void displayCurrentStep() {
        if (tutorialSteps.isEmpty()) {
            return;
        }

        TutorialStep step = tutorialSteps.get(currentStepIndex);

        // Update Judul di bagian navigasi bawah
        lblStepTitle.setText(step.title);

        // Update Deskripsi di panel kanan
        txtDesc.setText("PANDUAN DETAIL:\n\n" + step.description);
        txtDesc.setCaretPosition(0); // Scroll ke atas

    }

    // ========================================================
    // DATA KATA-KATA TUTORIAL BERBASIS LANGKAH (Step-by-Step)
    // ========================================================
    private void initTutorialData() {
        // [Langkah 1] Halaman Utama
        tutorialSteps.add(new TutorialStep(
                "1. Halaman Utama (Main Menu)",
                "Selamat Datang di Resto Tycoon!\n\n"
                        + "Ini adalah gerbang utama petualangan kuliner Anda.\n\n"
                        + "- Klik 'PERSIPAN' untuk belanja bahan, set menu resep, cek stok gudang, dan pasang booster jimat.\n"
                        + "- Klik 'BUKA RESTORAN' untuk memulai simulasi hari di mana pelanggan akan berdatangan.\n"
                        + "- Klik 'SAVE & EXIT' untuk menyimpan seluruh progress Level, Uang, Stok, dan Jimat Anda.",
                "menu_utama.png" // Placeholder nama file gambar ilustrasi
        ));

        // [Langkah 2] Pasar (Persiapan 1)
        tutorialSteps.add(new TutorialStep(
                "2. Persiapan: Pasar (Belanja)",
                "Langkah pertama adalah memastikan restoran Anda memiliki bahan baku.\n\n"
                        + "- Masuk ke menu PASAR untuk melihat daftar bahan baku yang tersedia.\n"
                        + "- Daftar bahan akan terbuka (unlocked) seiring kenaikan Level Anda.\n"
                        + "- Masukkan NOMOR bahan baku dan JUMLAH beli di kotak input bawah, lalu klik 'BELI'.\n"
                        + "- Jangan sampai kehabisan uang modal!",
                "pasar.png"
        ));

        // [Langkah 3] Dapur (Persiapan 2)
        tutorialSteps.add(new TutorialStep(
                "3. Persiapan: Dapur (Set Menu)",
                "Tentukan hidangan apa saja yang bisa dipesan pelanggan hari ini!\n\n"
                        + "- Masuk ke menu DAPUR untuk melihat buku resep resep masakan Anda.\n"
                        + "- Masukkan NOMOR resep di kotak input bawah.\n"
                        + "- Status resep akan berubah dari [NON-AKTIF] menjadi [AKTIF] atau sebaliknya.\n"
                        + "- Pelanggan hanya bisa memesan menu yang berstatus [AKTIF].",
                "dapur.png"
        ));

        // [Langkah 4] Gudang (Persiapan 3)
        tutorialSteps.add(new TutorialStep(
                "4. Persiapan: Cek Stok Gudang",
                "Pastikan stok bahan mencukupi sebelum restoran dibuka!\n\n"
                        + "- Masuk ke menu GUDANG untuk melihat daftar sisa bahan baku yang Anda miliki.\n"
                        + "- Pantau sisa unit unit bahan agar simulasi hari ini tidak gagal memasak.",
                "gudang.png"
        ));

        // [Langkah 5] Toko Jimat (Persiapan 4)
        tutorialSteps.add(new TutorialStep(
                "5. Persiapan: Toko Jimat (Booster)",
                "Jimat memberikan efek pasif yang menguntungkan!\n\n"
                        + "- Masuk ke menu TOKO JIMAT untuk membeli booster.\n"
                        + "- Pilih di antara tiga tipe: Charming, Security, atau Cleaner.\n"
                        + "- SETELAH BELI, Anda harus masuk ke 'GUNAKAN JIMAT' agar efek booster tersebut aktif.",
                "toko_jimat.png"
        ));

        // [Langkah 6] Buka Restoran (Simulasi)
        tutorialSteps.add(new TutorialStep(
                "6. Buka Restoran (Simulasi Hari)",
                "Saatnya simulasi berjalan secara otomatis!\n\n"
                        + "- Rombongan pelanggan akan datang secara acak (real-time).\n"
                        + "- Sistem akan memotong stok bahan baku di Gudang, memasak, dan menambah pendapatan Uang serta poin EXP otomatis ke akun Anda.\n"
                        + "- Jangan lupa, jimat yang Anda pasang akan memberikan efek di sini.",
                "buka_restoran.png"
        ));

        // [Langkah 7] Simpan Progres
        tutorialSteps.add(new TutorialStep(
                "7. Simpan Progres (Save Game)",
                "Jangan kehilangan progress Level dan Uang Anda!\n\n"
                        + "- Selalu klik tombol 'SAVE & EXIT' dari menu utama sebelum keluar dari permainan.\n"
                        + "- Progres Level, Modal Kas, Sisa Stok Gudang, dan Inventaris Jimat Anda akan tersimpan aman.",
                "save_game.png"
        ));
    }

    // Main method untuk tes mandiri
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Restoran dummy = new Restoran();
            dummy.tambahUang(500000); // Beri uang fiktif agar status bar terlihat nyata
            new TutorialGUI(dummy).setVisible(true);
        });
    }
}

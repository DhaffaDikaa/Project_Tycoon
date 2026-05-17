import javax.swing.*;
import java.awt.*;

public class SembakoGUI extends JFrame {

    private JPanel panelEtalase;
    boolean sedangInputJumlah = false;

    int barangDipilih = -1;

    SembakoGUI() {

        setTitle("Toko Sembako");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon bg = new ImageIcon(getClass().getResource("/assets/openingmenu.png"));
        JLabel background = new JLabel(bg);
        background.setLayout(null);
        setContentPane(background);

        Restoran r = new Restoran();
        HUDPanel hud = new HUDPanel(r);
        hud.setBounds(0, 0, 1600, 100);
        background.add(hud);

        //Terminal
        ImageIcon terminalBg = new ImageIcon(getClass().getResource("/assets/terminalSembako.png"));
        Image terminal = terminalBg.getImage();
        Image resize = terminal.getScaledInstance(600, 700, Image.SCALE_SMOOTH);
        ImageIcon terminalIcon = new ImageIcon(resize);
        JLabel terminalBox = new JLabel(terminalIcon);
        terminalBox.setBounds(850, 80, 600, 700);
        terminalBox.setLayout(null);
        background.add(terminalBox);

        JTextArea output = new JTextArea();
        output.setBounds(40, 70, 540, 620);
        output.setEditable(false);
        output.setOpaque(false);
        output.setForeground(Color.WHITE);
        output.append("Selamat datang di toko sembako !\nSilahkan pilih bahan yang ingin di beli dengan memasukkan angka sesuai dengan\nurutan di etalase kiri\n\n");


        terminalBox.add(output);

        //Input
        ImageIcon inputJimat = new ImageIcon(getClass().getResource("/assets/inputJimat.png"));
        JLabel inputBg = new JLabel(inputJimat);
        inputBg.setBounds(850, 800, 600, 100);
        inputBg.setLayout(null);
        background.add(inputBg);

        JTextField input = new JTextField();
        input.setBounds(30, 20, 540, 60);
        input.setOpaque(false);
        input.setBackground(new Color(0, 0, 0, 0));
        input.setForeground(Color.WHITE);
        //input.setCaretColor(Color.WHITE);
        input.setBorder(null);
        inputBg.add(input);


        //back
        ImageIcon back = new ImageIcon(getClass().getResource("/assets/back.png"));
        Image res = back.getImage();
        Image resize2 = res.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon resizeIcon = new ImageIcon(resize2);
        JButton iconBack = new JButton(resizeIcon);
        iconBack.setBounds(30, 820, 70, 70);
        background.add(iconBack);

        iconBack.addActionListener(e -> {
            new PersiapanGUI();
            dispose();
        });

        // DATA BARANG
        String[] namaBarang = {
                "Nasi", "Telur", "Minyak", "Gula",//1
                "Cabai", "Tepung", "Kentang",//2
                "Ayam", "Kopi", "Susu",//3
                "Mie", "Keju",//4
                "Buah", "Seafood",//5
                "Daging"//6
        };

        int[] hargaBarang = {
                4000, 3000, 3000, 2000,//1
                2000, 3000, 5000,//2
                8000, 5000, 4000,//3
                5000, 7000,//4
                7000, 15000,//5
                18000//6
        };

        int[] levelBarang = {
                0, 0, 0, 0,
                2, 2, 3,
                5, 5, 6,
                7, 8,
                10, 12,
                14
        };


        //Aksi
        input.addActionListener(e -> {
            String teks = input.getText();
            output.append("> " + teks + "\n");
            // MODE PILIH BARANG
            if (!sedangInputJumlah) {
                int a = Integer.parseInt(teks);
                if (a > 0 && a < 16) {
                    barangDipilih = a - 1;
                    output.append("Masukkan jumlah untuk " + namaBarang[barangDipilih] + " : \n");
                    sedangInputJumlah = true;
                }

                hud.updateHUD();
            }
            // MODE INPUT JUMLAH
            else {
                int jumlah = Integer.parseInt(teks);
                int harga = jumlah * hargaBarang[barangDipilih];
                output.append("Anda membeli " + jumlah + " " + namaBarang[barangDipilih] + "\n");
                output.append("Seharga : " + harga + "\n");
                r.kurangiUang(harga);
                // RESET KE MODE AWAL
                sedangInputJumlah = false;
            }

            input.setText("");

            hud.updateHUD();

        });

        //Blok
        panelEtalase = new JPanel();
        panelEtalase.setLayout(new BoxLayout(panelEtalase, BoxLayout.Y_AXIS));
        panelEtalase.setOpaque(false);

        // MEMBUAT ITEM
        for (int i = 0; i < namaBarang.length; i++) {
            panelEtalase.add(createItemBlock(namaBarang[i], hargaBarang[i], levelBarang[i]));
        }

        // SCROLL ETALASE
        JScrollPane scrollEtalase = new JScrollPane(panelEtalase);
        scrollEtalase.setBounds(70, 170, 700, 540);
        scrollEtalase.setOpaque(false);
        scrollEtalase.getViewport().setOpaque(false);
        scrollEtalase.setBorder(null);
        scrollEtalase.getVerticalScrollBar().setUnitIncrement(10);

        // TRANSPARAN
        scrollEtalase.getVerticalScrollBar().setOpaque(false);

        // TAMBAHKAN KE BACKGROUND
        background.add(scrollEtalase);
        setVisible(true);


        //Etalase
        ImageIcon etalase = new ImageIcon(getClass().getResource("/assets/etalaseSembako.png"));
        Image etalaseIcon = etalase.getImage();
        Image resizeEtalase = etalaseIcon.getScaledInstance(780, 670, Image.SCALE_SMOOTH);
        ImageIcon etalaseFix = new ImageIcon(resizeEtalase);
        JLabel iconEtalase = new JLabel(etalaseFix);
        iconEtalase.setBounds(30, 80, 780, 670);
        background.add(iconEtalase);
    }

    // MEMBUAT BLOK ITEM
    private JPanel createItemBlock(String nama, int harga, int lvl) {
        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(680, 120));
        itemPanel.setMaximumSize(new Dimension(680, 120));
        itemPanel.setLayout(null);
        itemPanel.setOpaque(false);

        // BORDER ITEM
        ImageIcon border1 = new ImageIcon(getClass().getResource("/assets/borderEtalase.png"));
        Image resizeBorder = border1.getImage().getScaledInstance(680,100,Image.SCALE_SMOOTH);
        ImageIcon fixboder = new ImageIcon(resizeBorder);
        JLabel border = new JLabel(fixboder);
        border.setLayout(null);
        border.setBounds(0, 0, 680, 120);

        //border.setIcon(new ImageIcon(getClass().getResource("/assets/borderEtalase.png")));
        itemPanel.add(border);
        itemPanel.setLayout(new BorderLayout());
        itemPanel.add(border, BorderLayout.CENTER);

        // NAMA BARANG
        JLabel namaBarang = new JLabel(nama);
        namaBarang.setBounds(35, 25, 250, 30);
        namaBarang.setForeground(Color.WHITE);
        namaBarang.setFont(new Font("Arial", Font.BOLD, 20));
        border.add(namaBarang);

        // HARGA BARANG
        JLabel hargaBarang = new JLabel("Rp " + harga + " | Lvl : " + lvl ) ;
        hargaBarang.setBounds(35, 60, 250, 30);
        hargaBarang.setForeground(Color.YELLOW);
        hargaBarang.setFont(new Font("Arial", Font.PLAIN, 18));
        border.add(hargaBarang);

        return itemPanel;
    }
}



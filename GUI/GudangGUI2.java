import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GudangGUI2 extends JFrame {

    private JPanel panelEtalase;
    boolean sedangInputJumlah = false;

    int barangDipilih = -1;

    GudangGUI2() {

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
        output.append("Selamat datang di daftar menu !\nSilahkan pilih menu yang ingin di aktifkan\n\n");


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




        //Aksi
        input.addActionListener(e -> {

        });

        ArrayList<Menu> daftarMenu = new ArrayList<>();
        // MAKANAN
        daftarMenu.add(new Makanan("Nasi Telur", 12000, 0, 1));
        daftarMenu.add(new Minuman("Air Gula", 4000, 0, BahanDasar.Fruit, Suhu.Dingin));
        daftarMenu.add(new Camilan("Telur Crispy", 10000, 1, JenisRasa.Asin));
        daftarMenu.add(new Makanan("Nasi Goreng", 15000, 2, 2));
        daftarMenu.add(new Minuman("Teh Manis", 7000, 3, BahanDasar.Fruit, Suhu.Panas));
        daftarMenu.add(new Camilan("Kentang Goreng", 12000, 3, JenisRasa.Asin));
        daftarMenu.add(new Makanan("Ayam Geprek", 22000, 5, 5));
        daftarMenu.add(new Minuman("Kopi Panas", 12000, 5, BahanDasar.Coffee, Suhu.Panas));
        daftarMenu.add(new Camilan("Pisang Goreng", 14000, 5, JenisRasa.Manis));
        daftarMenu.add(new Minuman("Susu Dingin", 14000, 6, BahanDasar.Milk, Suhu.Dingin));
        daftarMenu.add(new Makanan("Mie Pedas", 18000, 7, 4));
        daftarMenu.add(new Camilan("Cheese Ball", 18000, 8, JenisRasa.Asin));
        daftarMenu.add(new Makanan("Nasi Seafood", 30000, 10, 2));
        daftarMenu.add(new Minuman("Jus Buah", 18000, 10, BahanDasar.Fruit, Suhu.Dingin));
        daftarMenu.add(new Camilan("Seafood Roll", 25000, 12, JenisRasa.Asin));
        daftarMenu.add(new Makanan("Steak Daging", 45000, 14, 1));
        daftarMenu.add(new Minuman("Kopi Premium", 30000, 15, BahanDasar.Coffee, Suhu.Panas));
        daftarMenu.add(new Camilan("Beef Snack", 35000, 15, JenisRasa.Asin));

        //Blok
        panelEtalase = new JPanel();
        panelEtalase.setLayout(new BoxLayout(panelEtalase, BoxLayout.Y_AXIS));
        panelEtalase.setOpaque(false);

        // SCROLL ETALASE
        JScrollPane scrollEtalase = new JScrollPane(panelEtalase);
        scrollEtalase.setBounds(70, 170, 700, 540);
        scrollEtalase.setOpaque(false);
        scrollEtalase.getViewport().setOpaque(false);
        scrollEtalase.setBorder(null);
        scrollEtalase.getVerticalScrollBar().setOpaque(false);
        scrollEtalase.getVerticalScrollBar().setUnitIncrement(10);
        background.add(scrollEtalase);

        //Etalase
        ImageIcon etalase = new ImageIcon(getClass().getResource("/assets/etalaseSembako.png"));
        Image etalaseIcon = etalase.getImage();
        Image resizeEtalase = etalaseIcon.getScaledInstance(780, 670, Image.SCALE_SMOOTH);
        ImageIcon etalaseFix = new ImageIcon(resizeEtalase);
        JLabel iconEtalase = new JLabel(etalaseFix);
        iconEtalase.setBounds(30, 80, 780, 670);
        background.add(iconEtalase);

        // MEMBUAT ITEM
        for (Menu m : daftarMenu) {
            panelEtalase.add(createItemBlock(m.getNama(), m.getHargaJual(), m.getLevelMinimal()));
        }

        setVisible(true);
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
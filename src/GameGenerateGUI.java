
import java.io.*;
import java.util.*;
import javax.swing.SwingUtilities;

public class GameGenerateGUI {


    // Database Master (Ditukar ke public agar boleh dibaca oleh file GUI yang lain)
    public static List<BahanBaku> MASTER_BAHAN = new ArrayList<>();
    public static List<Menu> MASTER_MENU = new ArrayList<>();

    // Pemalar Harga Jimat
    public static final int HARGA_CHARMING = 150000;
    public static final int HARGA_SECURITY = 100000;
    public static final int HARGA_CLEANER = 75000;

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "false");
        System.setProperty("apple.awt.application.name", "Resto Tycoon");



        // 1. Wajib panggil ini dahulu untuk mengisi pangkalan data permainan
        initDatabase();

        // 2. Muatkan data restoran dari savegame.txt (Sama seperti versi terminal)
        Restoran r = muatGame();

        // 3. Lancarkan GUI Menu Utama dan hantar objek Restoran ke dalamnya
        /*SwingUtilities.invokeLater(() -> {
            System.out.println("Memulakan Mod GUI...");
            new MainMenuGUI(r).setVisible(true);
        });*/
        SwingUtilities.invokeLater(() -> new MainMenuGUI(r).setVisible(true));
    }

    // ==========================================
    // FITUR SAVE & LOAD KEKAL SAMA (Supaya serasi dengan Mod Terminal)
    // ==========================================
    // Ditukar ke public static supaya butang "SAVE & EXIT" di MainMenuGUI boleh memanggilnya
    public static void simpanGame(Restoran r) {
        try {
            FileWriter writer = new FileWriter("savegame.txt");

            writer.write(r.getLevel() + "\n");
            writer.write(r.getUang() + "\n");

            writer.write("---STOK---\n");
            for (Map.Entry<BahanBaku, Integer> entry : r.stok.entrySet()) {
                writer.write(entry.getKey().getNama() + "," + entry.getValue() + "\n");
            }

            writer.write("---MENU---\n");
            for (Menu m : r.getMenu().keySet()) {
                writer.write(m.getNama() + "\n");
            }

            writer.write("---JIMAT---\n");
            for (Jimat j : r.getInventarisJimat()) {
                writer.write(j.getNama() + "\n");
            }

            writer.write("---END---\n");
            writer.close();
            System.out.println("✅ Seluruh progress berjaya disimpan ke savegame.txt!");
        } catch (IOException e) {
            System.out.println("❌ Gagal menyimpan game: " + e.getMessage());
        }
    }

    private static Restoran muatGame() {
        Restoran r = new Restoran();
        File file = new File("savegame.txt");

        if (file.exists()) {
            try {
                Scanner fileReader = new Scanner(file);

                if (fileReader.hasNextLine()) {
                    r.setLevel(Integer.parseInt(fileReader.nextLine()));
                }
                if (fileReader.hasNextLine()) {
                    r.setUang(Integer.parseInt(fileReader.nextLine()));
                }

                String kategoriPembacaan = "";

                while (fileReader.hasNextLine()) {
                    String baris = fileReader.nextLine().trim();
                    if (baris.isEmpty()) {
                        continue;
                    }

                    if (baris.equals("---STOK---")) {
                        kategoriPembacaan = "STOK";
                        continue;
                    }
                    if (baris.equals("---MENU---")) {
                        kategoriPembacaan = "MENU";
                        continue;
                    }
                    if (baris.equals("---JIMAT---")) {
                        kategoriPembacaan = "JIMAT";
                        continue;
                    }
                    if (baris.equals("---END---")) {
                        break;
                    }

                    if (kategoriPembacaan.equals("STOK")) {
                        String[] data = baris.split(",");
                        if (data.length == 2) {
                            BahanBaku bahanDitemukan = cariBahan(data[0]);
                            if (bahanDitemukan != null) {
                                r.stok.put(bahanDitemukan, Integer.parseInt(data[1]));
                            }
                        }
                    } else if (kategoriPembacaan.equals("MENU")) {
                        Menu menuDitemukan = cariMenu(baris);
                        if (menuDitemukan != null) {
                            r.getMenu().put(menuDitemukan, true);
                        }
                    } else if (kategoriPembacaan.equals("JIMAT")) {
                        if (baris.toLowerCase().contains("charming")) {
                            r.getInventarisJimat().add(new JimatCharming()); 
                        }else if (baris.toLowerCase().contains("security")) {
                            r.getInventarisJimat().add(new JimatSecurity()); 
                        }else if (baris.toLowerCase().contains("cleaner")) {
                            r.getInventarisJimat().add(new JimatCleaner());
                        }
                    }
                }

                fileReader.close();
                System.out.println("📂 Data save game berjaya dimuatkan untuk Mod GUI!");

            } catch (FileNotFoundException | NumberFormatException e) {
                System.out.println("⚠️ Gagal memuat file, memulakan game baru...");
                r = new Restoran();
            }
        } else {
            System.out.println("📄 Tiada file savegame.txt. Memulakan permainan baru...");
        }
        return r;
    }

    private static BahanBaku cariBahan(String nama) {
        for (BahanBaku b : MASTER_BAHAN) {
            if (b.getNama().equalsIgnoreCase(nama)) {
                return b;
            }
        }
        return null;
    }

    private static Menu cariMenu(String nama) {
        for (Menu m : MASTER_MENU) {
            if (m.getNama().equalsIgnoreCase(nama)) {
                return m;
            }
        }
        return null;
    }

    // --- DATABASE DEVELOPER ---
    private static void initDatabase() {
        BahanBaku beras = new BahanBaku("Beras", 5000, 1);
        BahanBaku telur = new BahanBaku("Telur", 3000, 1);
        BahanBaku teh = new BahanBaku("Teh", 2000, 1);
        BahanBaku ayam = new BahanBaku("Ayam", 15000, 5);
        BahanBaku minyak = new BahanBaku("Minyak", 10000, 5);
        BahanBaku dagingSapi = new BahanBaku("Daging Sapi", 45000, 10);
        BahanBaku bijiKopi = new BahanBaku("Biji Kopi", 25000, 10);
        BahanBaku susu = new BahanBaku("Susu", 12000, 10);
        BahanBaku udang = new BahanBaku("Udang", 35000, 15);
        BahanBaku cumi = new BahanBaku("Cumi", 30000, 15);

        MASTER_BAHAN.add(beras);
        MASTER_BAHAN.add(telur);
        MASTER_BAHAN.add(teh);
        MASTER_BAHAN.add(ayam);
        MASTER_BAHAN.add(minyak);
        MASTER_BAHAN.add(dagingSapi);
        MASTER_BAHAN.add(bijiKopi);
        MASTER_BAHAN.add(susu);
        MASTER_BAHAN.add(udang);
        MASTER_BAHAN.add(cumi);

        Makanan nasiPutih = new Makanan("Nasi Putih", 8000, 1, 0);
        nasiPutih.tambahKomposisi(beras, 1);
        MASTER_MENU.add(nasiPutih);

        Makanan telurDadar = new Makanan("Telur Dadar", 10000, 1, 0);
        telurDadar.tambahKomposisi(telur, 2);
        MASTER_MENU.add(telurDadar);

        Minuman esTeh = new Minuman("Es Teh", 5000, 1, BahanDasar.Fruit, Suhu.Dingin);
        esTeh.tambahKomposisi(teh, 1);
        MASTER_MENU.add(esTeh);

        Makanan ayamGoreng = new Makanan("Ayam Goreng", 25000, 5, 2);
        ayamGoreng.tambahKomposisi(ayam, 1);
        ayamGoreng.tambahKomposisi(minyak, 1);
        MASTER_MENU.add(ayamGoreng);

        Makanan nasiGorengAyam = new Makanan("Nasi Goreng Ayam", 30000, 5, 3);
        nasiGorengAyam.tambahKomposisi(beras, 1);
        nasiGorengAyam.tambahKomposisi(ayam, 1);
        MASTER_MENU.add(nasiGorengAyam);

        Makanan steakSapi = new Makanan("Steak Sapi", 85000, 10, 2);
        steakSapi.tambahKomposisi(dagingSapi, 1);
        MASTER_MENU.add(steakSapi);

        Minuman cappuccino = new Minuman("Cappuccino", 20000, 2, BahanDasar.Coffee, Suhu.Panas);
        cappuccino.tambahKomposisi(bijiKopi, 1);
        cappuccino.tambahKomposisi(susu, 1);
        MASTER_MENU.add(cappuccino);

        Makanan udangSausPadang = new Makanan("Udang Saus Padang", 65000, 15, 3);
        udangSausPadang.tambahKomposisi(udang, 1);
        MASTER_MENU.add(udangSausPadang);

        Makanan cumiGorengTepung = new Makanan("Cumi Goreng Tepung", 55000, 15, 0);
        cumiGorengTepung.tambahKomposisi(cumi, 1);
        cumiGorengTepung.tambahKomposisi(minyak, 1);
        MASTER_MENU.add(cumiGorengTepung);
    }

    // Nota: Fungsi seperti menuPersiapan(), beliBahan(), setMenuRestoran(), dan mulaiSimulasi()
    // TIDAK DISERTAKAN DI SINI kerana keseluruhan input/output teks tersebut telah 
    // digantikan oleh class-class GUI (seperti PasarGUI, DapurGUI, BukaRestoranGUI).
}



import java.io.*;
import java.util.*;

public class GameGenerate {

    // Database Master (Ditentukan oleh Developer)
    private static List<BahanBaku> MASTER_BAHAN = new ArrayList<>();
    private static List<Menu> MASTER_MENU = new ArrayList<>();

    private static final int HARGA_CHARMING = 150000;
    private static final int HARGA_SECURITY = 100000;
    private static final int HARGA_CLEANER = 75000;

    public static void main(String[] args) {
        initDatabase(); // WAJIB dipanggil pertama kali agar database terisi

        Scanner scanner = new Scanner(System.in);

        // Memuat data dari file savegame.txt
        Restoran r = muatGame();

        boolean isRunning = true;

        System.out.println("=== WELCOME TO RESTO TYCOON DEVELOPER EDITION ===");

        while (isRunning) {
            System.out.println("\n[ STATUS ] Level: " + r.getLevel() + " | Kapasitas: " + r.getKapasitas() + " Orang | Kas: Rp " + r.getUang());
            System.out.println("1. Persiapan (Belanja, Resep, Jimat)");
            System.out.println("2. Buka Restoran");
            System.out.println("3. Save & Exit");
            System.out.print("Pilih: ");
            String mainOpt = scanner.nextLine();

            switch (mainOpt) {
                case "1":
                    menuPersiapan(scanner, r);
                    break;
                case "2":
                    mulaiSimulasi(r);
                    break;
                case "3":
                    simpanGame(r);
                    isRunning = false;
                    break;
            }
        }
    }

    // ==========================================
    // FITUR SAVE & LOAD FULL DATA (.TXT)
    // ==========================================
    private static void simpanGame(Restoran r) {
        try {
            FileWriter writer = new FileWriter("savegame.txt");

            // 1. Simpan Data Dasar
            writer.write(r.getLevel() + "\n");
            writer.write(r.getUang() + "\n");

            // 2. Simpan Data Stok Bahan Baku (Format: NamaBahan,Jumlah)
            writer.write("---STOK---\n");
            for (Map.Entry<BahanBaku, Integer> entry : r.stok.entrySet()) {
                writer.write(entry.getKey().getNama() + "," + entry.getValue() + "\n");
            }

            // 3. Simpan Daftar Menu yang Aktif (Format: NamaMenu)
            writer.write("---MENU---\n");
            for (Menu m : r.getMenu().keySet()) {
                writer.write(m.getNama() + "\n");
            }

            // 4. Simpan Inventaris Jimat (Format: NamaJimat)
            writer.write("---JIMAT---\n");
            for (Jimat j : r.getInventarisJimat()) {
                writer.write(j.getNama() + "\n");
            }

            writer.write("---END---\n");
            writer.close();
            System.out.println("✅ Seluruh progress berhasil disimpan ke savegame.txt!");
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

                // Membaca Data Dasar
                if (fileReader.hasNextLine()) {
                    r.setLevel(Integer.parseInt(fileReader.nextLine()));
                }
                if (fileReader.hasNextLine()) {
                    r.setUang(Integer.parseInt(fileReader.nextLine()));
                }

                // Membaca Data Kompleks (Stok, Menu, Jimat)
                String kategoriPembacaan = "";

                while (fileReader.hasNextLine()) {
                    String baris = fileReader.nextLine().trim();
                    if (baris.isEmpty()) {
                        continue;
                    }

                    // Deteksi pergantian kategori
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

                    // Memasukkan data ke dalam Restoran sesuai kategorinya
                    if (kategoriPembacaan.equals("STOK")) {
                        String[] data = baris.split(","); // Memisahkan nama dan jumlah
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
                System.out.println("📂 Data save game beserta stok gudang berhasil diload!");

            } catch (FileNotFoundException | NumberFormatException e) {
                System.out.println("⚠️ Gagal memuat file text, data corrupt. Memulai game baru...");
                r = new Restoran();
            }
        } else {
            System.out.println("📄 Tidak ada file savegame.txt. Memulai permainan baru...");
        }
        return r;
    }

    // Method bantuan untuk mencocokkan Nama Teks dengan Objek aslinya
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
    // ==========================================

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

    private static void menuPersiapan(Scanner sc, Restoran r) {

        boolean back = false;

        while (!back) {

            System.out.println(
                    "\n--- FASE PERSIAPAN (Lv."
                    + r.getLevel()
                    + " | Kapasitas: "
                    + r.getKapasitas()
                    + " Orang) ---"
            );

            System.out.println("1. Pasar (Beli Bahan Baku)");
            System.out.println("2. Dapur (Set Menu Restoran)");
            System.out.println("3. Toko Jimat (Beli/Jual)");
            System.out.println("4. Cek Stok Gudang");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");

            String opt = sc.nextLine();

            switch (opt) {
                case "1":
                    beliBahan(sc, r);
                    break;
                case "2":
                    setMenuRestoran(sc, r);
                    break;
                case "3":
                    manajemenJimat(sc, r);
                    break;
                case "4":
                    cekGudang(r);
                    break;
                case "5":
                    back = true;
                    break;
            }
        }
    }

    private static void beliBahan(Scanner sc, Restoran r) {
        System.out.println("\n--- PASAR BAHAN BAKU ---");
        List<BahanBaku> tersedia = new ArrayList<>();

        for (BahanBaku b : MASTER_BAHAN) {
            if (r.getLevel() >= b.getLevelMinimal()) {
                tersedia.add(b);
                System.out.println(tersedia.size() + ". " + b.getNama() + " (Rp " + b.getHarga() + ")");
            } else {
                System.out.println("X. [Terkunci - Butuh Lv." + b.getLevelMinimal() + "]");
            }
        }

        System.out.print("Pilih nomor bahan (0 untuk batal): ");
        int pil = Integer.parseInt(sc.nextLine());
        if (pil > 0 && pil <= tersedia.size()) {
            BahanBaku b = tersedia.get(pil - 1);
            System.out.print("Beli berapa unit? ");
            int jml = Integer.parseInt(sc.nextLine());
            int total = jml * b.getHarga();

            if (r.getUang() >= total) {
                r.kurangiUang(total);
                r.stok.put(b, r.stok.getOrDefault(b, 0) + jml);
                System.out.println("Berhasil membeli " + jml + " " + b.getNama());
            } else {
                System.out.println("Uang tidak cukup!");
            }
        }
    }

    private static void setMenuRestoran(Scanner sc, Restoran r) {
        System.out.println("\n--- BUKU RESEP (Unlock by Level) ---");
        List<Menu> resepTersedia = new ArrayList<>();

        for (Menu m : MASTER_MENU) {
            if (r.getLevel() >= m.getLevelMinimal()) {
                resepTersedia.add(m);
                String status = r.getMenu().containsKey(m) ? "[AKTIF]" : "[NON-AKTIF]";
                System.out.println(resepTersedia.size() + ". " + status + " " + m.getNama());
            } else {
                System.out.println("X. [Resep Terkunci - Butuh Lv." + m.getLevelMinimal() + "]");
            }
        }

        System.out.print("Pilih nomor menu untuk Aktif/Non-aktifkan (0 batal): ");
        int pil = Integer.parseInt(sc.nextLine());
        if (pil > 0 && pil <= resepTersedia.size()) {
            Menu m = resepTersedia.get(pil - 1);
            if (r.getMenu().containsKey(m)) {
                r.getMenu().remove(m);
                System.out.println("➖ " + m.getNama() + " dihapus dari daftar menu hari ini.");
            } else {
                r.getMenu().put(m, true);
                System.out.println("➕ " + m.getNama() + " sekarang tersedia untuk pelanggan.");
            }
        }
    }

    private static void manajemenJimat(Scanner sc, Restoran r) {

        System.out.println("\n--- MANAJEMEN JIMAT ---");
        System.out.println(
                "1. Beli Jimat (Charming:"
                + HARGA_CHARMING
                + ", Security:"
                + HARGA_SECURITY
                + ", Cleaner:"
                + HARGA_CLEANER
                + ")"
        );
        System.out.println("2. Jual Jimat (Harga 50%)");
        System.out.println("3. Gunakan Jimat");
        System.out.print("Pilihan: ");

        String pil = sc.nextLine();

        if (pil.equals("1")) {
            System.out.println("1. Charming, 2. Security, 3. Cleaner");
            String tipe = sc.nextLine();
            Jimat j = null;
            int h = 0;

            if (tipe.equals("1")) {
                j = new JimatCharming();
                h = HARGA_CHARMING;
            } else if (tipe.equals("2")) {
                j = new JimatSecurity();
                h = HARGA_SECURITY;
            } else if (tipe.equals("3")) {
                j = new JimatCleaner();
                h = HARGA_CLEANER;
            }

            if (j != null && r.getUang() >= h) {
                r.kurangiUang(h);
                r.getInventarisJimat().add(j);
                System.out.println(j.getNama() + " terbeli!");
            }
        } else if (pil.equals("2")) {
            if (r.getInventarisJimat().isEmpty()) {
                return;
            }
            for (int i = 0; i < r.getInventarisJimat().size(); i++) {
                System.out.println((i + 1) + ". " + r.getInventarisJimat().get(i).getNama());
            }

            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < r.getInventarisJimat().size()) {
                Jimat dijatuhin = r.getInventarisJimat().remove(idx);
                if (dijatuhin instanceof JimatSecurity) {
                    r.tambahUang(HARGA_SECURITY / 2); 
                }else if (dijatuhin instanceof JimatCharming) {
                    r.tambahUang(HARGA_CHARMING / 2); 
                }else if (dijatuhin instanceof JimatCleaner) {
                    r.tambahUang(HARGA_CLEANER / 2);
                }

                if (r.getJimatAktif() == dijatuhin) {
                    r.pasangJimat(null);
                }

                System.out.println("Jual berhasil!");
            }
        } else if (pil.equals("3")) {
            if (r.getInventarisJimat().isEmpty()) {
                System.out.println("Kamu tidak mempunyai Jimat, Beli Dulu");
                return;
            }
            for (int i = 0; i < r.getInventarisJimat().size(); i++) {
                System.out.println((i + 1) + ". " + r.getInventarisJimat().get(i).getNama());
            }

            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx >= 0 && idx < r.getInventarisJimat().size()) {
                r.pasangJimat(r.getInventarisJimat().get(idx));
                System.out.println(r.getJimatAktif().aktifkanEfek());
            }
        }
    }

    private static void cekGudang(Restoran r) {
        System.out.println("\n--- GUDANG PENYIMPANAN ---");
        if (r.stok.isEmpty()) {
            System.out.println("Gudang masih kosong!");
        } else {
            r.stok.forEach((bahan, jml) -> {
                System.out.println("- " + bahan.getNama() + ": " + jml + " unit");
            });
        }
    }

    private static void mulaiSimulasi(Restoran r) {
        if (r.getMenu().isEmpty()) {
            System.out.println("❌ Gagal buka! Anda belum menentukan menu di Dapur.");
            return;
        }
        System.out.println("\n=== RESTORAN DIBUKA (Kapasitas Maksimal: " + r.getKapasitas() + ") ===");
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
    

            int jml = ran.nextInt(r.getKapasitas()-3) + 1;
            System.out.println("[Rombongan " + (i + 1) + "] " + jml + " orang datang...");

            if (kursiTerisi + jml <= r.getKapasitas()) {
                kursiTerisi += jml;
                System.out.println("Tamu duduk. (Kursi terpakai: " + kursiTerisi + "/" + r.getKapasitas() + ")");

                totalTamu += jml;
                Pelanggan p = new Pelanggan(jml, r);
                p.pilihMenu(r);
                p.selesaikanTransaksi(r);

                kursiTerisi -= jml;
                System.out.println("Tamu selesai dan pergi. Kursi kosong kembali.\n");

            } else {
                System.out.println("Restoran penuh! " + jml + " tamu tidak jadi pesan dan langsung pergi.\n");
            }
        }

        r.tambahExp(totalTamu * 20);
        System.out.println("=== HARI BERAKHIR ===");
        System.out.println("Total tamu yang dilayani: " + totalTamu);
    }
}

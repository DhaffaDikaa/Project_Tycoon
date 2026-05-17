
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restoran implements Serializable {
    private int kapasitas;
    private int uang;
    protected Map<BahanBaku, Integer> stok;
    protected Map<Menu, Boolean> daftarMenu;
    private Jimat jimatAktif;
    private  int level =1;
    private  int exp =0;
    private int hari = 1;
    private ArrayList<Jimat> inventarisJimat = new ArrayList<>();



    public Restoran(int kapasitas, int uangAwal) {
        this.kapasitas = kapasitas;
        this.uang = uangAwal;
        this.stok = new HashMap<>();
        this.daftarMenu = new HashMap<>();
    }

    public Restoran() {
        this(10,200000);
    }

    public ArrayList<Jimat> getInventarisJimat() {
        return inventarisJimat;
    }

    public void pasangJimat(Jimat j){
        this.jimatAktif = j;
        if (j != null) {
            System.out.println(j.aktifkanEfek()); 
        } else {
            System.out.println("Jimat telah dilepas.");
        }
    }

    public Jimat getJimatAktif(){
        return jimatAktif;
    }

    public int getUang(){
        return uang;
    }

    public int getLevel(){
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void setUang(int uang) {
        this.uang = uang;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp(){
        return exp;
    }

    public int getHari() {
        return hari;
    }

    public void tambahHari(){
        hari++;
    }

    public void tambahUang(int uang){
        this.uang += uang;
    }

    public int getKapasitas() {
        return 10 + (this.level / 10) * 5;
    }

    public void kurangiUang(int uang){
        this.uang -= uang;
    }

    public void tambahExp(int jumlahExp) {
        this.exp += jumlahExp;
        int targetExp = level * 100; 
        while (this.exp >= targetExp) {
            this.exp -= targetExp;
            this.level++;
            this.uang += 100000;
            System.out.println("\nSELAMAT! Restoran naik ke Level " + this.level + "! dan mendapat uang bonus sebanyak 100000");

            if (this.level % 5 == 0) {
                System.out.println("[REWARD] Anda membuka inspirasi Resep Menu Baru!");
            }

            if (this.level % 10 == 0) {
                this.kapasitas += 10;
                System.out.println("[REWARD] Restoran diperluas! Kapasitas maksimal bertambah 10 orang.");
            }
            targetExp = level * 100; 
        }
    }

    public void beliBahan(BahanBaku bahan, int jumlah) {
        int totalHarga = bahan.getHarga() *  jumlah;
        if(this.uang >= totalHarga ){
            stok.merge(bahan, jumlah, Integer::sum);
            uang -= totalHarga;
            System.out.println("Berhasil beli " + jumlah + " " + bahan.getNama());
        }else{
            System.out.println("Uang tidak cukup untuk beli bahan! CARII UANG LAGEEE");
        }
        
        
    }

    public String cekBahan(){
        String hasil = "";
        if (stok.isEmpty()) return "Stok kosong!";

        for(Map.Entry<BahanBaku, Integer> cari : stok.entrySet()){
            BahanBaku bahan  = cari.getKey();
            Integer jumlah = cari.getValue();
            String nama = bahan.getNama();

            hasil += nama + " : " + jumlah + "\n";

        }

        return hasil;
    }

    public Map<BahanBaku,Integer> getStok(){
        return this.stok;
    }
    
    

    public void tambahMenu(Menu e) {
        daftarMenu.put(e, true);
    }

    public void hapusMenu(Menu e){
        daftarMenu.remove(e);
    }

    public Map<Menu, Boolean> getMenu() {
        return daftarMenu;
    }

    public Integer hitungStokMenu(Menu e) {
        if (!daftarMenu.containsKey(e)) return 0;

        int bisa = Integer.MAX_VALUE;

        for (Map.Entry<BahanBaku, Integer> cari : e.komposisi.entrySet()) {
            BahanBaku bahan = cari.getKey();
            Integer jumlahDibutuhkan = cari.getValue();

            if (!stok.containsKey(bahan)) return 0;  

            int jumlahStok = stok.get(bahan);
            bisa = Math.min(bisa, jumlahStok / jumlahDibutuhkan);
        }
        return bisa == Integer.MAX_VALUE ? 0 : bisa;
    }


    public void transaksi(int a){this.uang += a;}

   
}

import java.io.*;
import java.util.HashMap;
import java.util.Map;
public abstract class Menu implements Transaksi , Serializable{
    private String nama;
    private int hargaJual;
    private int levelMinimal;
    protected  Map<BahanBaku, Integer> komposisi;


    public Menu(String nama, int harga,int levelMinimal){
        this.nama = nama;
        this.hargaJual = harga;
        this.levelMinimal = levelMinimal;
        komposisi = new HashMap<>();
    }
    @Override
    public void jual(Restoran restoran) throws Exception {
        for (Map.Entry<BahanBaku, Integer> entry : komposisi.entrySet()) {
            BahanBaku bahanDiperlukan = entry.getKey();
            Integer jumlahDiperlukan = entry.getValue();
            
            Integer stokTersedia = restoran.stok.get(bahanDiperlukan);

            if (stokTersedia == null || stokTersedia < jumlahDiperlukan) {
                throw new BahanBakuKosongException("Bahan " + bahanDiperlukan.getNama() + " tidak cukup untuk membuat " + this.nama);
            }
        }

        for (Map.Entry<BahanBaku, Integer> entry : komposisi.entrySet()) {
            BahanBaku bahan = entry.getKey();
            int jumlah = entry.getValue();
            restoran.stok.put(bahan, restoran.stok.get(bahan) - jumlah);
        }

        this.siapkanMenu();

        restoran.transaksi(this.hargaJual);
    }

    public abstract void siapkanMenu() ;

    @Override
    public int getHargaJual() {
        return hargaJual;
    }

    public int getLevelMinimal(){
        return levelMinimal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHargaJual(int hargaJual) {
        this.hargaJual = hargaJual;
    }

    public void tambahKomposisi(BahanBaku a, Integer jumlah){
        komposisi.put(a,jumlah);
    }

    public void hapusKomposisi(BahanBaku a){
        komposisi.remove(a);
    }

}
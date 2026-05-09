import java.util.HashMap;
import java.util.Map;
public class Menu {
    private String nama;
    private int hargaJual;
    public Map<BahanBaku, Integer> komposisi;

    public Menu(String nama, int harga){
        this.nama = nama;
        this.hargaJual = harga;
        komposisi = new HashMap<>();
    }

    public int getHargaJual() {
        return hargaJual;
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
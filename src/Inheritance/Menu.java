import java.util.HashMap;
import java.util.Map;
public class Menu {
    private String nama;
    private int hargaJual;
    protected Map<BahanBaku, Integer> komposisi;

    public Menu(String nama, int harga){
        this.nama = nama;
        this.hargaJual = harga;
        komposisi = new HashMap<>();
    }

    public void tambahKomposisi(BahanBaku a, Integer jumlah){
        komposisi.put(a,jumlah);
    }
}
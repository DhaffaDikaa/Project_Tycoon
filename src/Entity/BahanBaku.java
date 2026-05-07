import java.util.Map;
public class BahanBaku {
    private int harga;
    private String nama;

    public BahanBaku(int harga, String nama) {
        this.harga = harga;
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }
}

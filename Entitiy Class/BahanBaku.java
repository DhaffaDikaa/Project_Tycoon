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

    public void beliBahan(Restoran e, BahanBaku bahan, Integer jumlah) {
        e.stok.merge(bahan, jumlah, Integer::sum);
    }

    public String kurangiStok(Restoran e, Map<BahanBaku, Integer> diPerlukan) {
        //map"stok" akan di ambil dari atribut restoran
        for (Map.Entry<BahanBaku, Integer> entry : diPerlukan.entrySet()) {
            BahanBaku keperluan = entry.getKey();
            Integer jumlahDiperlukan = entry.getValue();

            //menyesuaikan Map 'diPerlukan' dan Map 'stok'
            if (e.stok.containsKey(keperluan)) {
                int stokSekarang = e.stok.get(keperluan);

                if (stokSekarang >= jumlahDiperlukan) {
                    e.stok.put(keperluan, stokSekarang - jumlahDiperlukan);
                } else {
                    return "Bahan di restoran habis :(";
                }
                return "Bahan tidak ada";
            }//tidak perlu else karena barang yang di cari hanya dari pilihan GUI
        }
        return "";
    }
}

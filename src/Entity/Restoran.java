import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Restoran {
    private int kapasitas;
    private int uang;
    protected Map<BahanBaku, Integer> stok;
    protected Map<Menu, Integer> menu;//(?)


    public Restoran() {
        this.kapasitas = 10;
        this.uang = 200000;
        stok = new HashMap<>();
        menu = new HashMap<>();
    }

    //Bahan
    public void beliBahan(BahanBaku bahan, Integer jumlah) {
        stok.merge(bahan, jumlah, Integer::sum);
        uang -= bahan.getHarga() * jumlah;
    }

    public void jual(Menu m) throws BahanBakuKosongException {

        for (Map.Entry<BahanBaku, Integer> entry : m.komposisi.entrySet()) {
            BahanBaku keperluan = entry.getKey();
            Integer jumlahDiperlukan = entry.getValue();

            if (stok.containsKey(keperluan)) {
                int stokSekarang = stok.get(keperluan);

                if (stokSekarang >= jumlahDiperlukan) {
                    stok.put(keperluan, stokSekarang - jumlahDiperlukan);
                } else {
                    throw new BahanBakuKosongException("Yah bahannya habis :(");
                }
            } else {
                throw new BahanBakuKosongException("Menu nya ga ada nih kak");
            }
            uang += m.getHargaJual();
        }
    }

    //Menu
    public void tambahMenu(Menu e) {
        menu.put(e, 0);
    }

    //masih salah
    public Integer hitungStokMenu(Menu e) {
        for (Map.Entry<Menu, Integer> entry : menu.entrySet()) {
            if (menu.containsKey(e)) {
                for (Map.Entry<BahanBaku, Integer> cari : e.komposisi.entrySet()) {
                    if(stok.containsKey(cari)){
                        Integer jumlahDiButuhkan = cari.getValue();
                        Integer jumlahSementara = stok.get(cari);

                        jumlahSementara -= jumlahDiButuhkan;
                    }
                }
            }

        }
        return 1;
    }
}

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

    public String cekBahan(){
        String hasil = "";
        int i = 0;

        for(Map.Entry<BahanBaku, Integer> cari : stok.entrySet()){
            BahanBaku bahan  = cari.getKey();
            Integer jumlah = cari.getValue();
            String nama = bahan.getNama();

            hasil += nama + " : " + jumlah;

            if(i < stok.size()-1){
                hasil += "\n";
                i++;
            }
        }

        return hasil;
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
        }
        uang += m.getHargaJual();
    }

    //Menu
    public void tambahMenu(Menu e) {
        menu.put(e, 0);
    }

    public void hapusMenu(Menu e){
        menu.remove(e);
    }

    public Integer hitungStokMenu(Menu e) {
        if (!menu.containsKey(e)) return 0;

        int bisa = Integer.MAX_VALUE;

        for (Map.Entry<BahanBaku, Integer> cari : e.komposisi.entrySet()) {
            BahanBaku bahan = cari.getKey();
            Integer jumlahDibutuhkan = cari.getValue();

            if (!stok.containsKey(bahan)) return 0;  // bahan tidak ada = tidak bisa buat

            int jumlahStok = stok.get(bahan);
            bisa = Math.min(bisa, jumlahStok / jumlahDibutuhkan);
        }
        return bisa == Integer.MAX_VALUE ? 0 : bisa;
    }
}

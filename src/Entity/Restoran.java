import java.util.HashMap;
import java.util.Map;

public class Restoran {
    private int uang;
    protected Map<BahanBaku, Integer> stok;

    public Restoran(){
        int uang = 200000;
        stok = new HashMap<>();
    }

    public void beliBahan(BahanBaku bahan, Integer jumlah) {
        stok.merge(bahan, jumlah, Integer::sum);
        uang -= bahan.getHarga();
    }

    public void kurangiStok(Map<BahanBaku,Integer> menu) throws BahanBakuKosongException {

        for (Map.Entry<BahanBaku, Integer> entry : menu.entrySet()) {
            BahanBaku keperluan = entry.getKey();
            Integer jumlahDiperlukan = entry.getValue();

            if (stok.containsKey(keperluan)) {
                int stokSekarang = stok.get(keperluan);

                if (stokSekarang >= jumlahDiperlukan) {
                    stok.put(keperluan, stokSekarang - jumlahDiperlukan);
                }else{
                    throw new BahanBakuKosongException("Yah bahannya habis :(");
                }
            }
        }

    }
}

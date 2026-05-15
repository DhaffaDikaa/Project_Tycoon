import java.io.Serializable;
import java.util.*;

public class TikusLapar extends Bencana implements Serializable{
      public TikusLapar(){
        super("Serangan Tikus Lapar");
      }

      @Override
      public void terjadi(Restoran r){
        Map<BahanBaku,Integer> stokRestoran = r.getStok();
        if(stokRestoran == null || stokRestoran.isEmpty()){
            System.out.println("tikus datang tapi dapur kosong, tidak ada yang dicuri");
        }

        Random ran = new Random();

        int jumlahDiCuri = ran.nextInt(3)+1;
        int yangDiCuri = ran.nextInt(stokRestoran.size());
        
        BahanBaku[] rak = stokRestoran.keySet().toArray(new BahanBaku[0]);

        BahanBaku diCuri = rak[yangDiCuri];
        
        int stokSekarang = stokRestoran.get(diCuri);
        int jumlahRealYangDiCuri = jumlahDiCuri;

        if ( stokSekarang < jumlahDiCuri){
            jumlahRealYangDiCuri = stokSekarang;
            stokSekarang = 0;
        }
        else{
            stokSekarang -= jumlahDiCuri;
        }

        stokRestoran.put(diCuri,stokSekarang);
        
        System.out.println("BENCANA ! " + getNamaBencana() + "TERJADI !");
        System.out.println("Tikus Mencuri " + jumlahRealYangDiCuri + " " + diCuri.getNama() + "dari dapur anda !");
    }
        
}
    

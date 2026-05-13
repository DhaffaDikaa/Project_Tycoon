

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Pelanggan{
    private int jumlahRombongan;
    protected ArrayList<Menu> pesanan;

    //pelanggan kabur (tambahan) jika ada logic lain bisa di ubah;
    private boolean pelangganKabur;

    public Pelanggan(int a){
        this.jumlahRombongan = a;
        pesanan = new ArrayList<>();

        Random ran = new Random();
        //membuat persentase 25% kemungkinan pelanggan kabur
        int kemungkinan = ran.nextInt(4)+1;
        if(kemungkinan > 3) pelangganKabur = true;
    }

    public ArrayList<Menu> getPesanan() {
        return pesanan;
    }

    public boolean isPelangganKabur() {
        return pelangganKabur;
    }

    public int getJumlahRombongan() {
        return jumlahRombongan;
    }

    public void pilihMenu(Restoran r){
        //hanya memilih menu 1x
        //mengenai berapa kali beliMakan di lakukan atau seberapa banyak, akan di lakukan pada game generator dengan random juga.
        Random ran = new Random();

        ArrayList<Menu> daftarMenu = new ArrayList<>();
        for(Menu m : r.getMenu().keySet()){
            if(r.hitungStokMenu(m) != 0){
                daftarMenu.add(m);
            }
        }
        int index = ran.nextInt(daftarMenu.size());
        Menu diPilih = daftarMenu.get(index);
    }

}


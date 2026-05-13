

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Pelanggan{
    private int jumlahRombongan;
    protected ArrayList<Menu> pesanan;

    public Pelanggan(int a){
        this.jumlahRombongan = a;
    }

    public Menu beliMakan(Restoran r){
        //mengenai berapa kali beliMakan di lakukan atau seberapa banyak, akan di lakukan pada game generator dengan random juga.
        Random ran = new Random();

        ArrayList<Menu> daftarMenu = new ArrayList<>(r.getMenu().keySet());
        int index = ran.nextInt(daftarMenu.size());
        Menu diPilih = daftarMenu.get(index);

        return diPilih;
    }
}


import java.io.Serializable;
import java.util.*;

public class Pelanggan implements Serializable{
    private int jumlahRombongan;
    protected ArrayList<Menu> pesanan;

    private boolean pelangganKabur;

    public Pelanggan(int a,Restoran r){
        this.jumlahRombongan = a;
        pesanan = new ArrayList<>();

        Random ran = new Random();
        double peluangKabur = 25.0;
        if(r.getJimatAktif() instanceof JimatSecurity){
            JimatSecurity js = (JimatSecurity) r.getJimatAktif();
            peluangKabur = js.tingkatkanKeamanan(peluangKabur);
        }
        if((ran.nextDouble()*100) < peluangKabur){
            this.pelangganKabur = true;
        }
        
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
        Random ran = new Random();

        ArrayList<Menu> daftarMenu = new ArrayList<>();
        for(Menu m : r.getMenu().keySet()){
            if(r.hitungStokMenu(m) > 0){
                daftarMenu.add(m);
            }
        }

        if(daftarMenu.isEmpty()){
            System.out.println("Pelanggan Kecewa karena stock menu di restoran mu habis!");
            return;
        }
        for (int i = 0; i < jumlahRombongan; i++) {
            int index = ran.nextInt(daftarMenu.size());
            Menu diPilih = daftarMenu.get(index);
            pesanan.add(diPilih);
        }
    }

    public int hitungTotalTagihan(){
        int total = 0;
        for(Menu m : pesanan){
            total += m.getHargaJual();
        }
        return total;
    }

    public void selesaikanTransaksi(Restoran r){
        int totalTagihan = hitungTotalTagihan();
        if(totalTagihan == 0){
            System.out.println("Pelanggan pergi karena tidak ada pesanan yang bisa dibuat");
            return;
        }

        try {
            for (Menu m : pesanan) {
                m.jual(r);
            }
        }catch (Exception e){
            e.getMessage();
        }

        System.out.println("------\nTotal tagihan pelanggan ini: Rp" + totalTagihan);

        if(this.pelangganKabur){
            System.out.println("ALARM! Pelanggan dengan JumlahRombongan " + jumlahRombongan + " orang KABUR LEWAT JENDELA DAN ATAP!");
            System.out.println("Nasib buruk! Restoran rugi Rp " + totalTagihan + " (Stok terpakai,dan uang tidak masuk).");
        }else{
            double totalDiterima = (double) totalTagihan;
            if(r.getJimatAktif() instanceof JimatCharming){
                JimatCharming jc = (JimatCharming) r.getJimatAktif();
                totalDiterima = jc.bonus(totalTagihan);
                System.out.println("Efek Jimat Charming aktif! Anda mendapatkan tips tambahan sebesar " + String.format("%.2f",jc.getPresentaseEfek()) +"% dengan total tips sebesar : Rp." + (int)jc.getBanyakTips());
            }
            System.out.println("Pembayaran Berhasil Sebesar Rp." + (int)totalDiterima +"\n------\n");
            r.transaksi((int)totalDiterima);
        }

    }

}


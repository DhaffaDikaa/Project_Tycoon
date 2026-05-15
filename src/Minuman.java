import java.io.Serializable;

enum BahanDasar{
    Coffee, Milk, Fruit
}

enum Suhu{
    Dingin,Panas
}
public class Minuman extends Menu implements Serializable{
    BahanDasar bahanDasar;
    Suhu suhu;

    public Minuman(String nama, int harga,int levelMinimal,BahanDasar bahanDasar,Suhu suhu ){
        super(nama,harga,levelMinimal);
        this.bahanDasar = bahanDasar;
        this.suhu = suhu;
    }

    @Override
    public void siapkanMenu() {
        System.out.println("Meracik minuman " + getNama() + " berbasis " + bahanDasar + " " + suhu);  
    }

    public void setSuhu(Suhu suhu){
        this.suhu = suhu;
    }

    public void setBahanDasar(BahanDasar bahanDasar){
        this.bahanDasar = bahanDasar;
    }

    public Suhu getSuhu(){
        return this.suhu;
    }

    public BahanDasar getBahanDasar(){
        return this.bahanDasar;
    }
    
}
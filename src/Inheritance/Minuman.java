enum bahanDasar{
    Coffe, Milk, Fruit
}
public class Minuman extends Menu{
    bahanDasar dasar;

    Minuman(String nama, int harga,bahanDasar dasar){
        super(nama,harga);
        this.dasar = dasar;
    }

    //sama sama bisa tambah komposisi
}
public class GameGenerate{
    public static void main (String[] args){
        Restoran pakTo = new Restoran();

        //====Bahan Yang ada====
        BahanBaku nasi = new BahanBaku(2000,"Nasi");
        BahanBaku ayam = new BahanBaku(5000,"Ayam potong");
        BahanBaku bumbu = new BahanBaku(1000,"Bumbu instant");

        //====PakTo Menyiapkan Menu====
        Menu nasiGoreng = new Makanan("Nasi Goreng", 15000);
        nasiGoreng.tambahKomposisi(nasi, 1);
        nasiGoreng.tambahKomposisi(bumbu,2);

        pakTo.tambahMenu(nasiGoreng);

        //====Beli stok ====
        pakTo.beliBahan(nasi, 3);
        pakTo.beliBahan(bumbu, 4);

        System.out.println(pakTo.hitungStokMenu(nasiGoreng));
        System.out.println(pakTo.cekBahan());

        //====Jual====
        try {
            pakTo.jual(nasiGoreng);
            System.out.println("Nasi Goreng di Buat");
        } catch (BahanBakuKosongException e) {
            System.out.println(e.getMessage());
        }

    }
}
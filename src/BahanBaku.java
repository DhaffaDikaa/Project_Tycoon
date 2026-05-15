
import java.io.*;
public class BahanBaku implements Serializable{
    private int harga;
    private String nama;
    private int levelMinimal;

    public BahanBaku( String nama,int harga,int levelMinimal) {
        this.harga = harga;
        this.nama = nama;
        this.levelMinimal = levelMinimal;
    }

    public int getLevelMinimal(){
        return levelMinimal;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }
}

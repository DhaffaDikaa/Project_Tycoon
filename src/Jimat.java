
import java.io.Serializable;
import java.util.Random;

abstract class Jimat implements Serializable{

    private String nama;
    private double presentaseEfek;

    public Jimat(String nama) {
        this.nama = nama;
        Random rand = new Random();
        this.presentaseEfek = 7 + (30 - 7) * rand.nextDouble();
    }

    public abstract String aktifkanEfek();

    public String getNama() {
        return nama;
    }

    public double getPresentaseEfek() {
        return presentaseEfek;
    }
}

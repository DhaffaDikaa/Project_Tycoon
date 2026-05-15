import java.io.Serializable;

public class JimatSecurity extends Jimat implements Serializable {

    private double reduksiPeluangKabur;

    public JimatSecurity() {
        super("Jimat Security");
        this.reduksiPeluangKabur = this.getPresentaseEfek();
    }

    @Override
    public String aktifkanEfek() {
        return "Efek " + getNama() + " aktif! Keamanan meningkat, reduksi kabur: " + String.format("%.2f", reduksiPeluangKabur) + "%.";
    }

    public double tingkatkanKeamanan(double peluangAwal) {
        double peluangBaru = peluangAwal - (peluangAwal * (reduksiPeluangKabur / 100));
        return Math.max(0, peluangBaru);
    }
}

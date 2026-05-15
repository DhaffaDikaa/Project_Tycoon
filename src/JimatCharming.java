import java.io.Serializable;

public class JimatCharming extends Jimat implements Serializable{

    private double bonusTips;
    private double hargaAsli;

    public JimatCharming() {
        super("Jimat Charming");
        this.bonusTips = this.getPresentaseEfek();
    }

    @Override
    public String aktifkanEfek() {
        return "Efek " + getNama() + " aktif! Peluang mendapatkan tips meningkat sebesar " + String.format("%.2f", bonusTips) + "%.";
    }

    public double bonus(double hargaAsli) {
        this.hargaAsli = hargaAsli;
        return hargaAsli + (hargaAsli * (bonusTips / 100));
    }


    public double  getBanyakTips(){
        return  hargaAsli * (bonusTips/100);
    }
}

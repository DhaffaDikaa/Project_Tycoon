
public class JimatCharming extends Jimat {

    private double bonusTips;

    public JimatCharming() {
        super("Jimat Charming");
        this.bonusTips = this.getPresentaseEfek();
    }

    @Override
    public String aktifkanEfek() {
        return "Efek " + getNama() + " aktif! Peluang mendapatkan tips meningkat sebesar " + String.format("%.2f", bonusTips) + "%.";
    }

    public double bonus(double hargaAsli) {
        return hargaAsli + (hargaAsli * (bonusTips / 100));
    }
}

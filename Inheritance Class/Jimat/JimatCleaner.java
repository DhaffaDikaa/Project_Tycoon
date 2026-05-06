
public class JimatCleaner extends Jimat {

    private double reduksiPeluangTikus;

    public JimatCleaner() {
        super("Jimat Cleaner");
        this.reduksiPeluangTikus = this.getPresentaseEfek();
    }

    @Override
    public String aktifkanEfek() {
        return "Efek " + getNama() + " aktif! Area dapur bersih, reduksi tikus: " + String.format("%.2f", reduksiPeluangTikus) + "%.";
    }

    public double proteksiBahan(double peluangAwal) {
        double peluangBaru = peluangAwal - (peluangAwal * (reduksiPeluangTikus / 100));
        return Math.max(0, peluangBaru);
    }
}

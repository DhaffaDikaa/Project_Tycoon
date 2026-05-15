import java.io.Serializable;

enum JenisRasa{
    Manis,Asin
}

public class Camilan extends Menu implements Serializable{
    JenisRasa jenisRasa;
    public Camilan(String nama, int harga,int levelMinimal,JenisRasa jenisRasa){
        super(nama,harga,levelMinimal);
        this.jenisRasa = jenisRasa;
    }

    @Override
    public void siapkanMenu() {
        System.out.println("Menyiapkan camilan " + getNama() + " yang berasa " + jenisRasa);
    }

    public void setJenisRasa(JenisRasa jenisRasa){
        this.jenisRasa = jenisRasa;
    }

    public JenisRasa getJenisRasa(){
        return jenisRasa;
    }

    
}
import java.io.Serializable;

public class Makanan extends Menu implements Serializable{
    
    private int levelPedas;

    public Makanan(String nama, int harga,int levelMinimal,int levelPedas){
        super(nama,harga,levelMinimal);
        this.levelPedas = levelPedas;
    }

    public int getLevelPedas(){
        return this.levelPedas;
    }

    public void setLevelPedas(int levelPedas){
        this.levelPedas = levelPedas;
    }

    @Override
    public void siapkanMenu() {
        System.out.println("Memasak " + getNama() + " dengan tingkat kepedasan level " + levelPedas);
    }
}
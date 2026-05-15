import java.io.Serializable;

public abstract  class Bencana implements Serializable {
    protected  String namaBencana;

    public Bencana(String nama){
        this.namaBencana = nama;
    }

    public String getNamaBencana(){
        return namaBencana;
    }

    public abstract void terjadi(Restoran r);
}

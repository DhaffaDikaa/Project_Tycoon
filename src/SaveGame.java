
import java.io.*;

public class SaveGame {

    public static void simpan(Restoran restoran, String namaFile) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(namaFile))) {
            oos.writeObject(restoran);
            System.out.println("Game berhasil disimpan ke file: " + namaFile);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan game: " + e.getMessage());
        }
    }

    public static Restoran muat(String namaFile) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(namaFile))) {
            Restoran restoran = (Restoran) ois.readObject();
            System.out.println(" Game berhasil dimuat dari file: " + namaFile);
            return restoran;
        } catch (FileNotFoundException e) {
            System.out.println("File save tidak ditemukan! Memulai game baru...");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal memuat game: File rusak atau tidak valid.");
        }
        return new Restoran(); 
    }
}

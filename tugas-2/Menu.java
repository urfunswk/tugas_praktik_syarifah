import java.util.Objects;

/**
 * Class Menu represents a menu item in the restaurant.
 */
public class Menu {
    private String namaMenu;
    private int harga;
    private String kategori; // "Makanan" or "Minuman"

    // Constructor for Menu
    public Menu(String namaMenu, int harga, String kategori) {
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getters and setters
    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return namaMenu + " - Rp" + String.format("%,d", harga) + " (" + kategori + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(namaMenu, menu.namaMenu) && Objects.equals(kategori, menu.kategori);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namaMenu, kategori);
    }
}

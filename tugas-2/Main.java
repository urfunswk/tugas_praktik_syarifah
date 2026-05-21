import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class: aplikasi restoran sederhana berbasis console.
 * Memenuhi ketentuan: OOP, ArrayList, method, object, if-else, switch, loops, validation.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Menu> menus = new ArrayList<>();

    // Order map: Menu -> quantity
    private static Map<Menu, Integer> order = new HashMap<>();

    // Promo drink name (beli 1 gratis 1 applies to this drink)
    private static String promoDrinkName = "Es Teh Manis";

    public static void main(String[] args) {
        // Inisialisasi menu awal (minimal 4 makanan dan 4 minuman)
        seedMenus();

        // Loop utama program
        while (true) {
            showMainMenu();
            int choice = readInt("Pilih menu: ", 1, 3);
            switch (choice) {
                case 1:
                    pesanMakanan();
                    break;
                case 2:
                    kelolaMenu();
                    break;
                case 3:
                    System.out.println("Terima kasih. Program selesai.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        }
    }

    // Menampilkan menu utama
    private static void showMainMenu() {
        System.out.println("\n===== APLIKASI RESTORAN SEDERHANA =====");
        System.out.println("1. Pesan Makanan");
        System.out.println("2. Kelola Menu");
        System.out.println("3. Keluar");
    }

    // Menampilkan dan menangani pemesanan
    private static void pesanMakanan() {
        order.clear();
        while (true) {
            System.out.println("\n-- PESAN MENU --");
            System.out.println("1. Tampilkan Menu Makanan");
            System.out.println("2. Tampilkan Menu Minuman");
            System.out.println("3. Tampilkan Semua Menu");
            System.out.println("4. Selesai / Cetak Struk");
            int choice = readInt("Pilih: ", 1, 4);
            if (choice == 4) {
                if (order.isEmpty()) {
                    System.out.println("Belum ada pesanan. Kembali ke menu.");
                    return;
                }
                printReceiptAndFinish();
                return;
            }

            if (choice == 1) displayMenusByCategory("Makanan");
            else if (choice == 2) displayMenusByCategory("Minuman");
            else displayMenusByCategory("Semua");

            // Pilih menu berdasarkan index yang ditampilkan
            int idx = readInt("Masukkan nomor menu yang ingin dipesan (0 batal): ", 0, menus.size());
            if (idx == 0) continue;

            Menu selected = menus.get(idx - 1);
            int qty = readInt("Masukkan jumlah: ", 1, 1000);

            // Tambah ke order (jika sudah ada, tambah kuantitas)
            order.put(selected, order.getOrDefault(selected, 0) + qty);
            System.out.println(qty + " x " + selected.getNamaMenu() + " ditambahkan ke keranjang.");
        }
    }

    // Menampilkan menu berdasarkan kategori (Makanan / Minuman / Semua)
    private static void displayMenusByCategory(String kategori) {
        System.out.println("\nDaftar Menu (" + kategori + "):");
        int i = 1;
        for (Menu m : menus) {
            if (kategori.equals("Semua") || m.getKategori().equalsIgnoreCase(kategori)) {
                System.out.printf("%d. %s - Rp%,d\n", i, m.getNamaMenu(), m.getHarga());
            }
            i++;
        }
    }

    // Cetak struk lengkap dan selesaikan pesanan
    private static void printReceiptAndFinish() {
        System.out.println("\n===== STRUK PEMBELIAN =====");
        System.out.printf("%-4s %-25s %-8s %-12s\n", "No", "Nama", "Qty", "Subtotal");

        // Hitung subtotal awal
        int preliminarySubtotal = 0;
        int lineNo = 1;
        for (Map.Entry<Menu, Integer> e : order.entrySet()) {
            Menu m = e.getKey();
            int qty = e.getValue();
            int lineSubtotal = m.getHarga() * qty;
            preliminarySubtotal += lineSubtotal;
            System.out.printf("%-4d %-25s %-8d Rp%,12d\n", lineNo++, m.getNamaMenu(), qty, lineSubtotal);
        }

        System.out.println("----------------------------------------");
        System.out.printf("Subtotal awal: Rp%,d\n", preliminarySubtotal);

        // Promo beli 1 gratis 1 untuk promoDrinkName jika preliminarySubtotal > 50.000
        int promoSavings = 0;
        if (preliminarySubtotal > 50000) {
            for (Map.Entry<Menu, Integer> e : order.entrySet()) {
                Menu m = e.getKey();
                if (m.getKategori().equalsIgnoreCase("Minuman") && m.getNamaMenu().equalsIgnoreCase(promoDrinkName)) {
                    int qty = e.getValue();
                    int free = qty / 2; // setiap 2 minuman 1 gratis
                    int save = free * m.getHarga();
                    promoSavings += save;
                    if (free > 0) {
                        System.out.println("Promo: Beli 1 Gratis 1 untuk " + m.getNamaMenu() + " => Gratis " + free + " item (Hemat Rp" + String.format("%,d", save) + ")");
                    }
                }
            }
        }

        int subtotalAfterPromo = preliminarySubtotal - promoSavings;
        System.out.printf("Subtotal setelah promo: Rp%,d\n", subtotalAfterPromo);

        // Diskon 10% jika subtotalAfterPromo > 100.000
        int discount = 0;
        if (subtotalAfterPromo > 100000) {
            discount = (int) Math.round(subtotalAfterPromo * 0.10);
            System.out.printf("Diskon 10%% (subtotal > 100.000): -Rp%,d\n", discount);
        }

        int afterDiscount = subtotalAfterPromo - discount;

        // Pajak 10%
        int tax = (int) Math.round(afterDiscount * 0.10);
        System.out.printf("Pajak 10%%: Rp%,d\n", tax);

        // Biaya layanan tetap Rp20.000
        int service = 20000;
        System.out.printf("Biaya layanan: Rp%,d\n", service);

        int total = afterDiscount + tax + service;
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL DUE: Rp%,d\n", total);
        System.out.println("========================================");

        System.out.println("Terima kasih telah memesan. Kembali ke menu utama.");
        // tunggu enter sebelum kembali
        System.out.println("Tekan Enter untuk kembali ke menu utama...");
        scanner.nextLine();
    }

    // Menu kelola: tambah, ubah harga, hapus
    private static void kelolaMenu() {
        while (true) {
            System.out.println("\n-- KELOLA MENU --");
            System.out.println("1. Tambah menu");
            System.out.println("2. Ubah harga menu");
            System.out.println("3. Hapus menu");
            System.out.println("4. Lihat daftar menu");
            System.out.println("5. Kembali");
            int choice = readInt("Pilih: ", 1, 5);
            switch (choice) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHargaMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    displayMenusByCategory("Semua");
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void tambahMenu() {
        System.out.println("\n-- TAMBAH MENU --");
        String nama = readLine("Nama menu: ");
        int harga = readInt("Harga (angka): ", 0, Integer.MAX_VALUE);
        String kategori;
        while (true) {
            System.out.print("Kategori (Makanan/Minuman): ");
            kategori = scanner.nextLine().trim();
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) break;
            System.out.println("Kategori tidak valid. Masukkan 'Makanan' atau 'Minuman'.");
        }
        menus.add(new Menu(nama, harga, kategori));
        System.out.println("Menu berhasil ditambahkan.");
    }

    private static void ubahHargaMenu() {
        System.out.println("\n-- UBAH HARGA MENU --");
        displayMenusByCategory("Semua");
        int idx = readInt("Masukkan nomor menu yang ingin diubah (0 batal): ", 0, menus.size());
        if (idx == 0) return;
        Menu m = menus.get(idx - 1);
        System.out.println("Menu: " + m.getNamaMenu() + " (Harga saat ini: Rp" + String.format("%,d", m.getHarga()) + ")");
        int newPrice = readInt("Harga baru: ", 0, Integer.MAX_VALUE);
        m.setHarga(newPrice);
        System.out.println("Harga berhasil diubah.");
    }

    private static void hapusMenu() {
        System.out.println("\n-- HAPUS MENU --");
        displayMenusByCategory("Semua");
        int idx = readInt("Masukkan nomor menu yang ingin dihapus (0 batal): ", 0, menus.size());
        if (idx == 0) return;
        Menu m = menus.remove(idx - 1);
        System.out.println("Menu '" + m.getNamaMenu() + "' berhasil dihapus.");
    }

    // Membaca integer dengan validasi range
    private static int readInt(String prompt, int min, int max) {
        int val;
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                val = Integer.parseInt(line);
                if (val < min || val > max) {
                    System.out.println("Input harus antara " + min + " dan " + max + ". Silakan ulangi.");
                    continue;
                }
                return val;
            } catch (NumberFormatException ex) {
                System.out.println("Input tidak valid. Masukkan angka.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Membuat menu awal
    private static void seedMenus() {
        menus.add(new Menu("Nasi Goreng Spesial", 25000, "Makanan"));
        menus.add(new Menu("Mie Ayam", 20000, "Makanan"));
        menus.add(new Menu("Ayam Goreng", 30000, "Makanan"));
        menus.add(new Menu("Sate Ayam", 35000, "Makanan"));

        menus.add(new Menu("Es Teh Manis", 8000, "Minuman")); // promo
        menus.add(new Menu("Jus Jeruk", 15000, "Minuman"));
        menus.add(new Menu("Kopi Hitam", 12000, "Minuman"));
        menus.add(new Menu("Soda", 10000, "Minuman"));
    }
}

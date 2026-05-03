import java.util.Scanner;

// Class Menu untuk merepresentasikan item menu
class Menu {
    String nama;
    int harga;
    String kategori;

    // Constructor untuk inisialisasi Menu
    Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

// Class utama Main
public class Main {
    // Scanner untuk input user
    static Scanner scanner = new Scanner(System.in);

    // Array untuk menyimpan menu makanan (minimal 4)
    static Menu[] makanan = {
        new Menu("Nasi Goreng", 25000, "Makanan"),
        new Menu("Ayam Bakar", 30000, "Makanan"),
        new Menu("Sate Ayam", 20000, "Makanan"),
        new Menu("Rendang", 35000, "Makanan")
    };

    // Array untuk menyimpan menu minuman (minimal 4)
    static Menu[] minuman = {
        new Menu("Es Teh", 5000, "Minuman"),
        new Menu("Jus Jeruk", 10000, "Minuman"),
        new Menu("Kopi Hitam", 8000, "Minuman"),
        new Menu("Air Mineral", 3000, "Minuman")
    };

    // Array untuk menyimpan pesanan (maksimal 4)
    static Menu[] pesanan = new Menu[4];
    static int[] jumlahPesanan = new int[4];
    static int jumlahItemPesanan = 0;

    public static void main(String[] args) {
        // Tampilkan menu
        tampilMenu();

        // Terima input pesanan
        terimaPesanan();

        // Hitung total dan cetak struk
        cetakStruk();
    }

    // Method untuk menampilkan daftar menu dipisah berdasarkan kategori
    static void tampilMenu() {
        System.out.println("=== DAFTAR MENU MAKANAN ===");
        System.out.println("1. " + makanan[0].nama + " - Rp " + makanan[0].harga);
        System.out.println("2. " + makanan[1].nama + " - Rp " + makanan[1].harga);
        System.out.println("3. " + makanan[2].nama + " - Rp " + makanan[2].harga);
        System.out.println("4. " + makanan[3].nama + " - Rp " + makanan[3].harga);

        System.out.println("\n=== DAFTAR MENU MINUMAN ===");
        System.out.println("5. " + minuman[0].nama + " - Rp " + minuman[0].harga);
        System.out.println("6. " + minuman[1].nama + " - Rp " + minuman[1].harga);
        System.out.println("7. " + minuman[2].nama + " - Rp " + minuman[2].harga);
        System.out.println("8. " + minuman[3].nama + " - Rp " + minuman[3].harga);
        System.out.println();
    }

    // Method untuk menerima input pesanan (maksimal 4 menu)
    static void terimaPesanan() {
        System.out.println("Silakan pilih menu (maksimal 4 item). Masukkan nomor menu dan jumlah:");
        System.out.println("Contoh: 1 2 (menu 1, jumlah 2)");
        System.out.println("Ketik 0 untuk selesai pesan.");

        // Pesanan 1
        System.out.print("Pesanan 1: ");
        int pilihan1 = scanner.nextInt();
        if (pilihan1 != 0) {
            int jumlah1 = scanner.nextInt();
            tambahPesanan(pilihan1, jumlah1);
        }

        // Pesanan 2
        System.out.print("Pesanan 2: ");
        int pilihan2 = scanner.nextInt();
        if (pilihan2 != 0) {
            int jumlah2 = scanner.nextInt();
            tambahPesanan(pilihan2, jumlah2);
        }

        // Pesanan 3
        System.out.print("Pesanan 3: ");
        int pilihan3 = scanner.nextInt();
        if (pilihan3 != 0) {
            int jumlah3 = scanner.nextInt();
            tambahPesanan(pilihan3, jumlah3);
        }

        // Pesanan 4
        System.out.print("Pesanan 4: ");
        int pilihan4 = scanner.nextInt();
        if (pilihan4 != 0) {
            int jumlah4 = scanner.nextInt();
            tambahPesanan(pilihan4, jumlah4);
        }
    }

    // Method helper untuk menambah pesanan
    static void tambahPesanan(int pilihan, int jumlah) {
        Menu menuDipilih = null;
        switch (pilihan) {
            case 1: menuDipilih = makanan[0]; break;
            case 2: menuDipilih = makanan[1]; break;
            case 3: menuDipilih = makanan[2]; break;
            case 4: menuDipilih = makanan[3]; break;
            case 5: menuDipilih = minuman[0]; break;
            case 6: menuDipilih = minuman[1]; break;
            case 7: menuDipilih = minuman[2]; break;
            case 8: menuDipilih = minuman[3]; break;
            default: System.out.println("Pilihan tidak valid."); return;
        }
        if (jumlahItemPesanan < 4) {
            pesanan[jumlahItemPesanan] = menuDipilih;
            jumlahPesanan[jumlahItemPesanan] = jumlah;
            jumlahItemPesanan++;
        } else {
            System.out.println("Maksimal 4 item pesanan.");
        }
    }

    // Method untuk menghitung total biaya
    static int[] hitungTotal() {
        int totalHarga = 0;
        int pajak = 0;
        int biayaLayanan = 20000;
        int diskon = 0;
        boolean promoMinuman = false;

        // Hitung total harga pesanan
        if (jumlahItemPesanan >= 1) totalHarga += pesanan[0].harga * jumlahPesanan[0];
        if (jumlahItemPesanan >= 2) totalHarga += pesanan[1].harga * jumlahPesanan[1];
        if (jumlahItemPesanan >= 3) totalHarga += pesanan[2].harga * jumlahPesanan[2];
        if (jumlahItemPesanan >= 4) totalHarga += pesanan[3].harga * jumlahPesanan[3];

        // Pajak 10%
        pajak = totalHarga / 10;

        // Diskon dan promo
        if (totalHarga > 100000) {
            diskon = totalHarga / 10; // 10% diskon
        } else if (totalHarga > 50000) {
            promoMinuman = true;
        }

        // Total akhir
        int totalAkhir = totalHarga + pajak + biayaLayanan - diskon;

        return new int[]{totalHarga, pajak, biayaLayanan, diskon, totalAkhir, promoMinuman ? 1 : 0};
    }

    // Method untuk mencetak struk pembayaran
    static void cetakStruk() {
        int[] hasil = hitungTotal();
        int totalHarga = hasil[0];
        int pajak = hasil[1];
        int biayaLayanan = hasil[2];
        int diskon = hasil[3];
        int totalAkhir = hasil[4];
        boolean promoMinuman = hasil[5] == 1;

        System.out.println("\n=== STRUK PEMBAYARAN ===");
        System.out.println("Pesanan:");
        if (jumlahItemPesanan >= 1) {
            int totalItem = pesanan[0].harga * jumlahPesanan[0];
            System.out.println(pesanan[0].nama + " x" + jumlahPesanan[0] + " - Rp " + pesanan[0].harga + " = Rp " + totalItem);
        }
        if (jumlahItemPesanan >= 2) {
            int totalItem = pesanan[1].harga * jumlahPesanan[1];
            System.out.println(pesanan[1].nama + " x" + jumlahPesanan[1] + " - Rp " + pesanan[1].harga + " = Rp " + totalItem);
        }
        if (jumlahItemPesanan >= 3) {
            int totalItem = pesanan[2].harga * jumlahPesanan[2];
            System.out.println(pesanan[2].nama + " x" + jumlahPesanan[2] + " - Rp " + pesanan[2].harga + " = Rp " + totalItem);
        }
        if (jumlahItemPesanan >= 4) {
            int totalItem = pesanan[3].harga * jumlahPesanan[3];
            System.out.println(pesanan[3].nama + " x" + jumlahPesanan[3] + " - Rp " + pesanan[3].harga + " = Rp " + totalItem);
        }

        System.out.println("Total Keseluruhan: Rp " + totalHarga);
        System.out.println("Pajak (10%): Rp " + pajak);
        System.out.println("Biaya Layanan: Rp " + biayaLayanan);
        if (diskon > 0) {
            System.out.println("Diskon (10%): Rp " + diskon);
        }
        if (promoMinuman) {
            System.out.println("Promo: Beli 1 Gratis 1 untuk kategori Minuman!");
        }
        System.out.println("Total Bayar: Rp " + totalAkhir);
    }
}
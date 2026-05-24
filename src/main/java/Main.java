public class Main {

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("|   SISTEM MANAJEMEN PERPUSTAKAAN - JAVA OOP   |");
        System.out.println("================================================\n");


        // ===============================
        //    OBJECT DARI CONSTRUCTOR 1
        //    (Default — tanpa parameter
        // ===============================
        System.out.println("--- Constructor 1: Default ---");
        Buku buku1 = new Buku();                    // pakai Constructor 1
        buku1.setJudul("Laskar Pelangi");
        buku1.setPengarang("Andrea Hirata");
        buku1.setTahunTerbit(2005);
        buku1.setStok(10);
        System.out.println("Info: " + buku1.infoSingkat());
        buku1.tambahBuku();                         // CREATE


        // ===============================
        //    OBJECT DARI CONSTRUCTOR 2
        //    (Judul + Pengarang saja)
        // ===============================
        System.out.println("\n--- Constructor 2: Judul & Pengarang ---");
        Buku buku2 = new Buku("Bumi Manusia", "Pramoedya Ananta Toer");  // Constructor 2
        buku2.setTahunTerbit(1980);
        buku2.setStok(7);
        System.out.println("Info: " + buku2.infoSingkat());
        buku2.tambahBuku();                         // CREATE


        // ===============================
        //    OBJECT DARI CONSTRUCTOR 3
        //    (Semua atribut langsung)
        // ===============================
        System.out.println("\n--- Constructor 3: Semua Atribut ---");
        Buku buku3 = new Buku(0, "Murder on the Orient Express", "Agatha Christie", 1934, 8);  // Constructor 3
        System.out.println("Info: " + buku3.infoSingkat());
        buku3.tambahBuku();                         // CREATE


        // ===============================
        //   READ — Tampilkan semua buku
        // ===============================
        buku1.tampilkanSemuaBuku();                 // READ (void)


        // ===============================
        //     READ — Cari buku by ID
        // ===============================
        System.out.println("--- Cari Buku by ID ---");
        String hasilCari = buku1.cariBukuById(1);   // READ (return String)
        System.out.println(hasilCari);


        // ===============================
        //  UPDATE — Ubah data buku ID 1
        // ===============================
        System.out.println("\n--- Update Buku ID 1 ---");
        buku1.updateBuku(1,
            "Laskar Pelangi (Edisi Revisi)",
            "Andrea Hirata",
            2020,
            15
        );                                          // UPDATE (void)
        buku1.tampilkanSemuaBuku();                 // Cek hasil update


        // ===============================
        //    DELETE — Hapus buku ID 3
        // ===============================
        System.out.println("--- Hapus Buku ID 3 ---");
        boolean berhasil = buku1.hapusBuku(3);      // DELETE (return boolean)
        System.out.println("Status hapus: " + (berhasil ? "Berhasil" : "Gagal"));

        buku1.tampilkanSemuaBuku();                 // Tampil data akhir
    }
}

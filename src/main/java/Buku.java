import java.sql.*;

public class Buku {

    // ==================== ATRIBUT ====================
    private int    id;
    private String judul;
    private String pengarang;
    private int    tahunTerbit;
    private int    stok;

    // Konfigurasi koneksi database
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/perpustakaan";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";


    // ==================== CONSTRUCTOR ====================

    // Constructor 1 — Default (tanpa parameter)
    public Buku() {
        this.id          = 0;
        this.judul       = "Tidak Diketahui";
        this.pengarang   = "Tidak Diketahui";
        this.tahunTerbit = 0;
        this.stok        = 0;
    }

    // Constructor 2 — Hanya judul dan pengarang
    public Buku(String judul, String pengarang) {
        this.id          = 0;
        this.judul       = judul;
        this.pengarang   = pengarang;
        this.tahunTerbit = 0;
        this.stok        = 0;
    }

    // Constructor 3 — Semua atribut lengkap
    public Buku(int id, String judul, String pengarang, int tahunTerbit, int stok) {
        this.id          = id;
        this.judul       = judul;
        this.pengarang   = pengarang;
        this.tahunTerbit = tahunTerbit;
        this.stok        = stok;
    }


    // ==================== GETTER & SETTER ====================
    public int    getId()          { return id; }
    public void   setId(int id)    { this.id = id; }

    public String getJudul()             { return judul; }
    public void   setJudul(String judul) { this.judul = judul; }

    public String getPengarang()                  { return pengarang; }
    public void   setPengarang(String pengarang)  { this.pengarang = pengarang; }

    public int  getTahunTerbit()             { return tahunTerbit; }
    public void setTahunTerbit(int tahun)    { this.tahunTerbit = tahun; }

    public int  getStok()          { return stok; }
    public void setStok(int stok)  { this.stok = stok; }


    // ==================== HELPER ====================

    // Ambil koneksi ke database
    private Connection getKoneksi() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // Method dengan nilai balik — tampilkan info singkat buku ini
    public String infoSingkat() {
        return String.format("ID: %d | %s | %s | Tahun: %d | Stok: %d",
            id, judul, pengarang, tahunTerbit, stok);
    }


    // ==================== CREATE (tanpa nilai balik / void) ====================
    public void tambahBuku() {
        String sql = "INSERT INTO buku (judul, pengarang, tahun_terbit, stok) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection    conn = getKoneksi();
             PreparedStatement pst  = conn.prepareStatement(sql)) {

            pst.setString(1, this.judul);
            pst.setString(2, this.pengarang);
            pst.setInt   (3, this.tahunTerbit);
            pst.setInt   (4, this.stok);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("[CREATE] Buku \"" + this.judul + "\" berhasil ditambahkan!");
            }

        } catch (SQLException e) {
            System.out.println("[CREATE ERROR] " + e.getMessage());
        }
    }


    // ==================== READ ====================

    // READ semua buku — tanpa nilai balik (void)
    public void tampilkanSemuaBuku() {
        String sql = "SELECT * FROM buku ORDER BY id ASC";

        System.out.println("\n=====================================================");
        System.out.println("              DAFTAR BUKU PERPUSTAKAAN              ");
        System.out.println("====================================================");

        try (Connection conn = getKoneksi();
             Statement  st   = conn.createStatement();
             ResultSet  rs   = st.executeQuery(sql)) {

            boolean ada = false;
            while (rs.next()) {
                ada = true;
                System.out.printf(" %-4d | %-30s | %-22s | %4d | Stok: %d%n",
                    rs.getInt   ("id"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getInt   ("tahun_terbit"),
                    rs.getInt   ("stok")
                );
            }
            if (!ada) {
                System.out.println(" (Belum ada data buku di database)");
            }

        } catch (SQLException e) {
            System.out.println("[READ ERROR] " + e.getMessage());
        }
        System.out.println("====================================================\n");
    }

    // READ cari satu buku by ID — dengan nilai balik (String)
    public String cariBukuById(int cariId) {
        String sql    = "SELECT * FROM buku WHERE id = ?";
        String hasil  = "Buku dengan ID " + cariId + " tidak ditemukan.";

        try (Connection        conn = getKoneksi();
             PreparedStatement pst  = conn.prepareStatement(sql)) {

            pst.setInt(1, cariId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                hasil = String.format("[READ] ID: %d | %s | %s | Tahun: %d | Stok: %d",
                    rs.getInt   ("id"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getInt   ("tahun_terbit"),
                    rs.getInt   ("stok")
                );
            }

        } catch (SQLException e) {
            hasil = "[READ ERROR] " + e.getMessage();
        }
        return hasil;
    }


    // ==================== UPDATE (tanpa nilai balik / void) ====================
    public void updateBuku(int targetId, String judulBaru, String pengarangBaru,
                           int tahunBaru, int stokBaru) {

        String sql = "UPDATE buku "
                   + "SET judul = ?, pengarang = ?, tahun_terbit = ?, stok = ? "
                   + "WHERE id = ?";

        try (Connection        conn = getKoneksi();
             PreparedStatement pst  = conn.prepareStatement(sql)) {

            pst.setString(1, judulBaru);
            pst.setString(2, pengarangBaru);
            pst.setInt   (3, tahunBaru);
            pst.setInt   (4, stokBaru);
            pst.setInt   (5, targetId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("[UPDATE] Buku ID " + targetId + " berhasil diperbarui!");
            } else {
                System.out.println("[UPDATE] Buku ID " + targetId + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("[UPDATE ERROR] " + e.getMessage());
        }
    }


    // ==================== DELETE (dengan nilai balik / return boolean) ====================
    public boolean hapusBuku(int targetId) {
        String sql = "DELETE FROM buku WHERE id = ?";

        try (Connection        conn = getKoneksi();
             PreparedStatement pst  = conn.prepareStatement(sql)) {

            pst.setInt(1, targetId);
            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("[DELETE] Buku ID " + targetId + " berhasil dihapus!");
                return true;
            } else {
                System.out.println("[DELETE] Buku ID " + targetId + " tidak ditemukan.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("[DELETE ERROR] " + e.getMessage());
            return false;
        }
    }
}

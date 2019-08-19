package mighty13.minterin;

public class ListMateriKuliah {
    public String judulMateri;
    public int jumlah_video;

    public ListMateriKuliah(String judulMateri, int jumlah_video) {
        this.judulMateri = judulMateri;
        this.jumlah_video = jumlah_video;
    }

    public String getJudulMateri() {
        return judulMateri;
    }

    public int getJumlah_video() {
        return jumlah_video;
    }
}

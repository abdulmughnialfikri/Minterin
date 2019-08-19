package mighty13.minterin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MataKuliahActivity extends AppCompatActivity {

    ListView listView;
    String judul_materi[] = {"Himpunan", "Aljabar", "Integral", "Turunan", "Persamaan", "Pertidaksamaan"};
    int jumlah_video[] = {12, 9, 13, 8, 5, 7};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_materi_01);

        //ListView
        listView = findViewById(R.id.lv_materi_matkul);
        MyAdapter adapterlist = new MyAdapter(this, judul_materi, jumlah_video);
        listView.setAdapter(adapterlist);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rJudul_materi[];
        int rJumlah_video[];

        MyAdapter(Context c, String judulm[], int jumlahVideo[]){
            super(c, R.layout.row_lv_materi_matkul, R.id.judul_materi, judulm);
            this.context = c;
            this.rJudul_materi = judulm;
            this.rJumlah_video = jumlahVideo;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_lv_materi_matkul, parent, false);
            TextView judulMateri = row.findViewById(R.id.judul_materi);
            TextView jumlahVideo = row.findViewById(R.id.jumlah_video);

            judulMateri.setText(rJudul_materi[position]);
            jumlahVideo.setText(rJumlah_video[position]+"");


            return row;
        }
    }
}

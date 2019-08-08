package mighty13.minterin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String judul_materi[] = {"Himpunan", "Aljabar", "Integral", "Turunan", "Persamaan", "Pertidaksamaan"};
    int jumlah_video[] = {12, 9, 13, 8, 5, 7};

    ProgressBar progBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListVideo> dummyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //Set Dummy Progress Bar
        progBar = findViewById(R.id.progressBar);
        progBar.setProgress(30);

//        //ListView
//        listView = findViewById(R.id.lv_materi_matkul);
//        MyAdapter adapterlist = new MyAdapter(this, judul_materi, jumlah_video);
//        listView.setAdapter(adapterlist);

        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rv_materi_himpunan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dummyList = new ArrayList<>();

        for (int i = 0; i <7 ; i++) {
            ListVideo dummyListItem;
            dummyListItem = new ListVideo(
                    "Himpunan 0" + (i+1),
                    20 + (i*10) );
            dummyList.add(dummyListItem);
        }

        adapter =  new ListVideoAdapter(dummyList,this);
        recyclerView.setAdapter(adapter);

    }

    class MyAdapter extends ArrayAdapter<String>{
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

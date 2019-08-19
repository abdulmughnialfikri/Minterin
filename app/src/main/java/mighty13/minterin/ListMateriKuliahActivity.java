package mighty13.minterin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListMateriKuliahActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListMateriKuliahAdapter adapter;
    private List<ListMateriKuliah> dummyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_materi_01);

        recyclerView = findViewById(R.id.rv_materi_matkul);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dummyList = new ArrayList<>();

        for (int i = 0; i <7 ; i++) {
            ListMateriKuliah dummyListItem;
            dummyListItem = new ListMateriKuliah(
                    "Materi 0" + (i+1),
                    3 + (i*2) );
            dummyList.add(dummyListItem);
        }

        adapter = new ListMateriKuliahAdapter(dummyList, this);
        recyclerView.setAdapter(adapter);

    }
}

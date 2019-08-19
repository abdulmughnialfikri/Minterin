package mighty13.minterin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class ListVideoActivity extends AppCompatActivity {

    ProgressBar progBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListVideo> dummyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_materi_02);

        //Set Dummy Progress Bar
        progBar = findViewById(R.id.progressBar);
        progBar.setProgress(30);

        //RecyclerView
        recyclerView = findViewById(R.id.rv_materi_himpunan);
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
}

package mighty13.minterin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListMateriKuliahAdapter extends RecyclerView.Adapter<ListMateriKuliahAdapter.ViewHolder> {

    private List<ListMateriKuliah> listmaterikuliah;
    private Context context;

    public ListMateriKuliahAdapter(List<ListMateriKuliah> listmaterikuliah, Context context) {
        this.listmaterikuliah = listmaterikuliah;
        this.context = context;
    }

    @NonNull
    @Override
    public ListMateriKuliahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_materi_kuliah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMateriKuliahAdapter.ViewHolder holder, int position) {
        ListMateriKuliah listMK = listmaterikuliah.get(position);

        holder.judulMateriKuliah.setText(listMK.getJudulMateri());
        holder.jumlahVideo.setText(listMK.getJumlah_video() + "");
    }

    @Override
    public int getItemCount() {
        return listmaterikuliah.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judulMateriKuliah;
        public TextView jumlahVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judulMateriKuliah = itemView.findViewById(R.id.tv_materiKuliah);
            jumlahVideo = itemView.findViewById(R.id.tv_jumlahVideo);
        }
    }
}
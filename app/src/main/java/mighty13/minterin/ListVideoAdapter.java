package mighty13.minterin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.ViewHolder> {

    private List<ListVideo> listVideos;
    private Context context;

    public ListVideoAdapter(List<ListVideo> listVideos, Context context) {
        this.listVideos = listVideos;
        this.context = context;
    }

    @NonNull
    @Override
    public ListVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_materi_konten, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVideoAdapter.ViewHolder viewHolder, int i) {
        ListVideo listVideo = listVideos.get(i);

        viewHolder.judulVideo.setText(listVideo.getJudulVideo());
        viewHolder.durasi.setText(listVideo.getDuration()+"");

    }

    @Override
    public int getItemCount() {
        return listVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView judulVideo;
        public TextView durasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judulVideo = (TextView) itemView.findViewById(R.id.tv_judul_video);
            durasi = (TextView) itemView.findViewById(R.id.tv_durasi_video);

        }
    }
}

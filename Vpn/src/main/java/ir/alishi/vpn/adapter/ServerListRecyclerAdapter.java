package ir.alishi.vpn.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.alishi.vpn.R;
import ir.alishi.vpn.model.ServerListModel;


public class ServerListRecyclerAdapter extends RecyclerView.Adapter<ServerListRecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<ServerListModel> models;
    private ItemClickListener mClickListener;


    public ServerListRecyclerAdapter(Context context, List<ServerListModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.sl_rows, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ServerListModel model = models.get(i);
        viewHolder.server_name.setText(model.getName());
        Glide.with(context).load(model.getFlag_name()).into(viewHolder.server_flag);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public ServerListModel getItem(int id) {
        return models.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RelativeLayout root;
        private final TextView server_name;
        private final ImageView server_flag;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.linearLayout_row);
            server_name = itemView.findViewById(R.id.tv_server_region);
            server_flag = itemView.findViewById(R.id.iv_flag);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

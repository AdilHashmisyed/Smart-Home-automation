package com.example.ibrahim.firebase;
        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import java.util.List;
        public class CyclerAdapter extends RecyclerView.Adapter<CyclerAdapter.HolderView> {
        private static CyclerAdapter.onItemClickLIstener onItemClickLIstener;
        List<CardViewData> list;
        public Context context;
        //public onItemClickLIstener onItemClickLIstener;
        public interface
        onItemClickLIstener{
        void onItemClick(int position);
        }
        public void setOnItemClickLIstener(RecyclerAdapter.onItemClickLIstener onItemClickLIstener) {
        this.onItemClickLIstener = (CyclerAdapter.onItemClickLIstener) onItemClickLIstener;
        }
        public CyclerAdapter(Context context, List<CardViewData> list){
        this.list=list;
        this.context=context;
        }
        @Override
        public int getItemCount() {
        return list.size();
        }
        @NonNull
        @Override
        public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.card_layout,viewGroup,false);
        return new HolderView(view,com.example.ibrahim.firebase.CyclerAdapter.onItemClickLIstener);
        }
        @Override
        public void onBindViewHolder(@NonNull HolderView holderView, int i) {
        CardViewData cardViewData=list.get(i);
        holderView.vehicle_name.setText(cardViewData.getName());
        holderView.ssid.setText(cardViewData.getSsid());
        holderView.status.setText(cardViewData.getStatus());
        }
        public static class HolderView extends RecyclerView.ViewHolder {
        private TextView vehicle_name,ssid,status;
        public HolderView(@NonNull View itemView,final onItemClickLIstener listener) {
        super(itemView);
        vehicle_name=itemView.findViewById(R.id.vehicle_name);
        ssid=itemView.findViewById(R.id.ssid_name);
        status=itemView.findViewById(R.id.status);
        itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        int id=getAdapterPosition();
        listener.onItemClick(id);
        }
        });
        /* itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent=new Intent(v.getContext(),AppInfo.class);
        }
        });*/
        }
        }
        }
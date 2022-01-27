package com.example.machinetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterViewHolder> {
    interface UpdateViewPager{
         void sendPosition(int position);
    }
    Context context;
    ArrayList<UserList> arrayList;
    UpdateViewPager updateViewPager;
    RecyclerViewAdapter(Context context, ArrayList<UserList> arrayList,UpdateViewPager updateViewPager){
       this.context=context;
       this.arrayList=arrayList;
       this.updateViewPager=updateViewPager;
    }
    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.userName.setText(arrayList.get(position).getTitle()+". "+arrayList.get(position).getFirstName()+" "+arrayList.get(position).getLastName());
    holder.gender_nat.setText(arrayList.get(position).getGender().substring(0, 1).toUpperCase()+ arrayList.get(position).getGender().substring(1).toLowerCase()+" . "+arrayList.get(position).getNat());
    holder.email.setText(arrayList.get(position).getUserEmail());
    if(arrayList.get(position).getHighLight()){
      holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.theme));
      holder.userName.setTextColor(context.getResources().getColor(R.color.white));
      holder.gender_nat.setTextColor(context.getResources().getColor(R.color.white));
      holder.email.setTextColor(context.getResources().getColor(R.color.white));
    }
    else{
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        holder.userName.setTextColor(context.getResources().getColor(R.color.black));
        holder.gender_nat.setTextColor(context.getResources().getColor(R.color.black));
        holder.email.setTextColor(context.getResources().getColor(R.color.pink));
    }
    holder.cardView.setOnClickListener(view -> updateViewPager.sendPosition(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView gender_nat,userName,email;
        CardView cardView;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            gender_nat=itemView.findViewById(R.id.gender_nat);
            userName=itemView.findViewById(R.id.userName);
            email=itemView.findViewById(R.id.email);
            cardView=itemView.findViewById(R.id.cardView);


        }
    }
}

package com.example.machinetest;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    Context context;
    ArrayList<UserList> arrayList;
    public SliderAdapter(Context context, ArrayList<UserList> arrayList) {
    this.arrayList=arrayList;
    this.context=context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_view_layout,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder viewHolder, int position) {
     viewHolder.userName.setText(Html.fromHtml("<u>"+arrayList.get(position).getTitle()+". "+arrayList.get(position).getFirstName()+" "+arrayList.get(position).getLastName()+"</u>"));
        Picasso.get().load(arrayList.get(position).getUserProfieleUrl()).into(viewHolder.userProfile);
        String addressText="<b><font color='#A259FF'>"+arrayList.get(position).getNumber()+"</font></b>"+" ,"+arrayList.get(position).getStreetName()+" , "
                +arrayList.get(position).getCity()+", "+arrayList.get(position).getState()+", "+ "<b>"+arrayList.get(position).getCountry()+"</b>"+", "+arrayList.get(position).getPostCode()+"<br>"+arrayList.get(position).getOffset()+"- "+arrayList.get(position).getDescription();
        viewHolder.address.setText(Html.fromHtml(addressText));
        viewHolder.gender.setText(arrayList.get(position).getGender().substring(0, 1).toUpperCase()+ arrayList.get(position).getGender().substring(1).toLowerCase());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{
      CircleImageView userProfile;
      TextView address,userName,gender;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile=itemView.findViewById(R.id.userProfile);
            address=itemView.findViewById(R.id.address);
            userName=itemView.findViewById(R.id.userName);
            gender=itemView.findViewById(R.id.gender);

        }
    }
}

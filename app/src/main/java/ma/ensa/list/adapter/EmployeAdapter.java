package ma.ensa.list.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


import ma.ensa.list.EmployeActivity;
import ma.ensa.list.R;
import ma.ensa.list.beans.Employe;
import ma.ensa.list.beans.Role;

public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.EmployeViewHolder> {
    private static final String TAG = "EmployeAdapter";
    private List<Employe> employes;
    private Context context;

    private RequestQueue requestQueue ;


    public EmployeAdapter(Context context, List<Employe> employes) {
        this.employes = employes;
        this.context = context;

    }


    @NonNull
    @Override
    public EmployeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.employe_item,
                viewGroup, false);
        final EmployeViewHolder holder = new EmployeViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull EmployeViewHolder starViewHolder, int i) {

        Log.d(TAG, "onBindView call ! "+ i);
        starViewHolder.name.setText(employes.get(i).getNom() + " " + employes.get(i).getPrenom());
        starViewHolder.date.setText("2002-11-30");
        starViewHolder.service.setText(employes.get(i).getService().getNom());
        starViewHolder.id.setText(employes.get(i).getId()+"");

        try {
            starViewHolder.photo.setImageBitmap(employes.get(i).getPhoto());
        }catch (Exception e){

        }


    }
    @Override
    public int getItemCount() {
        return employes.size();
    }
    public class EmployeViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView date;

        ImageView photo;

        TextView service;


        RelativeLayout parent;
        public EmployeViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            service = itemView.findViewById(R.id.service);
            photo = itemView.findViewById(R.id.photo);


        }
    }
    public  void  updateEmployes(List<Employe> employes){
        this.employes = employes;
        notifyDataSetChanged();
    }


    public void loadEmployes(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.103:8080/api/employe",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rep", response);
                        Type type = new TypeToken<List<Employe>>() {
                        }.getType();
                        updateEmployes( new Gson().fromJson(response, type));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

}
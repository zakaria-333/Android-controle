package ma.ensa.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ensa.list.adapter.EmployeAdapter;
import ma.ensa.list.beans.Employe;
import ma.ensa.list.beans.Service;

public class AddEmployeActivity extends AppCompatActivity {
    private EditText nom;
    private EditText prenom;
    private EditText date;

    private Spinner serviceSpinner;
    private Button add;

    private RequestQueue requestQueue;

    private Service hamburg;

    List<Service> serviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        nom = findViewById(R.id.addnom);
        prenom = findViewById(R.id.addprenom);
        date = findViewById(R.id.adddate);
        serviceSpinner = findViewById(R.id.addfiliere);
        add = findViewById(R.id.button);
        requestQueue = Volley.newRequestQueue(this);

        fetchSpinnerOptionsFromAPI();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmploye();
            }
        });

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Service selectedService = serviceList.get(position);
                Log.d("ServiceSelected", "ID: " + selectedService.getId() + " Code: " + selectedService.getNom());
                hamburg = new Service(selectedService.getId(), selectedService.getNom());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addEmploye() {
        String noms = nom.getText().toString();
        String prenoms = prenom.getText().toString();
        String dates = "2002-10-44";
        String serviceId = String.valueOf(hamburg.getId());


        String insertUrl = "http://192.168.107.159:8080/api/employe";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nom", noms);
            jsonBody.put("prenom", prenoms);
            jsonBody.put("dateNaissance", dates);
            JSONObject serviceObject = new JSONObject();
            serviceObject.put("id", hamburg.getId());
            jsonBody.put("service", serviceObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Employe created successfully ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddEmployeActivity.this, EmployeActivity.class);
                startActivity(intent);
                AddEmployeActivity.this.finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
            }
        });
        requestQueue.add(request);
    }
    private void fetchSpinnerOptionsFromAPI() {
        String Url = "http://192.168.107.159:8080/api/service";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REPONSE", response);
                        Type type = new TypeToken<List<Service>>() {
                        }.getType();
                        serviceList = new Gson().fromJson(response, type);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEmployeActivity.this, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        for (Service service : serviceList) {
                            adapter.add(service.getNom());
                        }
                        serviceSpinner.setAdapter(adapter);


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


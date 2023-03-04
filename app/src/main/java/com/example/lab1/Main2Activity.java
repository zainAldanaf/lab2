package com.example.lab1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements UserAdapter.ItemClickListener, UserAdapter.ItemClickListener2 {
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFireStoreList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<com.example.lab1.User> items;
    UserAdapter[] myListData;
    UserAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    ImageView delete;
    EditText updateName;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        updateName = findViewById(R.id.update_username);
       // update = findViewById(R.id.btn_update);
        EditText edit_name;
        rv = findViewById(R.id.rvRest);
        items = new ArrayList<com.example.lab1.User>();
        adapter = new UserAdapter(this, items, this, this);
        delete = findViewById(R.id.delete);
        GetAllProducts();


    }


    private void GetAllProducts() {

        db.collection("Users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String username = documentSnapshot.getString("User Name");

                                    com.example.lab1.User user = new com.example.lab1.User(id, username);
                                    items.add(user);

                                    rv.setLayoutManager(layoutManager);
                                    rv.setHasFixedSize(true);
                                    rv.setAdapter(adapter);
                                    ;
                                    adapter.notifyDataSetChanged();
                                    Log.e("LogDATA", items.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LogDATA", "get failed with ");


                    }
                });
    }



    @Override
    public void onItemClick(int position, String id) {
    }

    @Override
    public void onItemClick2(int position, String id) {


    }
}



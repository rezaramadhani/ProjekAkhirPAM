package com.example.projekuas;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditorActivity extends AppCompatActivity {
    private EditText editName,editGenre, editSynopsis;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editName = findViewById(R.id.name);
        editGenre = findViewById(R.id.genre);
        editSynopsis = findViewById(R.id.synopsis);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(EditorActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnSave.setOnClickListener(v -> {
            if (editName.getText().length()>0 && editGenre.getText().length()>0 &&editSynopsis.getText().length()>0){
                saveData(editName.getText().toString(), editGenre.getText().toString(), editSynopsis.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            editName.setText(intent.getStringExtra("name"));
            editGenre.setText(intent.getStringExtra("genre"));
            editSynopsis.setText(intent.getStringExtra("synopsis"));
        }
    }

    private void saveData(String name, String genre, String synopsis){
        //mengambil data dari database
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("genre", genre);
        user.put("synopsis", synopsis);

        progressDialog.show();
        if (id!=null){
            db.collection("users").document(id)
                    .set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            //menambahkan data
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}
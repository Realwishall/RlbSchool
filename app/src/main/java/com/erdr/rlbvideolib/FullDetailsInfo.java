package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FullDetailsInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details_info);
        ClassSpinner();
        BatchSpinner();
    }

    private void BatchSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.branch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.batch_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void ClassSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void SavedDetails(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinnerBranch = (Spinner) findViewById(R.id.branch);


        EditText editText = findViewById(R.id.editText);
        if(editText.getText().toString().length()<1){
            Toast.makeText(getApplicationContext(),"Name can not be empty",Toast.LENGTH_LONG).show();
        }
        else if(String.valueOf(spinner.getSelectedItem()).equals("Select Class")){
            Toast.makeText(getApplicationContext(),"Class can not be empty",Toast.LENGTH_LONG).show();
        }
        else if(String.valueOf(spinnerBranch.getSelectedItem()).equals("Select Batch")){
            Toast.makeText(getApplicationContext(),"Branch can not be empty",Toast.LENGTH_LONG).show();
        }
        else {
            SaveDataInFireBase(editText.getText().toString(),String.valueOf(spinner.getSelectedItem()),String.valueOf(spinnerBranch.getSelectedItem()));
        }
    }

    private void SaveDataInFireBase(String Name, String UserClass, String Batch) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Uploaded");
        progressDialog.show();
        final String[] separated = UserClass.split(" ");

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        CollectionReference cities = db.collection("USERDATA");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("Name", Name);
        data1.put("classInfo", separated[0]+separated[1]);
        data1.put("Batch", Batch);
        data1.put("Verified", "Waiting");
        data1.put("SchoolName", R.string.schoolNamelectureFileAddress);
        data1.put("PhoneNumber", currentUser.getPhoneNumber());
        cities.document(currentUser.getUid()).set(data1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                OpenSecondAct(separated[0]+separated[1]);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed To Saved Try again",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void OpenSecondAct(String classInfo) {
        Intent intent = new Intent(this, SubjectPage.class);
        intent.putExtra("NameOfClass",classInfo);
        startActivity(intent);
    }
}

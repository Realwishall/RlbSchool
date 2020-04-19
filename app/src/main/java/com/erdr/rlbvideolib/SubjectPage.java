package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SubjectPage extends AppCompatActivity {
    String NameOfClass;
    RecyclerView recyclerView;
    List<ListSubjectPage> listSubjectPages;
    AdapterSubjectPage adapterSubjectPage;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_page);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.top));
        }
        listSubjectPages  = new ArrayList<>();
        recyclerView = findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NameOfClass = getIntent().getStringExtra("NameOfClass");


        GetNameOfSubject();
        SetThisTestInTop(NameOfClass);

       Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.change_class, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    public void ChangeClass(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] separated = String.valueOf(spinner.getSelectedItem()).split(" ");
        final String finalClass = separated[0]+separated[1];


        if(finalClass.equals(NameOfClass)){
            Toast.makeText(getApplicationContext(),"Class is unaltered",Toast.LENGTH_LONG).show();
        }
        else if(finalClass.equals("Change Class")){
            Toast.makeText(getApplicationContext(),"Please select class",Toast.LENGTH_LONG).show();
        }
        else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("There are limited times you can change class. Do you want to do it");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            TryToChangeTheClass(finalClass);
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }

    private void TryToChangeTheClass(final String finalClass) {


        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final DocumentReference sfDocRef = db.collection("USERDATA"+getString(R.string.storeSchoolName))
                .document(currentUser.getUid());

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sfDocRef);

                if(Integer.parseInt(snapshot.get("ClassChangeCount").toString())<Integer.parseInt(getString(R.string.maxNumberToChangeClass))){
                    transaction.update(sfDocRef, "ClassChangeCount", FieldValue.increment(1));
                    transaction.update(sfDocRef, "classInfo", finalClass);
                }
                else {
                    ToastTheFailMessage();
                }

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ToTheClasChange(finalClass);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ToastTheFailMessage();
                    }
                });
    }


    private void ToTheClasChange(String finalClass) {
        SetThisTestInTop(finalClass);
        NameOfClass = finalClass;
        listSubjectPages.clear();
        StartTheAdapter();
        GetNameOfSubject();
    }

    private void ToastTheFailMessage() {
        Toast.makeText(getApplicationContext(),"To many class change can not be changed again/ Contact admin",Toast.LENGTH_LONG).show();
    }

    private void SetThisTestInTop(String setter) {
        TextView ChapterName = findViewById(R.id.ChapterName);
        ChapterName.setText(setter);
    }

    private void GetNameOfSubject() {
        String FullAddress = getString(R.string.schoolNamelectureFileAddress)+"/VIDEODATA/"+NameOfClass;
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db
                .collection(FullAddress)
                .document("SubjectName");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int StartingNumber = 1;
                        listSubjectPages.clear();
                        while (document.get("Subject"+String.valueOf(StartingNumber)) != null){
                            listSubjectPages.add(new ListSubjectPage(document.get("Subject"+String.valueOf(StartingNumber)).toString()));
                            StartingNumber++;
                        }
                        StartTheAdapter();

                    } else {
                    }
                } else {
                }
            }
        });

    }

    private void StartTheAdapter() {
        adapterSubjectPage = new AdapterSubjectPage(this,listSubjectPages);
        recyclerView.setAdapter(adapterSubjectPage);
    }

    public void userItemClick(int adapterPosition) {
        Intent intent = new Intent(this, ChapterName.class);
        intent.putExtra("NameOfSubject",listSubjectPages.get(adapterPosition).getSubjectName());
        intent.putExtra("NameOfClass",NameOfClass);
        startActivity(intent);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}
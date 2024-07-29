package com.hiskytech.feastfleet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hiskytech.feastfleet.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirestoreRecyclerAdapter<ModelImage, AdapterImage.ImageViewHolder> adapterHeader;
    private FirestoreRecyclerAdapter<ModelImage, AdapterImage.ImageViewHolder> adapterSection1;
    private FirestoreRecyclerAdapter<ModelImage, AdapterImage.ImageViewHolder> adapterSection2;
    private FirestoreRecyclerAdapter<ModelImage, AdapterImage.ImageViewHolder> adapterSection3;
    private FirestoreRecyclerAdapter<ModelImage, AdapterImage.ImageViewHolder> adapterSection4;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userName = getIntent().getStringExtra("userName");
        binding.textView5.setText(userName);

        db = FirebaseFirestore.getInstance();

        setupRecyclerView(binding.rvHeader, "headerCollection");
        setupSectionRecyclerViews();
    }

    private void setupRecyclerView(RecyclerView recyclerView, String collection) {
        Query query;
        if (collection.equals("headerCollection")) {
            // Filter documents where the field "name" is equal to "res" for the headerCollection adapter
            query = db.collection("Food").whereEqualTo("name", "res");
        } else {
            // Default query without any filtering for other sections
            query = db.collection("Food");
        }

        FirestoreRecyclerOptions<ModelImage> options = new FirestoreRecyclerOptions.Builder<ModelImage>()
                .setQuery(query, ModelImage.class)
                .build();

        AdapterImage adapter = new AdapterImage(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        if (collection.equals("headerCollection")) {
            adapterHeader = adapter;
        }
    }

    private void setupSectionRecyclerViews() {
        db.collection("Food").whereNotEqualTo("name", "res").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        int totalDocuments = documents.size();
                        int partitionSize = totalDocuments / 4;

                        List<DocumentSnapshot> section1List = documents.subList(0, partitionSize);
                        List<DocumentSnapshot> section2List = documents.subList(partitionSize, partitionSize * 2);
                        List<DocumentSnapshot> section3List = documents.subList(partitionSize * 2, partitionSize * 3);
                        List<DocumentSnapshot> section4List = documents.subList(partitionSize * 3, totalDocuments);

                        setupSectionRecyclerView(binding.rvSection1, section1List, LinearLayoutManager.HORIZONTAL);
                        setupSectionRecyclerView(binding.rvSection2, section2List, LinearLayoutManager.HORIZONTAL);
                        setupSectionRecyclerView(binding.rvSection3, section3List, LinearLayoutManager.HORIZONTAL);
                        setupSectionRecyclerView(binding.rvSection4, section4List, LinearLayoutManager.VERTICAL);
                    } else {
                        Log.d("MainActivity", "Error getting documents: ", task.getException());
                    }
                });
    }

    private void setupSectionRecyclerView(RecyclerView recyclerView, List<DocumentSnapshot> documentList, int orientation) {
        if (documentList.isEmpty()) {
            // Handle the case where the document list is empty
            recyclerView.setAdapter(null);
            return;
        }

        Query query = db.collection("Food").whereIn("__name__", extractIds(documentList));

        FirestoreRecyclerOptions<ModelImage> options = new FirestoreRecyclerOptions.Builder<ModelImage>()
                .setQuery(query, ModelImage.class)
                .build();

        AdapterImage adapter = new AdapterImage(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, orientation, false));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private List<String> extractIds(List<DocumentSnapshot> documentList) {
        List<String> ids = new ArrayList<>();
        for (DocumentSnapshot doc : documentList) {
            ids.add(doc.getId());
        }
        return ids;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapterHeader != null) adapterHeader.stopListening();
        if (adapterSection1 != null) adapterSection1.stopListening();
        if (adapterSection2 != null) adapterSection2.stopListening();
        if (adapterSection3 != null) adapterSection3.stopListening();
        if (adapterSection4 != null) adapterSection4.stopListening();
    }
}

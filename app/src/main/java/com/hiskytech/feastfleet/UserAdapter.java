package com.hiskytech.feastfleet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<ModelUser> userList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserAdapter() {
        fetchUsers();
    }

    private void fetchUsers() {
        db.collection("users").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            String name = document.getString("name");
                            String email = document.getString("email");
                            userList.add(new ModelUser(name, email));
                        }
                        notifyDataSetChanged();
                    } else {
                        // Handle the error
                    }
                });
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ModelUser user = userList.get(position);
        holder.textViewUserName.setText(user.getName());
        holder.textViewUserEmail.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        TextView textViewUserEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
        }
    }
}

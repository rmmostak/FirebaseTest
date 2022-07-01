package com.rmproduct.apptest.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rmproduct.apptest.AddFragment;
import com.rmproduct.apptest.ImageModel;
import com.rmproduct.apptest.ListAdapter;
import com.rmproduct.apptest.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    RecyclerView recycler;
    Button add;
    TextView total;
    LinearLayout dash;
    List<ImageModel> modelList;
    ListAdapter adapter;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recycler = root.findViewById(R.id.recycler);
        add = root.findViewById(R.id.addImg);
        total = root.findViewById(R.id.total);
        dash = root.findViewById(R.id.dash);

        add.setOnClickListener(v -> {
            Log.d("Add", "Works");
            dash.setVisibility(View.GONE);
            FragmentManager manager = getFragmentManager();
            Fragment fragment = new AddFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.addFrame, fragment);
            transaction.commit();
        });

        modelList = new ArrayList<>();
        makeList();

        return root;
    }

    private void makeList() {
        modelList.clear();
        reference = FirebaseDatabase.getInstance().getReference("Gallery").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ImageModel model = snapshot1.getValue(ImageModel.class);
                    Log.d("title", model.getTitle());
                    modelList.add(model);
                    i++;
                }
                total.setText(""+i+"");
                adapter = new ListAdapter(modelList, getActivity());
                recycler.setAdapter(adapter);
                recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
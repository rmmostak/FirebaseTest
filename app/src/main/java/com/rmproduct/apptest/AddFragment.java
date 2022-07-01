package com.rmproduct.apptest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rmproduct.apptest.ui.dashboard.DashboardFragment;


public class AddFragment extends Fragment {

    View view;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 22;
    int id = 0;

    FirebaseStorage storage;
    public StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);

        EditText title = view.findViewById(R.id.inTitle);
        EditText date = view.findViewById(R.id.inDate);
        Button submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(view1 -> {
            if (!title.getText().toString().isEmpty()) {

                if (!date.getText().toString().isEmpty()) {

                    reference = FirebaseDatabase.getInstance().getReference("Gallery").child(user.getUid());
                    String id = reference.push().getKey();

                    ImageModel model = new ImageModel(id, title.getText().toString().trim(), "IMG URL", date.getText().toString().trim());
                    reference.child(id).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Fragment fragment = new DashboardFragment();
                            FragmentManager manager = getFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.setReorderingAllowed(true);
                            transaction.replace(R.id.addFrame, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }
                    });

                } else {
                    date.requestFocus();
                    date.setError("Please enter date");
                }

            } else {
                title.requestFocus();
                title.setError("Please enter image title");
            }
        });

        return view;
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case PICK_IMAGE_REQUEST:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    //.setImageURI(selectedImage);
                    break;
            }
    }
}
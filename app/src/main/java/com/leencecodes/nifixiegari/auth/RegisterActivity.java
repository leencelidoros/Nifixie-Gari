package com.leencecodes.nifixiegari.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leencecodes.nifixiegari.databinding.ActivityRegisterBinding;
import com.leencecodes.nifixiegari.models.Mechanic;
import com.leencecodes.nifixiegari.models.User;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.buttonCreateAccount.setOnClickListener(v -> {
            String mail = binding.signUpEmailEditText.getText().toString().trim();
            String pass = binding.signUpPasswordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(binding.signUpEmailEditText.getText().toString())) {
                binding.signUpEmailEditText.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.signUpPasswordEditText.getText().toString())) {
                binding.signUpPasswordEditText.setError("Field can't be empty!");
                return;
            } else if (binding.signUpPasswordEditText.length() < 6) {
                binding.signUpPasswordEditText.setError("Password should be 6 characters or more!");
            } else if (!isValidMail(binding.signUpEmailEditText.getText().toString())) {
                binding.signUpEmailEditText.setError("Invalid Email!");
            } else {
                binding.signUpProgressBar.setVisibility(View.VISIBLE);
            }

            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseUser = firebaseAuth.getCurrentUser();

                        User user = new User(binding.userFullName.getText().toString());
                        Mechanic mechanic = new Mechanic(binding.userFullName.getText().toString(),"Nakuru, Kenya", "",firebaseUser.getUid());

                        databaseReference.child("mechanics").push().setValue(mechanic);

                        databaseReference.child("users").child(firebaseUser.getUid()).setValue(user);

                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(binding.userFullName.getText().toString())
                                .build();

                        assert firebaseUser != null;
                        firebaseUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Verification Email Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        binding.signUpProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                        binding.signUpProgressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        });

        binding.textViewHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean isValidMail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
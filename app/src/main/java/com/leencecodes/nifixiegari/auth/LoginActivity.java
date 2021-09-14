package com.leencecodes.nifixiegari.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leencecodes.nifixiegari.MainActivity;
import com.leencecodes.nifixiegari.R;
import com.leencecodes.nifixiegari.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        //signUp.setOnClickListener(v ->
        //Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.signInEmailEditText.getText().toString().trim();
                String passwd = binding.signInPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(binding.signInEmailEditText.getText().toString())) {
                    binding.signInEmailEditText.setError("Field can't be empty!!");
                    return;
                } else if (TextUtils.isEmpty(binding.signInPasswordEditText.getText().toString())) {
                    binding.signInPasswordEditText.setError("Field can't be empty!!");
                    return;
                } else {
                    binding.signInProgressBar.setVisibility(View.VISIBLE);
                }

                firebaseAuth.signInWithEmailAndPassword(mail, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "welcome", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Verify your email first", Toast.LENGTH_LONG).show();
                                binding.signInEmailEditText.setText("");
                                binding.signInPasswordEditText.setText("");
                                binding.signInProgressBar.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            binding.signInProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        binding.textViewDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
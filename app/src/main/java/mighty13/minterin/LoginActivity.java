package mighty13.minterin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView tvToRegist;
    EditText email, password;
    Button signIn;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        tvToRegist = findViewById(R.id.tv_toRegist);
        email = findViewById(R.id.et_emailLogin);
        password = findViewById(R.id.et_passLogin);
        signIn = findViewById(R.id.btn_signIn);
        mFirebaseAuth = mFirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    //Toast.makeText(LoginActivity.this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MataKuliahActivity.class);
                    startActivity(intent);
                } else {
                    //Jika Belum Login
//                    Toast.makeText(LoginActivity.this, "Silahkan Login", Toast.LENGTH_SHORT)
//                            .show();
                }
            }
        };

        //Sign In Button Event
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailVal = email.getText().toString();
                final String passwordVal = password.getText().toString();

                if(emailVal.isEmpty()){
                    email.setError("Email Tidak Boleh Kosong");
                    email.requestFocus();
                }
                else if(passwordVal.isEmpty()){
                    password.setError("Password Tidak Boleh Kosong");
                    password.requestFocus();
                }
                else if(emailVal.isEmpty() && passwordVal.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi Seluruh Data", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(!(emailVal.isEmpty()) && !(passwordVal.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailVal, passwordVal).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MataKuliahActivity.class);
                                startActivity(intent);

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Intent to Registration Page
        tvToRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}

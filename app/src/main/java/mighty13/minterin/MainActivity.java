package mighty13.minterin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String judul_materi[] = {"Himpunan", "Aljabar", "Integral", "Turunan", "Persamaan", "Pertidaksamaan"};
    int jumlah_video[] = {12, 9, 13, 8, 5, 7};

    ProgressBar progBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListVideo> dummyList;

    EditText username, email, password;
    Button btnSignUp;
    TextView tvToLogin;
    FirebaseAuth mFirebaseAuth;
    CircleImageView civProfPict;
    Uri pickedImgUri;

    static int PReqCode = 1;
    static int REQUESTCODE = 1;

    private void checkAndRequestForPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(MainActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        mFirebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.et_unameRegist);
        email = findViewById(R.id.et_emailRegist);
        password = findViewById(R.id.et_passwordRegist);
        btnSignUp = findViewById(R.id.btn_signUp);
        tvToLogin = findViewById(R.id.tv_toLogin);
        civProfPict = findViewById(R.id.civ_ProfPict);

        civProfPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                if (Build.VERSION.SDK_INT >= 22){
                    checkAndRequestForPermission();
                } else {
                    //openGallery()
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameValue = username.getText().toString();
                final String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();

                if (usernameValue.isEmpty()){
                    username.setError("Username Tidak Boleh Kosong");
                    username.requestFocus();
                }
                else if(emailValue.isEmpty()){
                    email.setError("Email Tidak Boleh Kosong");
                    email.requestFocus();
                }
                else if(passwordValue.isEmpty()){
                    password.setError("Password Tidak Boleh Kosong");
                    password.requestFocus();
                }
                else if(usernameValue.isEmpty() && emailValue.isEmpty() && passwordValue.isEmpty()){
                    Toast.makeText(MainActivity.this, "Isi Seluruh Data", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(!(usernameValue.isEmpty()) && !(emailValue.isEmpty()) && !(passwordValue.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user = new User(
                                        usernameValue,
                                        emailValue);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            updateUserInfo(usernameValue, pickedImgUri, mFirebaseAuth.getCurrentUser());
                                            Toast.makeText(MainActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



        //Set Dummy Progress Bar
//        progBar = findViewById(R.id.progressBar);
//        progBar.setProgress(30);

//        //ListView
//        listView = findViewById(R.id.lv_materi_matkul);
//        MyAdapter adapterlist = new MyAdapter(this, judul_materi, jumlah_video);
//        listView.setAdapter(adapterlist);

        //RecyclerView
//        recyclerView = findViewById(R.id.rv_materi_himpunan);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        dummyList = new ArrayList<>();
//
//        for (int i = 0; i <7 ; i++) {
//            ListVideo dummyListItem;
//            dummyListItem = new ListVideo(
//                    "Himpunan 0" + (i+1),
//                    20 + (i*10) );
//            dummyList.add(dummyListItem);
//        }
//
//        adapter =  new ListVideoAdapter(dummyList,this);
//        recyclerView.setAdapter(adapter);

    }

    private void updateUserInfo(final String usernameValue, Uri pickedImgUri, final FirebaseUser currentUser) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Upload image success
                //now we can get out image uri
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(usernameValue)
                                .setPhotoUri(uri)
                                .build();
                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                });
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rJudul_materi[];
        int rJumlah_video[];

        MyAdapter(Context c, String judulm[], int jumlahVideo[]){
            super(c, R.layout.row_lv_materi_matkul, R.id.judul_materi, judulm);
            this.context = c;
            this.rJudul_materi = judulm;
            this.rJumlah_video = jumlahVideo;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_lv_materi_matkul, parent, false);
            TextView judulMateri = row.findViewById(R.id.judul_materi);
            TextView jumlahVideo = row.findViewById(R.id.jumlah_video);

            judulMateri.setText(rJudul_materi[position]);
            jumlahVideo.setText(rJumlah_video[position]+"");


            return row;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            pickedImgUri = data.getData();
            civProfPict.setImageURI(pickedImgUri);
        }
    }
}

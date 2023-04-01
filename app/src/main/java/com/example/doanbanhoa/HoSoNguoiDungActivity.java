package com.example.doanbanhoa;

import static com.example.doanbanhoa.LayHinhAnh.loadImageFromUrl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.Console;
import java.util.PrimitiveIterator;

public class HoSoNguoiDungActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView ImageCaNhan;

    EditText txtHoTen,txtSDT, txtNgaySinh;
    Button btnCatNhat;
    TextView email;
    private  StorageReference mSttorageRef;
    private DatabaseReference mDatabaseRef;

    private FirebaseAuth auth;
    private Uri Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so_nguoi_dung);
        ImageCaNhan = findViewById(R.id.anhhoso);
        txtHoTen = findViewById(R.id.HoTen);
        txtNgaySinh = findViewById(R.id.NgaySinh);
        txtSDT = findViewById(R.id.SDT);
        btnCatNhat = findViewById(R.id.catnhatthongtin);
        email = findViewById(R.id.email);

        mSttorageRef = FirebaseStorage.getInstance().getReference("uploadsCaNhan");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        mSttorageRef.child(auth.getCurrentUser().getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(150,150).into(ImageCaNhan);
            //    Toast.makeText(HoSoNguoiDungActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        email.setText(auth.getCurrentUser().getEmail());

        mDatabaseRef.child(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User user = (User) dataSnapshot.getValue(User.class);
                txtHoTen.setText(user.getHoTen());
                txtSDT.setText(user.getSDT());
                txtNgaySinh.setText(user.getNgaySinh());
            }
        });

        ImageCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoAnh();
            }
        });

        btnCatNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadthongtin();
                finish();
            }
        });

    }
    private void MoAnh(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&  data.getData() != null){
            Image = data.getData();
            Picasso.get().load(Image).resize(150,150).into(ImageCaNhan);
        }
    }

    private String getFileEx(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadthongtin(){
        FirebaseUser currentuser = auth.getCurrentUser();


        if(ImageCaNhan.getDrawable() != null){
           if(Image != null){
               StorageReference fileRef = mSttorageRef.child(currentuser.getUid() +  "." +
                       getFileEx(Image));

               fileRef.putFile(Image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(HoSoNguoiDungActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }
            mSttorageRef.child(auth.getCurrentUser().getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    User user = new User(uri.toString(),txtHoTen.getText().toString(),email.getText().toString(),txtSDT.getText().toString(),
                            txtNgaySinh.getText().toString());
                    mDatabaseRef.child(currentuser.getUid()).setValue(user);
                    Toast.makeText(HoSoNguoiDungActivity.this,"Cật nhật thành công!",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this,"Chưa chọn file",Toast.LENGTH_SHORT).show();
        }
    }
}
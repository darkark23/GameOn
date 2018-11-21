package br.com.daniel.gameon.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import br.com.daniel.gameon.Manifest;
import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Horarios;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.MaskEditUtil;

public class InformacoesPessoaisActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private ImageView editProfilePicture;
    private EditText userFullName;
    private EditText userDateOfBirth;
    private RadioButton maleGenre;
    private RadioButton femaleGenre;
    private EditText userCEP;
    private EditText userPhone;
    private String storagePath;

    private ProgressBar progressBar;

    private TextView nextStep;

    private final int GALLERY_IMAGES = 1;
    private final int PERMISSAO_REQUEST = 2;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_pessoais);

        FirebaseApp.initializeApp(InformacoesPessoaisActivity.this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        userFullName = (EditText) findViewById(R.id.userFullName);
        maleGenre = (RadioButton) findViewById(R.id.maleGenre);
        femaleGenre = (RadioButton) findViewById(R.id.femaleGenre);

        profilePicture = (ImageView) findViewById(R.id.profilePicture);
        editProfilePicture = (ImageView) findViewById(R.id.editProfilePicture);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        userDateOfBirth = (EditText) findViewById(R.id.userDateOfBirth);
        userDateOfBirth.addTextChangedListener(MaskEditUtil.mask(userDateOfBirth, MaskEditUtil.FORMAT_DATE));
        userCEP = (EditText) findViewById(R.id.userCEP);
        userCEP.addTextChangedListener(MaskEditUtil.mask(userCEP, MaskEditUtil.FORMAT_CEP));
        userPhone = (EditText) findViewById(R.id.userPhone);
        userPhone.addTextChangedListener(MaskEditUtil.mask(userPhone, MaskEditUtil.FORMAT_FONE));

        nextStep = (TextView) findViewById(R.id.nextStep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ nextStep(); }
        });

        editProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_IMAGES);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_IMAGES) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            profilePicture.setImageBitmap(thumbnail);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataProfilePicture = baos.toByteArray();

            storagePath = "profilePictures/" + this.mAuth.getCurrentUser().getUid() + ".png";
            StorageReference storagePathReference = storage.getReference(storagePath);
            UploadTask uploadTask = storagePathReference.putBytes(dataProfilePicture);

            editProfilePicture.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            uploadTask.addOnSuccessListener(InformacoesPessoaisActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    editProfilePicture.setEnabled(true);
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSAO_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  A  permissão  foi  concedida.  Pode  continuar
            } else {
                //  A  permissão  foi  negada.  Precisa  ver  o  que  deve  ser  desabilitado
            }
            return;
        }
    }

    public void nextStep(){
        Intent intent = new Intent(InformacoesPessoaisActivity.this, InformacoesGamerActivity.class);
        Bundle bundle = new Bundle();

        Usuario usuario = new Usuario();
        String id = UUID.randomUUID().toString();
        usuario.setIdAutenticacao(mAuth.getCurrentUser().getUid());
        usuario.setIdUsuario(id);
        usuario.setIdImagem(storagePath);
        usuario.setNomeComlpeto(userFullName.getText().toString());
        usuario.setEndereco(userCEP.getText().toString());
        usuario.setDataNascimento(userDateOfBirth.getText().toString());
        usuario.setTelefone(userPhone.getText().toString());
        usuario.setSexo(maleGenre.isSelected() ? "Masculino" : "Feminino");
        Horarios horarios = new Horarios();
        String id2 = UUID.randomUUID().toString();
        horarios.setIdHorarios(id2);
        ArrayList<String> inicio = new ArrayList<>();
        inicio.add("210000");
        inicio.add("210000");
        inicio.add("210000");
        inicio.add("210000");
        inicio.add("210000");
        inicio.add("210000");
        inicio.add("210000");
        ArrayList<String> end = new ArrayList<>();
        end.add("230000");
        end.add("230000");
        end.add("230000");
        end.add("230000");
        end.add("230000");
        end.add("230000");
        horarios.setHoariosInicio(inicio);
        horarios.setHoariosFim(end);
        horarios.setObservacao("babebabe do pirulaibe");
        horarios.setIdUsuario(id);
        databaseReference.child("usuarios").child(id).setValue(usuario);
        databaseReference.child("horarios").child(id2).setValue(horarios);

        bundle.putSerializable("user", usuario);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
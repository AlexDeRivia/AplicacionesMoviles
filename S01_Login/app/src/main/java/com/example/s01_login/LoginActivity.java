package com.example.s01_login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    EditText etCodigo, etDni, etPassword;
    Button btnLogin;

    // valores fijos
    String demoCodigo = "0202114001";
    String demoDni = "7654321";
    String demoPassword = "abc123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCodigo = findViewById(R.id.etCodigo);
        etDni = findViewById(R.id.etDni);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String codigo = etCodigo.getText().toString();
            String dni = etDni.getText().toString();
            String password = etPassword.getText().toString();

            if (codigo.equals(demoCodigo) && dni.equals(demoDni) && password.equals(demoPassword)) {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
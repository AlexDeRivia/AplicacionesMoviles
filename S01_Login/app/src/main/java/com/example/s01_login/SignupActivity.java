package com.example.s01_login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {
    EditText etDni, etCodigo, etPassword, etNombre, etApellido;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etDni = findViewById(R.id.etDni);
        etCodigo = findViewById(R.id.etCodigo);
        etPassword = findViewById(R.id.etPassword);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> {
            String dni = etDni.getText().toString();
            String codigo = etCodigo.getText().toString();
            String password = etPassword.getText().toString();
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();

            if (!dni.isEmpty() && !codigo.isEmpty() && !password.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty()) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
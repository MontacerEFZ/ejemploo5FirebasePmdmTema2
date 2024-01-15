package montacer.elfazazi.ejemplo5firebasepmdmtema2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import montacer.elfazazi.ejemplo5firebasepmdmtema2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myPersona;
    private ArrayList<Persona> listaPersonas;
    private DatabaseReference myListPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listaPersonas = new ArrayList<>();

        database = FirebaseDatabase.getInstance("https://ejemplofirebasebpmdmtema2-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = database.getReference("frase");
        myPersona = database.getReference("persona");
        myListPersonas = database.getReference("listaPersonas");

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue(binding.txtFrase.getText().toString());
                int edad = (int)(Math.random()*100);
                Persona p = new Persona(binding.txtFrase.getText().toString(), edad);
                myPersona.setValue(p);
                listaPersonas.add(p);
                myListPersonas.setValue(listaPersonas);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String texto = snapshot.getValue(String.class);
                binding.lbFrase.setText(texto);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.toException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        myPersona.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Persona p = snapshot.getValue(Persona.class);
                binding.lbFrase.setText(p.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myListPersonas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Persona>> gti = new GenericTypeIndicator<ArrayList<Persona>>() {};
                ArrayList<Persona> lista = snapshot.getValue(gti);
                binding.lbFrase.setText("elementos en la lista: " + lista.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
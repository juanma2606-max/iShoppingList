package es.iescarrillo.android.ishoppinglist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Detalles extends AppCompatActivity {

    TextView idTx,nomTx,infoTx,estadoTx;
    Button btnEditar, btnVolver;

    ActivityResultLauncher<Intent> editLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Product edited = (Product) result.getData().getSerializableExtra("editedProduct");
                    if (edited != null) {
                        // Actualiza los TextView en esta pantalla
                        idTx.setText(String.valueOf(edited.getId()));
                        nomTx.setText(edited.getNom());
                        infoTx.setText(edited.getInfo());
                        estadoTx.setText(edited.isEstado() ? "Pendiente" : "No pendiente");

                        // Devuelve el producto editado al MainActivity
                        Intent backIntent = new Intent();
                        backIntent.putExtra("updatedProduct", edited);
                        setResult(RESULT_OK, backIntent);
                    }
                }
            }
    );

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detalles);

        idTx=findViewById(R.id.id);
        nomTx=findViewById(R.id.nom);
        infoTx=findViewById(R.id.info);
        estadoTx=findViewById(R.id.estado);
        btnEditar=findViewById(R.id.btnEditar);
        btnVolver=findViewById(R.id.btnVolver);

        //Obtenemos el objeto producto del intent
        Product p = (Product) getIntent().getSerializableExtra("product");

        if(p !=null){
            idTx.setText(String.valueOf(p.getId()));
            nomTx.setText(p.getNom());
            infoTx.setText(p.getInfo());
            boolean estado= p.isEstado();
            if(estado){
                estadoTx.setText("Pendiente");
            }else{
                estadoTx.setText("No pendiente");
            }
        }

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p != null) {
                    Intent intent = new Intent(Detalles.this, Editar.class);
                    intent.putExtra("product", p);
                    editLauncher.launch(intent);
                }
            }
        });

    }

}

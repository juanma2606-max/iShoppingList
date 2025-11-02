package es.iescarrillo.android.ishoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class Aniadir extends ComponentActivity {
    EditText nomTx,infoTx,estadoTx;
    Button btnVolver, btnOk;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.aniadir);

        nomTx=findViewById(R.id.nomTx);
        infoTx=findViewById(R.id.infoTx);
        estadoTx=findViewById(R.id.estadoTx);
        btnOk=findViewById(R.id.btnOk);
        btnVolver=findViewById(R.id.btnVolver);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) (Math.random() * 10000);
                String nom = nomTx.getText().toString();
                String info = infoTx.getText().toString();
                String stat = estadoTx.getText().toString();
                boolean estado=false;

                if (nom.isEmpty() || info.isEmpty() || stat.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(stat.equals("Pendiente")){
                    estado=true;
                }else if(stat.equals("No Pendiente")){
                    estado=false;
                }else{
                    Toast.makeText(getApplicationContext(), "Error: estado no válido", Toast.LENGTH_SHORT).show();
                }

                Intent res = new Intent();
                Product p = new Product(id,nom,info,estado);
                res.putExtra("newProduct", p);
                setResult(RESULT_OK, res);
                finish();
            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

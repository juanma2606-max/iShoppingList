package es.iescarrillo.android.ishoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class Editar extends ComponentActivity {

    EditText nomTx,infoTx,estadoTx;
    Button btnVolver, btnOk;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.editar);

        nomTx=findViewById(R.id.nomTx);
        infoTx=findViewById(R.id.infoTx);
        estadoTx=findViewById(R.id.estadoTx);
        btnOk=findViewById(R.id.btnOk);
        btnVolver=findViewById(R.id.btnVolver);

        //Obtenemos el objeto producto del intent
        Product p = (Product) getIntent().getSerializableExtra("product");

        if(p !=null){
            nomTx.setText(p.getNom());
            infoTx.setText(p.getInfo());
            boolean estado= p.isEstado();
            if(estado){
                estadoTx.setText("Pendiente");
            }else{
                estadoTx.setText("No pendiente");
            }
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nomTx.getText().toString();
                String info = infoTx.getText().toString();
                String stat = estadoTx.getText().toString();
                boolean estado = false;

                //Mensaje de campos vacíos
                if (nom.isEmpty() || info.isEmpty() || stat.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (stat.equalsIgnoreCase("Pendiente")) {
                    estado = true;
                } else if (stat.equalsIgnoreCase("No Pendiente")) {
                    estado = false;
                    //Mensaje que controla el esatdo de los productos
                } else {
                    Toast.makeText(getApplicationContext(), "Error: estado no válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product p = (Product) getIntent().getSerializableExtra("product");
                if (p != null) {
                    p.setNom(nom);
                    p.setInfo(info);
                    p.setEstado(estado);
                }

                Intent res = new Intent();
                res.putExtra("editedProduct", p);
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

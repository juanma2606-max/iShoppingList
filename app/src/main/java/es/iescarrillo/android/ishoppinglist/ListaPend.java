package es.iescarrillo.android.ishoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

import java.util.ArrayList;

public class ListaPend extends ComponentActivity {

    Spinner spinnerPend;
    Button btnCambiar, btnVolver;
    ArrayList<Product> allProducts;
    ArrayList<Product> pendientes = new ArrayList<>();
    Product seleccionado;
    ArrayAdapter<Product> adapterPendientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.lista_pend);

        spinnerPend = findViewById(R.id.s);
        btnCambiar = findViewById(R.id.b1);
        btnVolver = findViewById(R.id.b2);

        // Recibir todos los productos del MainActivity
        allProducts = (ArrayList<Product>) getIntent().getSerializableExtra("allProducts");

        // Filtrar los pendientes
        if (allProducts != null) {
            for (Product p : allProducts) {
                if (p.isEstado()) { // true = pendiente
                    pendientes.add(p);
                }
            }
        }

        if (pendientes.isEmpty()) {
            Toast.makeText(this, "No hay productos pendientes", Toast.LENGTH_SHORT).show();
        }

        // Configurar el spinner
        adapterPendientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pendientes);
        adapterPendientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPend.setAdapter(adapterPendientes);

        spinnerPend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccionado = (Product) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                seleccionado = null;
            }
        });

        // Botón para cambiar el estado
        btnCambiar.setOnClickListener(v -> {
            if (seleccionado != null) {
                seleccionado.setEstado(false); // ahora ya no está pendiente
                pendientes.remove(seleccionado);
                adapterPendientes.notifyDataSetChanged();
                Toast.makeText(this, "Producto marcado como no pendiente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selecciona un producto primero", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para volver al MainActivity con la lista actualizada
        btnVolver.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("updatedList", allProducts);
            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }
}

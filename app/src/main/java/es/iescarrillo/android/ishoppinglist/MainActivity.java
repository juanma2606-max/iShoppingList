package es.iescarrillo.android.ishoppinglist;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner s;
    Button b1;
    Button b2;
    Button b3;
    Product selected;
    ArrayList<Product>products = new ArrayList<Product>();
    ArrayAdapter<Product> adapter;

    //Campo de Aniadir
    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Product newProduct = (Product) data.getSerializableExtra("newProduct");

                        if (newProduct != null) {
                            products.add(newProduct);
                            adapter.notifyDataSetChanged(); // refresca el spinner
                            s.setSelection(products.size() - 1); // selecciona el nuevo producto
                            Log.d(TAG, "Producto añadido: " + newProduct.toString());
                        }
                    } else {
                        Log.d(TAG, "No se recibió ningún producto nuevo o resultado cancelado");
                    }
                }
            }
    );

    //Campo de Detalles y Editar
    ActivityResultLauncher<Intent> detailsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Product updated = (Product) result.getData().getSerializableExtra("updatedProduct");
                    if (updated != null) {
                        // Busca el producto con el mismo id y actualízalo
                        for (int i = 0; i < products.size(); i++) {
                            if (products.get(i).getId() == updated.getId()) {
                                products.set(i, updated);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        s.setSelection(products.indexOf(updated)); // selecciona el actualizado
                    }
                }
            }
    );

    //Campo de Lista de Pendientes
    ActivityResultLauncher<Intent> pendientesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<Product> updatedList =
                            (ArrayList<Product>) result.getData().getSerializableExtra("updatedList");
                    if (updatedList != null) {
                        products.clear();
                        products.addAll(updatedList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Creación del spinner
        s=findViewById(R.id.s);
        //Cración de objetos tipo product
        products.add(new Product(1, "Cerveza", "Marca:Cruzcampo", true));
        products.add(new Product(2, "Pan", "mollete", true));
        products.add(new Product(3, "Jamón", "Serrano", false));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,products);
        s.setAdapter(adapter);

        //Creación de los botones
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);

        //Objeto del spinner seleccionado
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = (Product) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = null;
            }
        });


        //Acción de los botones
        //lISTENER del botón1 para abrir la nueva pantalla de Detalles
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null) {
                    Intent intent = new Intent(MainActivity.this, Detalles.class);
                    intent.putExtra("product", selected);
                    detailsLauncher.launch(intent);
                }
            }
        });

        //lISTENER del botón2 para abrir la nueva pantalla de Aniadir
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Aniadir.class);
            activityLauncher.launch(intent);
        });
        //listener del boton3 para abrir la nueva pantalla de Lista de Pendientes
        b3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaPend.class);
            intent.putExtra("allProducts", products);
            pendientesLauncher.launch(intent);
        });

    }
}
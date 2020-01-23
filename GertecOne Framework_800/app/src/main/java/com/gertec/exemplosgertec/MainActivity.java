package com.gertec.exemplosgertec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gertec.exemplosgertec.ExemploCodigoBarras1.CodigoBarras1;
import com.gertec.exemplosgertec.ExemploCodigoBarras2.CodigoBarras2;
import com.gertec.exemplosgertec.ExemploNFCIdRW.NfcExemplo;
import com.gertec.exemplosgertec.ExemploImpressora.Impressora;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String G800 = "Smart G800";
    private static final String version = "RC02";

    TextView txtProject;

    ArrayList<Projeto> projetos = new ArrayList<Projeto>();
    ListView lvProjetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtProject = findViewById(R.id.txtNameProject);
        lvProjetos = findViewById(R.id.lvProjetos);

        txtProject.setText("Android Studio - " + version);

        projetos.add(new Projeto("Código de Barras", R.drawable.barcode));
        projetos.add(new Projeto("Código de Barras V2",R.drawable.qr_code));
        projetos.add(new Projeto("Impressão",R.drawable.print));
        projetos.add(new Projeto("NFC Leitura/Gravação",R.drawable.nfc2));

        ProjetoAdapter adapter = new ProjetoAdapter(getBaseContext(), R.layout.listprojetos, projetos);
        lvProjetos.setAdapter(adapter);
        lvProjetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Projeto projeto = (Projeto) lvProjetos.getItemAtPosition(i);

                Intent intent = null;
                switch (projeto.getNome()){
                    case "Código de Barras":
                        intent = new Intent(MainActivity.this, CodigoBarras1.class);
                        break;
                    case "Código de Barras V2":

                        intent = new Intent(MainActivity.this, CodigoBarras2.class);
                        break;
                    case "Impressão":
                        intent = new Intent(MainActivity.this, Impressora.class);
                        break;
                    case "NFC Leitura/Gravação":
                        intent = new Intent(MainActivity.this, NfcExemplo.class);
                        break;
                }
                if(intent != null){
                    startActivity(intent);
                }
            }
        });
    }
}

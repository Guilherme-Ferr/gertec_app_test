package com.gertec.exemplosgertec.ExemploNFCId;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gertec.exemplosgertec.R;

public class NfcExemploId extends AppCompatActivity {

    private final static String TAG = NfcExemploId.class.getName();

    // Adaptador NFC
    private NfcAdapter nfcAdapter;

    // Class MifareClassic
    private MifareClassic mifareClassic;

    // Tag do Cartão
    private Tag tag;

    // Text
    private TextView text;

    // Static
    private static int Contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfcexemploid);
        text = findViewById(R.id.lblText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter idDetected = new IntentFilter((NfcAdapter.EXTRA_ID));
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected, idDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(nfcAdapter!= null)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                Toast.makeText(getApplicationContext(), "Não foi possível ler o cartão.", Toast.LENGTH_SHORT);
            }else{
                LerCartaoNfc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Faz a leitura do ID do cartão
    protected void LerCartaoNfc(){
        mifareClassic = MifareClassic.get(tag);
        Contador += 1;
        text.setText("Leitura: " + Contador + "\nId do Cartão: " + idCartao());
    }

    // Converte o ID do cartão que esta em Bytes para String
    public String idCartao() {

        byte[] idCartao = mifareClassic.getTag().getId();
        long result = 0;

        if (idCartao == null) return "";

        for (int i = idCartao.length - 1; i >= 0; --i) {
            result <<= 8;
            result |= idCartao[i] & 0x0FF;
        }
        Log.d(TAG, "ID Cartão: " + Long.toString(result));
        return Long.toString(result);
    }

}

package com.example.penyimpanansql;

import java.util.ArrayList;
import com.example.penyimpanansql.DatabaseManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    DatabaseManager dm;
    EditText nama, hobi, GetId, updateNama, updateAlamat, idDel;
    Button addBtn, getIdBtn, updateBtn, delBtn;
    TableLayout tabel4data;// tabel for data

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new DatabaseManager(this);
        setupView();
        fungsiBtn();
        updateTable();
    }
    public void setupView() {
        tabel4data = (TableLayout) findViewById(R.id.tabel_data);
        nama = (EditText) findViewById(R.id.inNama);
        hobi = (EditText) findViewById(R.id.inHobi);
        updateNama = (EditText) findViewById(R.id.inUpdateNama);
        updateAlamat = (EditText) findViewById(R.id.inUpdateAlamat);
        GetId = (EditText) findViewById(R.id.inGetId);
        idDel = (EditText) findViewById(R.id.idDelete);

        addBtn = (Button) findViewById(R.id.btnAdd);
        getIdBtn = (Button) findViewById(R.id.btnGetId);
        updateBtn = (Button) findViewById(R.id.btnUpdate);
        delBtn = (Button) findViewById(R.id.btnDel);
    }
    
    public void fungsiBtn() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpKamuta();
                kosongkanField();
            }
        });
        getIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View b) {
                ambilBaris();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                updateBaris();
                kosongkanField();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                // TODO Auto-generated method stub
                deleteData();
                kosongkanField();
            }
        });
    }
//    . fungsi button
    
    protected void kosongkanField() {
        nama.setText("");
        hobi.setText("");
        updateNama.setText("");
        updateAlamat.setText("");
        GetId.setText("");
        idDel.setText("");
    }

    private void deleteData() {
        dm.deleteBaris(Long.parseLong(idDel.getText().toString()));
        updateTable();
    }
    protected void updateBaris() {
        dm.updateBaris(Long.parseLong(GetId.getText().toString()), updateNama
                .getText().toString(), updateAlamat.getText().toString());
        updateTable();
    }

    private void ambilBaris() {
         try {
         ArrayList<Object> baris;
         baris =
         dm.ambilBaris(Long.parseLong(GetId.getText().toString()));
         updateNama.setText((String) baris.get(1));
         updateAlamat.setText((String) baris.get(2));
         } catch (NumberFormatException e) {
         e.printStackTrace();
         Log.e("eror db", e.toString());
         Toast.makeText(getBaseContext(), e.toString(),Toast.LENGTH_LONG).show();
         }}
         
    
       
//         penyimpanan data
        protected void simpKamuta() {
             try {
             dm.addRow(nama.getText().toString(),
             hobi.getText().toString());
             updateTable();
             } catch (Exception e) {
                 e.printStackTrace();
         Toast.makeText(getBaseContext(),"gagal simpan,"+
         e.toString(),Toast.LENGTH_LONG).show();
         }
         }
//        . penyimpanan data
       
        // update tabel
                 protected void updateTable() {
                 while (tabel4data.getChildCount() > 1) {
                 tabel4data.removeViewAt(1);
                 }
                 ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();//
                 for (int posisi = 0; posisi < data.size(); posisi++) {
                 TableRow tabelBaris = new TableRow(this);
                 ArrayList<Object> baris = data.get(posisi);
               
                 TextView idTxt = new TextView(this);
                 idTxt.setText(baris.get(0).toString());
                 tabelBaris.addView(idTxt);
               
                 TextView namaTxt = new TextView(this);
                 namaTxt.setText(baris.get(1).toString());
                 tabelBaris.addView(namaTxt);
               
                 TextView hobiTxt = new TextView(this);
                 hobiTxt.setText(baris.get(2).toString());
                 tabelBaris.addView(hobiTxt);
               
                 tabel4data.addView(tabelBaris);
                 }
                 }
//                 . update tabel
    

}
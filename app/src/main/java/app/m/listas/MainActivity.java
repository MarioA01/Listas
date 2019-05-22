package app.m.listas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView simpleList;
    EditText txtPais;
    String countryList[] = {"India", "China", "Australia", "Portugle", "America", "NewZeland"};
    ArrayList<String> lista;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btnAgregar = findViewById(R.id.btnAgregar);
        txtPais = findViewById(R.id.txtPais);

        simpleList = findViewById(R.id.simpleListView);
        lista = new ArrayList<>(Arrays.asList(countryList));

        arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, R.id.textView, lista);

        simpleList.setAdapter(arrayAdapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = txtPais.getText().toString();

                if ( (mensaje != null) && (!mensaje.isEmpty()) ){
                    lista.add(mensaje);
                    arrayAdapter.notifyDataSetChanged();
                    txtPais.setText("");
                }else{
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogoAlerta dialogo = new DialogoAlerta();
                    dialogo.show(fragmentManager, "tagAlerta");
                }

            }
        });

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box
                showInputBox(lista.get(position),position);
            }
        });

    }

    public static class DialogoAlerta extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setMessage("La entrada no puede estar vacia.")
                    .setTitle("Información")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }
    }

    public void showInputBox(String oldItem, final int index){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);
        TextView txtMessage=dialog.findViewById(R.id.txtmessage);
        txtMessage.setText("Modificar");
        txtMessage.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText=dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);
        Button bt = dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.set(index,editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        Button btdelete = dialog.findViewById(R.id.delete);
        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.remove(index);
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

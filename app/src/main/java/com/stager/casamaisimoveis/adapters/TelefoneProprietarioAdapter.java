package com.stager.casamaisimoveis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.TelefoneProprietarioAdapterInterface;
import com.stager.casamaisimoveis.models.TelefoneProprietario;

import java.util.List;

public class TelefoneProprietarioAdapter extends ArrayAdapter<TelefoneProprietario> {

    private Context myContext;
    private int myResource;
    private TelefoneProprietarioAdapterInterface telefoneProprietarioAdapterInterface;

    public TelefoneProprietarioAdapter(@NonNull Context context, int resource, @NonNull List<TelefoneProprietario> objects, TelefoneProprietarioAdapterInterface telefoneProprietarioAdapterInterface) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
        this.telefoneProprietarioAdapterInterface = telefoneProprietarioAdapterInterface;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(myResource, parent, false);
        }

        final TelefoneProprietario telefoneProprietario = getItem(position);

        TextView txtTelefoneProprietario = (TextView) convertView.findViewById(R.id.txtTelefoneProprietario);
        Button btnRemoverTelefone = (Button) convertView.findViewById(R.id.btnRemoverTelefone);

        txtTelefoneProprietario.setText(telefoneProprietario.getNumero());

        if(telefoneProprietarioAdapterInterface != null){
            btnRemoverTelefone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    telefoneProprietarioAdapterInterface.removerTelefone(telefoneProprietario);
                }
            });
        }else{
            btnRemoverTelefone.setVisibility(View.GONE);
        }
        return convertView;
    }
}

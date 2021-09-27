package com.stager.casamaisimoveis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.TelefoneProprietario;

import java.util.List;

public class ProprietarioImovelAdapter extends ArrayAdapter<Proprietario> {

    private Context myContext;
    private int myResource;

    public ProprietarioImovelAdapter(Context context, int resource, List<Proprietario> proprietarios) {
        super(context, resource, proprietarios);
        myContext = context;
        myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(myResource, parent, false);
        }

        Proprietario proprietario = getItem(position);
        TextView txtNomeProprietario = (TextView) convertView.findViewById(R.id.txtNomeProprietarioAdapter);
        TextView txtCPFProprietario = (TextView) convertView.findViewById(R.id.txtCPFProprietarioAdapter);
        TextView txtTelefonesProprietario = (TextView) convertView.findViewById(R.id.txtTelefonesProprietarioAdapter);

        txtNomeProprietario.setText("Nome: " + proprietario.getNome());
        txtCPFProprietario.setText("CPF: " + proprietario.getCpf());

        String telefones = "Telefone(s): ";

        for (TelefoneProprietario telefone : proprietario.getTelefones()){
            telefones += telefone.getNumero() + " ";
        }

        txtTelefonesProprietario.setText(telefones);

        return convertView;
    }
}

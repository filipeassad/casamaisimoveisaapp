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

import java.util.List;

public class ImovelBuscaAdapter extends ArrayAdapter<Imovel> {

    private Context myContext;
    private int myResource;

    public ImovelBuscaAdapter(@NonNull Context context, int resource, @NonNull List<Imovel> objects) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(myResource, parent, false);
        }

        Imovel imovel = getItem(position);

        TextView txtProprietario = (TextView) convertView.findViewById(R.id.txtProprietario);
        TextView txtCPFProprietario = (TextView) convertView.findViewById(R.id.txtCPFProprietario);
        TextView txtBairroImovel = (TextView) convertView.findViewById(R.id.txtBairroImovel);
        TextView txtRuaEnderecoImovel = (TextView) convertView.findViewById(R.id.txtRuaEnderecoImovel);
        TextView txtNumeroEnderecoImovel = (TextView) convertView.findViewById(R.id.txtNumeroEnderecoImovel);

        txtProprietario.setText("Proprietário: " + imovel.getProprietario().getNome());
        txtCPFProprietario.setText("CPF: " + imovel.getProprietario().getCpf());
        txtBairroImovel.setText("Bairro: " + imovel.getEnderecoImovel().getBairro());
        txtRuaEnderecoImovel.setText("Rua: " + imovel.getEnderecoImovel().getRua());
        txtNumeroEnderecoImovel.setText("Número: " + imovel.getEnderecoImovel().getNumero());

        return convertView;
    }
}

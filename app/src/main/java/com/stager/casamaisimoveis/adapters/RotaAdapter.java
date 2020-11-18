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
import com.stager.casamaisimoveis.models.Rota;

import java.util.List;

public class RotaAdapter extends ArrayAdapter<Rota> {

    private Context myContext;
    private int myResource;

    public RotaAdapter(@NonNull Context context, int resource, @NonNull List<Rota> objects) {
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

        Rota rota = getItem(position);

        TextView txtNomeRota = convertView.findViewById(R.id.txtNomeRota);
        TextView txtBairroRota = convertView.findViewById(R.id.txtBairroRota);

        txtNomeRota.setText(rota.getNome());
        txtBairroRota.setText(rota.getBairro());

        return convertView;
    }
}

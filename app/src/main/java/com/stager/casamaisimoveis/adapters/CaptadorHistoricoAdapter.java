package com.stager.casamaisimoveis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.Captador;

import java.util.List;

public class CaptadorHistoricoAdapter extends ArrayAdapter<Captador> {

    private Context myContext;
    private int myResource;

    public CaptadorHistoricoAdapter(@NonNull Context context, int resource, @NonNull List<Captador> objects) {
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

        Captador captador = getItem(position);

        ImageView ivImagemCaptador = (ImageView) convertView.findViewById(R.id.ivImagemCaptador);
        TextView txtNomeCaptador = (TextView) convertView.findViewById(R.id.txtNomeCaptador);

        txtNomeCaptador.setText(captador.getNome());
        if(captador.getAutenticacao().getImagemUsuario() != null)
            ivImagemCaptador.setImageBitmap(captador.getAutenticacao().getImagemUsuario());

        return convertView;
    }
}

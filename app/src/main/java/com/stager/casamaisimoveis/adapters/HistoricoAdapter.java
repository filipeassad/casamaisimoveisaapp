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
import com.stager.casamaisimoveis.models.RotaCaptador;

import java.util.List;

public class HistoricoAdapter extends ArrayAdapter<RotaCaptador> {

    private Context myContext;
    private int myResource;

    public HistoricoAdapter(@NonNull Context context, int resource, @NonNull List<RotaCaptador> objects) {
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

        RotaCaptador rotaCaptador = getItem(position);

        TextView textView = (TextView) convertView.findViewById(R.id.txtDataHistorico);

        textView.setText(rotaCaptador.getData_rota());

        return convertView;
    }
}

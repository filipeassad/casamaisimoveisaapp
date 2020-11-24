package com.stager.casamaisimoveis.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.interfaces.ComposicaoInterface;
import com.stager.casamaisimoveis.models.Composicao;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ComposicaoAdapter extends ArrayAdapter<Composicao> {

    private Context myContext;
    private int myResource;
    private ComposicaoInterface composicaoInterface;

    public ComposicaoAdapter(@NonNull Context context, int resource, @NonNull List<Composicao> objects, ComposicaoInterface composicaoInterface) {
        super(context, resource, objects);
        myContext = context;
        myResource = resource;
        this.composicaoInterface = composicaoInterface;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
            convertView = inflater.inflate(myResource, parent, false);
        }

        final Composicao composicao = getItem(position);
        TextView txtNomeAmbiente = convertView.findViewById(R.id.txtNomeAmbiente);
        TextView txtQuantidadeAmbinte = convertView.findViewById(R.id.txtQuantidadeAmbinte);
        Button btnRemoverAmbiente = convertView.findViewById(R.id.btnRemoverAmbiente);

        txtNomeAmbiente.setText(composicao.getNomeComposicao());
        txtQuantidadeAmbinte.setText("Quantidade: " + composicao.getQuantidade());

        if(composicaoInterface != null){
            btnRemoverAmbiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    composicaoInterface.removerComposicao(composicao);
                }
            });
        }else{
            btnRemoverAmbiente.setVisibility(View.GONE);
        }

        return convertView;
    }
}

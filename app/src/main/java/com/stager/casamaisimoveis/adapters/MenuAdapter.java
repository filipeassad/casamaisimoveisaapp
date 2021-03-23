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

import java.util.List;

public class MenuAdapter extends ArrayAdapter<String> {

    private Context myContext;
    private int myResource;

    public MenuAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
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

        TextView txtNomeItem = (TextView) convertView.findViewById(R.id.txtNomeItem);
        txtNomeItem.setText(getItem(position));

        return convertView;
    }
}

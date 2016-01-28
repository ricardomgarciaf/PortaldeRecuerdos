package com.example.ricardogarcia.portalderecuerdos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ricardogarcia on 1/28/16.
 */
public class RecuerdoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private List<Recuerdo> recuerdos;

    public RecuerdoAdapter(Activity activity, List<Recuerdo> recuerdos) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.recuerdos = recuerdos;
    }

    @Override
    public int getCount() {
        return recuerdos.size();
    }

    @Override
    public Object getItem(int i) {
        return recuerdos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vholder;
        if(recuerdos.size()>0){
            if(view==null){
                view = inflater.inflate(R.layout.list_row, viewGroup, false);
                vholder = new ViewHolder();
                vholder.nombreRecuerdo= (TextView) view.findViewById(R.id.nombreRecuerdo);
                vholder.nombreCreador= (TextView) view.findViewById(R.id.nombreCreador);
                view.setTag(vholder);
            }else{
                vholder= (ViewHolder) view.getTag();
            }
            vholder.nombreRecuerdo.setText(recuerdos.get(i).getNombre());
            vholder.nombreCreador.setText(recuerdos.get(i).getCreador().getNombre());
        }

        return view;
    }

    private class ViewHolder{
        TextView nombreRecuerdo;
        TextView nombreCreador;

    }
}

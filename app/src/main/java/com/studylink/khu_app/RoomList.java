package com.studylink.khu_app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class RoomList extends ArrayAdapter<RoomDTO> {

    private Activity context;
    private List<RoomDTO> roomLIst;

    public RoomList(Activity context, List<RoomDTO> roomList){
        super(context,R.layout.temp_listlayout, roomList);
        this.context=context;
        this.roomLIst=roomList;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.temp_listlayout,null,true);



        TextView textviewName =(TextView) listViewItem.findViewById(R.id.textviewName);
        TextView textviewType=(TextView) listViewItem.findViewById(R.id.textviewType);

        RoomDTO roomDTO =roomLIst.get(position);

        textviewName.setText(roomDTO.getRoomName());
        textviewType.setText(roomDTO.getSpinner1());




        return listViewItem;
    }
}

package com.vpmobitech.tungwahtsymsnect;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vpmobitech.tungwahtsymsnect.Receiver.MyAlarm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csa on 3/1/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private List<DataModel> dataModels;

    DBHelper helper;
    SQLiteDatabase db;

    public RecycleAdapter(Context context, List dataModels) {
        this.context = context;
        this.dataModels = dataModels;

        helper = new DBHelper(context);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return  viewHolder;*/


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(dataModels.get(position));

        DataModel pu = dataModels.get(position);

        holder.name.setText(pu.getName());
        holder.city.setText(pu.getCity());


    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,city;
        public ImageView imgDelete;

        public ViewHolder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name1);
            city = (TextView) itemView.findViewById(R.id.city1);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataModel cpu = (DataModel) view.getTag();
                    Toast.makeText(view.getContext(), cpu.getName()+" is "+ cpu.getCity()+" Srno== "+ cpu.getSrNo()+"  Med nam=="+cpu.getName()+"   time==="+cpu.getCity()+"    alarmID"+cpu.getAlarmID(), Toast.LENGTH_SHORT).show();

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataModel cpu = (DataModel) itemView.getTag();
                    String SrNo=cpu.getSrNo();
                    String childId=cpu.getName();
                    String childName=cpu.getCity();
                    int alarmID=cpu.getAlarmID();
                    System.out.println("SrNo=="+SrNo+"  Med nam=="+childId+"   time==="+childName+"    alarmID"+alarmID);

                    try {

                        helper=new DBHelper(context);
                        db=helper.getWritableDatabase();

                        db.delete(DBHelper.TABLE, "alarmID="+alarmID, null);
                        System.out.println("Reminder Deleted...");
                        dataModels.remove(getAdapterPosition());
                        notifyDataSetChanged();


                        CancelAlarm(alarmID);
                    }
                    catch(Exception e) { e.printStackTrace(); }

                }
            });

        }
    }


    public void CancelAlarm(int alarmID)
    {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                alarmID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        manager.cancel(pendingIntent);
    }


}

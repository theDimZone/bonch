package itsoftware.datdot.bonch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import itsoftware.datdot.bonch.data.workers.Target;

public class CustomAdapter extends BaseAdapter {
    private static final String TAG = "CustomAdapterXml";
    private FirebaseAuth mAuth;

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Target> targets;

    CustomAdapter(Context context, ArrayList<Target> objects) {
        ctx = context;
        targets = objects;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //FirebaseUser currentUser = mAuth.getCurrentUser();
       // currentUser.
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return targets.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return targets.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Target p = getTarget(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.name)).setText(p.getName());
        ((TextView) view.findViewById(R.id.location)).setText("далеко");
        ((TextView) view.findViewById(R.id.targetid)).setText(p.getId());

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.linearLayout1);
        /*
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ((TextView) v.findViewById(R.id.targetid)).getText().toString();
                Target target = getTargetById(id);
                //отправить в мапс таргет
            }
        });
        */

        return view;
    }

    private Target getTargetById(String id) {
        for(Target target : targets) {
            if(target.getId() == id) return target;
        }
        return new Target();
    }

    Target getTarget(int position) {
        return ((Target) getItem(position));
    }

}
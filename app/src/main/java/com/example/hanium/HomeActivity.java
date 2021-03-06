package com.example.hanium;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.hanium.Chat.ChatActivity;
import com.example.hanium.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import static java.util.Calendar.SUNDAY;


public class HomeActivity<listAdapter> extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "HomeActivity";
    Button myBtn, mback;
    TextView Mname;
    TextView Mlogout,markbt;
    private SwipeMenuListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ListDataAdapter listAdapter;
    IntentData data=new IntentData();
    MaterialCalendarView cal;
    User user;
    String[] thing;
    final ArrayList<String> record_list=new ArrayList<>();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_interface);
        final Intent Script_intent = new Intent(HomeActivity.this, ScriptActivity.class);
        final Intent Voca_intent = new Intent(HomeActivity.this, VocaActivity.class);
        final Intent Chat_intent = new Intent(HomeActivity.this, ChatActivity.class);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        myBtn = (Button) findViewById(R.id.my_bar);

        user = (User) getIntent().getParcelableExtra("user");
        user.updateMark();
        user.updateRecord();
        Log.d(TAG, user.getId());

        listAdapter=new ListDataAdapter();
        listView.setAdapter(listAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem study = new SwipeMenuItem(getApplicationContext());
                study.setWidth(dp2px(90));
                study.setTitle("study");
                study.setTitleSize(18);
                study.setTitleColor(Color.WHITE);
                study.setBackground(new ColorDrawable(Color.parseColor("#FFFFB6C1")));
                menu.addMenuItem(study);
                SwipeMenuItem voca = new SwipeMenuItem(getApplicationContext());
                voca.setWidth(dp2px(90));
                voca.setTitleColor(Color.parseColor("#FFFFB6C1"));
                voca.setTitle("voca");
                voca.setTitleSize(18);;
                voca.setBackground(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
                menu.addMenuItem(voca);
                SwipeMenuItem mark = new SwipeMenuItem(getApplicationContext());
                mark.setWidth(dp2px(90));
                mark.setTitleColor(Color.parseColor("#FFFFFFFF"));
                mark.setTitle("mark");
                mark.setTitleSize(18);;
                mark.setBackground(new ColorDrawable(Color.parseColor("#FFFFB6C1")));
                menu.addMenuItem(mark);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if (arrayList.contains(" \nNo Dialog\n ")) {
                            Toast.makeText(HomeActivity.this, "학습 자료가 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, String.valueOf(position));
                            switch (position) {
                                case 0:
                                    data.setData("s1");
                                    Script_intent.putExtra("data", (Parcelable) data);
                                    Script_intent.putExtra("user",(Parcelable)user);
                                    startActivity(Script_intent);
                                    break;
                                case 1:
                                    data.setData("s2");
                                    Script_intent.putExtra("data", (Parcelable) data);
                                    Script_intent.putExtra("user",(Parcelable)user);
                                    startActivity(Script_intent);
                                    break;
                                case 2:
                                    data.setData("d");
                                    Chat_intent.putExtra("data", (Parcelable) data);
                                    Chat_intent.putExtra("user",(Parcelable)user);
                                    startActivity(Chat_intent);
                                    break;
                            }
                        }
                        break;
                    case 1:
                        if (arrayList.contains(" \nNo Dialog\n ")) {
                            Toast.makeText(HomeActivity.this, "학습 자료가 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, String.valueOf(position));
                            switch (position) {
                                case 0:
                                    data.setData("s1");
                                    Voca_intent.putExtra("data", (Parcelable) data);
                                    startActivity(Voca_intent);
                                    break;
                                case 1:
                                    data.setData("s2");
                                    Voca_intent.putExtra("data", (Parcelable) data);
                                    startActivity(Voca_intent);
                                    break;
                                case 2:
                                    Toast.makeText(HomeActivity.this, "Dialog는 단어학습이 없습니다.", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        break;
                    case 2:
                        if (arrayList.contains(" \nNo Dialog\n ")) {
                            Toast.makeText(HomeActivity.this, "mark 자료가 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            String str;
                            ArrayList<String> tmp=new ArrayList();
                            switch (position) {
                                case 0:
                                    str = data.date + "  :  Script#1";
                                    break;
                                case 1:
                                    str = data.date + "  :  Script#2";
                                    break;
                                case 2:
                                    str = data.date + "  :  Dialog";
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + position);
                            }
                            if(user.getMark()==null){
                                Log.d(TAG,"mark==null");
                                tmp.add(str);
                                Toast.makeText(HomeActivity.this, "mark 설정완료", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                tmp=user.getMark();
                                if (tmp.contains(str)) {
                                    Log.d(TAG,"remove");
                                    tmp.remove(str);
                                    Toast.makeText(HomeActivity.this, "mark 해제완료", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d(TAG,"add");
                                    tmp.add(str);
                                    Toast.makeText(HomeActivity.this, "mark 설정완료", Toast.LENGTH_SHORT).show();
                                }
                            }
                            tmp.remove("");
                            String s=String.join(",",tmp);
                            Log.d(TAG,s);
                            db.collection("User")
                                    .document(user.getId())
                                    .update("Mark",s);
                            user.updateMark();
                        }
                        break;
                }
                return false;
            }
        });
        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {

            }

            @Override
            public void onMenuClose(int position) {

            }
        });
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
            }
            @Override
            public void onSwipeEnd(int position) {
            }
        });
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일");
        final String today=sdf.format(new Date());
        Log.d(TAG,"날짜가 왜이럴깡: "+today);
        db.collection("sentence").document(today)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                arrayList.add("Script #1\n"+document.get("smain1kor").toString());
                                arrayList.add("Script #2\n"+document.get("smain2kor").toString());
                                arrayList.add("Dialog\n"+document.get("dmainkor").toString());
                                data.setDate(today);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(HomeActivity.this, today+"\n공부할 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                                arrayList.add(" \nNo Script #1\n ");
                                arrayList.add(" \nNo Script #2\n ");
                                arrayList.add(" \nNo Dialog\n ");
                            }
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                });
        cal=(MaterialCalendarView)findViewById(R.id.Calendar);
        cal.state().edit()
                .setFirstDayOfWeek(SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        cal.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);



        cal.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                {
                    NumberFormat nf=NumberFormat.getIntegerInstance();
                    nf.setMinimumIntegerDigits(2);
                    final String selecdate=date.getYear()+"년"+nf.format((date.getMonth()+1))+"월"+nf.format(date.getDay())+"일";
                    arrayList.clear();
                    Log.d(TAG,"날짜 확인: "+selecdate+db.collection("sentence").document("2019년7월1일").get().toString());
                    db.collection("sentence").document(selecdate)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            arrayList.add("Script #1\n"+document.get("smain1kor").toString());
                                            arrayList.add("Script #2\n"+document.get("smain2kor").toString());
                                            arrayList.add("Dialog\n"+document.get("dmainkor").toString());
                                            data.setDate(selecdate);
                                        } else {
                                            arrayList.add(" \nNo Script #1\n ");
                                            arrayList.add(" \nNo Script #2\n ");
                                            arrayList.add(" \nNo Dialog\n ");
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                            Toast.makeText(HomeActivity.this, selecdate+"\n공부할 내용이 없습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                        listAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                }
            }
        });

        //여기여기//////////////
        db.collection("User").document(user.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                    record_list.addAll(Arrays.asList(document.get("Record").toString().split(",")));
                                    Log.d(TAG, "record 전체 출력 : "+record_list);
                                    new ApiSimulator(record_list).executeOnExecutor(Executors.newSingleThreadExecutor());

                            } else {

                            }
                        }
                    }
                });

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout mymenu=(LinearLayout)inflater.inflate(R.layout.menu_interface,null);
                mymenu.setBackgroundColor(Color.parseColor("#99000000"));
                LinearLayout.LayoutParams parm=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                addContentView(mymenu,parm);
                Mname=(TextView)findViewById(R.id.main_name);
                Mname.setText(user.getId());
                Mlogout = (TextView)findViewById(R.id.main_logout);
                markbt=(TextView)findViewById(R.id.markbt);
                markbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mark_intent = new Intent(HomeActivity.this, MarkActivity.class);
                        mark_intent.putExtra("user",(Parcelable)user);
                        startActivity(mark_intent);
                    }
                });
                Mlogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Login_intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(Login_intent);
                    }
                });
                mback=(Button)findViewById(R.id.main_back);
                mback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        ((ViewManager)mymenu.getParent()).removeView(mymenu);
                    }
                });
            }
        });


    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    class ListDataAdapter extends BaseAdapter {
        ViewHolder holder;
        @Override
        public int getCount() {
            return arrayList.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                holder=new ViewHolder();
                convertView=getLayoutInflater().inflate(R.layout.list_item,null);
                holder.mTextview=(TextView)convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.mTextview.setText(arrayList.get(position));
            return convertView;
        }
        class ViewHolder {
            TextView mTextview;
        }
    }

    //달력 Dot
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
        ArrayList<String> study_record=new ArrayList<>();
        ApiSimulator(ArrayList<String> study_record){
            this.study_record=study_record;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
            db.collection("sentence")
                    .whereEqualTo("check","done")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "=>" + document.getId());
                                    donedate.add(document.getId());
                                    Calendar calendar = Calendar.getInstance();
                                    DateFormat df = new SimpleDateFormat("yyyy년MM월dd일");
                                    Date date = null;
                                    try {
                                        date = df.parse(document.getId());
                                        Log.d(TAG,"점찍을 날짜 확인: "+date);
                                    } catch(ParseException e){
                                        e.printStackTrace();
                                    }
                                    calendar.setTime(date);
                                    calendar.add(Calendar.MONTH,+1);
                                    CalendarDay day = CalendarDay.from(calendar);
                                    dates.add(day);
                                    }
                                Log.d(TAG,"calendar day 리스트 확인: "+dates);
                                }
                            else{
                                Log.d(TAG,"Error getting documents: ",task.getException());
                            }
                        }
                    });*/
            ArrayList<CalendarDay> dates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy년MM월dd일");
            Date date = null;
            for(int i =0; i<study_record.size();i++) {
                try {
                    date = df.parse(study_record.get(i).toString());
                    Log.d(TAG,"점찍을 날짜 확인: "+date);
                } catch(ParseException e){
                    e.printStackTrace();
                }
                calendar.setTime(date);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            cal.addDecorator(new EventDecorator(Color.RED, calendarDays,HomeActivity.this));        }
    }
}
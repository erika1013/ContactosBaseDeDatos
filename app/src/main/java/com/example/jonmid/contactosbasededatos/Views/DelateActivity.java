package com.example.jonmid.contactosbasededatos.Views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonmid.contactosbasededatos.ContactsActivity;
import com.example.jonmid.contactosbasededatos.Helpers.SqliteHelper;
import com.example.jonmid.contactosbasededatos.R;

public class DelateActivity extends AppCompatActivity {
    TextView textViewId;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewEmail;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delate);
        textViewId=(TextView)findViewById(R.id.id_tv_delate_id);
        textViewName=(TextView)findViewById(R.id.id_tv_delate_name);
        textViewPhone=(TextView)findViewById(R.id.id_tv_delate_phone);
        textViewEmail=(TextView)findViewById(R.id.id_tv_delate_email);

        sqliteHelper = new SqliteHelper(this, "db_contacts", null, 1);

        textViewId.setText(Integer.toString(getIntent().getExtras().getInt("id")));
        textViewName.setText(getIntent().getExtras().getString("name"));
        textViewPhone.setText(getIntent().getExtras().getString("phone"));
        textViewEmail.setText(getIntent().getExtras().getString("email"));
    }
    public void onClickShowIndex(View view){
        Intent intent=new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
    public void onClickDelateIndex(View view){
        SQLiteDatabase db=sqliteHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("delete from users where id =1",null);
        


        cursor.close();
        Toast.makeText(this, "contacto eliminado ", Toast.LENGTH_SHORT).show();

        onClickShowIndex(view);

    }
}

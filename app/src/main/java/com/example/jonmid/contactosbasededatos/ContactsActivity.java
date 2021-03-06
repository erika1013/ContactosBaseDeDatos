package com.example.jonmid.contactosbasededatos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonmid.contactosbasededatos.Adapters.ContactAdapter;
import com.example.jonmid.contactosbasededatos.Helpers.SqliteHelper;
import com.example.jonmid.contactosbasededatos.Models.Contact;
import com.example.jonmid.contactosbasededatos.Utilities.Constants;
import com.example.jonmid.contactosbasededatos.Views.RegisterContactActivity;
import com.example.jonmid.contactosbasededatos.Views.SearchContactActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    RecyclerView recyclerViewContacts;
    ContactAdapter contactAdapter;
    List<Contact> contactList = new ArrayList<>();
    EditText editText;
    SqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerViewContacts = (RecyclerView) findViewById(R.id.id_rv_contacts);
        editText=(EditText) findViewById(R.id.id_et_search_index);

        sqliteHelper = new SqliteHelper(this, "db_contacts", null, 1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContacts.setLayoutManager(linearLayoutManager);

        sqliteHelper = new SqliteHelper(this, "db_contacts", null, 1);


        listContacts();
        processData();
    }

    public void onClickShowWindowRegister(View view){
        Intent intent = new Intent(this, RegisterContactActivity.class);
        startActivity(intent);
    }

    public void onClickShowWindowSearch(View view){
        Intent intent = new Intent(this, SearchContactActivity.class);
        startActivity(intent);
    }




    public void listContacts(){
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id,name,phone,email from users order by id desc", null);

        while (cursor.moveToNext()){
            Contact contact = new Contact();
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(2));
            contact.setEmail(cursor.getString(3));
            contactList.add(contact);
        }

        cursor.close();

        if (contactList.size() != 0){
            processData();
        }else{
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
        }
    }


    public void buscador(View view){
        contactList.clear();
        recyclerViewContacts.removeAllViewsInLayout();
        recyclerViewContacts.removeAllViews();
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();



        Cursor cursor = db.rawQuery("select id,name,phone,email from users where name = '"+editText.getText().toString()+"'", null);
        try {
            while (cursor.moveToNext()){
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contactList.add(contact);
            }
            cursor.close();
            processData();

        }catch (Exception e){
            Toast.makeText(this, "USUARIO NO ENCONTRADO ", Toast.LENGTH_SHORT).show();

        }

    }

    public void processData(){
        if (contactList.size() != 0){
            contactAdapter = new ContactAdapter(contactList, getApplicationContext());
            recyclerViewContacts.setAdapter(contactAdapter);
        }else{
            Toast.makeText(this, "lista vacia ", Toast.LENGTH_SHORT).show();
        }

    }
}

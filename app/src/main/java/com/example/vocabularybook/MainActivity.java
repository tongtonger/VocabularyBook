package com.example.vocabularybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Words> wordsList=new ArrayList<>();
    private SQLitedb sqldb;
    private EditText editText = null;
    private ListView listView = null;
    List<Words> list = null;
    Words word = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            sqldb = new SQLitedb(this);
            sqldb.openDatabase();
            sqldb.closeDatabase();
            //database = SQLiteDatabase.openOrCreateDatabase(SQLitedb.DB_PATH + "/" + SQLitedb.DB_NAME, null);
        }

        editText  = (EditText) findViewById(R.id.input);
        listView = (ListView) findViewById(R.id.words);
        editText.setText(null);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {//自动查询，一但输入框的数据改变，就重新查询单词数据库
                if(editText.getText().toString().length() == 0){

                    listView.setAdapter(null);
                }

                else {
                    String sql = "select  * from englishwords where word like ? order by word asc limit 0, 10 ";
                    String[] data = new String[]{editText.getText().toString() + "%"};
                    Words word = null;
                    list = new ArrayList<Words>();
                    Log.e("text", editText.getText().toString());
                    Cursor cursor = database.rawQuery(sql, new String[]{editText.getText().toString() + "%"});
                    //Cursor cursor = database.rawQuery(sql, data);

                    while (cursor.moveToNext()) {
                        word = new Words(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                        Log.e("word", word.toString());
                        list.add(word);
                    }
                    WordAdapter adapter = new WordAdapter(MainActivity.this, R.layout.word, list);
                    listView.setAdapter(adapter);
                }
            }
        });









        WordAdapter adapter=new WordAdapter(MainActivity.this,R.layout.word,wordsList);
      //  ListView listView=(ListView)findViewById(R.id.words);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        editText.setText(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_add,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("添加单词");
                LayoutInflater inflater=getLayoutInflater();
                View layout=inflater.inflate(R.layout.add,null);

                final EditText word_name = (EditText) layout.findViewById(R.id.word_name);
                final EditText word_pro = (EditText) layout.findViewById(R.id.word_pro);
                final EditText word_chinese = (EditText) layout.findViewById(R.id.word_chinese);

                dialog.setView(layout);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "insert into englishwords(word, pronunciation, meaning) values(?,?,?)";
                        database.execSQL(sql, new String[]{word_name.getText().toString(), word_pro.getText().toString(),word_chinese.getText().toString()});
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"添加失败",Toast.LENGTH_LONG).show();

                    }
                });
                dialog.show();
                break;
            case R.id.help_item:
                Toast.makeText(this, "这是一个帮助手册!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}

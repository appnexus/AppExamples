package appnexus.example.bannerlistsample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import appnexus.example.bannerlistsample.listview.ListViewActivity;
import appnexus.example.bannerlistsample.recyclerview.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {

    //Specify listview.
    ListView listView;

    //Add items in listview.
    String[] listTypes = { "Banner RecyclerView", "Banner ListView"};

    //Initialize the list array adapter.
    ArrayAdapter<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);

        //add items in an array adapter.
        arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listTypes);
        listView.setAdapter(arr);

        //add click listener on listview.
        listView.setOnItemClickListener((parent, view, position, id) -> {

            switch (position) {

                case 0:
                    //first position on click navigate to recyclerview activity
                    startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                    break;

                case 1:
                    //second position on click navigate to listview activity
                    startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                    break;
            }
        });
    }
}
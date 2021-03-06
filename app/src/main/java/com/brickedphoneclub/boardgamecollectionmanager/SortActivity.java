package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;


public class SortActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_name, spinner_rating, spinner_yrpub;
    public static String selectedName, selectedRating, selectedYearPublished = null;
    public String[] sortOptionsSelected = new String[3];
    public static String activeSortName, activeSortRating, activeSortYrPub = null;
    private RadioGroup radioSortGroup;
    private RadioButton radioSortButton, radioSortNameButton, radioSortRatingButton, radioSortYearPubButton;
    private int selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        // active sort options

        Intent intentActiveSort = getIntent();
        Bundle activeSortBundle = intentActiveSort.getExtras();

        if (!(activeSortBundle == null) && !(activeSortBundle.isEmpty())) {
            sortOptionsSelected = activeSortBundle.getStringArray("asoptions");

            if((sortOptionsSelected[0] == null) && (sortOptionsSelected[2] == null) && (sortOptionsSelected[2] == null))
            {
                System.out.println("Default");
                activeSortName = "A-Z";
                activeSortRating = "None";
                activeSortYrPub = "None";
            }
            else {
                activeSortName = sortOptionsSelected[0];
                activeSortRating = sortOptionsSelected[1];
                activeSortYrPub = sortOptionsSelected[2];
                System.out.println("User Selection");
            }
        }

        System.out.println(activeSortName+" @ "+activeSortRating+" @ "+activeSortYrPub );

        //Populate spinners

        spinner_name = (Spinner) findViewById(R.id.spnr_sortbyname);
        spinner_rating = (Spinner) findViewById(R.id.spnr_sortbyrating);
        spinner_yrpub = (Spinner) findViewById(R.id.spnr_sortbyyear);
        final Button buttonApply = (Button) findViewById(R.id.btn_sortapply);
        final Button buttonCancel = (Button) findViewById(R.id.btn_sortcancel);

        // Create an ArrayAdapter of name spinner using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_name = ArrayAdapter.createFromResource(this,
                R.array.alpha_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the name spinner
        spinner_name.setAdapter(adapter_name);
        // set value for name spinner
        spinner_name.setSelection(((ArrayAdapter<String>)spinner_name.getAdapter()).getPosition(activeSortName));
        // listener for change in selection
        spinner_name.setOnItemSelectedListener(this);

        //Populate rating spinner
        ArrayAdapter<CharSequence> adapter_rating = ArrayAdapter.createFromResource(this,
                R.array.rating_array, android.R.layout.simple_spinner_item);
        adapter_rating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rating.setAdapter(adapter_rating);
        spinner_rating.setSelection(((ArrayAdapter<String>)spinner_rating.getAdapter()).getPosition(activeSortRating));
        spinner_rating.setOnItemSelectedListener(this);

        //Populate year published spinner
        ArrayAdapter<CharSequence> adapter_yrpub = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter_yrpub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yrpub.setAdapter(adapter_yrpub);
        spinner_yrpub.setSelection(((ArrayAdapter<String>)spinner_yrpub.getAdapter()).getPosition(activeSortYrPub));
        spinner_yrpub.setOnItemSelectedListener(this);

        // Update Radio Button Status

        radioSortGroup = (RadioGroup) findViewById(R.id.radioSort);
        radioSortNameButton = (RadioButton) findViewById(R.id.radioByName);
        radioSortRatingButton = (RadioButton) findViewById(R.id.radioByrating);
        radioSortYearPubButton = (RadioButton) findViewById(R.id.radioByYrpub);

        radioSortGroup.clearCheck();
        if (!(activeSortName.equals("None")))
        {
            radioSortGroup.check(R.id.radioByName);
            radioSortNameButton.setChecked(true);
            spinner_name.setEnabled(true);
            spinner_yrpub.setEnabled(false);
            spinner_rating.setEnabled(false);
        }
        else if ((!activeSortRating.equals("None")))
        {
            radioSortGroup.check(R.id.radioByrating);
            radioSortRatingButton.setChecked(true);
            spinner_name.setEnabled(false);
            spinner_yrpub.setEnabled(false);
            spinner_rating.setEnabled(true);
        }
        else if ((!activeSortYrPub.equals("None")))
        {

            radioSortGroup.check(R.id.radioByYrpub);
            radioSortYearPubButton.setChecked(true);
            spinner_name.setEnabled(false);
            spinner_yrpub.setEnabled(true);
            spinner_rating.setEnabled(false);
        }

        //Button Listeners

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sortOptionsSelected[0]= selectedName ;
                sortOptionsSelected[1]= selectedRating ;
                sortOptionsSelected[2]= selectedYearPublished ;

                Bundle sortBundle = new Bundle();
                sortBundle.putStringArray("soptions",sortOptionsSelected);
                Log.i("Sort Options Selected:", sortOptionsSelected[0] + "/" + sortOptionsSelected[1] + "/" + sortOptionsSelected[2]);

                Intent lastIntent = new Intent();
                lastIntent.putExtras(sortBundle);
                setResult(RESULT_OK, lastIntent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       switch(parent.getId()){
            case R.id.spnr_sortbyname :
                selectedName  = spinner_name.getSelectedItem().toString();
                selectedRating = "None";
                selectedYearPublished = "None";
                break;
            case R.id.spnr_sortbyrating :
                selectedRating  = spinner_rating.getSelectedItem().toString();
                selectedName = "None";
                selectedYearPublished = "None";
                break;
            case R.id.spnr_sortbyyear :
                selectedYearPublished  = spinner_yrpub.getSelectedItem().toString();
                selectedRating = "None";
                selectedName = "None";
                break;
        }

        //System.out.println(selectedName+" - "+selectedRating+" - "+selectedYearPublished );

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioByName:
                if (checked)
                    spinner_name.setEnabled(true);
                    spinner_yrpub.setEnabled(false);
                    spinner_rating.setEnabled(false);
                    break;
            case R.id.radioByrating:
                if (checked)
                    spinner_name.setEnabled(false);
                    spinner_yrpub.setEnabled(false);
                    spinner_rating.setEnabled(true);
                    break;
            case R.id.radioByYrpub:
                if (checked)
                    spinner_name.setEnabled(false);
                    spinner_yrpub.setEnabled(true);
                    spinner_rating.setEnabled(false);
                    break;
        }
    }
}



package com.example.matt.recipeminiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import javax.xml.transform.Result;
import com.example.matt.recipeminiapp.R;

import java.util.ArrayList;

/**
 * Created by Matt on 3/25/2018.
 */

public class SearchActivity extends AppCompatActivity {

    private Context mContext;
    private Spinner dietSpinner;
    private Spinner servingSpinner;
    private Spinner prepTimeSpinner;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        mContext = this;
        dietSpinner = findViewById(R.id.diet_restriction_spinner);
        servingSpinner = findViewById(R.id.serving_restriction_spinner);
        prepTimeSpinner = findViewById(R.id.preparation_time_spinner);
        searchButton = findViewById(R.id.search_button);

        //arraylists for restrictions
        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipe.json", this);
        ArrayList<String> dietRestrictionLabel = new ArrayList<>();
        ArrayList<String> servingLabel = new ArrayList<>();
        ArrayList<String> prepTimeLabel = new ArrayList<>();

        //initialize arrays for spinners with default value "Select One"
        dietRestrictionLabel.add("Select One");
        servingLabel.add("Select One");
        prepTimeLabel.add("Select One");
        prepTimeLabel.add("30 Minutes or Less");


        for (int i = 0; i < recipeList.size(); i++) {
            String newDietLabel = recipeList.get(i).dietLabel;
            String servingSize = recipeList.get(i).servingLabel;
            String cookTime = recipeList.get(i).prepTimeLabel;

            // if new label
            // add label
            if (!dietRestrictionLabel.contains(newDietLabel)) {
                dietRestrictionLabel.add(newDietLabel);
            }

            // if new label
            // add label
            if (!servingLabel.contains(servingSize)) {
                servingLabel.add(servingSize);
            }

            // if new label
            // add label
            if (!prepTimeLabel.contains(cookTime)) {
                prepTimeLabel.add(cookTime);
            }
        }


        servingLabel.add("Less than 4");

        //add adapters for arrays
        ArrayAdapter<String> dietRestrictionArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dietRestrictionLabel);
        ArrayAdapter<String> servingSizeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servingLabel);
        ArrayAdapter<String> prepTimeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, prepTimeLabel);

        //add adapters to spinners
        dietSpinner.setAdapter(dietRestrictionArrayAdapter);
        servingSpinner.setAdapter(servingSizeArrayAdapter);
        prepTimeSpinner.setAdapter(prepTimeArrayAdapter);


        //set onClick listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent(mContext, ResultActivity.class);
                resultIntent.putExtra("newDietLabel", dietSpinner.getSelectedItem().toString());
                resultIntent.putExtra("servingSize", servingSpinner.getSelectedItem().toString());
                resultIntent.putExtra("cookTime", prepTimeSpinner.getSelectedItem().toString());

                startActivity(resultIntent);
            }
        });

    }

}
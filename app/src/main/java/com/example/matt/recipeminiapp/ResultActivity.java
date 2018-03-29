package com.example.matt.recipeminiapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matt on 3/25/2018.
 */

public class ResultActivity extends AppCompatActivity {

    private Context mContext;
    private ListView mListView;
    private ArrayList<Recipe> resultRecipes;
    private ArrayList<Recipe> recipeList;
    private TextView recipeResults;
    private RecipeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_results);
        mContext = this;

        recipeResults = findViewById(R.id.number_of_recipes);
        recipeList = Recipe.getRecipesFromFile("recipe.json", this);
        resultRecipes = new ArrayList<>();

        // get intent from searchActivity
        String dietLabel = this.getIntent().getExtras().getString("newDietLabel");
        String servingSize = this.getIntent().getExtras().getString("servingSize");
        String prepTime = this.getIntent().getExtras().getString("cookTime");


        // go through the whole recipe list
        // checkAll from method made below
        // if all are true add to the result recipe
        for(int i = 0; i<recipeList.size(); i++) {
            if(checkAll(dietLabel, servingSize, prepTime, recipeList.get(i))){
                resultRecipes.add(recipeList.get(i));
            }
        }

        // set the text at the top of the results page
        recipeResults.setText(resultRecipes.size() + " recipes match search!");


        // create the adapter
        adapter = new RecipeAdapter(this, resultRecipes);

        // find the listview in the layout
        // set the adapter to listview
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);

        // call notification creater from below
        this.createNotification();

    }

    //methods to check if dietrestrictions, servingsizes, and preptimes match up
    //must include default option "Select One" which includes all recipes
    public boolean checkDietLabels(String dietLabel, Recipe recipe) {
        if(dietLabel.equals("Select One") || recipe.searchLabel.contains(dietLabel)) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean checkServingSizes(String servingSize, Recipe recipe){
        if (servingSize.equals("Select One") || recipe.searchLabel.contains(servingSize)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkPrepTime(String prepTime, Recipe recipe){
        if (prepTime.equals("Select One")|| recipe.searchLabel.contains(prepTime)){
            return true;
        }
        //determine if less than 30 mins or in between 30 mins and 1 hr
        else if(prepTime.equals("30 Minutes or Less") && recipe.prepTimeLabel.equals("Less Than 1 Hour")){
            String prepTimeString = recipe.prepTime.substring(0, 2);
            int mins = Integer.parseInt(prepTimeString);
            if (mins <= 30){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    // use the methods from above to create one check
    public boolean checkAll(String dietLabel, String servingSize, String prepTime, Recipe recipe) {
        boolean sameDietLabel = checkDietLabels(dietLabel, recipe);
        boolean sameServingSize = checkServingSizes(servingSize, recipe);
        boolean samePrepTime = checkPrepTime(prepTime, recipe);

        if(sameDietLabel && sameServingSize && samePrepTime) {
            return true;
        }
        else{
            return false;
        }
    }

    // create the notification for the button on the recipe
    private void createNotification(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notificationID", "notificationName", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        String notificationText = "";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notificationName")
        .setContentTitle("Cooking Instruction")
        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
        .setSmallIcon(R.drawable.chef_picture)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //.setContentIntent(contentIntent)
        .setAutoCancel(true)
        ;

        final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(530, builder.build());
    }


}


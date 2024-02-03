package com.example.treasureadventure.GameSaver;

import android.content.Context;

import com.example.treasureadventure.Model.Model;

import org.simpleframework.xml.core.Persister;

import java.io.File;

public class GameLoader {

    private final Context context;
    Model model;

    public GameLoader(Context context) {
        this.context = context;
    }

    public Model retreiveSave(){
        Persister persister = new Persister();
        File source = new File(context.getFilesDir(), "savedGame.xml"); // Replace with your XML file name

        try {
            model = persister.read(Model.class, source);
            // The Model object is now populated with the data from the XML file
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return model;
    }

}

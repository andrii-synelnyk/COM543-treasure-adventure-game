package com.example.treasureadventure;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import android.content.Context;
import java.io.File;

public class GameSaver {

    private final Context context;

    public GameSaver(Context context) {
        this.context = context;
    }

    public void save(Model model) {
        String fileName = "savedGame.xml";
        File file = new File(context.getFilesDir(), fileName);

        try {
            Serializer serializer = new Persister();
            // Serialize the Model object to a file in the internal storage
            serializer.write(model, file);
            System.out.println("saved");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
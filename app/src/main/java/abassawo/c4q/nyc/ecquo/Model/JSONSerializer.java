package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by c4q-Abass on 8/18/15.
 */
public class JSONSerializer {
    private Context mContext;
    private String mFileName;

    public JSONSerializer(Context c, String f) {
        mContext = c;
        mFileName = f;
    }


    private String readStream(InputStream in) throws IOException {
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(in, "UTF8");
        StringWriter writer = new StringWriter();
        int n;
        while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
        }
        return writer.toString();
    }

    public void saveTasks(ArrayList<Task> tasks)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Task c : tasks)
            array.put(c.toJSON());

        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }

    }

    public ArrayList<Task> loadTasks()throws IOException, JSONException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of notes from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                tasks.add(new Task(array.getJSONObject(i).toString(), mContext));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return tasks;
    }


    public ArrayList<Goal> loadGoals() throws IOException, JSONException {
        ArrayList<Goal> goals = new ArrayList<Goal>();
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Build the array of notes from JSONObjects
            for (int i = 0; i < array.length(); i++) {
            //fixmegoals.add(new Goal(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return goals;
    }
}


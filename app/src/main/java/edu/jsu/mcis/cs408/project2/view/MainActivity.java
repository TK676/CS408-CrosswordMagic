package edu.jsu.mcis.cs408.project2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.beans.PropertyChangeEvent;
import edu.jsu.mcis.cs408.project2.DefaultController;
import edu.jsu.mcis.cs408.project2.databinding.ActivityMainBinding;
import edu.jsu.mcis.cs408.project2.model.*;

public class MainActivity extends AppCompatActivity implements AbstractView {
    public static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private DefaultController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        controller = new DefaultController();

        PuzzleModel model = new PuzzleModel(new PuzzleDatabaseModel(this, null, null, 1));

        controller.addModel(model);

        controller.addView(this);

        model.init();
    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        Log.i(TAG, "New " + propertyName + " Value(s) Received");
    }

    public DefaultController getController() {
        return controller;
    }
}
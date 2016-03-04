package cat.y2go.sample;

import android.os.Bundle;
import android.util.Log;

import cat.y2go.lib.ViewControllerActivity;

public class MainActivity extends ViewControllerActivity {
    FirstViewController firstViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstViewController = new FirstViewController();
        getViewControllerManager().add(firstViewController, null, true);
        setContentView(firstViewController.getView());
    }

    @Override
    public void onBackPressed() {
        Log.d("MainActivity", "onBackPressed");
    }
}
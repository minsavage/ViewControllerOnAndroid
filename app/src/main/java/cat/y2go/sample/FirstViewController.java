package cat.y2go.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.y2go.lib.ViewController;

/**
 * Created by danney on 16/3/4.
 */
public class FirstViewController extends ViewController{
    TopViewController topViewController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vc_main, container, false);
        topViewController = new TopViewController();
        getViewControllerManager().add(R.id.top, topViewController);
        return view;
    }
}
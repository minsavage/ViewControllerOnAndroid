package cat.y2go.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AndroidRuntimeException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by danney on 16/1/18.
 */
public abstract class ViewController {
    int containerViewId = 0;
    String tag;
    private Context context;
    private Boolean called = false;
    protected View view;
    protected ViewControllerManager viewControllerManager;

    public ViewController() {}

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public ViewControllerManager getViewControllerManager() {
        if (viewControllerManager == null) {
            viewControllerManager = new ViewControllerManager();
            viewControllerManager.addHost(hostCallback);
        }
        return viewControllerManager;
    }

    @Nullable
    public View getView() {
        return view;
    }

    public void finish() {
        ((Activity) getContext()).finish();
    }

    //以下为传递Activity的事件
    public void onCreate(Bundle savedInstanceState) {
        called = true;
    }

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void onStart() {
        called = true;
    }

    public void onResume() {
        called = true;
    }

    public void onPause() {
        called = true;
    }

    public void onStop() {
        called = true;
    }

    public void onDestroy() {
        called = true;
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {called = true;}

    public void onRestoreInstanceState(Bundle savedInstanceState) {called = true;}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {called = true;}

    public void onConfigurationChanged(Configuration newConfig) {
        called = true;
    }

    public void onLowMemory() {
        called = true;
    }

    public boolean onBackPressed() {
        return false;
    }

    void performCreate(Bundle savedInstanceState) {
        called = false;
        onCreate(savedInstanceState);

        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onCreate()");
        }
    }

    View performCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        view = onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    void performStart() {
        called = false;
        onStart();
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onStart()");
        }
        if (viewControllerManager != null) {
            viewControllerManager.dispatchStart();
        }
    }

    void performResume() {
        called = false;
        onResume();
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onResume()");
        }
        if (viewControllerManager != null) {
            viewControllerManager.dispatchResume();
        }
    }

    void performPause() {
        if (viewControllerManager != null) {
            viewControllerManager.dispatchPause();
        }
        called = false;
        onPause();
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onPause()");
        }
    }

    void performStop() {
        if (viewControllerManager != null) {
            viewControllerManager.dispatchStop();
        }
        called = false;
        onStop();
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onStop()");
        }
    }

    void performDestroy() {
        if (viewControllerManager != null) {
            viewControllerManager.dispatchDestroy();
            viewControllerManager = null;
        }
        called = false;
        onDestroy();
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onDestroy()");
        }
    }

    void performSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (viewControllerManager != null) {
            viewControllerManager.dispatchSaveInstanceState(outState, outPersistentState);
        }
        called = false;
        onSaveInstanceState(outState, outPersistentState);
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onSaveInstanceState()");
        }
    }

    void performRestoreInstanceState(Bundle savedInstanceState) {
        if (viewControllerManager != null) {
            viewControllerManager.dispatchRestoreInstanceState(savedInstanceState);
        }
        called = false;
        onRestoreInstanceState(savedInstanceState);
        if (!called) {
            throw new AndroidRuntimeException("ViewController " + this
                    + " did not call through to super.onRestoreInstanceState()");
        }
    }

    void performActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
        if (viewControllerManager != null) {
            viewControllerManager.dispatchActivityResult(requestCode, resultCode, data);
        }
    }

    void performConfigurationChanged(Configuration newConfig) {
        onConfigurationChanged(newConfig);
        if (viewControllerManager != null) {
            viewControllerManager.dispatchConfigurationChanged(newConfig);
        }
    }

    void performLowMemory() {
        onLowMemory();
        if (viewControllerManager != null) {
            viewControllerManager.dispatchLowMemory();
        }
    }

    boolean performBackPressed() {
        if (viewControllerManager != null) {
            if (viewControllerManager.dispatchBackPressed()) {
                return true;
            }
        }
        return onBackPressed();
    }

    private ViewControllerManager.ViewControllerHostCallback hostCallback = new ViewControllerManager.ViewControllerHostCallback() {
        @Override
        public View findViewById(int viewId) {
            return view.findViewById(viewId);
        }

        @Override
        public Context getContext() {
            return ViewController.this.getContext();
        }
    };
}
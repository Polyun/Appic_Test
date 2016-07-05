package com.example.polyun.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.customlbs.library.Indoors;
import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.LocalizationParameters;
import com.customlbs.library.callbacks.IndoorsServiceCallback;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.library.util.IndoorsCoordinateUtil;
import com.customlbs.shared.Coordinate;
import com.customlbs.surface.library.IndoorsSurfaceFactory;
import com.customlbs.surface.library.IndoorsSurfaceFragment;
import com.customlbs.surface.library.SurfaceState;
import com.customlbs.surface.library.ViewMode;

import java.util.List;


public class MainActivity extends FragmentActivity implements IndoorsLocationListener {

    private Indoors indoors;
    private IndoorsSurfaceFragment IndoorsSurfaceFragment;
    private SurfaceState custom_Surface_State       = new SurfaceState();
    IndoorsFactory.Builder indoorsBuilder           = new IndoorsFactory.Builder();
    IndoorsSurfaceFactory.Builder surfaceBuilder    = new IndoorsSurfaceFactory.Builder();
    Toast currentToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        showToast("Start indoors");
        //Toast.makeText(getApplicationContext(), "Start indoors", Toast.LENGTH_SHORT).show();
        show_indoors();
    }

    public void show_indoors() {

        indoorsBuilder.setUserInteractionListener(this);

        //surfaceBuilder.setIndoorsBuilder(indoorsBuilder);

        //IndoorsSurfaceFragment IndoorsSurfaceFragment = surfaceBuilder.build();


        indoorsBuilder.setContext(this);


        // TODO: replace this with your API-key
        indoorsBuilder.setApiKey("8367396f-ba11-4512-aeb3-6cef6a39acf7");

        // TODO: replace 12345 with the id of the building you uploaded to
        // our cloud using the MMT
        indoorsBuilder.setBuildingId((long) 783306659);
        showToast("BuildingID loaded and Interaction Listener loaded");
        //Toast.makeText(getApplicationContext(), "BuildingID loaded", Toast.LENGTH_SHORT).show();

        //Toast.makeText(getApplicationContext(), "Interaction Listener loaded", Toast.LENGTH_SHORT).show();
        surfaceBuilder.setIndoorsBuilder(indoorsBuilder);

        custom_Surface_State.autoSelect = false;
        custom_Surface_State.orientedNaviArrow = false;
        surfaceBuilder.setSurfaceState(custom_Surface_State);

        IndoorsSurfaceFragment = surfaceBuilder.build();
        IndoorsSurfaceFragment.setViewMode(ViewMode.LOCK_ON_ME);

        showToast("IndoorsSurfaceFragment loaded");
        //Toast.makeText(getApplicationContext(), "IndoorsSurfaceFragment loaded", Toast.LENGTH_SHORT).show();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, IndoorsSurfaceFragment, "indoors");
        transaction.commit();

    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {
        int progress = loadingBuildingStatus.getProgress();
        showToast("Building Loading progress " + progress);
        //Toast toast = Toast.makeText(getApplicationContext(), "Building Loading progress " + progress, Toast.LENGTH_SHORT);
        //toast.show();
    }

    @Override
    public void buildingLoaded(Building building) {
        showToast("Building loaded");
        //Toast.makeText(getApplicationContext(), "Building loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void leftBuilding(Building building) {

    }

    @Override
    public void positionUpdated(Coordinate userPosition, int accuracy) {
        //Location geoLocation = IndoorsCoordinateUtil.toGeoLocation(userPosition, this.building);
        int x = userPosition.x;
        int y = userPosition.y;
        int z = userPosition.z;
        //SurfaceState surface_State = surfaceBuilder.getSurfaceState();
        //surface_State.lockOnUserPosition = true;
        //surface_State.selectFittingBackground();
        //surface_State.adjustMapPosition(x,y);
        //IndoorsSurfaceFragment.centerUserPosition();
        //Toast toast = Toast.makeText(getApplicationContext(), "Position Update x:"+x+" y:"+y+" z:"+z, Toast.LENGTH_SHORT);
        //toast.show();
        showToast("Position Update x:"+x+" y:"+y+" z:"+z);
    }

    @Override
    public void orientationUpdated(float v) {
        //Toast.makeText(getApplicationContext(), "Orienation Update, phi:"+v, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changedFloor(int i, String s) {

    }

    @Override
    public void enteredZones(List<Zone> list) {
        //Toast.makeText(getApplicationContext(), "Entered Zone", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void buildingLoadingCanceled() {
        showToast("Canceled Buliding Loading");
        //Toast.makeText(getApplicationContext(), "Canceled Building Loading", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onError(IndoorsException e) {

    }

    void showToast(String text)
    {
        if(currentToast == null)
        {
            currentToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        }

        currentToast.setText(text);
        currentToast.setDuration(Toast.LENGTH_LONG);
        currentToast.show();
    }
}

package com.example.polyun.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.shared.Coordinate;
import com.customlbs.surface.library.IndoorsSurfaceFactory;
import com.customlbs.surface.library.IndoorsSurfaceFragment;

import java.util.List;


public class MainActivity extends FragmentActivity implements IndoorsLocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        show_indoors();
    }

    public void show_indoors() {

        /*ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);*/

        IndoorsFactory.Builder indoorsBuilder = new IndoorsFactory.Builder();
        IndoorsSurfaceFactory.Builder surfaceBuilder = new IndoorsSurfaceFactory.Builder();

        //indoorsBuilder.setUserInteractionListener(this);

        //surfaceBuilder.setIndoorsBuilder(indoorsBuilder);

        //IndoorsSurfaceFragment indoorsFragment = surfaceBuilder.build();


        indoorsBuilder.setContext(this);


        // TODO: replace this with your API-key
        indoorsBuilder.setApiKey("8367396f-ba11-4512-aeb3-6cef6a39acf7");

        // TODO: replace 12345 with the id of the building you uploaded to
        // our cloud using the MMT
        indoorsBuilder.setBuildingId((long) 783306659);

        surfaceBuilder.setIndoorsBuilder(indoorsBuilder);

        IndoorsSurfaceFragment indoorsFragment = surfaceBuilder.build();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, indoorsFragment, "indoors");
        transaction.commit();

    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {
        int progress = loadingBuildingStatus.getProgress();
    }

    @Override
    public void buildingLoaded(Building building) {

    }

    @Override
    public void leftBuilding(Building building) {

    }

    @Override
    public void positionUpdated(Coordinate coordinate, int i) {

    }

    @Override
    public void orientationUpdated(float v) {

    }

    @Override
    public void changedFloor(int i, String s) {

    }

    @Override
    public void enteredZones(List<Zone> list) {

    }

    @Override
    public void buildingLoadingCanceled() {

    }

    @Override
    public void onError(IndoorsException e) {

    }
}

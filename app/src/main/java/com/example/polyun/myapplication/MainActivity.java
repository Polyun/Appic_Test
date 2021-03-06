package com.example.polyun.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.customlbs.library.Indoors;
import com.customlbs.library.IndoorsException;
import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.IndoorsLocationListener;
import com.customlbs.library.callbacks.LoadingBuildingStatus;
import com.customlbs.library.model.Building;
import com.customlbs.library.model.Zone;
import com.customlbs.shared.Coordinate;
import com.customlbs.surface.library.IndoorsSurfaceFactory;
import com.customlbs.surface.library.IndoorsSurfaceFragment;
import com.customlbs.surface.library.SurfaceState;
import com.customlbs.surface.library.ViewMode;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements IndoorsLocationListener {

    private Indoors indoors;
    private IndoorsSurfaceFragment IndoorsSurfaceFragment;
    private SurfaceState custom_Surface_State       = new SurfaceState();
    IndoorsFactory.Builder indoorsBuilder           = new IndoorsFactory.Builder();
    IndoorsSurfaceFactory.Builder surfaceBuilder    = new IndoorsSurfaceFactory.Builder();
    Toast currentToast;
    List<Long> lastZoneIDList                       = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        showToast("Start indoors");
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

        surfaceBuilder.setIndoorsBuilder(indoorsBuilder);

        custom_Surface_State.autoSelect = false;
        custom_Surface_State.orientedNaviArrow = false;
        surfaceBuilder.setSurfaceState(custom_Surface_State);

        IndoorsSurfaceFragment = surfaceBuilder.build();
        IndoorsSurfaceFragment.setViewMode(ViewMode.LOCK_ON_ME);

        //IndoorsSurfaceFragment.setViewMode(ViewMode.HIGHLIGHT_ALL_ZONES);

        showToast("IndoorsSurfaceFragment loaded");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, IndoorsSurfaceFragment, "indoors");
        transaction.commit();

    }

    @Override
    public void loadingBuilding(LoadingBuildingStatus loadingBuildingStatus) {
        int progress = loadingBuildingStatus.getProgress();
        showToast("Building Loading progress " + progress);
    }

    @Override
    public void buildingLoaded(Building building) {
        showToast("Building loaded");
    }

    @Override
    public void leftBuilding(Building building) {
        //Deprecated - do not use, says indoors-documentation
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

        //Update old Zone-List cause there is no zone-left-event
        zoneListChanged(IndoorsSurfaceFragment.getCurrentZones());

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
        if (zoneListChanged(list)) {
            for (Zone zone : list) {
                showToast("Zone name: " + zone.getName() + "\nZone ID: " + zone.getId() +
                        "\nZone description: " + zone.getDescription());
            }
        }
    }

    /**
     * Check if given zone list is different from last one
     * @param zoneListNew List of new zones to check for differences
     * @return true: given zone list is different from previous one. Else false.
     */
    private boolean zoneListChanged(List<Zone> zoneListNew) {
        List<Long> zoneIDListNew    = new ArrayList<Long>();
        boolean isChanged           = false;
        for (Zone zone: zoneListNew) {
            zoneIDListNew.add(zone.getId());
            if (!lastZoneIDList.contains(zone.getId())) {
                isChanged           = true;
            }
        }
        lastZoneIDList              = zoneIDListNew;

        return isChanged;
    }

    @Override
    public void buildingLoadingCanceled() {
        showToast("Canceled Building Loading");
    }



    @Override
    public void onError(IndoorsException e) {

    }

    /**
     * Shows toast message with given text. Cancels prev toast messages.
     * @param text message to show in toast.
     */
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

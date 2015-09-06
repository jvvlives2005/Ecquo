package abassawo.c4q.nyc.ecquo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.Scopes;
//import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 9/4/15.
 */
public class EditLocationragment extends Fragment {
    public GoogleApiClient googleLogInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location_edit, container, false);
        return view;
    }


//    protected synchronized void buildGoogleApiClient(Context context) {
//        googleLogInClient = new GoogleApiClient.Builder(context)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API)
//                .addScope(new Scope(Scopes.EMAIL))
//                .build();
//    }

}

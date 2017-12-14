package com.icacuenca.carlos.icacuenca.lawyerdetail;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.icacuenca.carlos.icacuenca.R;
import com.icacuenca.carlos.icacuenca.bbdd;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LawyerDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LawyerDetailFragment extends Fragment {
    private static final String ARG_LAWYER_ID = "lawyerId";


    private String mLawyerId;

    private CollapsingToolbarLayout mCollapsingView;

    private TextView apellidos;
    private TextView direccion;
    private TextView correo;
    private TextView movil;

    private bbdd bd;



    public LawyerDetailFragment() {
        // Required empty public constructor
    }



    public static LawyerDetailFragment newInstance(String lawyerId) {
        LawyerDetailFragment fragment = new LawyerDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, lawyerId);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLawyerId = getArguments().getString(ARG_LAWYER_ID);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lawyer_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);

        apellidos = (TextView) root.findViewById(R.id.lApellidos);
        direccion = (TextView) root.findViewById(R.id.lDireccion);
        correo = (TextView) root.findViewById(R.id.lCorreo);
        movil = (TextView) root.findViewById(R.id.lMovil);

       bd = new bbdd(getActivity());
        loadLawyer();

        return root;
    }
    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    private void showLawyer(Cursor cursor) {
        mCollapsingView.setTitle(cursor.getString(1)+ cursor.getString(2));

        apellidos.setText(cursor.getString(1) + cursor.getString(2));

        direccion.setText(cursor.getString(cursor.getColumnIndex("direccion")) +" " + cursor.getString(cursor.getColumnIndex("cp")) +" - " + cursor.getString(cursor.getColumnIndex("localidad")));
        correo.setText(cursor.getString(cursor.getColumnIndex("email")));
        movil.setText(cursor.getString(cursor.getColumnIndex("movil")));
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return bd.getLawyerById(mLawyerId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(cursor);
            } else {
                showLoadError();
            }
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
    }

}

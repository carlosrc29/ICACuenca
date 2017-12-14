package com.icacuenca.carlos.icacuenca.lawyers;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.icacuenca.carlos.icacuenca.R;
import com.icacuenca.carlos.icacuenca.bbdd;
import com.icacuenca.carlos.icacuenca.lawyerdetail.LawyerDetailActivity;


/**
 * Vista para la lista de abogados que se corresponden con la busqueda
 */
public class LawyersFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;
    private bbdd db;

    private ListView mLawyersList;
    private LawyersCursorAdapter mLawyersAdapter;



    public LawyersFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static LawyersFragment newInstance() {
        LawyersFragment fragment = new LawyersFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lawyers, container, false);
        // Referencias UI
        mLawyersList = (ListView) root.findViewById(R.id.lawyers_list);
        mLawyersAdapter = new LawyersCursorAdapter(getActivity(), null);



        // Eventos
        mLawyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mLawyersAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex("numCol"));

                showDetailScreen(currentLawyerId);
            }
        });

        // Setup
        mLawyersList.setAdapter(mLawyersAdapter);

        // Instancia de bbdd
        db = new bbdd(getActivity());

        // Carga de datos
        loadLawyers();

        return root;
    }

    private void showDetailScreen(String lawyerCol) {
        Intent intent = new Intent(getActivity(), LawyerDetailActivity.class);
        intent.putExtra(LawyersActivity.EXTRA_LAWYER_ID, lawyerCol);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);
    }




    private void loadLawyers() {
        new LawyersLoadTask().execute();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_LAWYER:
                    loadLawyers();
                    break;
            }
        }
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {


            String query = getActivity().getIntent().getExtras().getString(SearchManager.QUERY);

          return db.getCursorBuscador(query);
            //return db.getAllLawyers2();
            //return db.getLawyerByNameOrCity2("Carlos");
            //String aux = "carlos";
            //return db.getLawyerByName(aux);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mLawyersAdapter.swapCursor(cursor);

            } else {
                // Mostrar empty state
            }

        }

    }


}

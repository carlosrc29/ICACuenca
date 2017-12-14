package com.icacuenca.carlos.icacuenca.lawyers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icacuenca.carlos.icacuenca.R;
import com.icacuenca.carlos.icacuenca.bbdd;

/**
 * Created by Carlos on 06/11/2017.
 */

public class LawyersCursorAdapter extends CursorAdapter {

    private bbdd db;

    public LawyersCursorAdapter(Context context, Cursor c){
        super(context, c, 0);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_lawyer, viewGroup, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);





        // Get valores.
        String name = cursor.getString(cursor.getColumnIndex("nombre"));
        String apellidos = cursor.getString(cursor.getColumnIndex("apellidos"));
        //String avatarUri = cursor.getString(cursor.getColumnIndex("apellidos");

        // Setup.
        nameText.setText(name+" "+ apellidos);
       /* Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.drawable.ic_account_circle)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });
                */

    }

    /*
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        Cursor currentCursor = null;

        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }

        String args = "";

        if (constraint != null) {
            args = constraint.toString();
        }

        currentCursor = db.getCursorBuscador(args);

        return currentCursor;
    }
    */
}

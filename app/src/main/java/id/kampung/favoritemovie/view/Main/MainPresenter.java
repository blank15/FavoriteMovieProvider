package id.kampung.favoritemovie.view.Main;

import android.content.Context;
import android.database.Cursor;

import static id.kampung.favoritemovie.db.DbContract.CONTENT_URI;

public class MainPresenter {
    private MainInterface mainInterface;
    private Context context;

    MainPresenter(MainInterface mainInterface,Context context) {
        this.mainInterface = mainInterface;
        this.context = context;
    }

    public void getData(){
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor.getCount() <= 0){
            mainInterface.empty();
        }else
        mainInterface.setData(cursor);
    }
}

package id.kampung.favoritemovie.view.Main;

import android.database.Cursor;

public interface MainInterface {
    void setData(Cursor listMovie);
    void empty();
}

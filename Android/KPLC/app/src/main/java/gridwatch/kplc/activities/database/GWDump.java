package gridwatch.kplc.activities.database;

import com.loopj.android.http.RequestParams;

import gridwatch.kplc.activities.config.DatabaseConfig;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by nklugman on 11/20/16.
 */

public class GWDump extends RealmObject {

    @Required
    private String mID;

    @Required
    private String mDump;

    private long mTime;


    public GWDump() {

    }

    public GWDump(String id, String dump) {
        mID = id;
        mDump = dump;
        mTime = System.currentTimeMillis();
    }

    public String toString() {
        return mDump;
    }

    public RequestParams toRequestParams() {
        RequestParams values = new RequestParams();
        values.put(DatabaseConfig.TYPE, mDump);
        values.put(DatabaseConfig.ID, mID);
        values.put(DatabaseConfig.TIME, mTime);
        return values;
    }

    public String getID() {
        return mID;
    }


}


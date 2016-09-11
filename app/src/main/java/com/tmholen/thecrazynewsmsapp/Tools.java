package com.tmholen.thecrazynewsmsapp;

import android.net.Uri;

/**
 * Created by dogsh on 11-Sep-16.
 */

public abstract class Tools {



    public Uri ParseResourceToUri(int resource){

        return Uri.parse("android.resource://com.tmholen.thecrazynewsmsapp/" + resource);
    }

}

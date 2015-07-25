package teamvulcan.refokus.helpers;

import android.content.Context;
import teamvulcan.refokus.R;

/**
 * Created by kcheng.2013 on 25/7/2015.
 */
public class Portal {
    Context context;
    public Portal(Context context){
        this.context = context;
    }

    public String base_url(){
        return context.getResources().getString(R.string.base_url);
    }

    public String api_url(){
        return base_url()+"/api";
    }
}

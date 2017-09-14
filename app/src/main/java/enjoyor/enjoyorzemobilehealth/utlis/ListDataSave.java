package enjoyor.enjoyorzemobilehealth.utlis;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by chenlikang
 */

public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public void setDataList(String tag, List datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        System.out.print(tag+"磁盘保存的数据："+datalist+"\n");
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public <T> List<T> getDataList(String tag,Class<T> clazz) {
        List<T> datalist=new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        System.out.print(tag+"从磁盘获取的数据位："+datalist+"\n");
        if (null == strJson) {
            return datalist;
        }


        /*
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());*/
        JsonArray array=new JsonParser().parse(strJson).getAsJsonArray();
        for(final JsonElement element:array){
            datalist.add(new Gson().fromJson(element,clazz));
        }
        return datalist;

    }
}

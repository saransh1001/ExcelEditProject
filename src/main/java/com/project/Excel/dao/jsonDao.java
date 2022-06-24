package com.project.Excel.dao;
import com.google.gson.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class jsonDao {

    public void findAllKeys(Object object, String key, Set<String> finalKeys, ArrayList<String>headings) {
        if (object instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) object;
            if(jsonObject.size()==0){
                if(!finalKeys.contains(key)){
                    headings.add(key);
                    finalKeys.add(key);
                }
            }
            else{
                jsonObject.keySet().forEach(childKey -> {
                    findAllKeys(jsonObject.get(childKey), key != null ? key + "." + childKey : childKey, finalKeys, headings);
                });
            }
        }
        else{
            if(!finalKeys.contains(key)){
                headings.add(key);
                finalKeys.add(key);
            }

        }
    }
    public JsonArray GetJsonArray (String json, ArrayList<String>headings) {
        //parsing the json string to get json array of objects
        try {
            JsonArray jsonArray = new Gson().fromJson(json, JsonArray.class);
            // JsonObject jsonObject = new Gson().fromJson(jsonStr, JsonObject.class);
            Set<String> finalKeys = new HashSet<>();
            for(int i=0;i<jsonArray.size();i++) {
                JsonObject ob = (JsonObject) jsonArray.get(i);
                findAllKeys(ob, null, finalKeys, headings);
            }
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
package com.project.Excel.dao;
import com.google.gson.*;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Component
public class jsonDao {

    /**
     * This function extract all the headings or keys from the Json Objects
     * @param object -> Object from which headings are extracted
     * @param key -> key represent the heading formed till yet by traversing the json objects
     * @param finalKeys -> this string holds the key that will be final heading
     * @param headings -> This array of strings contains the headings of the data
     */
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

    /**
     * This function will parse json string to JsonArray and will help to get all the keys of the data
     * @param json -> This is string which contains json data that has to be added to Excel file
     * @param headings ->This array of strings will get the headings of the data
     * @return JsonArray
     */
    public  JsonArray GetJsonArray(String json, ArrayList<String> headings) {
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
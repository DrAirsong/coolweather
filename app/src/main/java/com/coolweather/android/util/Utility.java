package com.coolweather.android.util;

import android.text.TextUtils;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /**
     * 解析和返回服务器返回的省级数据
     * @param response
     * @return
     * @throws JSONException
     */
    public static boolean handleProvinceResponse(String response) throws JSONException {
        if (!TextUtils.isEmpty(response)){
            JSONArray allProvinces = new JSONArray(response);
            for (int i =0; i< allProvinces.length();i++){
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                province.save();
            }
            return true;
        }
        return false;
    }

    /**
     * 解析和返回服务器返回的市级数据
     * @param response
     * @return
     * @throws JSONException
     */
    public static boolean handleCityResponse(String response, int provinceId) throws JSONException {
        if (!TextUtils.isEmpty(response)){
            JSONArray allCites = new JSONArray(response);
            for (int i =0; i< allCites.length();i++){
                JSONObject cityObject = allCites.getJSONObject(i);
                City city = new City();
                city.setCityName(cityObject.getString("name"));
                city.setCityCode(cityObject.getInt("id"));
                city.setProvinceId(provinceId);
                city.save();
            }
            return true;
        }
        return false;
    }

    /**
     * 解析和返回服务器返回的县级数据
     * @param response
     * @return
     * @throws JSONException
     */
    public static boolean handleCountyResponse(String response, int cityId) throws JSONException {
        if (!TextUtils.isEmpty(response)){
            JSONArray allCounties = new JSONArray(response);
            for (int i =0; i< allCounties.length();i++){
                JSONObject countyObject = allCounties.getJSONObject(i);
                County county = new County();
                county.setCountyName(countyObject.getString("name"));
                county.setWeatherId(countyObject.getString("weather_id"));
                county.setCityId(cityId);
                county.save();
            }
            return true;
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析成weather实体类
     */
    public static Weather handleWeatherResponse(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
        String weatherContent = jsonArray.getJSONObject(0).toString();
        return new Gson().fromJson(weatherContent, Weather.class);

    }


}




















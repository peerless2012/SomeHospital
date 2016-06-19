package com.peerless2012.somehospital.utils;

import android.content.Context;
import android.os.Environment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peerless2012.somehospital.data.bean.CityInfo;
import com.peerless2012.somehospital.data.bean.Geo;
import com.peerless2012.somehospital.data.bean.HospitalInfo;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/17 19:23
 * @Version V1.0
 * @Description :
 */
public class GeoGenerateUtils {

    public void generateGeo(Context context){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"HospitalsInfo.json");
        String json = null;
        try{
            //FileInputStream 用于读取诸如图像数据之类的原始字节流。要读取字符流，请考虑使用 FileReader。
            FileInputStream inStream=new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int length=-1;
            while( (length = inStream.read(buffer)) != -1){

                bos.write(buffer,0,length);
                // .write方法 SDK 的解释是 Writes count bytes from the byte array buffer starting at offset index to this stream.
                //  当流关闭以后内容依然存在
            }
            bos.close();
            inStream.close();
            json = bos.toString();

            // 为什么不一次性把buffer得大小取出来呢？为什么还要写入到bos中呢？ return new(buffer,"UTF-8") 不更好么?

            // return new String(bos.toByteArray(),"UTF-8");
            Gson gson = new Gson();
            Type type = new TypeToken<List<CityInfo>>(){}.getType();
            List<CityInfo> list = gson.fromJson(json, type);
            for (int i = 0; i < list.size(); i++) {
                CityInfo cityInfo = list.get(i);
                if (cityInfo != null){
                    List<HospitalInfo> hospitalInfos = cityInfo.getHospitalInfos();
                    if (hospitalInfos != null && hospitalInfos.size() > 0) {
                        for (int j = 0; j < hospitalInfos.size(); j++) {
                            HospitalInfo hospitalInfo = hospitalInfos.get(j);

                            Geo geo = getGeo(context,hospitalInfo.getName());
                            hospitalInfo.setGeo(geo);
                        }
                    }
                }
            }
        File destFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"HospitalsGeoInfo.json");
        String destJson = gson.toJson(list);
        FileWriter fileWriter = new FileWriter(destFile);
        fileWriter.write(destJson);
        fileWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private Geo getGeo(Context context,String name) {
        for (int i = 0; i < 3; i++) {
            PoiSearch.Query query = new PoiSearch.Query(name,"","");
            PoiSearch poiSearch = new PoiSearch(context,query);
            PoiResult poiResult = null;
            try {
                poiResult = poiSearch.searchPOI();
                if (poiResult != null && poiResult.getPois()!=null && poiResult.getPois().size() > 0){
                    PoiItem poiItem = poiResult.getPois().get(0);
                    Geo geo = new Geo();
                    LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                    geo.setLongitude(latLonPoint.getLongitude());
                    geo.setLatitude(latLonPoint.getLatitude());
                    return geo;
                }
            } catch (AMapException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

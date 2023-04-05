package com.example.doanbanhoa.Models;

import com.example.doanbanhoa.Models.Hoa;

import java.util.ArrayList;

public class HoaData {
    public static ArrayList<Hoa> HoaDT = new ArrayList<>();
    public static ArrayList<Hoa> GeneratePhotoData(){
    return  (ArrayList<Hoa>) HoaDT;
    }

    public static ArrayList<Hoa> GeneratePhotoDataSPM(){
       ArrayList<Hoa> kiet = new ArrayList<>();
       for(int i = 0; i < HoaDT.size(); i++){
           if(HoaDT.get(i).getID_DanhMuc().equals("2")){
               kiet.add(HoaDT.get(i));
           }
       }
       return kiet;
    }
//    public static Hoa GetImagebyId(int id){
//        for (int i = 0; i < HoaDT.size(); i++){
//            if(HoaDT.get(i).getID_Hoa() == id ){
//                return HoaDT.get(i);
//            }
//        }
//        return null;
//    }

    public HoaData(ArrayList<Hoa> k) {
        this.HoaDT = k;
    }



    public static void AddHoa(Hoa k){
        HoaDT.add(k);
    }
    public static void AddRangeHoa(ArrayList<Hoa> k){
        for(int i =0; i < k.size(); i++){
            HoaDT.add(k.get(i));
        }
    }
    public static int GetCount(){
        return HoaDT.size();
    }
}

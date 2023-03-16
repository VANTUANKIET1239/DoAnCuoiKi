package com.example.doanbanhoa;

import java.util.ArrayList;
import java.util.List;

public class HoaData {
    public static List<Hoa> HoaDT = new ArrayList<>();
    public  static ArrayList<Hoa> GeneratePhotoData(){
    return  (ArrayList<Hoa>) HoaDT;
    }
    public static Hoa GetImagebyId(int id){
        for (int i = 0; i < HoaDT.size(); i++){
            if(HoaDT.get(i).getID_Hoa() == id ){
                return HoaDT.get(i);
            }
        }
        return null;
    }
    public static void AddHoa(Hoa k){
        HoaDT.add(k);
    }
    public static int GetCount(){
        return HoaDT.size();
    }
}

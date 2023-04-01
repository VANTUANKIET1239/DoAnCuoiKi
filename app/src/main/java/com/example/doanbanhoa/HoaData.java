package com.example.doanbanhoa;

import java.util.ArrayList;
import java.util.List;

public class HoaData {
    public static ArrayList<Hoa> HoaDT = new ArrayList<>();
    public static ArrayList<Hoa> GeneratePhotoData(){
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

    public HoaData(ArrayList<Hoa> k) {
        this.HoaDT = k;
    }

    public static void xapxepcaothap(boolean k){
        if(k == true){
            for(int i = 0; i < HoaDT.size() - 1; i++){
                for( int j = i + 1; j < HoaDT.size(); j++){
                    if(HoaDT.get(i).getGia() < HoaDT.get(j).getGia()){
                        Hoa tam = new Hoa(HoaDT.get(i));
                        HoaDT.get(i).LayHoa(HoaDT.get(j));
                        HoaDT.get(j).LayHoa(tam);
                    }
                }
            }
        }
        else {
            for(int i = 0; i < HoaDT.size() - 1; i++){
                for( int j = i + 1; j < HoaDT.size(); j++){
                    if(HoaDT.get(i).getGia() > HoaDT.get(j).getGia()){
                        Hoa tam = new Hoa(HoaDT.get(i));
                        HoaDT.get(i).LayHoa(HoaDT.get(j));
                        HoaDT.get(j).LayHoa(tam);
                    }
                }
            }
        }

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

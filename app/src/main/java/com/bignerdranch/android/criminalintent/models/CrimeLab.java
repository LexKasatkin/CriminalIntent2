package com.bignerdranch.android.criminalintent.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lex on 5/12/18.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    public List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CrimeLab(Context context) {
        mCrimes=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            Crime crime=new Crime();
//            crime.setTitle("Crime #"+i);
//            crime.setSolved(i%2==0);
//            crime.setmRequiresPolice(i%2);
//            mCrimes.add(crime);
//        }
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public static CrimeLab get(Context context){
        if(sCrimeLab==null){
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public Crime getCrime(UUID id){
        for(Crime crime:mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

}

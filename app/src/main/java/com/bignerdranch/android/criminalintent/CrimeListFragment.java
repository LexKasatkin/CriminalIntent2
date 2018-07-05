package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.models.Crime;
import com.bignerdranch.android.criminalintent.models.CrimeLab;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lex on 5/12/18.
 */

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_crime_list, container,false);
        mCrimeRecyclerView=(RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        List<Crime>crimes=crimeLab.getCrimes();
        if(mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView iv;
        Crime mCrime;
        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Crime crime){
            mCrime=crime;
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, MMM dd, yyyy");
            mDateTextView.setText( simpleDateFormat.format((mCrime.getDate())).toString());
            mTitleTextView.setText(mCrime.getTitle().toString());
            if(mCrime.isSolved())
                iv.setImageResource(R.mipmap.ic_launcher);
        }
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType){
            super(inflater.inflate(R.layout.list_item_crime, parent,false));
            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDateTextView=itemView.findViewById(R.id.crime_date);
            iv=itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(),mCrime.getTitle()+" clicked!", Toast.LENGTH_LONG).show();\
            Intent intent=CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }


    private class DifficultCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView iv;
        Crime mCrime;
        public DifficultCrimeHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Crime crime){
            mCrime=crime;
            mDateTextView.setText(mCrime.getDate().toString());
            mTitleTextView.setText(mCrime.getTitle().toString());
            if(mCrime.isSolved())
                iv.setImageResource(R.mipmap.ic_launcher);
        }
        public DifficultCrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType){
            super(inflater.inflate(R.layout.list_item_difficult_crime, parent,false));
            mTitleTextView=itemView.findViewById(R.id.crime_title);
            mDateTextView=itemView.findViewById(R.id.crime_date);
            iv=itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(),mCrime.getTitle()+"difficult clicked!", Toast.LENGTH_LONG).show();
            Intent intent=CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes=crimes;
        }

        @Override
        public int getItemViewType(int position) {

            return mCrimes.get(position).getmRequiresPolice();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            switch (viewType) {
                case 0:
                    return new CrimeHolder(layoutInflater, parent, 0);
                case 1:
                    return new DifficultCrimeHolder(layoutInflater,parent,1);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    Crime crime = mCrimes.get(position);
                    CrimeHolder crimeHolder=(CrimeHolder) holder;
                    crimeHolder.bind(crime);
                    break;
                case 1:
                    Crime crime1 = mCrimes.get(position);
                    DifficultCrimeHolder difficultCrimeHolder=(DifficultCrimeHolder) holder;
                    difficultCrimeHolder.bind(crime1);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}

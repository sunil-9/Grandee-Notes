
package com.dhanas.grandeenotes.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dhanas.grandeenotes.Fragment.CourseFragment;
import com.dhanas.grandeenotes.Fragment.QuestionAnswerFragment;
import com.dhanas.grandeenotes.Fragment.SyllabusFragment;
import com.dhanas.grandeenotes.R;

public class ViewPagerAdapter extends FragmentStateAdapter {
private  int get_item_count;
private  String  sem_id;

    public ViewPagerAdapter(@NonNull FragmentActivity fm,int get_item_count,String  sem_id) {
        super(fm);
        this.get_item_count =get_item_count;
        this.sem_id =sem_id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0: return new CourseFragment(sem_id);
            case 1: return new QuestionAnswerFragment(sem_id);
            case 2: return new SyllabusFragment(sem_id);
            default: return null;
        }

    }

    @Override
    public int getItemCount() {
        return get_item_count;
    }
}





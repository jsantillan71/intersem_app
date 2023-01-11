package com.intersem.sdib.ui.services.adapters;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;
import java.util.List;

public class StepperAdapter extends AbstractFragmentStepAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();

    public StepperAdapter(FragmentManager fm, Context context){
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        return (Step) fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context).setTitle(stringList.get(position));
        if(position == 0){
            builder.setEndButtonLabel("Siguiente");
        }else if (position > 0 && position < fragmentList.size() - 1){
            builder.setBackButtonLabel("Atras").setEndButtonLabel("Siguiente");
        }else{
            builder.setBackButtonLabel("Atras").setEndButtonLabel("Finalizar");
        }

        return builder.create();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        stringList.add(title);
    }
}

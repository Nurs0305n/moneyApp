package com.nurs.moneyApp.views.fragments;

import static com.nurs.moneyApp.utils.Constant.SELECTED_STATS_TYPE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.material.tabs.TabLayout;
import com.nurs.moneyApp.R;
import com.nurs.moneyApp.databinding.FragmentStatsBinding;
import com.nurs.moneyApp.models.Transaction;
import com.nurs.moneyApp.utils.Constant;
import com.nurs.moneyApp.utils.Helper;
import com.nurs.moneyApp.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class StatsFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentStatsBinding binding;

    Calendar calendar;

    /*
    0 == День
    1 == Месяц
     */
    public MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updateDate();

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));

            SELECTED_STATS_TYPE = Constant.INCOME;
            updateDate();
        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));

            SELECTED_STATS_TYPE = Constant.EXPENSE;
            updateDate();
        });


        binding.nextDateBtn.setOnClickListener(c -> {
            if (Constant.SELECTED_TAB_STATS == Constant.DAILY){
                calendar.add(Calendar.DATE, 1);
            } else if (Constant.SELECTED_TAB_STATS == Constant.MONTHLY){
                calendar.add(Calendar.MONTH, 1);
            }
            updateDate();
        });

        binding.previousDateBtn.setOnClickListener(c ->{
            if (Constant.SELECTED_TAB_STATS == Constant.DAILY){
                calendar.add(Calendar.DATE, -1);
            } else if (Constant.SELECTED_TAB_STATS == Constant.MONTHLY){
                calendar.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Месяц")){
                    Constant.SELECTED_TAB_STATS = 1;
                    updateDate();
                }else if (tab.getText().equals("День")){
                    Constant.SELECTED_TAB_STATS = 0;
                    updateDate();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Pie pie = AnyChart.pie();

        viewModel.categoriesTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {

            @Override
            public void onChanged(RealmResults<Transaction> transactions) {

                if (!transactions.isEmpty()){
                    binding.emptyState.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);
                    List<DataEntry> data = new ArrayList<>();

                    Map<String, Double> categoryMap = new HashMap<>();

                    for (Transaction transaction: transactions){
                        String category = transaction.getCategory();
                        double amount = transaction.getAmount();

                        if (categoryMap.containsKey(category)){
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal += Math.abs(amount);

                            categoryMap.put(category, currentTotal);
                        } else {
                            categoryMap.put(category, Math.abs(amount));
                        }
                    }

                    for (Map.Entry<String, Double> entry: categoryMap.entrySet()){
                        data.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
                    }

                    pie.data(data);
                } else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }

            }
        });
        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);




//        pie.title("Fruits imported in 2015 (in kg)");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

        binding.anyChart.setChart(pie);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    void updateDate() {
        if (Constant.SELECTED_TAB_STATS == Constant.DAILY){
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if (Constant.SELECTED_TAB_STATS == Constant.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonthly(calendar.getTime()));
        }
        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);
    }

}
package com.nurs.moneyApp.views.fragments;

import static com.nurs.moneyApp.utils.Constant.accounts;
import static com.nurs.moneyApp.utils.Constant.categories;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nurs.moneyApp.R;
import com.nurs.moneyApp.adapters.AccountAdapter;
import com.nurs.moneyApp.adapters.CategoryAdapter;
import com.nurs.moneyApp.databinding.FragmentAddTransactionBinding;
import com.nurs.moneyApp.databinding.ListDialogBinding;
import com.nurs.moneyApp.models.Account;
import com.nurs.moneyApp.models.Category;
import com.nurs.moneyApp.models.Transaction;
import com.nurs.moneyApp.utils.Constant;
import com.nurs.moneyApp.utils.Helper;
import com.nurs.moneyApp.views.activites.MainActivity;

import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransactionBinding binding;
    Transaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddTransactionBinding.inflate(inflater);

        transaction = new Transaction();

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));

            transaction.setType(Constant.INCOME);
        });

        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));

            transaction.setType(Constant.EXPENSE);
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH, datePicker.getMonth());
                    calendar.set(Calendar.YEAR, datePicker.getYear());

//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                    String dateToShow = Helper.formatDate(calendar.getTime());

                    binding.date.setText(dateToShow);

                    transaction.setDate(calendar.getTime());
                    transaction.setId(calendar.getTime().getTime());
                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());


            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.category.setText(category.getCategoryName());
                    transaction.setCategory(category.getCategoryName());
                    categoryDialog.dismiss();
                }
            });
            dialogBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dialogBinding.recycleView.setAdapter(categoryAdapter);

            categoryDialog.show();
        });

        binding.account.setOnClickListener(c -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(dialogBinding.getRoot());


            AccountAdapter accountAdapter = new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.account.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());
                    accountDialog.dismiss();
                }
            });
            dialogBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogBinding.recycleView.setAdapter(accountAdapter);

            accountDialog.show();
        });

        binding.saveTransactionButton.setOnClickListener(c -> {
            double amount = Double.parseDouble(binding.amount.getText().toString());
            String note = binding.note.getText().toString();

            if (transaction.getType().equals(Constant.EXPENSE))
                transaction.setAmount(amount * -1);
            else
                transaction.setAmount(amount);

            transaction.setNote(note);

            ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
            ((MainActivity)getActivity()).getTransaction();
            dismiss();
        });

        return binding.getRoot();
    }
}
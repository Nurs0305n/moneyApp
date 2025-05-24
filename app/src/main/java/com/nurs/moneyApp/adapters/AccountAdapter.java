package com.nurs.moneyApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurs.moneyApp.R;
import com.nurs.moneyApp.databinding.RowAccountBinding;
import com.nurs.moneyApp.models.Account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    Context context;
    ArrayList<Account> accounts;

    public interface AccountClickListener{
        void onAccountSelected(Account account);
    }

    AccountClickListener accountClickListener;

    public AccountAdapter(Context context, ArrayList<Account> accounts, AccountClickListener accountClickListener) {
        this.accounts = accounts;
        this.context = context;
        this.accountClickListener = accountClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.row_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.binding.accountName.setText(account.getAccountName());
        holder.itemView.setOnClickListener(c -> {
            accountClickListener.onAccountSelected(account);
        });

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {

        RowAccountBinding binding;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountBinding.bind(itemView);
        }
    }

}

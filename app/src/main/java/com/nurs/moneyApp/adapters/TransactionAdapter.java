package com.nurs.moneyApp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurs.moneyApp.R;
import com.nurs.moneyApp.databinding.RowTransactionBinding;
import com.nurs.moneyApp.models.Category;
import com.nurs.moneyApp.models.Transaction;
import com.nurs.moneyApp.utils.Constant;
import com.nurs.moneyApp.utils.Helper;
import com.nurs.moneyApp.views.activites.MainActivity;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    RealmResults<Transaction> transactions;

    public TransactionAdapter(Context context, RealmResults<Transaction> transactions){
        this.context = context;
        this.transactions = transactions;

    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountLbl.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getCategory());

        Category transactionCategory = Constant.getCategoryDetails(transaction.getCategory());

        holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));

        holder.binding.accountLbl.setBackgroundTintList(context.getColorStateList(Constant.getAccountColor(transaction.getAccount())));

        if (transaction.getType().equals(Constant.INCOME)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor));
        } else if (transaction.getType().equals(Constant.EXPENSE)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Удалить транзакию");
                deleteDialog.setMessage("Вы уверены что хотите удалить эту транзакцию?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Да", (dialog, which) -> {
                    ((MainActivity)context).viewModel.deleteTransaction(transaction);
                });
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Нет", (dialog, which) -> {
                    deleteDialog.dismiss();
                });
                deleteDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        RowTransactionBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);

        }
    }
}

package com.nurs.moneyApp.utils;

import com.nurs.moneyApp.R;
import com.nurs.moneyApp.models.Account;
import com.nurs.moneyApp.models.Category;

import java.util.ArrayList;

public class Constant {
    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static ArrayList<Category> categories = new ArrayList<>();

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTE = 4;

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constant.INCOME;

    public static void setCategories() {
        categories.add(new Category("Зарпалата", R.drawable.ic_salary, R.color.category1));
        categories.add(new Category("Бизнес", R.drawable.ic_business, R.color.category2));
        categories.add(new Category("Инвестиции", R.drawable.ic_investment, R.color.category3));
        categories.add(new Category("Займ", R.drawable.ic_loan, R.color.category4));
        categories.add(new Category("Аренда", R.drawable.ic_rent, R.color.category5));
        categories.add(new Category("Свадьба", R.drawable.ic_wedding, R.color.category6));
        categories.add(new Category("Развелечение", R.drawable.ic_entertainment, R.color.category1));
        categories.add(new Category("Здоровье", R.drawable.ic_healthy, R.color.category3));
        categories.add(new Category("Другие", R.drawable.ic_other, R.color.category5));
    }

    public static Category getCategoryDetails(String categoryName){
        for (Category item :
                categories) {
            if (item.getCategoryName().equals(categoryName)) {
                return item;
            }
        }
        return null;
    }

    public static ArrayList<Account> accounts = new ArrayList<>();

    public static void setAccounts() {
        accounts.add(new Account(0, "Наличные", R.color.cashColor));
        accounts.add(new Account(0, "Карта", R.color.cardColor));
//        accounts.add(new Account(0, "Копилка", R.color.otherColor));
//        accounts.add(new Account(0, "Родителям", R.color.otherColor));
//        accounts.add(new Account(0, "Благотворительноть", R.color.otherColor));
    }

    public static int getAccountColor(String accountName){
        for (Account account :
                accounts) {
            if (account.getAccountName().equals(accountName))
                return account.getAccountColor();
        }
        return R.color.orange;
    }
}

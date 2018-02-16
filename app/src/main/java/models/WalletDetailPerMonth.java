package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armaan on 17-01-2018.
 */

public class WalletDetailPerMonth {
    private String monthYear;
    public List<WalletDetail> transactions=new ArrayList<>();

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public List<WalletDetail> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WalletDetail> transactions) {
        this.transactions = transactions;
    }
}

package michaelpotocar.projectkitty.results;

import michaelpotocar.projectkitty.dynamodb.model.Transaction;

import java.util.List;

public class GetTransactionsResult {
    private List<Transaction> transactions;

    public GetTransactionsResult() {
    }

    public GetTransactionsResult(List<Transaction> transactions) {
        this.setTransactions(transactions);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "GetTransactionsResult{" +
                "transactions=" + transactions +
                '}';
    }
}

package com.gmail.liliyayalovchenko.domains;

public class Warehouse {

    private int id;
    private int ingredId;
    private int amount;

    public Warehouse(int ingredId, int amount) {
        this.ingredId = ingredId;
        this.amount = amount;
    }

    public Warehouse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngredId() {
        return ingredId;
    }

    public void setIngredId(int ingredId) {
        this.ingredId = ingredId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", ingredId=" + ingredId +
                ", amount=" + amount +
                '}';
    }
}

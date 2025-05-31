package model;

import java.time.LocalDate;

public class ComparableData {
    private String id;
    private String address;
    private double price;
    private LocalDate transactionDate;
    private String propertyType; // e.g., "Rumah", "Tanah", "Apartemen"

    public ComparableData(String id, String address, double price, LocalDate transactionDate, String propertyType) {
        this.id = id;
        this.address = address;
        this.price = price;
        this.transactionDate = transactionDate;
        this.propertyType = propertyType;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    @Override
    public String toString() {
        return String.format("ID: %s, Alamat: %s, Harga: %.2f, Tgl Transaksi: %s, Tipe: %s",
                id, address, price, transactionDate.toString(), propertyType);
    }
}
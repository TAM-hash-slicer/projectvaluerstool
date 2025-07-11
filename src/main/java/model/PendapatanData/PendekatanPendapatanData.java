package model.PendapatanData;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data model specifically for the Income Approach (Pendekatan Pendapatan).
 * This class holds all inputs, expenses, and calculated values for this valuation method,
 * acting as a branch within the main {@link model.ValuationModel}.
 */
public class PendekatanPendapatanData {

    //region Property Details
    /**
     * A unique identifier for the type of project this data represents.
     * e.g., "INCOME_SIMPLIFIED", "MARKET_COMPARISON", etc.
     * This is crucial for filtering projects in the load dialog.
     */
    private String projectTypeIdentifier;

    /**
     * The user-defined name for this specific save file (e.g., "Draft 1", "Final Version").
     * This is distinct from the objectName.
     */
    private String saveName;
    /**
     * The name of the property being valued.
     * Corresponds to the 'Nama Objek' field.
     */
    private String objectName;

    /**
     * A Unix timestamp (milliseconds since epoch) of when this data was last saved.
     * Used for sorting projects by date.
     */
    private long lastSavedTimestamp;

    /**
     * The full address of the property.
     * Corresponds to the 'Alamat' field.
     */
    private String address;

    /**
     * The type of the property (e.g., Ruko, Hotel, Kos-kosan).
     * Corresponds to the 'Jenis Objek' field.
     */
    private String objectType;

    /**
     * The total land area in square meters.
     * Corresponds to the 'Luas Tanah' field.
     */
    private double landArea;

    /**
     * The total building area in square meters.
     * Corresponds to the 'Luas Bangunan' field.
     */
    private double buildingArea;

    /**
     * The year the property was built.
     * Corresponds to the 'Dibangun Sejak Tahun' field.
     */
    private int builtSinceYear;

    /**
     * The rental price of the property. Using BigDecimal for financial precision.
     * Corresponds to the 'Harga Sewa' field.
     */
    private BigDecimal rentalPrice;

    /**
     * The term of the rental agreement (e.g., "Per Tahun", "Per Bulan").
     * Corresponds to the 'Jangka Waktu Sewa' field.
     */
    private String rentalTerm;

    /**
     * Specifies who bears the costs (e.g., "Penyewa", "Pemilik").
     * Corresponds to the 'Biaya Ditanggung' field.
     */
    private String costBearer;

    /**
     * Any additional notes or remarks about the property valuation.
     * Corresponds to the 'Catatan Tambahan' field.
     */
    private String additionalNotes;
    //endregion

    //region Expense Containers
    /**
     * Holds all monthly expenses, typically borne by the tenant.
     */
    private TenantExpenses tenantExpenses;

    /**
     * Holds all annual expenses, typically borne by the owner.
     */
    private OwnerExpenses ownerExpenses;
    //endregion

    /**
     * Default constructor. Initializes the expense containers.
     */
    public PendekatanPendapatanData() {
        this.tenantExpenses = new TenantExpenses();
        this.ownerExpenses = new OwnerExpenses();
    }

    //region Getters and Setters
    public long getLastSavedTimestamp() {
        return lastSavedTimestamp;
    }

    public void setLastSavedTimestamp(long lastSavedTimestamp) {
        this.lastSavedTimestamp = lastSavedTimestamp;
    }
    public String getProjectTypeIdentifier() {
        return projectTypeIdentifier;
    }

    public void setProjectTypeIdentifier(String projectTypeIdentifier) {
        this.projectTypeIdentifier = projectTypeIdentifier;
    }
    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public double getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(double buildingArea) {
        this.buildingArea = buildingArea;
    }

    public int getBuiltSinceYear() {
        return builtSinceYear;
    }

    public void setBuiltSinceYear(int builtSinceYear) {
        this.builtSinceYear = builtSinceYear;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getRentalTerm() {
        return rentalTerm;
    }

    public void setRentalTerm(String rentalTerm) {
        this.rentalTerm = rentalTerm;
    }

    public String getCostBearer() {
        return costBearer;
    }

    public void setCostBearer(String costBearer) {
        this.costBearer = costBearer;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public TenantExpenses getTenantExpenses() {
        return tenantExpenses;
    }

    public void setTenantExpenses(TenantExpenses tenantExpenses) {
        this.tenantExpenses = tenantExpenses;
    }

    public OwnerExpenses getOwnerExpenses() {
        return ownerExpenses;
    }

    public void setOwnerExpenses(OwnerExpenses ownerExpenses) {
        this.ownerExpenses = ownerExpenses;
    }
    //endregion

    // =================================================================================
    // Nested Classes for Flexible Expense Handling
    // =================================================================================

    /**
     * Represents a single, named expense item with a specific monetary amount.
     * This class is the basic building block for all expense lists.
     */
    public static class ExpenseItem {
        private String name;
        private BigDecimal amount;

        /**
         * Default constructor.
         */
        public ExpenseItem() {
        }

        /**
         * Constructs an ExpenseItem with a name and an amount.
         *
         * @param name   The name of the expense (e.g., "Listrik", "PBB").
         * @param amount The monetary value of the expense.
         */
        public ExpenseItem(String name, BigDecimal amount) {
            this.name = name;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

    /**
     * Manages a list of monthly expenses, typically those borne by the tenant.
     * Corresponds to the 'Keterangan Biaya (Bulanan)' section.
     */
    public static class TenantExpenses {
        private final List<ExpenseItem> monthlyExpenses;

        /**
         * Default constructor. Initializes the list of monthly expenses.
         */
        public TenantExpenses() {
            this.monthlyExpenses = new ArrayList<>();
        }

        /**
         * Adds a new expense item to the list of monthly expenses.
         *
         * @param name   The name of the monthly expense.
         * @param amount The amount of the monthly expense.
         */
        public void addExpense(String name, BigDecimal amount) {
            this.monthlyExpenses.add(new ExpenseItem(name, amount));
        }

        /**
         * Calculates the sum of all monthly expenses using a Java Stream.
         *
         * @return A BigDecimal representing the total of all monthly expenses.
         */
        @JsonIgnore
        public BigDecimal getTotalMonthlyExpenses() {
            return monthlyExpenses.stream()
                    .map(ExpenseItem::getAmount)
                    .filter(Objects::nonNull) // Ensure no NullPointerExceptions
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public List<ExpenseItem> getMonthlyExpenses() {
            return monthlyExpenses;
        }
    }

    /**
     * Manages a list of annual expenses, typically those borne by the owner.
     * Corresponds to the 'Biaya Tahunan' section.
     */
    public static class OwnerExpenses {
        private final List<ExpenseItem> annualExpenses;

        /**
         * Default constructor. Initializes the list of annual expenses.
         */
        public OwnerExpenses() {
            this.annualExpenses = new ArrayList<>();
        }

        /**
         * Adds a new expense item to the list of annual expenses.
         *
         * @param name   The name of the annual expense.
         * @param amount The amount of the annual expense.
         */
        public void addExpense(String name, BigDecimal amount) {
            this.annualExpenses.add(new ExpenseItem(name, amount));
        }

        /**
         * Calculates the sum of all annual expenses using a Java Stream.
         *
         * @return A BigDecimal representing the total of all annual expenses.
         */
        @JsonIgnore
        public BigDecimal getTotalAnnualExpenses() {
            return annualExpenses.stream()
                    .map(ExpenseItem::getAmount)
                    .filter(Objects::nonNull) // Ensure no NullPointerExceptions
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public List<ExpenseItem> getAnnualExpenses() {
            return annualExpenses;
        }
    }
}
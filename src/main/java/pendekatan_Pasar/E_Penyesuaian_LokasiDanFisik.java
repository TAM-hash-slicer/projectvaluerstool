import java.util.Arrays; // For Arrays.toString
import java.text.DecimalFormat; // For formatting output

// Existing class definition
// package com.example.penilaian; // Your package
public class E_Penyesuaian_LokasiDanFisik {

    // --- Constants for Pairwise Comparison Scores ---
    // (As defined in the previous response)
    public static final int E_Peruntukan_score_for_vs_PosisiTanah = 5;
    public static final int E_PosisiTanah_score_for_vs_Peruntukan = 4;
    public static final int E_Peruntukan_score_for_vs_Aksesibilitas = 4;
    public static final int E_Aksesibilitas_score_for_vs_Peruntukan = 3;
    public static final int E_Peruntukan_score_for_vs_FasilitasRadius1km = 4;
    public static final int E_FasilitasRadius1km_score_for_vs_Peruntukan = 3;
    public static final int E_PosisiTanah_score_for_vs_Aksesibilitas = 3;
    public static final int E_Aksesibilitas_score_for_vs_PosisiTanah = 2;
    public static final int E_PosisiTanah_score_for_vs_FasilitasRadius1km = 3;
    public static final int E_FasilitasRadius1km_score_for_vs_PosisiTanah = 2;
    public static final int E_Aksesibilitas_score_for_vs_FasilitasRadius1km = 2;
    public static final int E_FasilitasRadius1km_score_for_vs_Aksesibilitas = 1;

    // Factor names in order
    private static final String[] FACTORS = {
            "Peruntukan",
            "Posisi Tanah",
            "Aksesibilitas",
            "Fasilitas (Radius 1km)"
    };
    private static final int NUM_FACTORS = FACTORS.length;
    private static final DecimalFormat df = new DecimalFormat("#.####"); // For printing doubles

    public static void performNormalizationSteps() {
        // --- Step 1: Construct the Pairwise Comparison Ratio Matrix ---
        System.out.println("Step 1: Pairwise Comparison Ratio Matrix");
        double[][] ratioMatrix = new double[NUM_FACTORS][NUM_FACTORS];

        // Peruntukan (0)
        ratioMatrix[0][0] = 1.0; // P/P
        ratioMatrix[0][1] = (double) E_Peruntukan_score_for_vs_PosisiTanah / E_PosisiTanah_score_for_vs_Peruntukan; // P/PT
        ratioMatrix[0][2] = (double) E_Peruntukan_score_for_vs_Aksesibilitas / E_Aksesibilitas_score_for_vs_Peruntukan; // P/A
        ratioMatrix[0][3] = (double) E_Peruntukan_score_for_vs_FasilitasRadius1km / E_FasilitasRadius1km_score_for_vs_Peruntukan; // P/F

        // Posisi Tanah (1)
        ratioMatrix[1][0] = (double) E_PosisiTanah_score_for_vs_Peruntukan / E_Peruntukan_score_for_vs_PosisiTanah; // PT/P
        ratioMatrix[1][1] = 1.0; // PT/PT
        ratioMatrix[1][2] = (double) E_PosisiTanah_score_for_vs_Aksesibilitas / E_Aksesibilitas_score_for_vs_PosisiTanah; // PT/A
        ratioMatrix[1][3] = (double) E_PosisiTanah_score_for_vs_FasilitasRadius1km / E_FasilitasRadius1km_score_for_vs_PosisiTanah; // PT/F

        // Aksesibilitas (2)
        ratioMatrix[2][0] = (double) E_Aksesibilitas_score_for_vs_Peruntukan / E_Peruntukan_score_for_vs_Aksesibilitas; // A/P
        ratioMatrix[2][1] = (double) E_Aksesibilitas_score_for_vs_PosisiTanah / E_PosisiTanah_score_for_vs_Aksesibilitas; // A/PT
        ratioMatrix[2][2] = 1.0; // A/A
        ratioMatrix[2][3] = (double) E_Aksesibilitas_score_for_vs_FasilitasRadius1km / E_FasilitasRadius1km_score_for_vs_Aksesibilitas; // A/F

        // Fasilitas (3)
        ratioMatrix[3][0] = (double) E_FasilitasRadius1km_score_for_vs_Peruntukan / E_Peruntukan_score_for_vs_FasilitasRadius1km; // F/P
        ratioMatrix[3][1] = (double) E_FasilitasRadius1km_score_for_vs_PosisiTanah / E_PosisiTanah_score_for_vs_FasilitasRadius1km; // F/PT
        ratioMatrix[3][2] = (double) E_FasilitasRadius1km_score_for_vs_Aksesibilitas / E_Aksesibilitas_score_for_vs_FasilitasRadius1km; // F/A
        ratioMatrix[3][3] = 1.0; // F/F

        printMatrix(ratioMatrix, "Ratio Matrix");
        System.out.println();

        // --- Step 2: Normalize the Ratio Matrix (Column Sum Normalization) ---
        System.out.println("Step 2: Normalized Matrix");
        double[][] normalizedMatrix = new double[NUM_FACTORS][NUM_FACTORS];
        double[] columnSums = new double[NUM_FACTORS];

        // Calculate column sums
        for (int j = 0; j < NUM_FACTORS; j++) {
            for (int i = 0; i < NUM_FACTORS; i++) {
                columnSums[j] += ratioMatrix[i][j];
            }
        }
        System.out.println("Column Sums: " + arrayToString(columnSums));

        // Normalize by dividing each element by its column sum
        for (int j = 0; j < NUM_FACTORS; j++) {
            if (columnSums[j] == 0) { // Avoid division by zero, though unlikely here with positive ratios
                System.err.println("Warning: Column sum for column " + j + " is zero. Cannot normalize.");
                continue;
            }
            for (int i = 0; i < NUM_FACTORS; i++) {
                normalizedMatrix[i][j] = ratioMatrix[i][j] / columnSums[j];
            }
        }
        printMatrix(normalizedMatrix, "Normalized Matrix");
        System.out.println();

        // --- Step 3: Calculate Average Weights (Priorities) ---
        System.out.println("Step 3: Factor Weights (Row Averages of Normalized Matrix)");
        double[] factorWeights = new double[NUM_FACTORS];
        for (int i = 0; i < NUM_FACTORS; i++) {
            double rowSum = 0;
            for (int j = 0; j < NUM_FACTORS; j++) {
                rowSum += normalizedMatrix[i][j];
            }
            factorWeights[i] = rowSum / NUM_FACTORS;
        }

        System.out.println("Calculated Factor Weights:");
        double totalWeight = 0;
        for (int i = 0; i < NUM_FACTORS; i++) {
            System.out.println("  - " + FACTORS[i] + ": " + df.format(factorWeights[i]));
            totalWeight += factorWeights[i];
        }
        System.out.println("Sum of weights: " + df.format(totalWeight)); // Should be close to 1.0
        System.out.println();
    }

    // Helper method to print a 2D matrix
    private static void printMatrix(double[][] matrix, String title) {
        System.out.println(title + ":");
        System.out.print("\t");
        for(int i=0; i< FACTORS.length; i++) {
            System.out.print(FACTORS[i].substring(0, Math.min(FACTORS[i].length(),4)) + "\t\t"); // Abbreviated headers
        }
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            System.out.print(FACTORS[i].substring(0, Math.min(FACTORS[i].length(),4)) + "\t");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(df.format(matrix[i][j]) + "\t\t");
            }
            System.out.println();
        }
    }

    // Helper method to print a 1D array
    private static String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(df.format(array[i]));
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }


    // Existing main method - now it can call performNormalizationSteps
    public static void main(String[] args) {
        System.out.println("Starting E_Penyesuaian_LokasiDanFisik script...\n");

        // (Original printouts of constants can be kept or removed)
        // System.out.println("Constants defined in E_Penyesuaian_LokasiDanFisik:");
        // ... (previous print statements for constants) ...

        performNormalizationSteps(); // Call the new method

        // Here, you would add any logic that uses these constants and weights
        // to calculate the actual "Penyesuaian Lokasi Dan Fisik" (Location and Physical Adjustment).
        // For example:
        // double adjustmentFactor = calculateAdjustment(factorWeights);
        // System.out.println("Final Calculated Adjustment Factor: " + df.format(adjustmentFactor));
    }

    // You could add other static methods here that use these constants and weights
    // to perform calculations related to "Penyesuaian Lokasi Dan Fisik".
}
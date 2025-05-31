package pendekatan_Pasar.approach;

import pendekatan_Pasar.model.Subjek;
import pendekatan_Pasar.model.Pembanding;

public class MarketApproach {
    public static double calculateAdjustment(Subjek subject, Pembanding comparable) {
        double adjustment = 0.0;

        double landDiff = subject.getLandArea() - comparable.getLandArea();
        adjustment += landDiff * 500000;

        double buildingDiff = subject.getBuildingArea() - comparable.getBuildingArea();
        adjustment += buildingDiff * 700000;

        return adjustment;
    }

    public static double getAdjustedPrice(Pembanding comp, double adjustment) {
        return comp.getTransactionPrice() + adjustment;
    }
}

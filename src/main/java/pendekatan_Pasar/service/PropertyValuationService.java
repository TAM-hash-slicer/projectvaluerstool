package pendekatan_Pasar.service;

import pendekatan_Pasar.approach.MarketApproach;
import pendekatan_Pasar.model.Pembanding;
import pendekatan_Pasar.model.Subjek;

import java.util.List;

public class PropertyValuationService {

    public double estimateMarketValue(Subjek subject, List<Pembanding> comparables) {
        double total = 0.0;

        for (Pembanding comp : comparables) {
            double adj = MarketApproach.calculateAdjustment(subject, comp);
            double adjPrice = MarketApproach.getAdjustedPrice(comp, adj);
            total += adjPrice;
        }

        return total / comparables.size();
    }
}

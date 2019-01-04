package info.nightscout.androidaps.plugins.PumpMedtronic.data.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;

import info.nightscout.androidaps.plugins.PumpCommon.utils.ByteUtil;
import info.nightscout.androidaps.plugins.PumpMedtronic.comm.history.pump.PumpHistoryEntryType;

/**
 * Created by andy on 11/3/18.
 */

public class DailyTotalsDTO {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTotalsDTO.class);

    // bg avg, bg low hi, number Bgs,
    // Sen Avg, Sen Lo/Hi, Sens Cal/Data = 0/0,
    // Insulin=19.8[8,9], Basal[10,11], Bolus[13,14], Carbs,
    // Bolus=1.7, Fodd, Corr, Manual=1.7,
    // Num bOlus=1, food/corr, Food+corr, manual bolus=1
    Double bgAvg;
    Double bgLow;
    Double bgHigh;
    Integer bgCount;

    Double sensorAvg;
    Double sensorMin;
    Double sensorMax;
    Integer sensorCalcCount;
    Integer sensorDataCount;

    Double insulinTotal;
    Double insulinBasal;
    Double insulinBolus;
    Double insulinCarbs;

    Double bolusTotal;
    Double bolusFood;
    Double bolusFoodAndCorr;
    Double bolusCorrection;
    Double bolusManual;

    Integer bolusCount;
    Integer bolusCountFoodOrCorr;
    // Integer bolusCountCorr;
    Integer bolusCountFoodAndCorr;
    Integer bolusCountManual;
    private Integer bolusCountFood;
    private Integer bolusCountCorr;


    public DailyTotalsDTO(PumpHistoryEntryType entryType, byte[] data) {
        switch (entryType) {

            case DailyTotals515:
                decodeDailyTotals515(data);
                break;

            case DailyTotals522:
                decodeDailyTotals522(data);
                break;
            case DailyTotals523:
                decodeDailyTotals523(data);
                break;

            default:
                break;
        }
    }


    private void testDecode(byte[] data) {

        // Daily

        byte body[] = data; // entry.getBody();
        System.out.println("Totoals 522");

        for (int i = 0; i < body.length - 2; i++) {

            int j = ByteUtil.toInt(body[i], body[i + 1]);
            int k = ByteUtil.toInt(body[i], body[i + 1], body[i + 2]);

            int j1 = ByteUtil.toInt(body[i + 1], body[i]);
            int k1 = ByteUtil.toInt(body[i + 2], body[i + 1], body[i]);

            System.out.println(String.format(
                "index: %d, number=%d, del/40=%.3f, del/10=%.3f, singular=%d, sing_hex=%s", i, j, j / 40.0d, j / 10.0d,
                body[i], ByteUtil.getHex(body[i])));

            System.out.println(String.format("     number[k,j1,k1]=%d / %d /%d, del/40=%.3f, del/40=%.3f, del/40=%.3f",
                k, j1, k1, k / 40.0d, j1 / 40.0d, k1 / 40.0d));

        }

    }


    private void decodeDailyTotals515(byte[] data) {
        LOG.debug("Can't decode DailyTotals515: Body={}", ByteUtil.getHex(data));

        testDecode(data);
    }


    private void decodeDailyTotals522(byte[] data) {

        this.insulinTotal = ByteUtil.toInt(data[8], data[9]) / 40.0d;
        this.insulinBasal = ByteUtil.toInt(data[10], data[11]) / 40.0d;
        this.insulinBolus = ByteUtil.toInt(data[13], data[14]) / 40.0d;

        this.bolusTotal = ByteUtil.toInt(data[17], data[18], data[19]) / 40.0d;
        this.bolusFood = ByteUtil.toInt(data[21], data[22]) / 40.0d;
        this.bolusCorrection = ByteUtil.toInt(data[23], data[24], data[25]) / 40.0d;
        this.bolusManual = ByteUtil.toInt(data[26], data[27], data[28]) / 40.0d;

        bolusCount = ByteUtil.asUINT8(data[30]);
        bolusCountFoodOrCorr = ByteUtil.asUINT8(data[31]);
        bolusCountFoodAndCorr = ByteUtil.asUINT8(data[32]);
        bolusCountManual = ByteUtil.asUINT8(data[33]);

        // bg avg, bg low hi, number Bgs,
        // Sen Avg, Sen Lo/Hi, Sens Cal/Data = 0/0,
        // Insulin=19.8[8,9], Basal[10,11], Bolus[13,14], Carbs,
        // Bolus=1.7[18,19], Fodd, Corr, Manual=1.7[27,28],
        // Num bOlus=1, food/corr, Food+corr, manual bolus=1

        LOG.debug("522: {}", toString());

    }


    private void decodeDailyTotals523(byte[] data) {

        this.insulinTotal = ByteUtil.toInt(data[8], data[9]) / 40.0d;
        this.insulinBasal = ByteUtil.toInt(data[10], data[11]) / 40.0d;
        this.insulinBolus = ByteUtil.toInt(data[13], data[14]) / 40.0d;
        this.insulinCarbs = ByteUtil.toInt(data[16], data[17]) * 1.0d;

        this.bolusFood = ByteUtil.toInt(data[18], data[19]) / 40.0d;
        this.bolusCorrection = ByteUtil.toInt(data[20], data[21]) / 40.0d;
        this.bolusFoodAndCorr = ByteUtil.toInt(data[22], data[23]) / 40.0d;
        this.bolusManual = ByteUtil.toInt(data[24], data[25]) / 40.0d;

        this.bolusCountFood = ByteUtil.asUINT8(data[26]);
        this.bolusCountCorr = ByteUtil.asUINT8(data[27]);
        this.bolusCountFoodAndCorr = ByteUtil.asUINT8(data[28]);
        this.bolusCountManual = ByteUtil.asUINT8(data[29]); // +

        // Delivery Stats: Carbs=11, Total Insulin=3.850, Basal=2.000
        // Delivery Stats: Basal 52,Bolus 1.850, Bolus=48%o
        // Delivery Stats: Food only=0.9, Food only#=1, Corr only = 0.0
        // Delivery Stats: #Corr_only=0,Food+Corr=0.000, #Food+Corr=0
        // Delivery Stats: Manual = 0.95, #Manual=5

        LOG.debug("523: {}", toString());

    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
            .add("bgAvg", bgAvg) //
            .add("bgLow", bgLow) //
            .add("bgHigh", bgHigh) //
            .add("bgCount", bgCount) //
            .add("sensorAvg", sensorAvg) //
            .add("sensorMin", sensorMin) //
            .add("sensorMax", sensorMax) //
            .add("sensorCalcCount", sensorCalcCount) //
            .add("sensorDataCount", sensorDataCount) //
            .add("insulinTotal", insulinTotal) //
            .add("insulinBasal", insulinBasal) //
            .add("insulinBolus", insulinBolus) //
            .add("insulinCarbs", insulinCarbs) //
            .add("bolusTotal", bolusTotal) //
            .add("bolusFood", bolusFood) //
            .add("bolusCorrection", bolusCorrection) //
            .add("bolusManual", bolusManual) //
            .add("bolusCount", bolusCount) //
            .add("bolusCountFoodOrCorr", bolusCountFoodOrCorr) //
            .add("bolusCountFoodAndCorr", bolusCountFoodAndCorr) //
            .add("bolusCountFood", bolusCountFood) //
            .add("bolusCountCorr", bolusCountCorr) //
            .add("bolusCountManual", bolusCountManual) //
            .omitNullValues() //
            .toString();
    }
}
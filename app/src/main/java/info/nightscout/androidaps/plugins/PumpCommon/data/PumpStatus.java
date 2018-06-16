package info.nightscout.androidaps.plugins.PumpCommon.data;

import java.util.Date;

import info.nightscout.androidaps.data.ProfileStore;
import info.nightscout.androidaps.interfaces.PumpDescription;
import info.nightscout.androidaps.plugins.PumpCommon.defs.PumpStatusType;
import info.nightscout.androidaps.plugins.PumpCommon.defs.PumpType;

/**
 * Created by andy on 4/28/18.
 */

public abstract class PumpStatus {

    // connection
    public Date lastDataTime;
    public long lastConnection = 0L;

    // last bolus
    public Date lastBolusTime;
    public Double lastBolusAmount;

    // other pump settings
    public String activeProfileName = "0";
    public double reservoirRemainingUnits = 0d;
    public String reservoirFullUnits = "???";
    public int batteryRemaining = 0; // percent, so 0-100

    // iob
    public String iob = null;

    // TDD
    public Double dailyTotalUnits;
    public String maxDailyTotalUnits;


    protected PumpDescription pumpDescription;
    public boolean validBasalRateProfileSelectedOnPump = true;

    public PumpType pumpType = PumpType.GenericAAPS;


    public ProfileStore profileStore;
    public String units; // Constants.MGDL or Constants.MMOL
    public PumpStatusType pumpStatusType = PumpStatusType.Running;

    // TODO maybe not needed anymore in 2.0
    public Double constraintBasalRateAbsolute;
    public Integer constraintBasalRatePercent;
    public Double constraintBolus;
    public Integer constraintCarbs;
    public Double constraintMaxIob;
    public Double[] basalsByHour;


    public PumpStatus(PumpDescription pumpDescription) {
        this.pumpDescription = pumpDescription;

        this.initSettings();
    }


    public abstract void initSettings();


    public void setLastCommunicationToNow() {
        this.lastDataTime = new Date();
        this.lastConnection = System.currentTimeMillis();
    }


    public abstract String getErrorInfo();


    public abstract void refreshConfiguration();


    public PumpType getPumpType() {
        return pumpType;
    }

    public void setPumpType(PumpType pumpType) {
        this.pumpType = pumpType;
    }

    // FIXME cleanup this is from RT2

    public long getTimeIndex() {
        return (long) Math.ceil(time.getTime() / 60000d);
    }

    public void setTimeIndex(long timeIndex) {
        this.timeIndex = timeIndex;
    }

    public long timeIndex;

    public Date time;

    public double remainUnits = 0;
    public int remainBattery = 0;

    public double currentBasal = 0;

    public int tempBasalInProgress = 0;
    public int tempBasalRatio = 0;
    public int tempBasalRemainMin = 0;
    public Date tempBasalStart;

    public Date last_bolus_time;
    public double last_bolus_amount = 0;


}
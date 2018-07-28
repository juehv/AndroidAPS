package info.nightscout.androidaps.plugins.PumpDanaR.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;
import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.interfaces.Constraint;

public class MsgBolusStart extends MessageBase {
    private static Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public static int errorCode;

    public MsgBolusStart() {
        SetCommand(0x0102);
    }

    public MsgBolusStart(double amount) {
        this();

        // HARDCODED LIMIT
        amount = MainApp.getConstraintChecker().applyBolusConstraints(new Constraint<>(amount)).value();

        AddParamInt((int) (amount * 100));

        if (Config.logPumpComm)
            log.debug("Bolus start : " + amount);
    }

    @Override
    public void handleMessage(byte[] bytes) {
        errorCode = intFromBuff(bytes, 0, 1);
        if (errorCode != 2) {
            failed = true;
            if (Config.logPumpComm)
                log.debug("Messsage response: " + errorCode + " FAILED!!");
        } else {
            if (Config.logPumpComm)
                log.debug("Messsage response: " + errorCode + " OK");
        }
    }
}

package info.nightscout.androidaps.plugins.PumpDanaR.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;

public class MsgSetActivateBasalProfile extends MessageBase {
    private static Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public MsgSetActivateBasalProfile() {
        SetCommand(0x330C);
        if (Config.logPumpComm)
            log.debug("New message");
    }

    // index 0-3
    public MsgSetActivateBasalProfile(byte index) {
        this();
        AddParamByte(index);
        if (Config.logPumpComm)
            log.debug("Activate basal profile: " + index);
    }

    @Override
    public void handleMessage(byte[] bytes) {
        int result = intFromBuff(bytes, 0, 1);
        if (result != 1) {
            failed = true;
            if (Config.logPumpComm)
                log.debug("Activate basal profile result: " + result + " FAILED!!!");
        } else {
            if (Config.logPumpComm)
                log.debug("Activate basal profile result: " + result);
        }
    }
}

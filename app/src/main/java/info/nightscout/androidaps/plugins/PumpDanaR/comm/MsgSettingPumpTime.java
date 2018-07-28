package info.nightscout.androidaps.plugins.PumpDanaR.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;
import info.nightscout.androidaps.plugins.PumpDanaR.DanaRPump;

public class MsgSettingPumpTime extends MessageBase {
    private static Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public MsgSettingPumpTime() {
        SetCommand(0x320A);
        if (Config.logPumpComm)
            log.debug("New message");
    }

    public void handleMessage(byte[] bytes) {
        Date time =
                new Date(
                        100 + intFromBuff(bytes, 5, 1),
                        intFromBuff(bytes, 4, 1) - 1,
                        intFromBuff(bytes, 3, 1),
                        intFromBuff(bytes, 2, 1),
                        intFromBuff(bytes, 1, 1),
                        intFromBuff(bytes, 0, 1)
                );

        if (Config.logPumpComm)
            log.debug("Pump time: " + time + " Phone time: " + new Date());

        DanaRPump.getInstance().pumpTime = time;
    }
}

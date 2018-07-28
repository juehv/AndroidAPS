package info.nightscout.androidaps.plugins.PumpDanaRv2.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;
import info.nightscout.androidaps.plugins.PumpDanaR.DanaRPump;
import info.nightscout.androidaps.plugins.PumpDanaR.comm.MessageBase;

public class MsgStatusAPS_v2 extends MessageBase {
    private Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public MsgStatusAPS_v2() {
        SetCommand(0xE001);
        if (Config.logPumpComm)
            log.debug("New message");
    }

    public void handleMessage(byte[] bytes) {
        double iob = intFromBuff(bytes, 0, 2) / 100d;
        double deliveredSoFar = intFromBuff(bytes, 2, 2) / 100d;

        DanaRPump pump = DanaRPump.getInstance();
        pump.iob = iob;

        if (Config.logPumpComm) {
            log.debug("Delivered so far: " + deliveredSoFar);
            log.debug("Current pump IOB: " + iob);
        }
    }

}

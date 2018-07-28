package info.nightscout.androidaps.plugins.PumpDanaR.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;

/**
 * Created by mike on 20.07.2016.
 */
public class MsgHistoryRefill extends MsgHistoryAll {
    private static Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public MsgHistoryRefill() {
        SetCommand(0x3108);
        if (Config.logPumpComm)
            log.debug("New message");
   }
    // Handle message taken from MsgHistoryAll
}

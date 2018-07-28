package info.nightscout.androidaps.plugins.PumpDanaRS.comm;

import com.cozmo.danar.util.BleCommandUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;
import info.nightscout.androidaps.plugins.PumpDanaR.DanaRPump;

public class DanaRS_Packet_General_Get_Today_Delivery_Total extends DanaRS_Packet {
    private Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public DanaRS_Packet_General_Get_Today_Delivery_Total() {
        super();
        opCode = BleCommandUtil.DANAR_PACKET__OPCODE_REVIEW__GET_TODAY_DELIVERY_TOTAL;
        if (Config.logPumpComm)
            log.debug("New message");
    }

    @Override
    public void handleMessage(byte[] data) {
        DanaRPump pump = DanaRPump.getInstance();

        int dataIndex = DATA_START;
        int dataSize = 2;
        pump.dailyTotalUnits = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

        dataIndex += dataSize;
        dataSize = 2;
        pump.dailyTotalBasalUnits = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

        dataIndex += dataSize;
        dataSize = 2;
        pump.dailyTotalBolusUnits = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

        if (Config.logPumpComm) {
            log.debug("Daily total: " + pump.dailyTotalUnits + " U");
            log.debug("Daily total bolus: " + pump.dailyTotalBolusUnits + " U");
            log.debug("Daily total basal: " + pump.dailyTotalBasalUnits + " U");
        }
    }

    @Override
    public String getFriendlyName() {
        return "REVIEW__GET_TODAY_DELIVERY_TOTAL";
    }
}

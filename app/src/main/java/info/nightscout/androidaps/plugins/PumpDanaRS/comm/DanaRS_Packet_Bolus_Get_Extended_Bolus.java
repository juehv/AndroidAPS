package info.nightscout.androidaps.plugins.PumpDanaRS.comm;

import com.cozmo.danar.util.BleCommandUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.Constants;
import info.nightscout.androidaps.plugins.PumpDanaR.DanaRPump;

public class DanaRS_Packet_Bolus_Get_Extended_Bolus extends DanaRS_Packet {
    private Logger log = LoggerFactory.getLogger(Constants.PUMPCOMM);

    public DanaRS_Packet_Bolus_Get_Extended_Bolus() {
        super();
        opCode = BleCommandUtil.DANAR_PACKET__OPCODE_BOLUS__GET_EXTENDED_BOLUS;
        if (Config.logPumpComm)
            log.debug("New message");
    }

    @Override
    public void handleMessage(byte[] data) {
        DanaRPump pump = DanaRPump.getInstance();

        int dataIndex = DATA_START;
        int dataSize = 1;
        int error = byteArrayToInt(getBytes(data, dataIndex, dataSize));

        dataIndex += dataSize;
        dataSize = 2;
        pump.extendedBolusAbsoluteRate = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

        dataIndex += dataSize;
        dataSize = 2;
        pump.maxBolus = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

        dataIndex += dataSize;
        dataSize = 1;
        pump.bolusStep = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

        if (Config.logPumpComm) {
            log.debug("Result: " + error);
            log.debug("Extended bolus running: " + pump.extendedBolusAbsoluteRate + " U/h");
            log.debug("Max bolus: " + pump.maxBolus + " U");
            log.debug("Bolus step: " + pump.bolusStep + " U");
        }
    }

    @Override
    public String getFriendlyName() {
        return "BOLUS__GET_EXTENDED_BOLUS";
    }
}

package com.intuit.wasabi.repository;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.UUID;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.utils.Bytes;
import com.intuit.wasabi.assignmentobjects.User;
import com.intuit.wasabi.experimentobjects.Experiment;

public class ExperimentIDCodec extends TypeCodec<Experiment.ID> {

    private final Charset charset = Charset.forName("UTF-8");

    public ExperimentIDCodec() {
        super(DataType.uuid(), Experiment.ID.class);
    }

    @Override
    public Experiment.ID parse(String value) {
        if (value == null )
            return null;

        return Experiment.ID.valueOf(value);
    }

    @Override
    public String format(Experiment.ID value) {
        if (value == null)
            return "NULL";
        return value.toString();
    }

//    @Override
//    public Experiment.ID parse(UUID value) {
//        if (value == null )
//            return null;
//
//        return Experiment.ID.valueOf(value);
//    }
//
//    @Override
//    public UUID format(Experiment.ID value) {
//        if (value == null)
//            return null;
//        return value.getRawID();
//    }

    @Override
    public ByteBuffer serialize(Experiment.ID value, ProtocolVersion protocolVersion) {
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(0, value.getRawID().getMostSignificantBits());
        bb.putLong(8, value.getRawID().getLeastSignificantBits());
        return bb;
    }

    @Override
    public Experiment.ID deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) {
        if (bytes == null || bytes.remaining() == 0)
            return null;
        return Experiment.ID.valueOf(bytes.array());
    }
}
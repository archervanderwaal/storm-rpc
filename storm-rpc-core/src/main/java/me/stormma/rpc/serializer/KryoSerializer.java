package me.stormma.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class KryoSerializer implements Serializer {

    // 1024
    protected static final int SIZE = 1 << 10;

    private KryoSerializer() {}

    private static volatile KryoSerializer instance;

    public static KryoSerializer getSerializer() {
        if (instance == null) {
            synchronized (KryoSerializer.class) {
                if (instance == null) {
                    instance = new KryoSerializer();
                }
            }
        }
        return instance;
    }

    public byte[] serialize(Object data) {
        Kryo kryoSerializer = new Kryo();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(SIZE);
        Output output = new Output(byteArrayOutputStream);
        kryoSerializer.writeClassAndObject(output, data);
        output.close();
        return byteArrayOutputStream.toByteArray();
    }

    public <T> T deserialize(byte[] dataBytes) {
        Kryo kryoSerializer = new Kryo();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataBytes);
        Input input = new Input(byteArrayInputStream);
        input.close();
        return (T) kryoSerializer.readClassAndObject(input);
    }
}

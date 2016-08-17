package tk.zhangh.java.io.stream;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * DataInput/OutputStream,ObjectInput/OutputStream读写对象测试
 * Created by ZhangHao on 2016/8/17.
 */
public class DataIOTest {
    private double pi = 3.14;
    private long num = 123456;
    private String str = "It's a String";

    private byte[] testWriteBasic() throws Exception {
        byte[] dest;
        try(ByteArrayOutputStream arrayOutStream = new ByteArrayOutputStream();
                DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(arrayOutStream))) {
            outStream.writeDouble(pi);
            outStream.writeLong(num);
            outStream.writeUTF(str);
            outStream.flush();
            dest = arrayOutStream.toByteArray();
        }catch (IOException e) {
            throw e;
        }
        return dest;
    }

    private void testReadBasic(byte[] src) throws Exception {
        try(DataInputStream inStream = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(src)))) {
            double pi = inStream.readDouble();
            long num = inStream.readLong();
            String str = inStream.readUTF();
            Assert.assertTrue(this.pi == pi);
            Assert.assertTrue(this.num == num);
            Assert.assertEquals(this.str, str);
        }catch (IOException e) {
            throw e;
        }
    }

    /**
     * 测试基本类型的序列化反序列化
     * @throws Exception
     */
    @Test
    public void testReadWriteBasic() throws Exception {
        byte[] src = testWriteBasic();
        testReadBasic(src);
    }

    private byte[] testWriteReference() throws Exception {
        byte[] dest;
        try(ByteArrayOutputStream arrayOutStream = new ByteArrayOutputStream();
            ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(arrayOutStream))) {
            outStream.writeObject(new Date());
            outStream.flush();
            dest = arrayOutStream.toByteArray();
        }catch (IOException e) {
            throw e;
        }
        return dest;
    }

    private void testReadReference(byte[] src) throws Exception {
        try(ObjectInputStream inStream = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(src)))) {
            Object object = inStream.readObject();
            Assert.assertTrue(object instanceof Date);
        }catch (IOException e) {
            throw e;
        }
    }

    /**
     * 测试对象的序列化与反序列化
     * @throws Exception
     */
    @Test
    public void testReadWriteReference() throws Exception {
        byte[] src = testWriteReference();
        testReadReference(src);
    }
}

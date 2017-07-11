package tk.zhangh.toolkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.rsa.RSAPrivateCrtKeyImpl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 签名工具
 * 使用私钥签名,使用证书验证数据
 * Created by ZhangHao on 2017/3/18.
 */
public abstract class SignatureUtils {
    private static Logger logger = LoggerFactory.getLogger(SignatureUtils.class);

    /**
     * 获取私钥签名服务
     *
     * @param path 私钥文件路径
     * @return 私钥签名服务, 提供私钥签名
     */
    public static SignatureUtils getSignInstance(String path) {
        return new SignService(path);
    }

    /**
     * 获取公钥验证数据服务
     *
     * @param path 证书文件路径
     * @return 验证结果
     */
    public static SignatureUtils getVerifyInstance(String path) {
        return new Verify(path);
    }

    /**
     * 使用私钥签名数据
     *
     * @param data 等签名数据
     * @return 签名后的字节数组
     * @throws SignatureException
     */
    public abstract byte[] signData(String data) throws SignatureException;

    /**
     * 使用公钥验证签名
     *
     * @param sign 签名
     * @param data 待验证数据
     * @return 验证是否通过
     * @throws SignatureException
     */
    public abstract boolean verifyData(byte[] sign, String data) throws SignatureException;

    /**
     * 私钥签名服务
     */
    private static class SignService extends SignatureUtils {
        private Signature signature;

        /**
         * 创建私钥签名服务实例
         * 使用openssl产生的RSA密钥(openssl生成密钥不能直接使用要做使用Java的格式转换)
         *
         * @param path 私钥路径
         */
        private SignService(String path) {
            File privateKeyFile = new File(path);
            byte[] privateKeyBytes;
            try (DataInputStream dataInStream = new DataInputStream(new FileInputStream(privateKeyFile))) {
                privateKeyBytes = new byte[(int) privateKeyFile.length()];
                dataInStream.readFully(privateKeyBytes);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                RSAPrivateCrtKeyImpl privateKey = (RSAPrivateCrtKeyImpl) keyFactory.generatePrivate(keySpec);
                signature = Signature.getInstance("SHA1withRSA");
                signature.initSign(privateKey);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Init SignService error, key path:{}", path, e);
                throw new RuntimeException("Init SignService error", e);
            }
        }

        @Override
        public byte[] signData(String data) throws SignatureException {
            signature.update(data.getBytes());
            return signature.sign();
        }

        @Override
        public boolean verifyData(byte[] sign, String data) {
            throw new UnsupportedOperationException("SignService unsupported method:verifyData");
        }
    }

    /**
     * 证书验证服务
     */
    private static class Verify extends SignatureUtils {
        private Signature signature;

        /**
         * 创建证书验证服务实例
         * 证书为使用openssl产生的RSA密钥产生的对应证书
         *
         * @param path 证书路径
         */
        private Verify(String path) {
            try (FileInputStream in = new FileInputStream(path)) {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
                X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(in);
                signature = Signature.getInstance("SHA1withRSA");
                signature.initVerify(certificate);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Init Verify error, key path:{}", path, e);
                throw new RuntimeException("Init Verify error", e);
            }
        }

        @Override
        public boolean verifyData(byte[] sign, String data) throws SignatureException {
            signature.update(data.getBytes());
            return signature.verify(sign);
        }

        @Override
        public byte[] signData(String data) throws SignatureException {
            throw new UnsupportedOperationException("Verify unsupported method:signData");
        }
    }
}

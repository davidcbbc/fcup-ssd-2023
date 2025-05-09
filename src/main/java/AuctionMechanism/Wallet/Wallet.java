package AuctionMechanism.Wallet;

import AuctionMechanism.TransactionTypes.Transaction;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.KeyStore.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import java.security.cert.X509Certificate;

public class Wallet implements Serializable{
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private float initial_balance;

    public Wallet() {
        generateKeyPair();
        // All wallets initially have a balance of 100
        this.initial_balance = 100;
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public float getInitialBalance() {
        return initial_balance;
    }

    public void setInitialBalance(float balance) {
        this.initial_balance = balance;
    }

    public void saveToFile(String filePath, String passphrase) throws KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException, UnrecoverableEntryException, OperatorCreationException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider()); // Register Bouncy Castle provider

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);

        X500Principal issuer = new X500Principal("CN=SelfSigned");
        X500Principal subject = new X500Principal("CN=SelfSigned");
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
        Date notBefore = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date notAfter = Date.from(LocalDate.now().plusYears(10).atStartOfDay(ZoneId.systemDefault()).toInstant());

        X509Certificate cert = generateSelfSignedCertificate(issuer, subject, serial, notBefore, notAfter, publicKey, privateKey);

        ProtectionParameter protParam = new PasswordProtection(passphrase.toCharArray());
        PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry(privateKey, new java.security.cert.Certificate[]{cert});
        keyStore.setEntry("privateKey", privateKeyEntry, protParam);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            keyStore.store(fos, passphrase.toCharArray());
        }
    }


    private X509Certificate generateSelfSignedCertificate(X500Principal issuer, X500Principal subject, BigInteger serial,
                                                          Date notBefore, Date notAfter, PublicKey publicKey, PrivateKey privateKey)
            throws OperatorCreationException, CertificateException, NoSuchAlgorithmException, IOException, GeneralSecurityException {
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer, serial, notBefore, notAfter, subject, publicKey);

        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner contentSigner = builder.build(privateKey);

        X509CertificateHolder certHolder = certBuilder.build(contentSigner);
        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }




    public static Wallet loadFromFile(String filePath, String passphrase)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(filePath)) {
            keyStore.load(fis, passphrase.toCharArray());
        }

        ProtectionParameter protParam = new PasswordProtection(passphrase.toCharArray());
        PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) keyStore.getEntry("privateKey", protParam);

        Wallet wallet = new Wallet();
        wallet.privateKey = privateKeyEntry.getPrivateKey();
        wallet.publicKey = privateKeyEntry.getCertificate().getPublicKey();
        return wallet;
    }

    public byte[] signTransaction(byte[] hash) {
        byte[] signatureBytes = null;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(hash);
            signatureBytes = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

        return signatureBytes;
    }
}


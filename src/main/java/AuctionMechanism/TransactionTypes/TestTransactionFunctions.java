package AuctionMechanism.TransactionTypes;

import AuctionMechanism.Wallet.Wallet;
import AuctionMechanism.util.Item;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class TestTransactionFunctions {
    public static void main(String[] args) throws UnrecoverableEntryException, CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException {
        Wallet wallet = Wallet.loadFromFile("/Users/fabiocorreia/Desktop/MSI/SSD/wallet.p12", "pwd123");


        System.out.println(wallet.getPublicKey() + "\n" + wallet.getPrivateKey());
        Wallet fake_wallet = new Wallet();
        Item item = new Item("item1", "description1");
        CreateAuctionTransaction transaction = new CreateAuctionTransaction(wallet.getPublicKey(), item,10);

        byte[] signature = wallet.signTransaction(transaction.getHash());

        transaction.setSignature(signature);

        Boolean response = transaction.verifySignature();
        System.out.println(response);
    }
}

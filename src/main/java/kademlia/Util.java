package kademlia;

import AuctionMechanism.Wallet.Wallet;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class Util {

    static int port=8080;
    private Util() {
        port=8080;
    }

    public static KademliaNode createNewNode(){
        SecureRandom random = new SecureRandom();
        BigInteger id = new BigInteger(159, random).setBit(159);
        KademliaNode kademliaNode =  new KademliaNode("localhost",id,port);
        port=port+1;
        return kademliaNode;
    }

    public static PublicKey stringToPublicKey(String pubKeyString){
        pubKeyString = pubKeyString.replace("\n", ""); // Remove any newline characters

        byte[] decodedKey = Base64.getDecoder().decode(pubKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception e){
            System.out.println("[-] Error parsing String public key to PublicKey");
        }
        return null;
    }
}

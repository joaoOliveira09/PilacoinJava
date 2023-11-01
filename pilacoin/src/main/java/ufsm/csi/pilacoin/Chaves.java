package ufsm.csi.pilacoin;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.Data;


@Data
public class Chaves {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    //     keyPairGenerator.initialize(1024);
    //     publicKey = keyPairGenerator.generateKeyPair().getPublic();
    //     privateKey = keyPairGenerator.generateKeyPair().getPrivate();

        public Chaves() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }
    
}

package ufsm.csi.pilacoin.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import java.io.*;
import java.security.*;
import lombok.Data;


@Data
public class Chaves {
    private PublicKey publicKey;
    private PrivateKey privateKey;

        public Chaves() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    //Futuramente Salvar essas chaves em um arquivo

    //  public void salvaChavesArquivo(String publicKeyFile, String privateKeyFile) throws IOException {
    //     try (ObjectOutputStream publicKeyStream = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
    //          ObjectOutputStream privateKeyStream = new ObjectOutputStream(new FileOutputStream(privateKeyFile))) {
    //         publicKeyStream.writeObject(publicKey);
    //         privateKeyStream.writeObject(privateKey);
    //     }
    // }

    // public void getChavesArquivo(String publicKeyFile, String privateKeyFile) throws IOException, ClassNotFoundException {
    //     try (ObjectInputStream publicKeyStream = new ObjectInputStream(new FileInputStream(publicKeyFile));
    //          ObjectInputStream privateKeyStream = new ObjectInputStream(new FileInputStream(privateKeyFile))) {
    //         publicKey = (PublicKey) publicKeyStream.readObject();
    //         privateKey = (PrivateKey) privateKeyStream.readObject();
    //     }
    // }
    
}

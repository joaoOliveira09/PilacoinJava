package ufsm.csi.pilacoin.utils;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Chaves {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public Chaves() throws Exception {
        carregaChavesArquivo("publicKey.dat", "privateKey.dat");
    }

    public void carregaChavesArquivo(String publicKeyFile, String privateKeyFile) {
        try (ObjectInputStream publicKeyStream = new ObjectInputStream(new FileInputStream(publicKeyFile));
             ObjectInputStream privateKeyStream = new ObjectInputStream(new FileInputStream(privateKeyFile))) {
            this.publicKey = (PublicKey) publicKeyStream.readObject();
            this.privateKey = (PrivateKey) privateKeyStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}


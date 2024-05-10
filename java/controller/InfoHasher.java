package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class InfoHasher {

    public static String hashInfo(String info) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(info.getBytes());
        return Base64.getEncoder().encodeToString(md.digest());
    }
}

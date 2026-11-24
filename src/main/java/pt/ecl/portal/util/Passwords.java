package pt.ecl.portal.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.util.Base64;

public final class Passwords {
    private static final int ITERACOES = 210_000;

    private Passwords() { }

    public static String gerarHash(String password) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return ITERACOES + ":" + b64(salt) + ":" + b64(pbkdf2(password.toCharArray(), salt, ITERACOES));
    }

    public static boolean verificar(String password, String guardado) {
        String[] p = guardado.split(":");
        if (p.length != 3) return false;
        byte[] salt = Base64.getDecoder().decode(p[1]);
        byte[] esperado = Base64.getDecoder().decode(p[2]);
        byte[] obtido = pbkdf2(password.toCharArray(), salt, Integer.parseInt(p[0]));
        return MessageDigest.isEqual(esperado, obtido);
    }

    private static byte[] pbkdf2(char[] pw, byte[] salt, int it) {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(new PBEKeySpec(pw, salt, it, 256)).getEncoded();
        } catch (GeneralSecurityException e) { throw new IllegalStateException(e); }
    }

    private static String b64(byte[] b) { return Base64.getEncoder().encodeToString(b); }


    public static void main(String[] args) { System.out.println(gerarHash(args[0])); }
}

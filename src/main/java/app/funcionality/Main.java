import java.math.BigInteger;
public class Main{

public static byte[] encodeSharedSecret(final BigInteger sharedSecret, final int primeSizeBits) {

    // TODO assignment add additional tests on input

    final int sharedSecretSize = (primeSizeBits + Byte.SIZE - 1) / Byte.SIZE;

    final byte[] signedSharedSecretEncoding = sharedSecret.toByteArray();
    final int signedSharedSecretEncodingLength = signedSharedSecretEncoding.length;

    if (signedSharedSecretEncodingLength == sharedSecretSize) {
        return signedSharedSecretEncoding;
    }

    if (signedSharedSecretEncodingLength == sharedSecretSize + 1) {
        final byte[] sharedSecretEncoding = new byte[sharedSecretSize];
        System.arraycopy(signedSharedSecretEncoding, 1, sharedSecretEncoding, 0, sharedSecretSize);
        return sharedSecretEncoding;
    }

    if (signedSharedSecretEncodingLength < sharedSecretSize) {
        final byte[] sharedSecretEncoding = new byte[sharedSecretSize];
        System.arraycopy(signedSharedSecretEncoding, 0,
                sharedSecretEncoding, sharedSecretSize - signedSharedSecretEncodingLength, signedSharedSecretEncodingLength);
        return sharedSecretEncoding;
    }

    throw new IllegalArgumentException("Shared secret is too big");
}
public static void main(String[] args) {
    long value = 1000000000; 
    encodeSharedSecret(sharedSecret, primeSizeBits);
    
}
}

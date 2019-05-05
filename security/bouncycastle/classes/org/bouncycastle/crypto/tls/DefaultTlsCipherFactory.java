package org.bouncycastle.crypto.tls;

import java.io.IOException;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;

public class DefaultTlsCipherFactory
    extends AbstractTlsCipherFactory
{
    public TlsCipher createCipher(TlsContext context, int encryptionAlgorithm, int macAlgorithm)
        throws IOException
    {
        switch (encryptionAlgorithm)
        {
        case EncryptionAlgorithm.AES_128_CBC:
            return createAESCipher(context, 16, macAlgorithm);
        case EncryptionAlgorithm.AES_256_CBC:
            return createAESCipher(context, 32, macAlgorithm);
        case EncryptionAlgorithm.NULL:
            return createNullCipher(context, macAlgorithm);
        default:
            throw new TlsFatalAlert(AlertDescription.internal_error);
        }
    }

    protected TlsBlockCipher createAESCipher(TlsContext context, int cipherKeySize, int macAlgorithm)
        throws IOException
    {
        return new TlsBlockCipher(context, createAESBlockCipher(), createAESBlockCipher(),
            createHMACDigest(macAlgorithm), createHMACDigest(macAlgorithm), cipherKeySize);
    }

    protected TlsNullCipher createNullCipher(TlsContext context, int macAlgorithm)
        throws IOException
    {
        return new TlsNullCipher(context, createHMACDigest(macAlgorithm),
            createHMACDigest(macAlgorithm));
    }

    protected BlockCipher createAESEngine()
    {
        return new AESEngine();
    }

    protected BlockCipher createAESBlockCipher()
    {
        return new CBCBlockCipher(createAESEngine());
    }

    protected Digest createHMACDigest(int macAlgorithm) throws IOException
    {
        switch (macAlgorithm)
        {
        case MACAlgorithm._null:
            return null;
        case MACAlgorithm.hmac_md5:
            return TlsUtils.createHash(HashAlgorithm.md5);
        case MACAlgorithm.hmac_sha1:
            return TlsUtils.createHash(HashAlgorithm.sha1);
        case MACAlgorithm.hmac_sha256:
            return TlsUtils.createHash(HashAlgorithm.sha256);
        default:
            throw new TlsFatalAlert(AlertDescription.internal_error);
        }
    }
}

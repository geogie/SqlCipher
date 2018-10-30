package com.georgeren.sqlcipher.aes;

import java.security.Provider;

/**
 * Created by George.ren on 2018/10/30.
 * Email:1063658094@qq.com
 * Describe:
 */
public class CryptoProvider extends Provider {
    /**
     * Creates a Provider and puts parameters
     */
    public CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }
}

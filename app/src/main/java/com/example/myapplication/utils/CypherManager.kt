package com.banquemisr.bm.tokenization.data.utils


import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class CypherManager {
    private val keyGen: KeyPairGenerator =
        KeyPairGenerator.getInstance(DEFAULT_ENCRYPTION_ALGORITHM)
    private val keyPair: KeyPair

    init {
        keyGen.initialize(DEFAULT_ENCRYPTION_KEY_LENGTH)
        keyPair = keyGen.generateKeyPair()
    }

    companion object {
        private const val DEFAULT_ENCRYPTION_ALGORITHM = "RSA"
        private const val DEFAULT_ENCRYPTION_KEY_LENGTH = 1024
    }

    @Throws(Exception::class)
    fun decrypt(
        privateKey: ByteArray,
        data: String
    ): String {
        val cipher: Cipher = Cipher.getInstance(DEFAULT_ENCRYPTION_ALGORITHM)
        cipher.init(
            Cipher.DECRYPT_MODE,
            KeyFactory.getInstance(DEFAULT_ENCRYPTION_ALGORITHM).generatePrivate(
                PKCS8EncodedKeySpec(privateKey)
            )
        )
        return String(cipher.doFinal(Base64.decode(data, Base64.DEFAULT)), Charsets.UTF_8)
    }

    @Throws(Exception::class)
    fun encrypt(
        publicKeyEncoded: String,
        data: String
    ): String {
        val cipher: Cipher = Cipher.getInstance(DEFAULT_ENCRYPTION_ALGORITHM)
        cipher.init(
            Cipher.ENCRYPT_MODE,
            KeyFactory.getInstance(DEFAULT_ENCRYPTION_ALGORITHM).generatePublic(
                X509EncodedKeySpec(Base64.decode(publicKeyEncoded, Base64.DEFAULT))
            )
        )
        return Base64.encodeToString(cipher.doFinal(data.toByteArray()), Base64.DEFAULT)
    }


    fun fetchPrivateKey(): ByteArray {
        return this.keyPair.private.encoded
    }

    fun fetchPublicKey(): ByteArray {
        return this.keyPair.public.encoded
    }
}

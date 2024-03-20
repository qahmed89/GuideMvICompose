import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey


class KeyStoreManager {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }
    companion object {
        private const val DEFAULT_ENCRYPTION_ALGORITHM = "RSA"
        private const val DEFAULT_ENCRYPTION_KEY_LENGTH = 1024
        private const val DEFAULT_ALGORITHM = "RSA/ECB/PKCS1Padding"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val KEY_ALIAS = "Keyalaisras"
        fun hasMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    }

    init {
        checkInitKey()
    }

    private fun initKeyPairGenerator(): KeyPairGenerator {
        val generator: KeyPairGenerator

        if (hasMarshmallow()) {
            generator =
                KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEY_STORE)
            val builder = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setKeySize(DEFAULT_ENCRYPTION_KEY_LENGTH)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)


            generator.initialize(builder.build())
            return generator
        }
        generator = KeyPairGenerator.getInstance(DEFAULT_ENCRYPTION_ALGORITHM)
        generator.initialize(DEFAULT_ENCRYPTION_KEY_LENGTH)
        return generator
    }

    private fun createAsymmetricKeyPair(): KeyPair {
        val generator = initKeyPairGenerator()
        return generator.generateKeyPair()
    }

    private fun checkInitKey() {
        if (!keyStore.isKeyEntry(KEY_ALIAS) ){
            createAsymmetricKeyPair()
        }
    }
     fun getAsymmetricKeyPair(): KeyPair? {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)
        val privateKey = keyStore.getKey(KEY_ALIAS, null) as PrivateKey?
        val publicKey = keyStore.getCertificate(KEY_ALIAS)?.publicKey
        return if (privateKey != null && publicKey != null) {
            KeyPair(publicKey, privateKey)
        } else {
            null
        }
    }

    fun encrypt(data: String): String {
        val cipher: Cipher = Cipher.getInstance(DEFAULT_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, getAsymmetricKeyPair()?.public)
        val bytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decrypt(data: String): String {
        val cipher: Cipher = Cipher.getInstance(DEFAULT_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, getAsymmetricKeyPair()?.private)
        val encryptedData = Base64.decode(data, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)
         removeKeyStoreEntries()
        return String(decodedData)
    }

    private fun removeKeyStoreEntries() {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        keyStore.load(null)
        keyStore.deleteEntry(KEY_ALIAS)
    }

    @Throws(Exception::class)
    fun getPublicKeyAsString(publicKeyAlias: String = KEY_ALIAS): String {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null)
        }
        val entry = keyStore.getEntry(publicKeyAlias, null)
        if (entry !is KeyStore.PrivateKeyEntry) {
            throw IllegalStateException("Not an instance of KeyStore.PublicKeyEntry")
        }
        val publicKey = entry.certificate.publicKey
        return Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)
    }

    fun privateKeyToPem(privateKey: PrivateKey = getAsymmetricKeyPair()?.private as PrivateKey): String {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(privateKey.encoded)
        val privateKeyInfo = keyFactory.generatePrivate(keySpec)

        val pemHeader = "-----BEGIN PRIVATE KEY-----"
        val pemFooter = "-----END PRIVATE KEY-----"
        val base64Encoded = Base64.encodeToString(privateKeyInfo.encoded, Base64.DEFAULT)

        return "$pemHeader\n$base64Encoded\n$pemFooter"
    }

}

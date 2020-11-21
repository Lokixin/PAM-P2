package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author lokix This class contains some useful functions related to
 * encrypting/decrypting files: - Generate SecretKeys. - Store SecretKeys in the
 * KeyStore. - Load SecretKeys from the Keystore. - Encrypt images. - Decrypt
 * images.
 */
public class CryptoTools {

    protected String password, filepath;

    public CryptoTools(String password, String filepath) {
        this.password = password;
        this.filepath = filepath;
    }

    /**
     *
     * @param keyToStore
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     *
     * Description: Stores the SecretKey provided as a parameter in the
     * KeyStore.
     */
    public void storeToKeyStore(SecretKey keyToStore) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        File file = new File(this.filepath);
        KeyStore javaKeyStore = KeyStore.getInstance("JCEKS");
        if (!file.exists()) {
            javaKeyStore.load(null, null);
        }
        javaKeyStore.setKeyEntry("dimas", keyToStore, this.password.toCharArray(), null);
        OutputStream writeStream = new FileOutputStream(this.filepath);
        javaKeyStore.store(writeStream, this.password.toCharArray());
    }

    /**
     *
     * @return SecretKey
     *
     * Description: Loads and returns a SecreKey from the KeyStore. The password
     * and filepath from the KeyStore are the ones declared in the CryptoTools
     * constructor.
     */
    public SecretKey loadFromKeyStore() {
        try {
            KeyStore store = KeyStore.getInstance("JCEKS");
            InputStream readStream = new FileInputStream(this.filepath);
            store.load(readStream, this.password.toCharArray());
            SecretKey key = (SecretKey) store.getKey("dimas", this.password.toCharArray());
            readStream.close();
            return key;
        } catch (Exception ex) {
            System.out.println("Excecpión: " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @return SecretKey
     *
     * Description: Generates and returns a SecretKey.
     */
    public SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecretKey key = keyGen.generateKey();
            return key;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param is InputStream
     * @param cipher Cipher
     * @param pathToFile String
     *
     * Description: Static function to encrypt and save an image.
     */
    public static void encryptImg(InputStream is, Cipher cipher, String pathToFile) {
        FileOutputStream os = null;
        try {
            CipherInputStream cipherIn = new CipherInputStream(is, cipher);
            os = new FileOutputStream(new File(pathToFile));
            int i;
            while ((i = cipherIn.read()) != -1) {
                os.write(i);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]- > File not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("[ERROR]- > A problem occured while dealing with a file stream: " + ex.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(CryptoTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String decryptImg(Cipher cipher, String pathToFile, String outputPath) {
        FileInputStream ins = null;
        FileOutputStream os = null;

        try {
            File tmp = File.createTempFile("pattern", ".jpg", new File("C:\\Users\\lokix\\Documents\\NetBeansProjects\\WebApplication1\\build\\web\\media"));
            ins = new FileInputStream(new File(pathToFile));
            os = new FileOutputStream(tmp);
            CipherInputStream cipherIn = new CipherInputStream(ins, cipher);
            int i;
            while ((i = cipherIn.read()) != -1) {
                os.write(i);
            }
            tmp.deleteOnExit();
            ins.close();
            os.close();
            System.out.println("[MESSAGE] -> el archivo ha sido creado en: " + tmp.getName());
            return tmp.getName();
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]- > File not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("[ERROR]- > Problem related to the streams: " + ex.getMessage());
        }
        return null;
    }

    public static void encryptXML(XMLCipher xmlCipher, SecretKey symmetricKey, String pathToFile) {
        try {
            /* Get the instance of BuilderFactory class. */
            DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
            /* Instantiate DocumentBuilder object. */
            DocumentBuilder docBuilder = builder.newDocumentBuilder();
            /* Get the Document object */
            Document document = docBuilder.parse(pathToFile);
            Element rootElement = document.getDocumentElement();
            xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);
            boolean encryptContentsOnly = false;
            xmlCipher.doFinal(document, rootElement, encryptContentsOnly);
            File encryptionFile = new File(pathToFile+File.separator+"encrypted");
            FileOutputStream fOutStream = new FileOutputStream(encryptionFile);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fOutStream);
            transformer.transform(source, result);
            fOutStream.close();
            
            
        } catch (XMLEncryptionException ex) {
            System.out.println("[ERROR] - > XML Encryption failed due to: " + ex.getMessage());
        } catch (ParserConfigurationException ex) {
            System.out.println("[ERROR] - > XML Parsing Failed: " + ex.getMessage());
        } catch (SAXException ex) {
            System.out.println("[ERROR] - > A problem occured while building the XML dcument: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(CryptoTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CryptoTools.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

package br.com.global5.loginG5.infra.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

public class AppUtils {

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();


    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue =
            new byte[]{'G', 'l', 'o', 'b', 'a', 'l', 'C', 'i', 'n', 'c', 'o', 'S', 'e', 'n', 'h', 'a'};


    private static Properties prop = new Properties();
    private static InputStream input = null;

    private static String ambiente;
    // load a properties file

	public static final String dirImagens = "/var/www/fotos/";
	
	public static final String dirXML = "\\XML\\";

    public static String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


	public static String gerarNovaSenha() {
		String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
				"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "!", "@", "#", "$", "%", "ˆ", "&", "*" };

		String senha = "";

		for (int x = 0; x < 10; x++) {
			int j = (int) (Math.random() * carct.length);
			senha += carct[j];

		}

		return senha;
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static byte[] HexToBytes(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	public static Map<String,String> NamedParams(String... params) {
	    Map<String, String> map = new HashMap<String, String>();
        for(int i=0;i<params.length;i++){
            map.put(params[i],params[++i]);
        }
	    return map;
    }

	public static String toMd5(String valor) {
        MessageDigest mDigest;
        try {
            //Instanciamos o nosso HASH MD5, poder�amos usar outro como
            //SHA, por exemplo, mas optamos por MD5.
            mDigest = MessageDigest.getInstance("MD5");

            //Convert a String valor para um array de bytes em MD5
            byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

            //Convertemos os bytes para hexadecimal, assim podemos salvar
            //no banco para posterior compara��o se senhas
            StringBuffer sb = new StringBuffer();
            for (byte b : valorMD5){
                sb.append(Integer.toHexString((b & 0xFF) |
                        0x100).substring(1,3));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * Formata competência dd/mm/AAAA em AAAA/MM
	 * @param date
	 * @return
	 */
	public static String formataCompetencia(Date date) {


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");

		return sdf.format( date );
	}

	
	public static String saveArqXML(String path, UploadedFile upload, int id, String tipo) {
		if (upload == null) {
			return null;
		}
		String extesion = FilenameUtils.getExtension(upload.getFileName());
		try {
			InputStream is = upload.getInputstream();
			byte[] bytes = IOUtils.toByteArray(is);
			FileOutputStream fileOuputStream = new FileOutputStream(path + tipo + " - " + id + "." + extesion);
			fileOuputStream.write(bytes);
			fileOuputStream.close();

			return tipo + " - " +  id + "." + extesion;

		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documentos  " + id
							+ " não terá seu arquivo enviado por um erro não tratado.", null));

		}
		return null;
	}

}

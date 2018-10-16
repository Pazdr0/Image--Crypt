import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class cypherJPG {
	private RSAcrypt rsa;
	private GetInfo image;
	private byte[] imageByteArray;
	private byte[][] cryptedImageByteArray;
	private int imageStart;
	private final String cryptedImageName = "cryptedmustang67.jpg";
	private final int columnsInCrypted = 4;
	private final int sampleSize = 1000;
//	private final int sampleSize = image.myByteList.size();
		
//	private final String decryptedImageName = "decryptedmustang67.jpg";
	
	public static void main(String[] args) {
		new cypherJPG().run();
	}

	public void run() {
		rsa = new RSAcrypt();
		rsa.generateKeys();
		System.out.println("\n");
		image = new GetInfo();
		image.run();
		
//		copyValuesToImageByteArray();
//		displayImageBytes();
//		cryptImage();
//		System.out.println(imageByteArray.length);
//		System.out.println(image.myByteList.size());
//		writeBytes();
		
		
		
		
		
//		for (int i=0; i<cryptedImageByteArray.length; i++) {
//			for (int j=0; j<3; j++) {
//				System.out.print(cryptedImageByteArray[i][j] + " ");
//			}
//			System.out.println();
//		}
		
	}
	public void test() {
		BigInteger[] big = new BigInteger[10];
		for (int i=0; i<big.length; i++) {
			big[i] = new BigInteger(Integer.toString(10000+i));
		}
		byte[][] arr = new byte[big.length][4];
		byte[] bajcik;
		for (int i=0; i<big.length; i++) {
			bajcik = new byte[big[i].toByteArray().length];
			bajcik = big[i].toByteArray();
			for (int j=0; j<bajcik.length; j++) {
				arr[i][j] = bajcik[j];
			}
		}
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<4; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
		int n = 0;
		byte[] arr2 = new byte[arr.length*4];
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<4; j++) {
				arr2[n] = arr[i][j];
				n++;
			}
		}
		n = 0;
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<4; j++) {
				System.out.print(arr2[n] + " ");
				n++;
			}
			System.out.println();
		}
		
		n = 0;
		byte[][]arr3 = new byte[big.length][4];
		for (int i=0; i<arr3.length; i++) {
			for (int j=0; j<4; j++) {
				arr3[i][j] = arr2[n];
				n++;
			}
		}
		for (int i=0; i<arr3.length; i++) {
			for (int j=0; j<4; j++) {
				System.out.print(arr3[i][j] + " ");
			}
			System.out.println();
	}
	
	BigInteger[] big2 = new BigInteger[arr3.length];
	for (int i=0; i<big2.length; i++) {
		if (arr3[i][3] == 0 && arr3[i][2] == 0 && arr3[i][1] != 0) {
			big2[i] = new BigInteger(Integer.toString((arr3[i][0]*256 + arr3[i][1])));
		}
		else if (arr3[i][3] == 0 && arr3[i][2] == 0 && arr3[i][1] == 0) {
			big2[i] = new BigInteger(Integer.toString((arr3[i][0])));
		}
		else if (arr3[i][3] == 0 && arr3[i][2] != 0) {
			big2[i] = new BigInteger(Integer.toString((arr3[i][0]*256*256 + arr3[i][1]*256 + arr3[i][2])));
		}
		else {
			big2[i] = new BigInteger(Integer.toString((arr3[i][0]*256*256*256 + arr3[i][1]*256*256 + arr3[i][2]*256 + arr3[i][3])));
		}
	}
	for (int i=0; i<big2.length; i++) {
		System.out.println(big2[i]);
	}
}
	
	
	
	
	
	
	
	public void readBytes() {
//		byte[] bytesToRead = image.readBytesToArray(decryptedImageName);
		
	}
	

	
	public void decryptImage(BigInteger[] bigArr) {
		bigArr = rsa.decrypt(rsa.getPrivateKeyN(), rsa.getPrivateKeyD(), bigArr);
	}
	
	public void cryptImage() {																				// Szyfrowanie masy bitowej obrazu
		BigInteger[] cryptedImage = rsa.encrypt(rsa.getPublicKeyN(), rsa.getPublicKeyE(), imageByteArray);
		cryptedImageByteArray = new byte[cryptedImage.length][columnsInCrypted];
		byte[] bajcik;
		for (int i=0; i<cryptedImage.length; i++) {
			bajcik = new byte[cryptedImage[i].toByteArray().length];
			bajcik = cryptedImage[i].toByteArray();
			for (int j=0; j<bajcik.length; j++) {
				cryptedImageByteArray[i][j] = bajcik[j];
			}
		}
	}
	
	public void writeBytes() {																				// Wysyła tablicej bajtów do zapisania
		byte[] bytesToWrite = new byte[imageStart + 2 + cryptedImageByteArray.length*columnsInCrypted];
		System.out.println(bytesToWrite.length);
		
		for (int i=0; i<imageStart; i++) {
			bytesToWrite[i] = image.myByteList.get(i);
		}
		for (int i=0; i<cryptedImageByteArray.length; i++) {
			for (int j=0; j<columnsInCrypted; j++) {
				bytesToWrite[imageStart+i+j] = cryptedImageByteArray[i][j];
			}
		}
		bytesToWrite[bytesToWrite.length-2] = -1;
		bytesToWrite[bytesToWrite.length-1] = -39;

		writeBytesToFile(bytesToWrite, cryptedImageName);
	}
	
	public void copyValuesToImageByteArray() {																// Kopiuje bajty do zaszyfrowania do tablicy
		String stValue = new String();																		// z posrod wszystkich bajtow pliku
		imageStart = 0;
		for (int i=0; i<sampleSize; i++) {
			if (image.myByteList.get(i) == 0xffffffff && image.myByteList.get(i+1) == 0xffffffda) {
				stValue = image.replaceStr(Integer.toHexString(image.myByteList.get(i+2))) + image.replaceStr(Integer.toHexString(image.myByteList.get(i+3)));
				imageStart = Integer.parseInt(stValue, 16) + i + 2;
			}
		}
		imageByteArray = new byte[image.myByteList.size() - imageStart - 2];
		for (int i=0; i<image.myByteList.size() - imageStart - 2; i++) {
			imageByteArray[i] = image.myByteList.get(imageStart + i);
		}
	}
	
	public void displayImageBytes() {																		// Wyswietla tablice bajtow do zaszyfrowania
		int n = 0;
		for (int i=0; i<imageByteArray.length/16+1; i++) {
			System.out.print(i + "\t");
			for (int j=0; j<16; j++) {
				if (i == 3905 && j == 14) {
					break;
				}
				System.out.printf("%-10s ", Integer.toHexString(imageByteArray[n]));
				n++;
			}
			System.out.println();
		}
	}
	
	public void writeBytesToFile(byte[] bytesToWrite, String fileName) {									// Zapisuje otrzymana tablice bajtow do pliku
		Path path = Paths.get(fileName);
		try {
			Files.write(path, bytesToWrite);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
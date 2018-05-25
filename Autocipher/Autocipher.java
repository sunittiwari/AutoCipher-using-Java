/*This program enables us to encrypt plain text to a cipher text using Auto Key Cipher
 * */

/*imports the scanner class from the 
 *util package used for reading files.*/
import java.util.Scanner; 

/*imports the regular expression class from 
 * util package to enable the program to 
 * understand regular expressions.*/
import java.util.regex.Pattern; 

//imports the package I/O
import java.io.*; 

/*imports nio package with File and Path classes enabling writing to a 
 *file directly without use of traditional I/O operations. 
 *Supported by JDK-7 and up*/  
import java.nio.file.Files; 
import java.nio.file.Paths;
/**
 * 
 */

/**
 * @author Sunit Tiwari
 *
 */
public class Autocipher {
	// Creating an object for Scanner class and using it to reference it whenever needed.
	private static Scanner scanner = new Scanner( System.in ); 

	// Creating an object for Autocipher class and using it for method call.	
	private static Autocipher methodCall = new Autocipher(); 

	/*// Initializes the entire alphabets in a string which will be referenced upon 
	 * as index value to compute the required letter. */	
	public String autokeyval = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//Global variables	
	public String newKey = new String();
	public String decrypt = new String();
	public String temp = new String();
	public String key = new String();
	public String ciphertext = new String();


	/**
	 * @param args
	 * @throws IOException 
	The main function call */
	public static void main(String[] args) throws IOException {

		System.out.println("Enter your name");
		String input = scanner.nextLine(); //Stores the input provided by the user in a string with the help of scanner class

		System.out.println("Welcome" +"@" +input+" "+ "Choose any one from the list below");
		System.out.println("1.ENCRYPTION");
		System.out.println("2.EXIT");

		int option = scanner.nextInt();
		if (option == 1) {
			methodCall.encryption(); // Encryption method being called.Jumps the execution to encryption method
			System.out.println("ENCRYPTION Done.The encrypted File is saved as encrypt.txt and the key has been stored in Keyword.txt");

		} else if (option == 2) {
			System.out.println("See you later"+" "+input+"!!!! Bye Bye :)");
			System.exit(0);
		}


	}
	public void encryption() 
	{
		try {
			/*To implement the Auto cipher we need a key.
			 *Here we are getting the key as an Input from the user.
			 *The key is a single word which should not have any spaces,special characters,punctuation eg:- welcome*/			
			Scanner auto_key = new Scanner(System.in);
			System.out.println("Please enter a single word without space and any special character");
			key = auto_key.next();
			Pattern pattern = Pattern.compile("^[A-Za-z]++$"); //Using regular expression to validate the input provided by the user.
			if (!pattern.matcher(key).matches()) {
				throw new IllegalArgumentException("Invalid String");
			}
			else
			{
				/*Now we need to check whether the input file size is greater>1 mb.
				 *If greater then discard the file and throw exception.*/				
				File file = new File ("./input.txt");
				long filesizeinBytes = file.length();
				long filesizeinKB = (filesizeinBytes/1024);
				long filesizeinMB = (filesizeinKB/1024);
				if (filesizeinMB >1) {
					throw new IllegalArgumentException("File Size greater than 1 MB");
				}
				/*If the file size is less than 1 mb and it is not empty then 
				 *pull out the data into a string from the text file.*/				
				System.out.println("File size is less than 1 mb");
				BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
				StringBuffer stringBuffer = new StringBuffer();
				String line = null;
				while((line =bufferedReader.readLine())!=null){

					stringBuffer.append(line).append("\n");
					/*Now we format the string.Remove the spaces,
					 *punctuation and special character if any*/	 	
					String withoutspace = line.replaceAll("\\s", "");
					System.out.println(withoutspace);
					String withoutspecialchar = withoutspace.replaceAll("\\W", "");
					System.out.println(withoutspecialchar);
					String withoutnumber = withoutspecialchar.replaceAll("\\d", "");
					temp = withoutnumber.toUpperCase(); // Convert the entire string to uppercase to remove ambiguity.
					System.out.println(temp); 

				}
				String simple =((key+temp)).toUpperCase();
				/*Writing the key to a text file.
				 *RandomAccessFile allows us to replace the old key with the latest one each time the program is run*/				
				RandomAccessFile f = new RandomAccessFile(new File("keyword.txt"), "rw");
				f.seek(0);  
				f.write(key.getBytes());
				f.close();
				/*Below we generate subkey of equal length as the plain text.
				 *And generate cipher text using the auto key cipher*/			
				for (int i = 0; i <temp.length(); i++) {
					char subkey = simple.charAt(i);
					newKey += Character.toString(subkey);
				}
				System.out.println(newKey);
				for (int index = 0; index < temp.length(); index++) {
					int inputFileTextVal = autokeyval.indexOf(temp.charAt(index));
					int newkeyVal = autokeyval.indexOf(newKey.charAt(index));
					int cipherval = (inputFileTextVal+newkeyVal)%26;
					System.out.println(inputFileTextVal);
					System.out.println(newkeyVal);
					System.out.println(cipherval);

					ciphertext +=  autokeyval.charAt(cipherval);
					System.out.println(ciphertext);
					Files.write(Paths.get("./encrypt.txt"), ciphertext.getBytes());}// Writes the cipher text to a file

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} 

		catch (IOException e) {

			e.printStackTrace();
		}
	}

}




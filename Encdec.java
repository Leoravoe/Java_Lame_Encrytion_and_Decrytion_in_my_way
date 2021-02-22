import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class Encdec{
    public static void encrypt(String sentence, int key) throws Exception {
        File output = new File("encrypted.txt");
        // System.out.println(sentence);
        String encoded = Base64.getMimeEncoder().encodeToString(sentence.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(encoded.length());
        for (int i = 0; i < encoded.length(); i++) {
            int temporary = (int)encoded.charAt(i) + key;
            stringBuilder.setCharAt(i, (char) temporary);
        }
        String s = stringBuilder.toString();
        OutputStream outputStream = new FileOutputStream(output);
        Writer writer = new OutputStreamWriter(outputStream,Charset.forName("UTF-8"));
        writer.write(s);
        writer.close();
    }
    
    public static void decrypt(File input, int key) throws Exception {
        File output = new File("decrypted.txt");
        InputStream inputStream = new FileInputStream(input);
        Reader reader = new InputStreamReader(inputStream,Charset.forName("UTF-8"));
        int data = reader.read();
        StringBuilder es = new StringBuilder();
        while(data != -1){
            char theChar = (char) (data-key);
            es.append(theChar);
            data = reader.read();
        }
        reader.close();
        String d = es.toString();
        String s = new String(Base64.getMimeDecoder().decode(d));
        OutputStream outputStream = new FileOutputStream(output);
        Writer writer = new OutputStreamWriter(outputStream,Charset.forName("UTF-8"));
        writer.write(s);
        writer.close();
    }

    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        BufferedReader in = new BufferedReader(new FileReader("text.txt"));
        StringBuilder sb = new StringBuilder();
        String line = in.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = in.readLine();
        }
        in.close();
        String contents = sb.toString();
        int key = random.nextInt(100);
        System.out.println("Your key is " + key);
        try {
            encrypt(contents, key);
            System.out.println("Your file is encrypted successfully");
            System.out.println("Do you want to decrypt the file if yes press 1 else press 2");
            int choice = scanner.nextInt();
            if (choice == 1) {
                File file = new File("encrypted.txt");
                System.out.println("Enter the correct key to decrypt the file");
                int keyForDecryption = scanner.nextInt();
                decrypt(file, keyForDecryption);
            }
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}
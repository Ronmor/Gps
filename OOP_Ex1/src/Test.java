import java.io.*;

public class Test {
	public static void main(String [] args) {
		write();
		read();
		
	}
	

	public static void read() {
// The name of the file to open.
String fileName = "temp.txt";

try {
    // Use this for reading the data.
    byte[] buffer = new byte[1000];

    FileInputStream inputStream = 
        new FileInputStream(fileName);

    // read fills buffer with data and returns
    // the number of bytes read (which of course
    // may be less than the buffer size, but
    // it will never be more).
    int total = 0;
    int nRead = 0;
    while((nRead = inputStream.read(buffer)) != -1) {
        // Convert to String so we can display it.
        // Of course you wouldn't want to do this with
        // a 'real' binary file.
        System.out.println(new String(buffer));
        total += nRead;
    }   

    // Always close files.
    inputStream.close();        

    System.out.println("Read " + total + " bytes");
}
catch(FileNotFoundException ex) {
    System.out.println(
        "Unable to open file '" + 
        fileName + "'");                
}
catch(IOException ex) {
    System.out.println(
        "Error reading file '" 
        + fileName + "'");                  
    // Or we could just do this: 
    // ex.printStackTrace();
}
}
	
    public static void write() {
    	System.out.println("start");
        // The name of the file to open.
        String fileName = "temp.txt";
        
        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write("Hello there,");
            bufferedWriter.write(" here is some text.");
            bufferedWriter.newLine();
            bufferedWriter.write("We are writing");
            bufferedWriter.write(" the text to the file.");

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        System.out.println("end");
    }
}

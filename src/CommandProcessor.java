import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author Blake Everman
 * 
 * @version 2024-05-6
 */
public class CommandProcessor {

	// the Controller object to manipulate the
	// commands that the command processor
	// feeds to it
	private Controller controller;
	private File file;

	/**
	 * The constructor for the command processor requires a Controller instance to
	 * exist, so the only constructor takes a Controller class object to feed
	 * commands to.
	 * 
	 * @param data the Controller object to manipulate
	 * @param file input file with the commands we want to run
	 */
	public CommandProcessor(Controller controller, File file) {
		this.controller = controller;
		this.file = file;
	}

	/**
	 * Begins the process of opening the file and reading out the commands on each
	 * line
	 */
	public void processFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					processor(line.trim());
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}
	}

	/**
	 * This method parses keywords in the line and calls methods in the Controller
	 * as required. Each line command will be specified by one of the keywords to
	 * perform the actions. These actions are performed on specified objects and
	 * include insert, remove, regionsearch, search, and dump. If the command in the
	 * file line is not one of these, an appropriate message will be written in the
	 * console. This processor method is called for each line in the file. Note that
	 * the methods called will themselves write to the console, this method does
	 * not, only calling methods that do.
	 * 
	 * @param line a single line from the text file
	 */
	public void processor(String line) {
	    String[] arr = line.split("\\s+");
	    String command = arr[0];

	    if (command.equals("insert")) {
	        // Join everything after "insert" back into a single string
	        String rest = line.substring(line.indexOf(" ") + 1).trim();
	        if (rest.contains("<SEP>")) {
	            String[] artSong = rest.split("<SEP>");
	            if (artSong.length == 2) {
	                String artist = artSong[0].trim();
	                String song = artSong[1].trim();
	                controller.insert(artist, song);
	            } else {
	                System.out.println("wrong format");
	            }
	        } else {
	            System.out.println("missing delimeter");
	        }
	    }

	    else if (command.equals("remove")) {
	        if (arr.length >= 2) {
	            String rest = line.substring(line.indexOf(" ") + 1).trim();
	            int song = 0;

	            // remove "artist " or "song " if present
	            if (rest.startsWith("artist ")) {
	                rest = rest.substring("artist ".length()).trim();
	            } else if (rest.startsWith("song ")) {
	                rest = rest.substring("song ".length()).trim();
	                song = 1;
	            }

	            controller.remove(rest, song);
	        } else {
	            System.out.println("Wrong number of args");
	        }
	    }

	    else if (command.equals("print")) {
	        if (arr.length >= 2) {
	            controller.print(arr[1]);
	        } else {
	            System.out.println("Wrong number of args");
	        }
	    }

	    else {
	        System.out.println("Unrecognized command.");
	    }
	}


}

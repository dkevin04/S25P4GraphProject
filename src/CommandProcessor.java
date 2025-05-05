import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author CS Staff
 * 
 * @version 2024-01-22
 */
public class CommandProcessor {

	// the Controller object to manipulate the
	// commands that the command processor
	// feeds to it
	private Controller controller;
	private File file;

	/**
	 * The constructor for the command processor requires a Controller instance to
	 * exist, so the only constructor takes a Controller class object to feed commands
	 * to.
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
	 * This method parses keywords in the line and calls methods in the Controller as
	 * required. Each line command will be specified by one of the keywords to
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
		// converts the string of the line into an
		// array of its space (" ") delimited elements
		String[] arr = line.split("\\s{1,}");
		String command = arr[0]; // the command will be the first of these
									// elements
		// calls the insert function and passes the correct
		// parameters by converting the string integers into
		// their Integer equivalent, trimming the whitespace
		/*
		 * Grabbing the rectangle data x, y, w, h and turning them into ints. Creating a
		 * new rectangle and pair with the data and inserting it.
		 */
		if (command.equals("insert")) {
			if (arr.length == 2) {
				String str = arr[1];
				if (str.contains("<SEP>")) {
					String[] artSong = str.split("<SEP>");
					if (artSong.length == 2) {
						String artist = artSong[0];
						String song = artSong[1];
						controller.insert(artist, song);
					}
					else {
						System.out.println("wrong format");
					}
				}
				else {
					System.out.println("missing delimeter");
				}
			}
			else {
				System.out.println("wrong number of args");
			}

		}
		// calls the appropriate remove method based on the
		// number of white space delimited strings in the line
		/*
		 * The remove and removeByValue method. For remove, we grab the name from the
		 * arr and call remove. For removeByValue, we have to grab the x, y, w, h and
		 * call remove with those values.
		 */
		else if (command.equals("remove")) {
			// checks the number of white space delimited strings in the line
			if (arr.length == 2) {
				String name = arr[1];
				controller.remove(name);
			}
			else {
				System.out.println("Wrong number of args");
			}

		}
		/*
		 * Grab x, y, w, h from arr and call region search.
		 */
		else if (command.equals("print")) {
			// calls the regionsearch method for a set of coordinates
			// the string integers in the line will be trimmed of whitespace
			if ( arr.length == 2) {
				String name = arr[1];
				controller.print(name);
			}
		
		} else {
			// the first white space delimited string in the line is not
			// one of the commands which can manipulate the Controller,
			// a message will be written to the console
			System.out.println("Unrecognized command.");
		}
	}

}

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
	private Controller data;
	private File file;

	/**
	 * The constructor for the command processor requires a Controller instance to
	 * exist, so the only constructor takes a Controller class object to feed commands
	 * to.
	 * 
	 * @param data the Controller object to manipulate
	 * @param file input file with the commands we want to run
	 */
	public CommandProcessor(Controller data, File file) {
		this.data = data;
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
			if (arr.length == 4) {
				String name = arr[1];
				int x = Integer.parseInt(arr[2]);
				int y = Integer.parseInt(arr[3]);
				
				
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
			int numParam = arr.length - 1;
			if (numParam == 1) {
				String name = arr[1];
				

			} else if (numParam == 2) {
				// Calls remove by coordinate, converting string
				// integers into their Integer equivalent minus whitespace
				int x = Integer.parseInt(arr[1]);
				int y = Integer.parseInt(arr[2]);
				

			}

		}
		/*
		 * Grab x, y, w, h from arr and call region search.
		 */
		else if (command.equals("regionsearch")) {
			// calls the regionsearch method for a set of coordinates
			// the string integers in the line will be trimmed of whitespace
			int x = Integer.parseInt(arr[1]);
			int y = Integer.parseInt(arr[2]);
			int w = Integer.parseInt(arr[3]);
			int h = Integer.parseInt(arr[4]);

		}
		/*
		 * Calls search with no arguments
		 */
		else if (command.equals("search")) {
			// calls the search method for a name of object

		} else if (command.equals("duplicates")) {
		}
		/*
		 * Calls dump with no arguments
		 */
		else if (command.equals("dump")) {
			// calls the dump method for the Controller, takes no parameters
			// (see the dump() JavaDoc in the Controller class for more
			// information)
		} else {
			// the first white space delimited string in the line is not
			// one of the commands which can manipulate the Controller,
			// a message will be written to the console
			System.out.println("Unrecognized command.");
		}
	}

}

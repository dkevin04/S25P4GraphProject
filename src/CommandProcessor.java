import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec. Its largely
 * copied from previous projects.
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
	 * Splits the command into a string array and checks which command is being
	 * called. Then, we put the rest of the line, excluding the command, into a
	 * substring. If there is a delimiter (<SEP>), we split that substring again to
	 * just grab the song or artist. Then, we call our methods with the strings that
	 * we are left with.
	 * 
	 * @param line a single line from the text file
	 */
	public void processor(String line) {
		String[] arr = line.split("\\s+");
		String command = arr[0];

		if (command.equals("insert")) {
			// Only grabs what's after the command insert.
			String rest = line.substring(line.indexOf(" ") + 1).trim();
			if (rest.contains("<SEP>")) {
				// Splits delimiter into song and artist.
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
				// Same as insert, grabs what is after command remove.
				String rest = line.substring(line.indexOf(" ") + 1).trim();
				int song = 0;

				// if the substring starts with artist, then grabs the 
				//artist name.
				if (rest.startsWith("artist ")) {
					rest = rest.substring("artist ".length()).trim();
				}
				// if the substring starts with song, grab song name and set
				//song to 1. That way we can print our error statment's 
				//correctly in controller.
				else if (rest.startsWith("song ")) {
					rest = rest.substring("song ".length()).trim();
					song = 1;
				}

				controller.remove(rest, song);
			} else {
				System.out.println("Wrong number of args");
			}
		}

		// Print call with type of print being the second command argument.
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

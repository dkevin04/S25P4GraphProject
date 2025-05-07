import java.io.*;

/**
 * 
 */
public class CommandProcessorTest extends student.TestCase {
    private CommandProcessor cmd;

    public void setUp() {
        File input = new File(
            ".\\solutionTestData\\P4_sampleInput.txt");
        Controller control = new Controller(10);
        cmd = new CommandProcessor(control, input);
    }


    public void testInput() {
        cmd.processFile();
        assertFuzzyEquals(systemOut().getHistory(),
            "|When Summer's Through| does not exist in the Song database.\n"
                + "total songs: 0\n" + "total artists: 0\n"
                + "There are 0 connected components\n"
                + "The largest connected component has 0 elements\n"
                + "|Blind Lemon Jefferson| is added to the Artist database.\n"
                + "|Long Lonesome Blues| is added to the Song database.\n"
                + "|Blind Lemon Jefferson<SEP>Long Lonesome Blues| duplicates a record already in the database.\n"
                + "|Long   Lonesome Blues| is added to the Song database.\n"
                + "|long Lonesome Blues| is added to the Song database.\n"
                + "|Ma Rainey| is added to the Artist database.\n"
                + "|Ma Rainey's Black Bottom| is added to the Song database.\n"
                + "|Mississippi Boweavil Blues| is added to the Song database.\n"
                + "Song hash table size doubled.\n"
                + "|Fixin' To Die Blues| is added to the Song database.\n"
                + "0: |Blind Lemon Jefferson|\n" + "7: |Ma Rainey|\n"
                + "total artists: 2\n" + "1: |Fixin' To Die Blues|\n"
                + "2: |Mississippi Boweavil Blues|\n"
                + "7: |long Lonesome Blues|\n" + "15: |Long Lonesome Blues|\n"
                + "16: |Ma Rainey's Black Bottom|\n"
                + "19: |Long   Lonesome Blues|\n" + "total songs: 6\n"
                + "There are 1 connected components\n"
                + "The largest connected component has 8 elements\n"
                + "|Sleepy| does not exist in the Song database.\n"
                + "|ma rainey| does not exist in the Artist database.\n"
                + "|Ma Rainey| is removed from the Artist database.\n"
                + "0: |Blind Lemon Jefferson|\n" + "7: TOMBSTONE\n"
                + "total artists: 1\n");
    }
}

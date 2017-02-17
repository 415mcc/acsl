import java.io.IOException;
import java.io.FileReader;

public class AALInterpreter {
    public static void main(String[] args) {
        Parser parser = null;
        try {
            if (args.length == 0)
                parser = new Parser(System.in);
            else
                parser = new Parser(new FileReader(args[0]));
        } catch (IOException ex) {
            System.err.println("IO error occured");
        }

        Runner runner = new Runner(parser);
        runner.run();
    }
}

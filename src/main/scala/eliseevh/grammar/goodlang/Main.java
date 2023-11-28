package eliseevh.grammar.goodlang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import eliseevh.goodlang.generated.package$;

public class Main {
    public static void main(String... args) {
        if (args.length != 1) {
            System.err.println("Exactly one argument expected: path to source code");
            return;
        }
        String filename = args[0];
        if (!filename.endsWith(".bl")) {
            System.err.println("Source code file must have '.bl' extension");
            return;
        }
        try {
            String code = Files.readString(Path.of(filename));
            Files.writeString(
                    Path.of(filename.substring(0, filename.length() - "bl".length()) + "c"),
                    package$.MODULE$.parseGoodLang(code).value());
        } catch (IOException e) {
            System.err.println("Cannot run tool: " + e.getMessage());
        }
    }
}

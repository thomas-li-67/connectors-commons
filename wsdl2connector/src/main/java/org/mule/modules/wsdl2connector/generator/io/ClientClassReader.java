package org.mule.modules.wsdl2connector.generator.io;

import org.apache.maven.plugin.Mojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.String.format;

public class ClientClassReader {

    private final Mojo mojo;

    public ClientClassReader(Mojo mojo) {
        this.mojo = mojo;
    }

    public String read(String basePath, String serviceFullyQualifiedName, String clientRetrievalMethod) {
        try {
            Scanner scanner = new Scanner(new File(format("%s/%s.java", basePath, serviceFullyQualifiedName.replace(".", "/"))));
            String result = null;
            while (scanner.hasNext() && result == null) {
                String line = scanner.nextLine().trim();
                if (line.matches(format("public .* %s().*", clientRetrievalMethod))) {
                    result = line.split(" ")[1];
                }
            }
            String clientFullyQualifiedName = format("%s%s", serviceFullyQualifiedName.substring(0, serviceFullyQualifiedName.lastIndexOf(".") + 1), result);
            mojo.getLog().info("Found client fully qualified name: " + clientFullyQualifiedName);
            return clientFullyQualifiedName;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

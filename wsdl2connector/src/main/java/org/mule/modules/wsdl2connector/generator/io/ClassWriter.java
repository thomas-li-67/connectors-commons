package org.mule.modules.wsdl2connector.generator.io;

import org.apache.maven.plugin.Mojo;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.mule.modules.wsdl2connector.generator.model.ModeledClass;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.format;

public class ClassWriter {

    private final String basePath;
    private final VelocityEngine velocityEngine;
    private final Mojo mojo;

    public ClassWriter(Mojo mojo, String basePath) {
        this.mojo = mojo;
        this.basePath = basePath;
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    public void writeClass(ModeledClass modeledClass) {
        writeClass(modeledClass.getName(), modeledClass.getClass().getSimpleName(), modeledClass);
    }

    public void writeClass(String template, ModeledClass modeledClass) {
        writeClass(modeledClass.getName(), template, modeledClass);
    }

    public void writeClass(String fileName, String template, ModeledClass modeledClass) {
        try (PrintWriter writer = getWriter(modeledClass.getBasePackage(), fileName)) {
            VelocityContext context = new VelocityContext();
            context.put("modeledClass", modeledClass);
            velocityEngine.getTemplate(format("%s.vm", template), "UTF-8").merge(context, writer);
        } catch (ResourceNotFoundException | ParseErrorException | MethodInvocationException e) {
            throw new RuntimeException(e);
        }
    }

    private PrintWriter getWriter(String basePackage, String name) {
        try {
            File packageFile = new File(format("%s/%s", basePath, basePackage.replace(".", "/")));
            if (!packageFile.exists()) {
                log("Packaging folders do not exists, creating folder structure.");
                packageFile.mkdirs();
            }
            File writeFile = new File(packageFile, format("%s.java", name));
            if (!writeFile.exists()) {
                log("File %s doesn't exist. Creating.", writeFile.getAbsolutePath());
                writeFile.createNewFile();
            }
            log("Writing into file '%s'", writeFile.getAbsolutePath());
            return new PrintWriter(writeFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(String message, String...params) {
        mojo.getLog().info(format(message, params));
    }
}

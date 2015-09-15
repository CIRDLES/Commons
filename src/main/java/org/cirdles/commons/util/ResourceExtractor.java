package org.cirdles.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * A utility for extracting resources into local files. This should be used to
 * avoid issues when manipulating resources inside of a JAR or a WAR.
 */
public class ResourceExtractor {

    private final ClassLoader classLoader;
    private final Class<?> clazz;

    /**
     * Creates a new resource extractor.
     *
     * @param clazz the class whose class loader should be used to access
     *              resources
     */
    public ResourceExtractor(Class<?> clazz) {
        this.classLoader = null;
        this.clazz = clazz;
    }

    /**
     * Creates a new resource extractor.
     *
     * @param classLoader the class loader that should be used to access
     *                    resources
     */
    public ResourceExtractor(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.clazz = null;
    }

    /**
     * Extracts a resource as a file.
     *
     * @param name the name of the resource
     * @return a temporary file with the same contents as the resource
     * @see Class#getResourceAsStream(String)
     * @see ClassLoader#getResourceAsStream(String)
     */
    public File extractResourceAsFile(String name) {
        File result = null;
        Path resourcePath = extractResourceAsPath(name);

        if (resourcePath != null) {
            result = resourcePath.toFile();
        }

        return result;
    }

    private InputStream getResourceAsStream(String name) {
        InputStream result = null;

        if (classLoader != null) {
            result = classLoader.getResourceAsStream(name);
        } else if (clazz != null) {
            result = clazz.getResourceAsStream(name);
        }

        return result;
    }

    /**
     * Extracts a resource as a file path.
     *
     * @param name the name of the resource
     * @return a temporary file path with the same contents as the resource
     * @see Class#getResourceAsStream(String)
     * @see ClassLoader#getResourceAsStream(String)
     */
    public Path extractResourceAsPath(String name) {
        Path result = null;
        InputStream resourceStream = getResourceAsStream(name);

        // resource must be found
        if (resourceStream != null) {
            // should always succeed
            try {
                Path tempFile = Files.createTempFile(null, null);
                Files.copy(resourceStream, tempFile, REPLACE_EXISTING);
                result = tempFile;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        return result;
    }

}

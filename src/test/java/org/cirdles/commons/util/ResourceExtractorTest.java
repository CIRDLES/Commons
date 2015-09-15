package org.cirdles.commons.util;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by johnzeringue on 9/15/15.
 */
public class ResourceExtractorTest {

    private ResourceExtractor resourceExtractor;

    @Before
    public void setUp() {
        resourceExtractor = new ResourceExtractor(this.getClass());
    }

    @Test
    public void testExtractResourceAsFile() throws Throwable {
        File file = resourceExtractor
                .extractResourceAsFile("resourceExtractorTest.txt");

        Scanner scanner = new Scanner(file, "UTF-8");
        assertThat(scanner.nextLine(), is("Hello, World!"));
    }

    @Test
    public void testExtractResourceAsPath() throws Throwable {
        Path path = resourceExtractor
                .extractResourceAsPath("resourceExtractorTest.txt");

        Scanner scanner = new Scanner(path, "UTF-8");
        assertThat(scanner.nextLine(), is("Hello, World!"));
    }

}

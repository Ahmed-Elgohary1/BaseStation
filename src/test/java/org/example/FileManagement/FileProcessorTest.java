package org.example.FileManagement;

import lombok.Value;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @Test
    void testGenerateUniquePathName(){



        FileProcessor fileProcessor=FileProcessor.getInstance();
        fileProcessor.enableNameManager();

        String demoDirectory=fileProcessor.nameManager.appendDirectory( "E:\\project\\BaseStation\\ParquetArch\\","frst\\");

        String fileName=fileProcessor.nameManager.generateUniquePathName( demoDirectory,"hi",".txt");

        System.out.println(fileName);


        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("Hello, World!"); // Write the content to the file
            writer.close(); // Remember to close the writer
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}



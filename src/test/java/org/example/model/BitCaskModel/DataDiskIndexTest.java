package org.example.model.BitCaskModel;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.example.model.BitCaskModel.DataDiskIndex.convertBytesToDataDiskIndex;
import static org.junit.jupiter.api.Assertions.*;

class DataDiskIndexTest {
    @Test
    void bytr() {
        String myString = "Hello";

        // Get the byte representation of the string using the default Charset
        byte[] bytes = myString.getBytes();

        // Determine the encoding used
        Charset charset = Charset.defaultCharset();
        System.out.println("Used encoding: " + charset.displayName()+"String length: "+myString.length()+"Bytes used: "+bytes.length);
    }

    @Test
    void convertToBytes() {
    DataDiskIndex dataDiskIndex=new DataDiskIndex("te456456@#$%^st.cask",12365448L,56);
    byte[]testArray= dataDiskIndex.convertToBytes();


    DataDiskIndex dataDiskIndex1=convertBytesToDataDiskIndex(testArray);


// Use the retrieved values
        System.out.println(dataDiskIndex1.getFileName());


    }
}
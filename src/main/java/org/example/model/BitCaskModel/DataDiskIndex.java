package org.example.model.BitCaskModel;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class DataDiskIndex {
    private String fileName;

    //read from start offset
  private   Long offset;

    //either read until end of the Entry in the file (offset +Size)
   private int size;

    public DataDiskIndex(String fileName, Long offset, int size) {
        this.fileName = fileName;
        this.offset = offset;
        this.size = size;
    }
    public DataDiskIndex(){
    }



    byte [] convertToBytes() {

        // Calculate the total size of the byte array
        int totalSize = 4 + fileName.getBytes(StandardCharsets.UTF_8).length + 8 + 4;

        ByteBuffer buffer = ByteBuffer.allocate(totalSize);

        // Store the values in the byte array
        buffer.putInt(fileName.getBytes(StandardCharsets.UTF_8).length);
        buffer.put(fileName.getBytes(StandardCharsets.UTF_8));
        buffer.putLong(offset);
        buffer.putInt(size);

        // Convert the ByteBuffer to a byte array
        byte[] byteArray = buffer.array();

        return byteArray;
    }


    public static DataDiskIndex convertBytesToDataDiskIndex(byte[] value){
        DataDiskIndex dataDiskIndex=new DataDiskIndex();
        ByteBuffer buffer = ByteBuffer.wrap(value);

        // Retrieve the values from the byte array
        int fileNameLength = buffer.getInt();
        // Get the length of the file name
        byte[] fileNameBytes = new byte[fileNameLength];
        buffer.get(fileNameBytes); // Get the bytes representing the file name
        dataDiskIndex.setFileName( new String(fileNameBytes, StandardCharsets.UTF_8)); // Convert bytes to String
        dataDiskIndex.setOffset(buffer.getLong()); // Get the offset
        dataDiskIndex.setSize(buffer.getInt()); // Get the size

        return dataDiskIndex;
    }



    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Long getOffset() {
        return offset;
    }

    public String getFileName() {
        return fileName;
    }
}

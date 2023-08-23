package org.example.model.BitCaskModel;

public class DataDiskIndex {
    private String fileName;

    //read from start offset
  private   int offset;

    //either read until end of the Entry in the file (offset +Size)
   private int size;

    public DataDiskIndex(String fileName, int offset, int size) {
        this.fileName = fileName;
        this.offset = offset;
        this.size = size;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public String getFileName() {
        return fileName;
    }
}

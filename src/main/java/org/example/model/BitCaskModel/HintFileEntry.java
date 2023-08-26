package org.example.model.BitCaskModel;

import java.nio.ByteBuffer;

import static org.example.model.BitCaskModel.CaskFileEntry.convertBytesToCaskEntry;
import static org.example.model.BitCaskModel.DataDiskIndex.convertBytesToDataDiskIndex;

public class HintFileEntry {
    private Long key;
    // int caskFileEntryByteSize;
    private DataDiskIndex dataDiskIndex;

    public HintFileEntry(){}
    public HintFileEntry(Long key,DataDiskIndex dataDiskIndex){
        this.key=key;
        this.dataDiskIndex=dataDiskIndex;
    }


   public byte[] toByteArray(){
        byte[] dataDiskIndexByte= dataDiskIndex.convertToBytes();
        int totalSize=8+4+dataDiskIndexByte.length;
        ByteBuffer buffer = ByteBuffer.allocate(totalSize);
        buffer.putLong(key);
        buffer.putInt(dataDiskIndexByte.length);
        buffer.put(dataDiskIndexByte);

        return buffer.array();
    }

    public static HintFileEntry convertBytesToHintEntry(byte[] value) {
        HintFileEntry hintFileEntry=new HintFileEntry();

        ByteBuffer buffer = ByteBuffer.wrap(value);

        hintFileEntry.setKey(buffer.getLong());
        int dataDiskIndexByteSize= buffer.getInt();
        byte[] dataDiskIndexByte=new byte[dataDiskIndexByteSize];
        buffer.get(dataDiskIndexByte);

        DataDiskIndex dataDiskIndex =convertBytesToDataDiskIndex(dataDiskIndexByte);
        hintFileEntry.setDataDiskIndex(dataDiskIndex);

        return hintFileEntry;
    }



    public void setKey(Long key) {
        this.key = key;
    }

    public void setDataDiskIndex(DataDiskIndex dataDiskIndex) {
        this.dataDiskIndex = dataDiskIndex;
    }

    public Long getKey() {
        return key;
    }

    public DataDiskIndex getDataDiskIndex() {
        return dataDiskIndex;
    }
}

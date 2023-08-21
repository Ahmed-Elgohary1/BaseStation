package org.example.FileManagement.components;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.hadoop.conf.Configuration;
import org.example.model.WeatherStationMessage;



import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DiskManager {

    private static DiskManager instance;
    public static DiskManager getInstance() {
        if (instance == null) {
            synchronized (DiskManager.class) {
                if (instance == null) {
                    instance = new DiskManager( );
                }
            }
        }
        return instance;
    }

    private String stationDirectory;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setStationDirectory(String stationDirectory) {
        this.stationDirectory = stationDirectory;
    }

   public void writeWithParquetWriter(List<GenericRecord> records) throws IOException {

       Schema schema= WeatherStationMessage.getWeatherStationMessageAvroSchema();
       Configuration conf = new Configuration();
       FileSystem fileSystem = FileSystem.get(conf);
       ParquetWriter<GenericRecord> writer ;
       fileName = fileName.replaceAll(" ", "_");
       Path file=new Path(fileName);
       try {
           writer = AvroParquetWriter
                   .<GenericRecord>builder(file)
                   .withSchema(schema)
                //   .withDataModel(GenericData.get())
                   .withConf(conf)
                   .build();
       } catch (IOException e) {
           System.out.println("I haven't configured yet");
           throw new RuntimeException(e);
       }

       // Write each record to the Parquet file
//       for (GenericRecord record : records) {
//           try {
//               writer.write(record);
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//       }
//
//       // Close the writer
//       try {
//           writer.close();
//       } catch (IOException e) {
//           e.printStackTrace();
//       }


       try {


           for (GenericRecord record : records) {
               System.out.println(record.toString());
               writer.write(record);
           }
           writer.close();

       }
       catch (IOException e){
           System.out.println("An error occurred while writing to the file.");
           e.printStackTrace();
       }
       System.out.println("Wrote to parquet file!");


   }
   public void writeWithFileWriter(List<GenericRecord> records){



           try {
               FileWriter writer = new FileWriter(fileName);
               for(GenericRecord record:records) {
                   writer.write(record.toString()); // Write the content to the file
               }
               writer.close(); // Remember to close the writer
               System.out.println("Successfully wrote to the file.");
           } catch (IOException e) {
               System.out.println("An error occurred while writing to the file.");
               e.printStackTrace();
           }

   }

}

/*

<configuration>
<property>
<name></name>
<value></value>
</property>


*/
package com.team1.tm.bantutani.app.configuration;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@Configuration
public class StorageConfig {
    private Storage storage;
    @Value("bucket")
    private String bucket;
    @Value("directory")
    private String directory;

    @Value("subdirectory.news")
    private static String subNews;
    @Value("subdirectory.plants")
    private static String subPlants;
    @Value("subdirectory.planting")
    private static String subPlanting;
    @Value("subdirectory.diseases")
    private static String subDisease;
    @Value("subdirectory.weeds")
    private static String subWeeds;
    @Value("subdirectory.pests")
    private static String subPests;
    @Value("subdirectory.care")
    private static String subCare;
    @Value("subdirectory.user")
    private static String subUser;

    public enum SubDir {
        NEWS(subNews),
        PLANTS(subPlants),
        PLANTING(subPlanting),
        DISEASE(subDisease),
        WEEDS(subWeeds),
        PEST(subPests),
        CARE(subCare),
        USER(subUser);

        private String dirname;
        SubDir(String dirname) {
            this.dirname = dirname;
        }

        public String getDirname() {
            return dirname;
        }
    }

    StorageConfig(Storage storage){
        this.storage = storage;
    }

    public String addImage(MultipartFile file, String prefixName, SubDir subDir) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + prefixName + "." + ext;
        try {
            storage.create(
                    BlobInfo.newBuilder(bucket, directory+subDir.getDirname()+filename).build(),
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }

    public byte[] getImage(String filename, SubDir subDir) {
        Blob blob = storage.get(BlobId.of(bucket, directory + subDir.getDirname() + filename));
        if (blob != null) return blob.getContent();
        else throw new NullPointerException();
    }

    public void updateImage(MultipartFile file, String filename, SubDir subDir) throws IOException {
        Blob blob = storage.get(BlobId.of(bucket, directory + subDir.getDirname() + filename));
        WriteChannel wr = blob.writer();
        wr.write(ByteBuffer.wrap(file.getBytes()));
        wr.close();
    }

    public boolean deleteImage(String filename, SubDir subDir) {
        return storage.delete(BlobId.of(bucket, directory + subDir.getDirname() + filename));
    }
}

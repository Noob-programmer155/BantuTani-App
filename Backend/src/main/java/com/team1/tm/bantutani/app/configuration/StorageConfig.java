package com.team1.tm.bantutani.app.configuration;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import com.team1.tm.bantutani.app.configuration.storage.SubPathConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
public class StorageConfig {
    private Storage storage;
    private static SubPathConfiguration subPathConfigurations;
    @Value("bucket")
    private String bucket;
    @Value("directory")
    private String directory;

    public enum SubDir {
        NEWS(subPathConfigurations.getNews()),
        PLANTS(subPathConfigurations.getPlants()),
        PLANTING(subPathConfigurations.getPlanting()),
        DISEASE(subPathConfigurations.getDiseases()),
        WEEDS(subPathConfigurations.getWeeds()),
        PEST(subPathConfigurations.getPests()),
        CARE(subPathConfigurations.getCare()),
        USER(subPathConfigurations.getUser()),
        ANIMATION("animation"),
        AVATAR("avatar");


        private String dirname;
        SubDir(String dirname) {
            this.dirname = dirname;
        }

        public String getDirname() {
            return dirname;
        }
    }

    StorageConfig(Storage storage, SubPathConfiguration subPathConfiguration) {
        this.storage = storage;
        subPathConfigurations = subPathConfiguration;
    }

    public String addMedia(MultipartFile file, String prefixName, SubDir subDir) {
        try {
            DataFile dataFile = validateFile(file);
            String ext = dataFile.getExtension();
            String filename = UUID.randomUUID().toString() + prefixName + "." + ext;
            storage.create(
                    BlobInfo.newBuilder(bucket, directory+"/"+subDir.getDirname()+"/"+filename).build(),
                    dataFile.getData());
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getMedia(String filename, SubDir subDir) {
        Blob blob = storage.get(BlobId.of(bucket, directory+"/"+subDir.getDirname()+"/"+filename));
        if (blob != null) return blob.getContent();
        else throw new NullPointerException();
    }

    public void updateMedia(MultipartFile file, String filename, SubDir subDir) throws IOException {
        DataFile dataFile = validateFile(file);
        Blob blob = storage.get(BlobId.of(bucket, directory+"/"+subDir.getDirname()+"/"+filename));
        WriteChannel wr = blob.writer();
        wr.write(ByteBuffer.wrap(dataFile.getData()));
        wr.close();
    }

    public boolean deleteMedia(String filename, SubDir subDir) {
        return storage.delete(BlobId.of(bucket, directory+"/"+subDir.getDirname()+"/"+filename));
    }

    private DataFile validateFile(MultipartFile file) throws IOException {
        List<String> exts = Arrays.asList("jpg","jpeg","png","gif");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if(exts.contains(ext)) {
            if(ext.equalsIgnoreCase("gif")){
                return new DataFile(file.getOriginalFilename(), ext, file.getBytes());
            } else {
                BufferedImage bf = ImageIO.read(file.getInputStream());
                Image image = bf.getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
                BufferedImage img = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
                img.createGraphics().drawImage(image, 0, 0, null);
                ByteArrayOutputStream inputStream = new ByteArrayOutputStream();
                ImageIO.write(img, "jpq", inputStream);
                return new DataFile(file.getOriginalFilename(),"jpg",inputStream.toByteArray());
            }
        } else {
            throw new RuntimeException("File not Allowed !!!");
        }
    }

    private class DataFile {
        private String filename;
        private String extension;
        private byte[] data;

        public DataFile(String filename, String extension, byte[] data) {
            this.filename = filename;
            this.extension = extension;
            this.data = data;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }
}

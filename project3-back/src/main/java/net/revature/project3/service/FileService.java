package net.revature.project3.service;

import net.revature.project3.enumerator.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final S3Client s3Client;

    @Value("${s3.bucket}")
    private String s3Bucket;

    private final List<String> fileLocation = List.of("videos", "images");

    private final List<String> allowedImageTypes = List.of(
            "image/jpeg", "image/png", "image/jpg", "image/gif", "image/bmp", "image/ico",
            "image/tif", "image/tiff", "image/webp", "image/svg", "image/svgz", "image/ai", "image/drw",
            "image/pct", "image/psp", "image/xcf", "image/psd", "image/raw", "image/heic"
    );

    private final List<String> allowedVideoTypes = List.of(
            "video/mp4", "video/m4a", "video/m4b", "video/webm", "video/mov", "video/gif"
    );

    @Autowired
    public FileService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Upload a file to the server.
     * @param fileType The type of file to upload.
     * @param filePath The path to the file.
     * @param fileName The name of the file.
     * @return The URL of the uploaded file.
     * @throws IOException If an I/O error occurs.
     */
    public String uploadFile(FileType fileType, String filePath, String fileName) throws IOException {
        if (fileType == null || filePath == null || fileName == null) {
            throw new IllegalArgumentException("File path and name cannot be null");
        }

        if(s3Client == null) {
            logger.warn("The S3 client is null. Exiting out of upload file.");
            throw new IllegalArgumentException("S3 client cannot be null");
        }

        String pathBucketKey = "";

        long maxImageSize = 10 * 1024 * 1024;
        long maxVideoSize = 150 * 1024 * 1024;

        Path fromPath = Paths.get(filePath);
        long fileSize = Files.size(fromPath);
        String mimeType = Files.probeContentType(fromPath);

        switch (fileType) {
            case IMAGE:
                if (!allowedImageTypes.contains(mimeType)) {
                    logger.warn("Unsupported image type: {}", mimeType);
                    throw new IllegalArgumentException("Unsupported image type: " + mimeType);
                }

                if (fileSize > maxImageSize) {
                    logger.warn("Image exceeds maximum size: {} bytes", fileSize);
                    throw new IllegalArgumentException("Image exceeds maximum size of 10 MB");
                }

                pathBucketKey = "images/" + fileName;
                break;

            case VIDEO:

                if (!allowedVideoTypes.contains(mimeType)) {
                    logger.warn("Unsupported video type: {}", mimeType);
                    throw new IllegalArgumentException("Unsupported video type: " + mimeType);
                }

                if (fileSize > maxVideoSize) {
                    logger.warn("Video exceeds maximum size: {} bytes", fileSize);
                    throw new IllegalArgumentException("Video exceeds maximum size of 100 MB");
                }
                pathBucketKey = "videos/" + fileName;
                break;

            default:
                logger.error("Unsupported file type: {}", fileType);
                return null;
        }

        try
        {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(pathBucketKey)
                    .build();

            s3Client.putObject(putOb, RequestBody.fromFile(fromPath));
        }
        catch (RuntimeException e) {
            logger.error("Error while uploading file: ", e);
        }

        String url = "https://" + s3Bucket + ".s3." + "us-east-2" + ".amazonaws.com/" + pathBucketKey;
        return url.replace("\"", "");
    }

    /**
     * Create a new file and a temporary file from multipart file.
     * @param file Take in the file to set the new media URL.
     * @throws IOException If it fails to create a file it throws the exception.
     */
    public String createFile(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()){
            return "";
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        if (extension.isEmpty()) {
            throw new IllegalArgumentException("File must have an extension");
        }

        String uniqueFileName = UUID.randomUUID() + extension;
        String contentType = file.getContentType();
        Path tempFile = Files.createTempFile(uniqueFileName, "." + extension);

        FileType fileType;
        if (allowedVideoTypes.contains(contentType)) {
            fileType = FileType.VIDEO;
        } else if (allowedImageTypes.contains(contentType)) {
            fileType = FileType.IMAGE;
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + contentType);
        }

        try{
            file.transferTo(tempFile.toFile());
        } catch (IOException e) {
            logger.error("Error while moving multipart file: ", e);
        }

        String mediaUrl = "";
        try{
            mediaUrl = uploadFile(fileType, tempFile.toFile().getPath(), uniqueFileName);
        } catch (RuntimeException e) {
            logger.error("Illegal Statement: Error while uploading file to S3.");
        }

        Files.deleteIfExists(tempFile);
        return mediaUrl;
    }

    /**
     * Used to delete objects from the S3 Bucket.
     *
     * @param urlPath Take in a file path to find to create an object key.
     */
    public void deleteFile(String urlPath) {
        if(urlPath == null || urlPath.isEmpty() || "null".equals(urlPath)){
            return;
        }

        String objectKey = "";
        if(urlPath.contains("videos")){
            objectKey = urlPath.substring(urlPath.indexOf("videos/"));
        } else if(urlPath.contains("images")){
            objectKey = urlPath.substring(urlPath.indexOf("images/"));
        } else {
            throw new IllegalArgumentException("Unsupported url path: " + urlPath);
        }

        try
        {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(s3Bucket)
                    .key(objectKey)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        }
        catch (RuntimeException e) {
            logger.error("Illegal Statement: Error while deleting file to S3.");
        }

    }
}
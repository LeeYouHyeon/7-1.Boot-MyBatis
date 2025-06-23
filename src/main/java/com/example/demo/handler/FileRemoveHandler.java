package com.example.demo.handler;

import com.example.demo.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileRemoveHandler {

    private final String UP_DIR = "D:\\web_0226_lyh\\_myProject\\_java\\_fileUpload\\";

    public boolean deleteFile(FileVO fileVO) {
        boolean isDel = false;
        File fileDir = new File(UP_DIR + File.separator, fileVO.getSaveDir());
        try {
            String deleteFileName = fileVO.getUuid() + "_" + fileVO.getFileName();
            File removeFile = new File(fileDir, deleteFileName);
            log.info(">>> delete file >> {}", deleteFileName);

            if (!removeFile.exists()) {
                log.info(">>> file not found ");
                return isDel;
            }
            isDel = removeFile.delete();
            if (fileVO.getFileType() == 1) {
                String deleteThumbName = fileVO.getUuid() + "_th_" + fileVO.getFileName();
                log.info(">>> delete thumb >> {}", deleteThumbName);
                File removeThumbFile = new File(fileDir, deleteThumbName);
                isDel = removeThumbFile.delete();
            }
        } catch (Exception e) {
            log.info(">>> file remove error");
            e.printStackTrace();
        }
        return isDel;
    }
}

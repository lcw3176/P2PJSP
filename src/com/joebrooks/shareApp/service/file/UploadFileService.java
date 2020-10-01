package com.joebrooks.shareApp.service.file;

import java.io.File;

import com.joebrooks.shareApp.common.ByteConverter;
import com.joebrooks.shareApp.common.FolderSize;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.FileDTO;

// 파일 업로드
public class UploadFileService implements IFileService {

	@Override
	public void execute(FileDTO dto) {
		
		FileDAO dao = FileDAO.getInstance();
		dao.insert(dto);
		
		// 폴더에 소속된 파일이면 폴더 용량 업데이트
		if (!dto.getPath().equals("")) {
			String filePath = "";
			for (int i = 0; i < dto.getPath().split("\\\\").length; i++) {
				String folderName = dto.getPath().split("\\\\")[i];
				File tempFolder = new File("D:\\uploadFile\\" + dto.getId() + "\\" + filePath + folderName);
				ByteConverter converter = new ByteConverter();
				String updateFolderSize = converter.byteCalculation(String.valueOf(new FolderSize().getSize(tempFolder)));
				FileDTO updateFolder = new FileDTO(dto.getId(), folderName, updateFolderSize, "folder", filePath);
				
				FileDAO tempdao = FileDAO.getInstance();
				tempdao.update(updateFolder);

				filePath += folderName + "\\";
			}
		}
		
	}

}

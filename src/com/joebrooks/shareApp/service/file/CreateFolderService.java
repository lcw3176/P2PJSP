package com.joebrooks.shareApp.service.file;

import java.io.File;

import com.joebrooks.shareApp.common.ByteConverter;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.FileDTO;

// 폴더 새로 만들기
public class CreateFolderService implements IFileService {

	File folder;
	
	public CreateFolderService(File folder) {
		this.folder = folder;
	}

	@Override
	public void execute(FileDTO dto) {

		if (!folder.exists()) {
			folder.mkdir();

			ByteConverter converter = new ByteConverter();
			String size = converter.byteCalculation(String.valueOf(folder.length()));
			dto.setSize(size);
			
			FileDAO dao = FileDAO.getInstance();
			dao.insert(dto);

		}

	}

}

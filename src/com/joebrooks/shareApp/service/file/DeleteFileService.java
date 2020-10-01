package com.joebrooks.shareApp.service.file;

import java.io.File;

import com.joebrooks.shareApp.common.ByteConverter;
import com.joebrooks.shareApp.common.FolderSize;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.FileDTO;

// 파일 삭제
public class DeleteFileService implements IFileService {

	@Override
	public void execute(FileDTO dto) {
		String path = "D:\\uploadFile\\" + dto.getId() + "\\" + dto.getPath() + dto.getName();
		
		File file = new File(path);
		file.delete();
		
		
		FileDAO dao = FileDAO.getInstance();
		dao.delete(dto);
		
		// 파일과 연관된 폴더들 용량 표기 새로 업데이트
		if (!dto.getPath().equals("")) {
			
			String addPath = "";

			for (int i = 0; i < dto.getPath().split("\\\\").length; i++) {
				
				addPath += dto.getPath().split("\\\\")[i] + "\\";
				File folder = new File("D:\\uploadFile\\" + dto.getId() + "\\" + addPath);
				ByteConverter con = new ByteConverter();
				String size = con.byteCalculation(String.valueOf((new FolderSize().getSize(folder))));
				FileDTO changedto = new FileDTO();
				changedto.setId(dto.getId());
				changedto.setName(folder.getName());
				changedto.setPath(addPath.substring(0, addPath.length() - (folder.getName().length() + 1)));
				changedto.setSize(size);
				changedto.setType("folder");
				
				
				FileDAO temp = FileDAO.getInstance();
				temp.update(changedto);
				
				
				
			}

		}
		
	}
}

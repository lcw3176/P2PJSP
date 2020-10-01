package com.joebrooks.shareApp.service.file;

import java.io.File;

import com.joebrooks.shareApp.common.ByteConverter;
import com.joebrooks.shareApp.common.FileType;
import com.joebrooks.shareApp.common.FolderSize;
import com.joebrooks.shareApp.model.dao.FileDAO;
import com.joebrooks.shareApp.model.dto.FileDTO;


// 폴더 삭제 서비스
public class DeleteFolderService implements IFileService {

	@Override
	public void execute(FileDTO dto) {
		String path = "D:\\uploadFile\\" + dto.getId() + "\\" + dto.getPath() + dto.getName();


		if (dto.getPath().equals("")) {
			deleteFolder(path, dto);
			
		} else { // 하위폴더인 경우 상위폴더를 용량 업데이트 해준다
			deleteFolder(path, dto);
			String dynamicPath = "";
			String folderName = "";
			String[] paths = dto.getPath().split("\\\\");
			int length = paths.length;
			for (int i = 0; i < length; i++) {
				System.out.println(i);
				if (i == 0) {
					dynamicPath = "";
					folderName = paths[i] + "\\";
				} else {
					dynamicPath = folderName;
					folderName += paths[i];
				}
				

				File file = new File("D:\\uploadFile\\" + dto.getId() + "\\" + folderName);
				
				ByteConverter converter = new ByteConverter();
				String size = converter.byteCalculation(String.valueOf((new FolderSize().getSize(file))));
				dto.setSize(size);
				dto.setName(paths[i]);
				dto.setPath(dynamicPath);
				
				FileDAO dao = FileDAO.getInstance();
				dao.update(dto);

			}

		}
	

	}

	private void deleteFolder(String path, FileDTO dto) {

		File folder = new File(path);
		try {
			if (folder.exists()) {
				File[] folder_list = folder.listFiles();

				for (int i = 0; i < folder_list.length; i++) {
					if (folder_list[i].isFile()) {
						folder_list[i].delete();

						FileDTO dtoFile = new FileDTO();
						String type = folder_list[i].getName().substring(folder_list[i].getName().lastIndexOf(".") + 1);
						dtoFile.setId(dto.getId());
						dtoFile.setType(new FileType().getType(type));
						dtoFile.setName(folder_list[i].getName());
						dtoFile.setPath(dto.getPath() + folder.getName() + "\\");
						
						// 지워진 파일 DB에서도 삭제
						FileDAO tempdao = FileDAO.getInstance();
						tempdao.delete(dtoFile);

					} else {
						FileDTO dtoFolder = new FileDTO();
						dtoFolder.setId(dto.getId());
						dtoFolder.setName(folder_list[i].getName());
						dtoFolder.setPath(dto.getPath() + folder.getName() + "\\");
						dtoFolder.setType("folder");
						deleteFolder(folder_list[i].getPath(), dtoFolder);
					}
				}

				folder.delete(); // 폴더 삭제
				
				FileDAO dao = FileDAO.getInstance();
				dao.delete(dto);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	



}

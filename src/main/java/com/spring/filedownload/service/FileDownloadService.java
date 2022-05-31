package com.spring.filedownload.service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.spring.filedownload.exception.MyFileNotFoundException;

@Service
public class FileDownloadService {

	@Value("${file.directory}")
	private String fileLocation;

	public Resource loadFileAsResource(String fileName) throws MyFileNotFoundException {
		try {

			Path filePath = Paths.get(fileLocation + File.separator + fileName).toAbsolutePath().normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}

}

package com.spring.filedownload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.filedownload.exception.MyFileNotFoundException;
import com.spring.filedownload.service.FileDownloadService;

@Controller
@RequestMapping("/api")
public class FileDownloadController {

	@Autowired
	private FileDownloadService fileDownloadService;

	@GetMapping("/showPage")
	public ModelAndView showPage() {
		return new ModelAndView("download-page");
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
		Resource resource = null;
		String contentType = null;
		try {
			resource = fileDownloadService.loadFileAsResource(fileName);
			if (resource == null) {
				return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
			}
		} catch (MyFileNotFoundException e) {
			return ResponseEntity.internalServerError().build();
		}

		contentType = "application/octet-stream";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

}

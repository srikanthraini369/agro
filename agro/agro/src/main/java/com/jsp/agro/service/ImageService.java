package com.jsp.agro.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.agro.dao.ImageDao;
import com.jsp.agro.dao.UserDao;
import com.jsp.agro.entity.Image;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.IdNotFoundException;
import com.jsp.agro.exception.ImageNotFoundException;
import com.jsp.agro.util.ImageUtil;
import com.jsp.agro.util.ResponseStructure;

@Service
public class ImageService {

	@Autowired
	private ImageDao imagedao;
	@Autowired
	private UserDao dao;

	ResponseStructure<Image> rs = new ResponseStructure<Image>();

	public ResponseEntity<ResponseStructure<Image>> uploadImage(int id, MultipartFile file) throws IOException {
		User db = dao.findUserById(id);
		if (db != null) {
			if (db.getImage() != null) {
				return updateImage(db.getImage().getId(), file);
			}
			Image image = new Image();
			image.setName(file.getOriginalFilename());
			image.setData(ImageUtil.compressImage(file.getBytes()));
			db.setImage(image);
			dao.updateUser(db);
			rs.setData(image);
			rs.setMessage("Image uploaded successfully");
			rs.setStatus(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Image>>(rs, HttpStatus.ACCEPTED);
		} else {
			throw new IdNotFoundException("User not found with id :" + id);
		}
	}

	public ResponseEntity<ResponseStructure<Image>> updateImage(int id, MultipartFile file) throws IOException {
		Image image = new Image();
		image.setId(id);
		image.setName(file.getOriginalFilename());
		image.setData(file.getBytes());
		imagedao.updateUser(image);
		rs.setMessage("Image update successfull");
		rs.setStatus(HttpStatus.ACCEPTED.value());
		rs.setData(image);
		return new ResponseEntity<ResponseStructure<Image>>(rs, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<byte[]> fetchImageById(int id) {
		Image image = imagedao.fetchImageById(id);
		if (image != null) {
			byte[] imagebyte=image.getData();
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<>(imagebyte, headers,HttpStatus.FOUND);
		} else {
			throw new ImageNotFoundException("Image not found with id :" + id);
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteImageById(int id) {
		ResponseStructure<String> rss = new ResponseStructure<String>();
		Image image = imagedao.fetchImageById(id);
		if (image != null) {
			User img = dao.findByImage(image);
			if (img != null) {
				img.setImage(null);
				dao.updateUser(img);
			}
			imagedao.deleteImage(image);
			rss.setData(image.getName() + "Delete Successfully...");
			rss.setMessage("Image delete successfully");
			rss.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(rss, HttpStatus.FOUND);
		} else {
			throw new ImageNotFoundException("Image not found with id :" + id);
		}
	}
}

package com.jsp.agro.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.agro.entity.Image;
import com.jsp.agro.repo.ImageRepo;

@Repository
public class ImageDao {

	@Autowired
	private ImageRepo imagerepo;

	public Image saveImage(Image image) {
		return imagerepo.save(image);
	}

	public Image fetchImageById(int id) {
		Optional<Image> db = imagerepo.findById(id);
		if (db.isPresent())
			return db.get();
		else
			return null;
	}

//	public List<Image> fetchAll() {
//		return imagerepo.findAll();
//	}
	public Image updateUser(Image image) {
//		return imagerepo.save(image);

		Optional<Image> db = imagerepo.findById(image.getId());
		if (db.isPresent()) {
			Image imagedb = db.get();
			if (image.getName() == null) {
				image.setName(imagedb.getName());
			}
			if (image.getData() == null) {
				image.setData(imagedb.getData());
			}
			return imagerepo.save(image);
		} else {

		}
		return image;
	}

	public void deleteImage(Image image) {
		imagerepo.delete(image);

	}

//	public Image deleteById(int id) {
//		Optional<Image> db = imagerepo.findById(id);
//		if(db.isPresent()) {
//			imagerepo.deleteById(id);
//			return db.get();
//		}else {
//		return null;
//		}
//	}

}

package ecommerce.service.storage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import ecommerce.exception.FileException;

@Service
public class CloudinaryPhotoStorageService implements PhotoStorageService {

	@Override
	public String upload(byte[] file) {
		try {
			Cloudinary cloudinary = new Cloudinary();
			String filename = UUID.randomUUID().toString();
			var map = cloudinary.uploader().upload(file, ObjectUtils.asMap("public_id", filename));
			return (String) map.get("url");
		} catch (IOException e) {
			throw new FileException("Não foi possível salvar o arquivo");
		}
	}

	@Override
	public void delete(String photo) {
		if (Objects.isNull(photo) || photo.isBlank()) {
			return;
		}
		try {
			Cloudinary cloudinary = new Cloudinary();
			cloudinary.uploader().destroy(publicId(photo), ObjectUtils.emptyMap());
		} catch (IOException e) {
			throw new FileException("Não foi possível atualizar o arquivo");
		}
	}

	private String publicId(String photo) {
		return photo.substring(photo.lastIndexOf('/') + 1, photo.lastIndexOf('.'));
	}

}

package ecommerce.service.storage;

public interface PhotoStorageService {

	public String upload(byte[] file);

	public void delete(String photo);

}

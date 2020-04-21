package ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Recurso não encontrado");
	}

}

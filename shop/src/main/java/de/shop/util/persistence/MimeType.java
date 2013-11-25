package de.shop.util.persistence;


public enum MimeType {
	JPEG("image/jpeg"),
	PJPEG("image/pjpeg"),
	PNG("image/png"),
	GIF("image/gif"),
	MP4("video/mp4"),
	WEBM("video/webm"),
	WAV("audio/wav");

	
	private final String value;

	private MimeType(String value) {
		this.value = value;
	}
	
	public static MimeType build(String value) {
		if (value == null) {
			return null;
		}
		
		switch (value) {
			case "image/jpeg":	return JPEG;
			case "image/pjpeg":	return PJPEG;
			case "image/png":	return PNG;
			case "image/gif":	return GIF;
			case "video/mp4":	return MP4;
			case "video/webm":  return WEBM;
			case "audio/wav":	return WAV;
			
			default:			return null;
		}
	}
	
	public String getExtension() {
		switch (this) {
			case JPEG:	return "jpeg";
			case PJPEG:	return "jpeg";
			case PNG:	return "png";
			case GIF:	return "gif";
			case MP4:	return "mp4";
			case WEBM:	return "webm";
			default:	throw new IllegalStateException("Der MIME-Type " + this + " wird nicht unterstuetzt");
		}
	}
	
	public static MimeType buildFromExtension(String extension) {
		switch (extension) {
			case "jpeg":	return JPEG;
			case "png":		return PNG;
			case "gif":		return GIF;
			case "mp4":		return MP4;
			case "webm":	return WEBM;
			default:	 	throw new IllegalStateException("Die Extension " + extension + " wird nicht unterstuetzt");
		}
	}
	
	public MultimediaType getMultimediaType() {
		if (value.startsWith("image/")) {
			return MultimediaType.IMAGE;
		}
		if (value.startsWith("video/")) {
			return MultimediaType.VIDEO;
		}
		if (value.startsWith("audio/")) {
			return MultimediaType.AUDIO;
		}
		
		throw new IllegalStateException("Der MultimediaType " + this + " wird nicht unterstuetzt");
	}
	
	@Override
	public String toString() {
		return value;
	}
}

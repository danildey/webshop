package com.epam.preprod.web.bean;

public class Avatar {
	private String fileName;

	private String extension;

	private byte[] image;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Avatar{" +
				"fileName='" + fileName + '\'' +
				", extension='" + extension + '\'' +
				'}';
	}
}

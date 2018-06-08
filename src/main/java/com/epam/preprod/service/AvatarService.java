package com.epam.preprod.service;

import com.epam.preprod.web.bean.Avatar;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface AvatarService {
	void saveAvatar(Avatar avatar, String fileName) throws IOException;

	BufferedImage getAvatar(String avatarFile) throws IOException;

	boolean isImageExist(String avatarFileName) throws IOException;
}

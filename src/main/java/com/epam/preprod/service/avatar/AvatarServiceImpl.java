package com.epam.preprod.service.avatar;

import com.epam.preprod.service.AvatarService;
import com.epam.preprod.web.bean.Avatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class AvatarServiceImpl implements AvatarService {
	private static final Logger LOG = LoggerFactory.getLogger(AvatarServiceImpl.class);

	private final File AVATAR_FOLDER;

	public AvatarServiceImpl(File folder) {
		this.AVATAR_FOLDER = folder;
	}

	@Override
	public void saveAvatar(Avatar avatar, String newAvatarName) throws IOException {
		InputStream imageStream = new ByteArrayInputStream(avatar.getImage());
		File avatarFileDestination = new File(AVATAR_FOLDER, newAvatarName + "." + avatar.getExtension());
		Files.copy(imageStream, avatarFileDestination.toPath());

		LOG.debug("Save avatar to path :" + avatar.getFileName() + " -->" + avatarFileDestination);
	}

	@Override
	public boolean isImageExist(String avatarFileName) throws IOException {
		File avatarFile = new File(AVATAR_FOLDER, avatarFileName);
		return avatarFile.exists();
	}

	@Override
	public BufferedImage getAvatar(String avatarFileName) throws IOException {
		File avatarFile = new File(AVATAR_FOLDER, avatarFileName);

		LOG.debug("Read image from Avatar file  : avatarFile --> {}", avatarFile);
		return ImageIO.read(avatarFile);
	}
}

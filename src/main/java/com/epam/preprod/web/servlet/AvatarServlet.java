package com.epam.preprod.web.servlet;

import com.epam.preprod.entity.User;
import com.epam.preprod.service.AvatarService;
import com.epam.preprod.service.avatar.AvatarServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/avatar")
public class AvatarServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(AvatarServlet.class);

	private static final String DEFAULT_AVATAR = "default.png";
	private static final String DEFAULT_EXTENSION = "png";

	private static final String AVATAR_SERVICE = "avatarService";

	private static final String USER_BEAN = "userBean";

	private AvatarService avatarService;

	@Override
	public void init() throws ServletException {
		avatarService = (AvatarServiceImpl) getServletContext().getAttribute(AVATAR_SERVICE);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("doGet() called");

		User user = (User) req.getSession().getAttribute(USER_BEAN);
		String imagePath = user.getEmail() + "." + user.getUserAvatarExtension();

		RenderedImage renderedImage;
		if (Objects.nonNull(user.getUserAvatarExtension()) && avatarService.isImageExist(imagePath)) {
			renderedImage = avatarService.getAvatar(imagePath);
			ImageIO.write(renderedImage, user.getUserAvatarExtension(), resp.getOutputStream());
		} else {
			renderedImage = avatarService.getAvatar(DEFAULT_AVATAR);
			ImageIO.write(renderedImage, DEFAULT_EXTENSION, resp.getOutputStream());
		}

		LOG.debug("doGet() finished");
	}
}

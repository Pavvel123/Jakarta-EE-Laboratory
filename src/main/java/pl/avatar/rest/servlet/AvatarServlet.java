package pl.avatar.rest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pl.avatar.domain.service.AvatarService;
import pl.user.domain.service.UserService;
import pl.user.persistance.id.UserId;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/api/avatars/*")
public class AvatarServlet extends HttpServlet {

    private AvatarService avatarService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.avatarService = (AvatarService) getServletContext().getAttribute("avatarService");
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    private UserId extractUserId(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        try {
            return UserId.of(UUID.fromString(pathInfo.substring(1)));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserId userId = extractUserId(req.getPathInfo());
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        byte[] avatar = avatarService.getAvatar(userId);
        if (avatar != null) {
            resp.setContentType("image/png");
            resp.getOutputStream().write(avatar);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserId userId = extractUserId(req.getPathInfo());
        
        if (userId == null || userService.findUser(userId).isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        avatarService.saveAvatar(userId, req.getInputStream());
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserId userId = extractUserId(req.getPathInfo());
        
        if (userId != null && avatarService.deleteAvatar(userId)) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
package pl.user.rest.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pl.user.domain.service.UserService;
import pl.user.persistance.id.UserId;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/api/users/*")
public class UserRestServlet extends HttpServlet
{
    @Inject
    private UserService userService;

    private Jsonb jsonb;

    @Override
    public void init() throws ServletException
    {
        this.jsonb = JsonbBuilder.create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/"))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonb.toJson(userService.findAllUsers()));
        } else {
            try {
                UserId id = UserId.of(UUID.fromString(pathInfo.substring(1))); // Usuwamy pierwszy znak '/'
                userService.findUser(id).ifPresentOrElse(
                    user -> {
                        try {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write(jsonb.toJson(user));
                        } catch (IOException e) {
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    },
                    () -> response.setStatus(HttpServletResponse.SC_NOT_FOUND)
                );
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
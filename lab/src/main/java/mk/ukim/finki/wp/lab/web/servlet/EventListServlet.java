package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.service.impl.EventServiceImplementation;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventListServlet", urlPatterns = "")
public class EventListServlet extends HttpServlet {
    private final EventServiceImplementation eventService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventListServlet(EventServiceImplementation eventService, SpringTemplateEngine springTemplateEngine) {
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String searchName = req.getParameter("searchName");
        String searchRatingStr = req.getParameter("searchRating");
        String locationName = req.getParameter("location");

        List<Event> events = eventService.listAll();

        if (searchName != null && !searchName.isEmpty()) {
            events = eventService.searchByName(searchName, events);
        }

        if (searchRatingStr != null && !searchRatingStr.isEmpty()) {
            try {
                Integer searchRating = Integer.parseInt(searchRatingStr);
                events = eventService.searchByRating(searchRating, events);
            } catch (NumberFormatException e) {
                // Handle invalid input for rating if necessary
            }
        }
        if (locationName != null && !locationName.isEmpty()) {
            events = eventService.searchByLocation(locationName, events);
        }

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("user-agent"));
        context.setVariable("events", events);

        Integer userViews = (Integer) getServletContext().getAttribute("userViews");
        getServletContext().setAttribute("userViews", ++userViews);
        context.setVariable("userViews", getServletContext().getAttribute("userViews"));


        springTemplateEngine.process("listEvents.html", context, resp.getWriter());
    }
}

package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.service.impl.EventBookingServiceImplementation;
import mk.ukim.finki.wp.lab.service.impl.EventServiceImplementation;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "EventBookingServlet", urlPatterns = "/eventBookingServlet")
public class EventBookingServlet extends HttpServlet {
    private final EventBookingServiceImplementation eventBookingService;
    private final EventServiceImplementation eventService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventBookingServlet(EventBookingServiceImplementation eventBookingService, EventServiceImplementation eventService, SpringTemplateEngine springTemplateEngine) {
        this.eventBookingService = eventBookingService;
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName = req.getParameter("selectedEvent");
        Event event = eventService.findByName(eventName).orElseThrow(ServletException::new);

        String attendeeName = req.getParameter("userName");
        String numberOfTickets = req.getParameter("numTickets");

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("user-agent"));
        context.setVariable("event", event);
        context.setVariable("attendeeName", attendeeName);
        context.setVariable("numberOfTickets", numberOfTickets);

        eventBookingService.placeBooking(eventName, attendeeName, req.getRemoteAddr(), Integer.parseInt(numberOfTickets));

        springTemplateEngine.process("bookingConfirmation.html", context, resp.getWriter());
    }
}

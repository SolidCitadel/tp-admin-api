package solidcitadel.transitplannermanager.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindTicketService {
    private final TicketRepository ticketRepository;

}

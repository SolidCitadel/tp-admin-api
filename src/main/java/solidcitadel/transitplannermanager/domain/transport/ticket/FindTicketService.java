package solidcitadel.transitplannermanager.domain.transport.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindTicketService {
    private final TicketRepository ticketRepository;

}

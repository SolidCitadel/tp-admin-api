package solidcitadel.tp.admin.api.ticket;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TicketRepository {
    private static final Map<Long, Ticket> store = new HashMap<>();

    private static long sequence = 0L;

    public Ticket save(Ticket ticket) {
        ticket.setId(++sequence);
        store.put(ticket.getId(), ticket);
        return ticket;
    }

    public Ticket findById(Long id) {
        return store.get(id);
    }

    public List<Ticket> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}

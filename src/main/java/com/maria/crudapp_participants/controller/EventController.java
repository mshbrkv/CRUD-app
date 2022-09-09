package com.maria.crudapp_participants.controller;

import com.maria.crudapp_participants.dto.EventDTO;
import com.maria.crudapp_participants.dto.ShortEvent;
import com.maria.crudapp_participants.entity.Event;
import com.maria.crudapp_participants.entity.Market;
import com.maria.crudapp_participants.facade.EventFacade;
import com.maria.crudapp_participants.selections.Selection;
import com.maria.crudapp_participants.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/events")
public class EventController {

    private final EventFacade eventFacade;
    private final EventService eventService;

    @GetMapping
    public Page<EventDTO> getEventList(Pageable pageable) {
        return eventFacade.getEventList(pageable);
    }

    @GetMapping("{id}")
    public EventDTO getEventById(@PathVariable("id") UUID eventId) {
        return eventFacade.getEventById(eventId);
    }

    @GetMapping("/in_play=true")
    public Page<EventDTO> getEventInPlay(Pageable pageable) {
        return eventFacade.getEventInPlay(pageable);
    }


    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO event) {
        return eventFacade.saveEvent(event);
    }

    @PutMapping("{id}")
    public EventDTO updateEvent(@RequestBody EventDTO newEvent, @PathVariable UUID id) {
        return eventFacade.updateEvent(newEvent, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") UUID eventId) {
        eventFacade.deleteEventById(eventId);
    }

    @GetMapping("search/by_market")
    public Page<EventDTO> getEventsByMarketName(Pageable pageable, @RequestParam String marketName) {
        return eventFacade.getEventsByMarketName(pageable, marketName);
    }

    @GetMapping("search/by_participant")
    public Page<EventDTO> getEventsByParticipantName(Pageable pageable, @RequestParam String participantName) {
        return eventFacade.getEventsByParticipantsName(pageable, participantName);
    }

    @GetMapping("search/by_price_range")
    public Page<EventDTO> findEventsByPriceRange(Pageable pageable, @RequestParam BigDecimal from, @RequestParam BigDecimal to) {
        return eventFacade.findEventsByPriceRange(from, to, pageable);
    }

    @GetMapping("sorted_by_price")
    public Page<EventDTO> getSortedEventsByPrice(Pageable pageable) {
        return eventFacade.getSortedEventsByPrice(pageable);
    }

    @GetMapping("search/without")
    public Page<EventDTO> getEventsWithNMarketsAndNotSport(Pageable pageable, @RequestParam String sport, @RequestParam Integer n) {
        return eventFacade.getEventsWithNMarketsAndNotSport(pageable, sport, n);
    }

    @GetMapping("prices/{id}")
    public List<BigDecimal> priceOfEvent(@PathVariable UUID id) {
        return eventFacade.priceOfEvent(id);
    }

    @GetMapping("pricesMarkets")
    public   List<Selection> getAveragePricesForPreMatchMarkets() {
        return eventFacade.getAveragePricesForPreMatchMarkets();
    }

    @GetMapping("shortEvents")
    public List<ShortEvent> getAllShortEvents(){
        return eventService.allShortEvent();
    }

    @GetMapping("names")
    public  List<Event> getDuplicate(){
        return eventService.getEventsWithDuplicatedParticipantAndDifferentYears();
    }

}

package com.maria.crudapp_participants.facade;

import com.maria.crudapp_participants.dto.EventDTO;
import com.maria.crudapp_participants.entity.Event;
import com.maria.crudapp_participants.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventFacadeImpl implements EventFacade {

    private final EventService eventService;
    private final Converter<Event, EventDTO> eventToEventDTOConverter;
    private final Converter<EventDTO, Event> eventDTOToEventConverter;

    @Override
    public Page<EventDTO> getEventList(Pageable pageable) {
        Page<Event> allEvent = eventService.getAllEventList(pageable);
        List<EventDTO> eventDTO = allEvent.getContent().stream().map(eventToEventDTOConverter::convert).toList();
        return new PageImpl<>(eventDTO, allEvent.getPageable(), allEvent.getTotalElements());
    }

    @Override
    public EventDTO getEventById(UUID eventId) {
        Event event = eventService.findEventById(eventId);
        return eventToEventDTOConverter.convert(event);
    }

    @Override
    public Page<EventDTO> getEventInPlay(Pageable pageable) {
       Page <Event> event=eventService.getEventInPlay(pageable);
        List<EventDTO> eventDTO=event.getContent().stream().map(eventToEventDTOConverter::convert).toList();
        return new PageImpl<>(eventDTO, event.getPageable(), event.getTotalElements());
    }

    @Override
    public EventDTO saveEvent(EventDTO event) {
      Event convert=eventDTOToEventConverter.convert(event);
      Event event1=eventService.saveEvent(convert);
      return eventToEventDTOConverter.convert(event1);
    }

    @Override
    public void deleteEventById(UUID eventId) {
        eventService.deleteEventById(eventId);
    }

    @Override
    public EventDTO updateEvent(EventDTO newEvent, UUID eventId) {
        Event convert=eventDTOToEventConverter.convert(newEvent);
        Event event=eventService.updateEvent(convert, newEvent.getId());
        return eventToEventDTOConverter.convert(event);
    }
}
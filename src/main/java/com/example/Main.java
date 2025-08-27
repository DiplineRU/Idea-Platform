package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.model.Ticket;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Чтение JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/main/resources/tickets.json"));
        JsonNode ticketsNode = root.get("tickets");
        Ticket[] tickets = mapper.treeToValue(ticketsNode, Ticket[].class);

        // 2. Фильтрация по маршруту Владивосток → Тель-Авив
        List<Ticket> filteredTickets = Arrays.stream(tickets)
                .filter(t -> t.getOrigin().equals("VVO") && t.getDestination().equals("TLV"))
                .collect(Collectors.toList());

        // 3. Минимальное время полета для каждого перевозчика
        Map<String, Long> minFlightTimes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

        for (Ticket ticket : filteredTickets) {
            LocalDateTime departure = LocalDateTime.parse(ticket.getDeparture_date() + " " + ticket.getDeparture_time(), formatter);
            LocalDateTime arrival = LocalDateTime.parse(ticket.getArrival_date() + " " + ticket.getArrival_time(), formatter);
            long durationMinutes = Duration.between(departure, arrival).toMinutes();

            minFlightTimes.merge(ticket.getCarrier(), durationMinutes, Math::min);
        }

        System.out.println("Минимальное время полета (минуты) для каждого авиаперевозчика:");
        for (Map.Entry<String, Long> entry : minFlightTimes.entrySet()) {
            long hours = entry.getValue() / 60;
            long minutes = entry.getValue() % 60;
            System.out.printf("%s: %d ч %d мин%n", entry.getKey(), hours, minutes);
        }

        // 4. Средняя цена и медиана
        List<Integer> prices = filteredTickets.stream().map(Ticket::getPrice).sorted().collect(Collectors.toList());

        double average = prices.stream().mapToInt(Integer::intValue).average().orElse(0);
        double median;
        int size = prices.size();
        if (size % 2 == 0) {
            median = (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            median = prices.get(size / 2);
        }

        System.out.printf("%nСредняя цена: %.2f%n", average);
        System.out.printf("Медиана: %.2f%n", median);
        System.out.printf("Разница (средняя - медиана): %.2f%n", average - median);
    }
}
package data_access;

import entity.Event;
import use_case.import_calendar.CalendarImportException;
import use_case.import_calendar.CalendarSourceGateway;
import use_case.import_calendar.ImportCalendarInputData;
import use_case.import_calendar.ImportSourceType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IcsCalendarGateway implements CalendarSourceGateway {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
    private static final DateTimeFormatter DATE_ONLY_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;
    @Override
    public ImportSourceType getSourceType() {
        return ImportSourceType.ICS_FILE;
    }

    @Override
    public List<Event> fetchEvents(ImportCalendarInputData input) throws CalendarImportException {
        String filePath = input.getSourceIdentifier();

        if (filePath == null || filePath.isBlank()) {
            throw new CalendarImportException("Please choose a .ics file to import.");
        }

        Path path = Path.of(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new CalendarImportException("Selected file does not exist or is not a valid file.");
        }

        List<String> rawLines;
        try {
            rawLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new CalendarImportException("Failed to read .ics file.", e);
        }
        List<String> lines = unfoldLines(rawLines);
        return parseEvents(lines);
    }

    private List<String> unfoldLines(List<String> rawLines) {
        List<String> unfoldLines = new ArrayList<>();

        for (String line : rawLines) {
            if (line.startsWith(" ") || line.startsWith("\t")) {
                // This is a continuation of the previous line
                if (!unfoldLines.isEmpty()) {
                    int lastIndex = unfoldLines.size() - 1;
                    unfoldLines.set(lastIndex, unfoldLines.get(lastIndex) + line.substring(1));
                }
            } else {
                unfoldLines.add(line);
            }
        }

        return unfoldLines;
    }

    private List<Event> parseEvents(List<String> lines) {
        List<Event> events = new ArrayList<>();

        boolean inEvent = false;
        String uid = null;
        String summary = null;
        String description = null;
        LocalDateTime start = null;
        LocalDateTime end = null;
        for (String line : lines) {
            if (line.equals("BEGIN:VEVENT")) {
                inEvent = true;
                uid = null;
                summary = null;
                description = null;
                start = null;
                end = null;
            } else if (line.equals("END:VEVENT") && inEvent) {
                inEvent = false;
                if (start != null && end != null && end.isAfter(start)) {

                    String id;
                    if (uid != null && !uid.isBlank()) {
                        id = "ics:" + uid;
                    } else {
                        id = UUID.randomUUID().toString();
                    }

                    String title;
                    if (summary != null && !summary.isBlank()) {
                        title = summary;
                    } else {
                        title = "(No Title)";
                    }
                    Event event = new Event(id, title, description, start, end);
                    events.add(event);
                }
            } else if (inEvent) {
                int colonIndex = line.indexOf(':');
                if (colonIndex < 0) {
                    continue;
                }

                String header = line.substring(0, colonIndex);
                String value = line.substring(colonIndex + 1).strip();

                int semiIndex = header.indexOf(';');
                String propertyName;
                if (semiIndex > 0) {
                    propertyName = header.substring(0, semiIndex).toUpperCase();
                } else {
                    propertyName = header.toUpperCase();
                }

                switch (propertyName) {
                    case "UID":
                        uid = value;
                        break;
                    case "SUMMARY":
                        summary = unescapeText(value);
                        break;
                    case "DESCRIPTION":
                        description = unescapeText(value);
                        break;
                    case "DTSTART":
                        start = parseDateTime(header, value);
                        break;
                    case "DTEND":
                        end = parseDateTime(header, value);
                        break;
                    default:
                        break;
                }
            }
        }
        return events;
    }

    private String unescapeText(String text) {
        return text.replace("\\n", " ")
                .replace("\\N", " ")
                .replace("\\,", ",")
                .replace("\\;", ";")
                .replace("\\\\", "\\");
    }

    private LocalDateTime parseDateTime(String header, String value) {
        try {
            if (value.length() == 8) {
                return LocalDate.parse(value, DATE_ONLY_FORMAT).atStartOfDay();
            } else if (header.contains("TZID")) {
                LocalDateTime localDateTime = LocalDateTime.parse(value, DATE_TIME_FORMAT);
                String tzid = header.substring(header.indexOf("TZID=") + 5);
                return localDateTime.atZone(ZoneId.of(tzid))
                        .withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime();
            } else if (value.endsWith("Z")) {
                String withoutZ = value.substring(0, value.length() - 1);
                LocalDateTime utcTime = LocalDateTime.parse(withoutZ, DATE_TIME_FORMAT);
                return utcTime.atZone(ZoneOffset.UTC)
                        .withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime();
            } else {
                return LocalDateTime.parse(value, DATE_TIME_FORMAT);
            }
        } catch (DateTimeException e) {
            return null;
        }
    }
}

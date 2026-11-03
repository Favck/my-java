package ru.university.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DeviceFileReader {
    private final InputStream inputStream;
    private int objectsFound = 0;
    private int propertiesFound = 0;
    private int propertiesMissing = 0;

    // Список всех ожидаемых полей для каждого типа (для подсчёта пропущенных свойств)
    private static final Map<String, List<String>> EXPECTED_FIELDS = Map.of(
        "printer", List.of("model", "papercount", "iscolor", "isduplex", "printspeed"),
        "scanner", List.of("model", "issheetfed", "maxdpi", "scanspeed"),
        "mfp",     List.of("model", "papercount", "iscolor", "speedwork")
    );

    public DeviceFileReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<OfficeDevice> parse() {
        List<OfficeDevice> result = new ArrayList<>();

        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }

            // Извлекаем все пары "Ключ: Значение" (до запятой или точки с запятой)
            Map<String, String> currentBlock = new HashMap<>();

            // Разбиваем текст по запятой или точке с запятой, фильтруя мусор
            String[] tokens = content.toString().split("[,;]");
            for (String token : tokens) {
                String trimmed = token.trim();
                int colonIdx = trimmed.indexOf(':');
                if (colonIdx == -1) continue; // пропускаем лишний текст/мусор

                String key = trimmed.substring(0, colonIdx).trim().toLowerCase();
                String value = trimmed.substring(colonIdx + 1).trim();

                // Если встретили маркер начала нового объекта 'type' и блок уже наполнен
                if (key.equals("type") && !currentBlock.isEmpty()) {
                    OfficeDevice dev = buildDevice(currentBlock);
                    if (dev != null) result.add(dev);
                    currentBlock.clear();
                }

                currentBlock.put(key, value);
            }

            // Обрабатываем последний блок в файле
            if (!currentBlock.isEmpty()) {
                OfficeDevice dev = buildDevice(currentBlock);
                if (dev != null) result.add(dev);
            }

        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return result;
    }

    private OfficeDevice buildDevice(Map<String, String> data) {
        String type = data.get("type");
        if (type == null) return null;
        type = type.toLowerCase();

        List<String> expected = EXPECTED_FIELDS.get(type);
        if (expected == null) return null;

        objectsFound++;

        // Считаем прочитанные и не найденные свойства
        for (String field : expected) {
            if (data.containsKey(field)) {
                propertiesFound++;
            } else {
                propertiesMissing++;
            }
        }

        String model = data.getOrDefault("model", "Загруженное устройство");

        switch (type) {
            case "printer": {
                int paper = parseOrDefault(data.get("papercount"), 0);
                boolean color = parseBoolOrDefault(data.get("iscolor"), false);
                boolean duplex = parseBoolOrDefault(data.get("isduplex"), false);
                double speed = parseDoubleOrDefault(data.get("printspeed"), 15.0);
                return new Printer(model, paper, color, duplex, speed);
            }
            case "scanner": {
                boolean sheetfed = parseBoolOrDefault(data.get("issheetfed"), false);
                int maxDpi = parseOrDefault(data.get("maxdpi"), 600);
                double speed = parseDoubleOrDefault(data.get("scanspeed"), 10.0);
                return new DocumentScanner(model, sheetfed, maxDpi, speed);
            }
            case "mfp": {
                int paper = parseOrDefault(data.get("papercount"), 0);
                boolean color = parseBoolOrDefault(data.get("iscolor"), true);
                double speed = parseDoubleOrDefault(data.get("speedwork"), 20.0);
                return new ScannerPrinter(model, paper, color, speed);
            }
        }
        return null;
    }

    private int parseOrDefault(String val, int def) {
        try { return Integer.parseInt(val); } catch (Exception e) { return def; }
    }

    private double parseDoubleOrDefault(String val, double def) {
        try { return Double.parseDouble(val); } catch (Exception e) { return def; }
    }

    private boolean parseBoolOrDefault(String val, boolean def) {
        return val != null ? Boolean.parseBoolean(val) : def;
    }

    public int getObjectsFound() { return objectsFound; }
    public int getPropertiesFound() { return propertiesFound; }
    public int getPropertiesMissing() { return propertiesMissing; }
}
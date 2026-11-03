package ru.university.model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DeviceManager {

    public List<OfficeDevice> readFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден: " + filePath);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            DeviceFileReader reader = new DeviceFileReader(fis);
            return reader.parse();
        }
    }

    public void replaceFile(String sourcePath, String targetPath) throws IOException {
        Path src = Paths.get(sourcePath);
        Path dst = Paths.get(targetPath);
        if (!Files.exists(src)) {
            throw new FileNotFoundException("Исходный файл не найден: " + sourcePath);
        }
        Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
    }

    public void logToCsv(List<OfficeDevice> devices, String csvPath) throws IOException {
        File file = new File(csvPath);
        boolean writeHeader = !file.exists() || file.length() == 0;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            if (writeHeader) {
                pw.println("timestamp,id,model,wear_percentage,paper_count,is_jammed,extra_info");
            }
            for (OfficeDevice d : devices) {
                String extra = "";
                if (d instanceof ScannerPrinter sp) {
                    extra = "cartridge:" + sp.getCartridgeInk() + ";copies:" + sp.getTotalCopies();
                } else if (d instanceof Printer p) {
                    extra = "totalPages:" + p.getTotalPages();
                } else if (d instanceof DocumentScanner ds) {
                    extra = "scanned:" + ds.getTotalDocumentsScan();
                }
                pw.printf("%s,%d,%s,%.2f,%d,%s,%s%n",
                        timestamp, d.getId(), d.getModel(), d.getWearPercentage(),
                        d.getPaperCount(), d.isPaperJammed(), extra);
            }
        }
    }
}

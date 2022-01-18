package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static org.assertj.core.api.Assertions.assertThat;

public class HomeWork {

    @Test
    void zipTest() throws Exception {
        ZipFile zipFile = new ZipFile("src\\test\\resources\\files\\HomeWork.zip");

        // Проверка csv файла
        ZipEntry csvEntry = zipFile.getEntry("simple-csv.csv");
        try (InputStream CsvStream = zipFile.getInputStream(csvEntry)) {
            CSVReader reader = new CSVReader(new InputStreamReader(CsvStream));
            List<String[]> list = reader.readAll();
            assertThat(list)
                    .hasSize(4)
                    .contains(
                            new String[]{"Petr"},
                            new String[]{"Masha"},
                            new String[]{"Igor"},
                            new String[]{"Stepa"});
        }

        // Проверка PDF файла
        ZipEntry pdfEntry = zipFile.getEntry("simple-pdf.pdf");
        try (InputStream pdfStream = zipFile.getInputStream(pdfEntry)) {
            PDF parsed = new PDF(pdfStream);
            assertThat(parsed.author).contains("Michael Sorens");

        }

        // Проверка xlsx файла
        ZipEntry xlsxEntry = zipFile.getEntry("simple-xlsx.xlsx");
        try (InputStream stream = zipFile.getInputStream(xlsxEntry)) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(3).getCell(0).getStringCellValue())
                    .isEqualTo("Ластик");
        }
    }



}

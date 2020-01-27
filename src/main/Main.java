package main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class Main {

    public static void main(String[] args) {
        List<Autor> authors = new ArrayList<>();
        HashMap<String,Autor> authorsMap = new HashMap<>();
        generateAuthors(authors,authorsMap);

        writeAuthors(authors);

    }

    public static void generateAuthors(List<Autor> authors,HashMap<String,Autor> authorsMap){
        try {
            int id = 0;
            File file = new File("resources\\UB_cs_authors.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            Iterator<Sheet> sheets = wb.iterator();
            while (sheets.hasNext()) {
                XSSFSheet sheet = (XSSFSheet) sheets.next();     //creating a Sheet object to retrieve object
                Iterator<Row> itr = sheet.iterator();    //iterating over excel file
                Row row2 = itr.next();
                while (itr.hasNext()) {
                    Row row = itr.next();

                    Cell cell = row.getCell(0);
                    switch (cell.getCellTypeEnum()) {
                        case STRING:    //field that represents string cell type
                            Autor newAuthor = new Autor(id++, (row.getCell(1).getStringCellValue()+" "+cell.getStringCellValue().charAt(0)).toLowerCase());
                            authors.add(newAuthor);
                            authorsMap.put(newAuthor.getIme(),newAuthor);
                            break;
                        case _NONE:
                            break;
                        case NUMERIC:
                            break;
                        default:
                    }
                }

            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeAuthors(List<Autor> authors){
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(" Autori ");

        //Create row object
        XSSFRow row=null;

        //Iterate over data and write to sheet
        int rowid = 0;
        row = spreadsheet.createRow(rowid++);
        Cell naslov1 = row.createCell(0);
        naslov1.setCellValue("Id");
        Cell naslov2 = row.createCell(1);
        naslov2.setCellValue("Label");
        for (Autor a : authors) {
            row = spreadsheet.createRow(rowid++);

            Cell cell = row.createCell(0);
            cell.setCellValue(a.getId() + "");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue((String) a.getIme());

        }


        //Write the workbook in file system
        try {
            FileOutputStream out = new FileOutputStream(
                    new File("izlazFajlovi\\Autori.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Autori.xlsx written successfully");
    }
}

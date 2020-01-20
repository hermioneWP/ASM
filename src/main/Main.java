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
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class Main {

    public static void main(String[] args) {
        List<Autor> autori = new ArrayList<>();
        int id = 0;
        try {
            File file = new File("C:\\Users\\jesm\\Downloads\\ASM_PZ2_podaci_1920 (1)\\UB_cs_authors.xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
//creating Workbook instance that refers to .xlsx file
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
                            System.out.print(cell.getStringCellValue() + "\t\t\t");
                            autori.add(new Autor(id++, cell.getStringCellValue() + " " + row.getCell(1).getStringCellValue()));
                            break;
                        case _NONE:
                            break;
                        case NUMERIC:    //field that represents number cell type
                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            break;
                        default:
                    }
                    System.out.println("");
                }

            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(" Autori ");

        //Create row object
        XSSFRow row=null;

        //Iterate over data and write to sheet
        int rowid = 0;
        row = spreadsheet.createRow(rowid++);
        Cell naslov1 = row.createCell(0);
        naslov1.setCellValue("ID");
        Cell naslov2 = row.createCell(1);
        naslov2.setCellValue("Ime");
        for (Autor a : autori) {
            row = spreadsheet.createRow(rowid++);

            Cell cell = row.createCell(0);
            cell.setCellValue(a.getId() + "");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue((String) a.getIme());

        }


        //Write the workbook in file system
        try {
            FileOutputStream out = new FileOutputStream(
                    new File("C:\\Users\\jesm\\Desktop\\Autori.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Writesheet.xlsx written successfully");
    }
}

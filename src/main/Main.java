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
        HashMap<String, Autor> authorsMap = new HashMap<>();
        List<Pair> edges = new ArrayList<>();
        generateAuthors(authors, authorsMap);

        writeAuthors(authors);

        generateEdges(edges,authorsMap);
        writeEdges(edges);
    }

    public static void generateAuthors(List<Autor> authors, HashMap<String, Autor> authorsMap) {
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
                            Autor newAuthor = new Autor(id++, (row.getCell(1).getStringCellValue() + " " + cell.getStringCellValue().charAt(0)).toLowerCase());
                            authors.add(newAuthor);
                            authorsMap.put(newAuthor.getIme(), newAuthor);
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

    public static void writeAuthors(List<Autor> authors) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(" Autori ");

        //Create row object
        XSSFRow row = null;

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

    public static void convertToEnglishLatin(String authorsRow){
        authorsRow = authorsRow.replace("ć", "c");
        authorsRow = authorsRow.replace("č", "c");
        authorsRow = authorsRow.replace("š", "s");
        authorsRow = authorsRow.replace("ž", "z");
        authorsRow = authorsRow.replace("đ", "dj");
        authorsRow = authorsRow.replace("ð", "dj");
    }

    public static void generateEdges(List<Pair> edges,HashMap<String,Autor> authorsMap){
        try {
            HashSet<String> articles = new HashSet<>();
            File file = new File("resources\\UB_cs_papers_scopus.xlsx");
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            Iterator<Sheet> sheets = wb.iterator();
            while (sheets.hasNext()) {
                XSSFSheet sheet = (XSSFSheet) sheets.next();     //creating a Sheet object to retrieve object
                Iterator<Row> itr = sheet.iterator();    //iterating over excel file
                Row row = itr.next();
                while (itr.hasNext()) {
                    row = itr.next();
                    String type = row.getCell(5).getStringCellValue();
                    if (type.equalsIgnoreCase("Article") || type.equalsIgnoreCase("Conference Paper") ||
                            type.equalsIgnoreCase("Article in Press") || type.equalsIgnoreCase("Review") || type.equalsIgnoreCase("Book Chapter")) {

                        String article = row.getCell(1).getStringCellValue();
                        if (!articles.contains(article)) {
                            articles.add(article);
                            String authorsRow = row.getCell(3).getStringCellValue().toLowerCase();
                            convertToEnglishLatin(authorsRow);

                            String[] possibleAuthors = authorsRow.split(" and ");

                            List<Autor> bgAuthors = new ArrayList<>();
                            for (String a : possibleAuthors) {
                                int i = a.indexOf(',');
                                if (i > 0) {
                                    a = a.substring(0, i + 3);
                                    a = a.substring(0, i) + a.substring(i + 1);
                                }

                                if (authorsMap.containsKey(a)) {
                                    bgAuthors.add(authorsMap.get(a));
                                }
                            }
                            for (int i = 0; i < bgAuthors.size() - 1; i++) {
                                for (int j = i + 1; j < bgAuthors.size(); j++) {
                                    boolean found = false;
                                    for (Pair p : edges) {
                                        if (p.getFirst().getIme().equalsIgnoreCase(bgAuthors.get(i).getIme()) &&
                                                p.getSecond().getIme().equalsIgnoreCase(bgAuthors.get(j).getIme())) {
                                            p.addWeight();
                                            found = true;
                                        } else if (p.getFirst().getIme().equalsIgnoreCase(bgAuthors.get(j).getIme()) &&
                                                p.getSecond().getIme().equalsIgnoreCase(bgAuthors.get(i).getIme())) {
                                            p.addWeight();
                                            found = true;
                                        }
                                    }
                                    if (!found) {
                                        edges.add(new Pair(bgAuthors.get(i), bgAuthors.get(j)));
                                    }
                                }
                            }
                        }
                    }
                }
                for(Pair p:edges){
                    System.out.println(p.getFirst().getIme()+" "+p.getSecond().getIme()+" "+p.getWeight());
                }
                System.out.println(edges.size());

            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeEdges(List<Pair> edges) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet("Edges");

        //Create row object
        XSSFRow row = null;

        //Iterate over data and write to sheet
        int rowid = 0;
        row = spreadsheet.createRow(rowid++);
        Cell naslov1 = row.createCell(0);
        naslov1.setCellValue("source");
        Cell naslov2 = row.createCell(1);
        naslov2.setCellValue("target");
        Cell naslov3 = row.createCell(2);
        naslov3.setCellValue("weight");
        for (Pair p : edges) {
            row = spreadsheet.createRow(rowid++);

            Cell cell = row.createCell(0);
            cell.setCellValue(p.getFirst().getId() + "");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(p.getSecond().getId() + "");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(p.getWeight() + "");

        }


        //Write the workbook in file system
        try {
            FileOutputStream out = new FileOutputStream(
                    new File("izlazFajlovi\\Edges.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Edges.xlsx written successfully");
    }
}

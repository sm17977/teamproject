package pesonalFinanceApp;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CompanyData {
    private ArrayList<String> fileNames = new ArrayList<>();
    private Map<String, String> companyNameAndSymbol = new HashMap<>();

    CompanyData(String ...fileName) {
        for (int i = 0; i < fileName.length; i++) {
            fileNames.add(fileName[i]);
        }
        readCSVCompanyData();
    }

    public String getSymbol(String companyName) {
        return companyNameAndSymbol.get(companyName);
    }

    public ArrayList getCompanies() {
        ArrayList<String> companies = new ArrayList<>();
        if (companyNameAndSymbol.size() == 0) return null;
        for (Map.Entry<String, String> entry : companyNameAndSymbol.entrySet()) {
            companies.add(entry.getKey());
        }
        companies.sort((String s1, String s2) -> s1.compareTo(s2));
        return companies;
    }

    private void readCSVCompanyData() {
        FileReader file;
        for (int i = 0; i < fileNames.size(); i++) {
            file = openFile(fileNames.get(i));
            if(file != null) {
                readLines(file);
            }
        }
    }

    private FileReader openFile(String fileName) {
        FileReader file;
        try {
            file = new FileReader(fileName);
            return file;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private void readLines(FileReader file) {
        try {
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();
            while (line != null) {
                List<String> lineSplit = processLine(line);
                companyNameAndSymbol.put(lineSplit.get(1), lineSplit.get(0));
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private List processLine(String rawLine) {
        String line = rawLine.replace("\"", "");
        List<String> lineItems = Arrays.asList(line.split(","));
        return lineItems;
    }
}

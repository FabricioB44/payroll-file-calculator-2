package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class PayrollCalculator {
    public static void main(String[] args) throws Exception {
        System.out.println("Please write the file you'd like to process: ");
        Scanner myScanner = new Scanner(System.in);

        try {
            File oldFile = new File(myScanner.nextLine());
            FileReader fileReader = new FileReader(oldFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            System.out.println("Please write the name for your new processed file: ");
            File newFile = new File(myScanner.nextLine());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));


            Employee[] employees = new Employee[8];
            String line;
            int index = 0;


            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double hoursWorked = Double.parseDouble(parts[2]);
                double payRate = Double.parseDouble(parts[3]);

                employees[index] = new Employee(id, name, hoursWorked, payRate);
                // System.out.printf("ID: %d | Name: %s | Pay: %.2f\n", employees[index].getEmployeeId(), employees[index].getName(), employees[index].getPayRate());
                index++;
            }

            if (newFile.getName().endsWith(".json")) {
                bufferedWriter.write("[");
                bufferedWriter.newLine();

                for (int i = 0; i < employees.length; i++) {
                    if (employees[i] != null) {

                        bufferedWriter.write(employees[i].toJson());

                        if (i < employees.length - 1 && employees[i + 1] != null) {
                            bufferedWriter.write(",");
                        }

                        bufferedWriter.newLine();
                    }
                }

                bufferedWriter.write("]");
            } else {
                for (Employee employee : employees) {
                    if (employee != null) {
                        bufferedWriter.write(employee.toString());
                        bufferedWriter.newLine();
                    }
                }
            }

            bufferedWriter.close();
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("File could not be found!");
        }

        myScanner.close();
    }
}

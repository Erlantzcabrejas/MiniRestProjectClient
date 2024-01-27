import com.google.gson.Gson;
import pojo.Customer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner myObj = new Scanner(System.in);
        Scanner referenceObj = new Scanner(System.in);

		String action = "";

        while (!action.equalsIgnoreCase("exit")){
            System.out.println("Choose to INSERT values, READ a customers information or EXIT to finish: ");
            action = myObj.nextLine();

            if(action.equalsIgnoreCase("insert")){
                addCustomers();
            }else if(action.equalsIgnoreCase("read")) {
                System.out.println("Insert the customers reference :");
                try{
                    Long reference = referenceObj.nextLong();
                    readCustomerData(reference);
                }catch(InputMismatchException e){
                    referenceObj = new Scanner(System.in);
                    System.out.println("Introduce a numeric value for the reference");
                }
            }else if(action.equalsIgnoreCase("exit")){
                System.out.println("Exiting the program...");
            }else{
                System.out.println("Please choose the right action to take: INSERT to insert values, or READ to obtain the information from a customer, or EXIT to finish the program");
            }
        }
    }

    public static void addCustomers() throws Exception {
        String jsonData = readCSVFile();
        if(jsonData != null && !jsonData.trim().isEmpty()) {
            URL url = new URL("http://localhost:8080/api/customers");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();

            PrintWriter writer = new PrintWriter(con.getOutputStream());
            writer.print(jsonData);
            writer.flush();

            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response);
            } else {
                System.out.println("POST request did not work.");
            }
        }
    }

    public static void readCustomerData(Long reference) throws IOException {
        URL url = new URL("http://localhost:8080/api/customers/" + reference);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response);
        } else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND){
            System.out.println("Customer was not found.");
        }else{
            System.out.println("There was an error with the request");
        }
    }

    public static String readCSVFile(){
        File csvFile = new File("src/main/resources/data/Customers.csv");
        ArrayList<Customer> listOfCustomers = new ArrayList<>();
        String json = "";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] customerArray = line.split(",");
                Customer customer = new Customer(Long.parseLong(customerArray[0].trim()), customerArray[1].trim(), customerArray[2].trim(), customerArray[3].trim(), customerArray[4].trim(), customerArray[5].trim(),
                        customerArray[6], customerArray[7]);

                listOfCustomers.add(customer);
            }

            json = new Gson().toJson(listOfCustomers);

            return json;
        }catch(FileNotFoundException e){
            System.out.println("The file Customers.csv could not be found");
        }catch(Exception e  ){
            System.out.println("Error while reading the customers");
        }
        return json;
    }
}

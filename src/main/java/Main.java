import com.google.gson.Gson;
import pojo.Customer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner myObj = new Scanner(System.in);
        Scanner referenceObj = new Scanner(System.in);

		String action = "";

        while (!action.equalsIgnoreCase("exit")){
            System.out.println("Choose to INSERT values or READ a customers information: ");
            action = myObj.nextLine();

            if(action.equalsIgnoreCase("insert")){
                addCustomers();
            }else if(action.equalsIgnoreCase("read")) {
                System.out.println("Insert the customers reference :");
                Long reference = referenceObj.nextLong();
                readCustomerData(reference);
            }else if(action.equalsIgnoreCase("exit")){
                System.out.println("Exiting the program...");
            }else{
                System.out.println("Please choose the right action to take: INSERT to insert values, or READ to obtain the information from a customer, or EXIT to finish the program");
            }
        }
    }

    public static void addCustomers() throws IOException {
        String jsonData = readCSVFile();

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

            // print result
            System.out.println(response);
        } else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND){
            System.out.println("Customer was not found.");
        }else{
            System.out.println("There was an error with the request");
        }
    }

    public static String readCSVFile() throws IOException{
        File csvFile = new File("src/main/resources/data/Customers.csv");

        ArrayList<Customer> listOfCustomers = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] customerArray = line.split(",");
            Customer customer = new Customer(Long.parseLong(customerArray[0]), customerArray[1], customerArray[2], customerArray[3], customerArray[4], customerArray[5],
                    customerArray[6], customerArray[7]);

            listOfCustomers.add(customer);
        }

        String json = new Gson().toJson(listOfCustomers);

        return json;
    }
}

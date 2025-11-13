package org.example.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.control.Alert;
import org.example.models.Transaction;
import org.example.models.TransactionCategory;
import org.example.models.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {

    public static boolean register(String username, String email, String password){
        JsonObject registerRequest = new JsonObject();
        registerRequest.addProperty("name", username);
        registerRequest.addProperty("email", email);
        registerRequest.addProperty("password", password);

        try {
            JsonObject response = ApiUtil.fetchApi("/api/v1/user/register", ApiUtil.RequestMethod.POST, registerRequest);

            int status = response.get("status").getAsInt();

            if (status != 201) {
                System.out.println("❌ Login failed: " + response.get("error").getAsString());
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Failed to authenticate Due to:\n " + response.get("error").getAsString());
                return false;
            }

            System.out.println("✅ Register user is Successful: " + response);
            Utility.showAlertDialog(Alert.AlertType.INFORMATION, "Registration successful");
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + e.getMessage());
            return false;
        }
    }

    public static User getUserByEmail(String email) {
        try {


            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/user?email=" + email, ApiUtil.RequestMethod.GET, null
            );

            int status = response.get("status").getAsInt();

            if (status != 200) {
                System.out.println("Can't find user with email: " + email);
                return null;
            }

            JsonObject userResponse = response.get("user").getAsJsonObject();
            System.out.println("User found: " + response.get("user").toString());
            return Utility.responseToUserMapper(userResponse);

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + ex.getMessage());
            return null;
        }
    }

    public static User login(String email, String password){
        JsonObject loginRequest = new JsonObject();
        loginRequest.addProperty("email", email);
        loginRequest.addProperty("password", password);


        try {
            JsonObject response = ApiUtil.fetchApi("/api/v1/user/login", ApiUtil.RequestMethod.POST, loginRequest);

            int status = response.get("status").getAsInt();

            if (status != 200) {
                System.out.println("❌ Login failed: " + response.get("error").getAsString());
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Failed to authenticate Due to:\n " + response.get("error").getAsString());
                return null;
            }

            if (status == 200) {
                System.out.println("✅ Login successful: " + response);
                Utility.showAlertDialog(Alert.AlertType.INFORMATION, "Login successful");
                return Utility.responseToUserMapper(response.get("data").getAsJsonObject());
            }
            return  null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + e.getMessage());
            return null;
        }
    }


    public static JsonObject postTransactionCategory(Long userId, String categoryName, String categoryColor) {
        JsonObject request = new JsonObject();
        request.addProperty("userId", userId);
        request.addProperty("categoryName", categoryName);
        request.addProperty("categoryColor", categoryColor);

        try {


            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction-category",
                    ApiUtil.RequestMethod.POST,
                    request
            );

            int status = response.get("status").getAsInt();

            if (status != 201) {
                System.out.println("Create new category failed:" + response.get("message").toString());
                return null;
            }

            System.out.println("Create new category successful" + response.get("message").toString());
            return response;
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + exception.getMessage());
            return null;
        }
    }

    public static List<TransactionCategory> getAllTransactionCategoriesByUser(Long userId) {
        List<TransactionCategory> transactionCategories = new ArrayList<>();

        try {
            // API Response
            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction-category/user/" + userId,
                    ApiUtil.RequestMethod.GET,
                    null
            );


            if (response.has("status") && response.get("status").getAsInt() !=  200){
                System.out.println(response.get("error").getAsString());
                return transactionCategories;
            }

            String message = response.has("message") ? response.get("message").getAsString() : "No message provided.";

            System.out.println("Success: " + message);

            // Extract "data"
            JsonObject data = response.get("data").getAsJsonObject();

            // Optional: extract user info (for logging or use)
//            if (data.has("user")) {
//                JsonObject user = data.get("user").getAsJsonObject();
//                System.out.println("User: " + user.get("name").getAsString() + " (" + user.get("email").getAsString() + ")");
//            }
            // Extract categories array
            if (!data.has("categories")) {
                System.out.println("No categories found for this user.");
                return transactionCategories;
            }

            JsonArray categories = data.get("categories").getAsJsonArray();

            if (categories.size() == 0) {
                System.out.println("User has no transaction categories.");
                return transactionCategories;
            }

            // Parse each category object
            for (JsonElement categoryElement : categories) {
                JsonObject categoryObj = categoryElement.getAsJsonObject();

                Long id = categoryObj.has("id") ? categoryObj.get("id").getAsLong() : null;
                String name = categoryObj.has("name") ? categoryObj.get("name").getAsString() : "Unnamed";
                String color = categoryObj.has("color") ? categoryObj.get("color").getAsString() : "#FFFFFF";

                TransactionCategory category = new TransactionCategory(id, name, color);
                transactionCategories.add(category);
            }

            System.out.println("Fetched " + transactionCategories.size() + " categories.");

        } catch (IOException e) {
            System.err.println("Network or I/O error while fetching categories: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        return transactionCategories;
    }


    public static boolean putTransactionCategory(Long categoryId, String newCategoryName, String newCategoryColor) {

        JsonObject request = new JsonObject();
        request.addProperty("categoryId", categoryId);
        request.addProperty("categoryName", newCategoryName);
        request.addProperty("categoryColor", newCategoryColor);

        try {

            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction-category",
                    ApiUtil.RequestMethod.PUT,
                    request
            );

            if (response.has("status") && response.get("status").getAsInt() !=  200){
                System.out.println(response.get("error").getAsString());
                return false;
            }

            boolean success = response.has("success") && response.get("success").getAsBoolean();
            String message = response.has("message") ? response.get("message").getAsString() : "No message provided.";


            System.out.println("Success: " + message);
            return true;

        } catch (IOException e) {
            System.err.println("Network or I/O error while update category: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteTransactionCategory(Long categoryId) {
        try {
            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction-category/" + categoryId,
                    ApiUtil.RequestMethod.DELETE,
                    null
            );


            if (response.has("status") && response.get("status").getAsInt() !=  200){
                System.out.println(response.get("error").getAsString());
                return false;
            }

            boolean success = response.has("success") && response.get("success").getAsBoolean();
            String message = response.has("message") ? response.get("message").getAsString() : "No message provided.";


            System.out.println("Success: " + message);
            return true;

        } catch (IOException e) {
            System.err.println("Network or I/O error while deleting categories: " + e.getMessage());
            return false;
        }
    }

    // Transactions

    public static boolean postTransaction(Long userId, Long categoryId, String name, Double amount, LocalDate date, String type){
        try {
            // create Request body
            JsonObject request = new JsonObject();
            request.addProperty("name", name);
            request.addProperty("amount", amount);
            request.addProperty("type", type);
            request.addProperty("date", date.format(DateTimeFormatter.ISO_DATE));
            request.addProperty("userId", userId);
            request.addProperty("categoryId", categoryId);

            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction",
                    ApiUtil.RequestMethod.POST,
                    request
            );
            if (response.has("status") && response.get("status").getAsInt() != 201){
                String error = response.get("error").toString();
                System.out.println("Failed: "+ error);
                return false;
            }
            System.out.println("Success: " + response.get("message").toString());

            return true;
        }catch (IOException ex){
            return false;
        }
    }


    public static boolean putTransaction(Long transactionId, String transactionName, Double transactionAmount, String transactionType,
                                         LocalDate date, Long categoryId){
        try {
            JsonObject request = new JsonObject();
            request.addProperty("id", transactionId);
            request.addProperty("name", transactionName);
            request.addProperty("amount", transactionAmount);
            request.addProperty("type", transactionType);
            request.addProperty("date", date.format(DateTimeFormatter.ISO_DATE));
            request.addProperty("categoryId", categoryId);

            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction",
                    ApiUtil.RequestMethod.PUT,
                    request
            );

            if (response.has("status") && response.get("status").getAsInt() != 200){
                String error = response.get("error").toString();
                System.out.println("Failed: "+ error);
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Something went wrong:\n"+error);
                return false;
            }

            if(response.has("success") && response.get("success").getAsBoolean())
                System.out.println("Success: " + response.get("message").toString());
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + ex.getMessage());
            Utility.showAlertDialog(Alert.AlertType.ERROR, "ConnectionError\nCheck connection and refresh");
            return false;
        }
    }


    public static List<Transaction> getUserTransactions(int userId){

        try {
            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction/"+userId,
                    ApiUtil.RequestMethod.GET,
                    null
            );
            var transactions = new ArrayList<Transaction>();

            if (response.has("status") && response.get("status").getAsInt() != 200){
                String error = response.get("error").toString();
                System.out.println("Failed: "+ error);
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Something went wrong:\n"+error);
                return transactions;
            }

            System.out.println("Success: " + response.get("message").toString());

            // Extract "data"
            if (!response.has("data") || response.get("data").isJsonNull()) {
                System.out.println("No 'data' field found in response.");
                return transactions;
            }

            JsonObject data = response.get("data").getAsJsonObject();

            if(data.isJsonNull()){
                System.out.println("User doesn't have transactions");
                return transactions;
            }

            JsonArray transactionsList = data.get("transactions").getAsJsonArray();

            if(transactionsList.isEmpty()){
                System.out.println("User doesn't have transactions");
                return transactions;
            }

            // parse the transactions
            parseTransactionsList(transactions, transactionsList);
            System.out.println("Fetched " + transactions.size() + " categories.");
            return transactions;

        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + ex.getMessage());
            Utility.showAlertDialog(Alert.AlertType.ERROR, "ConnectionError\nCheck connection and refresh");
            return new ArrayList<Transaction>();
        }

    }


    public static List<Transaction> getRecentTransactions(Long userId, int page, int size){
        try {

            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction/recent/user/"+userId+"?page="+page+"&size="+size,
                    ApiUtil.RequestMethod.GET,
                    null
            );

            var transactions = new ArrayList<Transaction>();

            if (response.has("status") && response.get("status").getAsInt() != 200){
                String error = response.get("error").toString();
                System.out.println("Failed: "+ error);
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Something went wrong:\n"+error);
                return transactions;
            }

            System.out.println("Success: " + response.get("message").toString());

            // Extract "data"
            if (!response.has("data") || response.get("data").isJsonNull()) {
                System.out.println("No 'data' field found in response.");
                return transactions;
            }

            JsonObject data = response.get("data").getAsJsonObject();

            if(data.isJsonNull()){
                System.out.println("User doesn't have transactions");
                return transactions;
            }

            JsonArray content = data.get("content").getAsJsonArray();

            // parse data
            parseTransactionsList(transactions, content);

            return transactions;
        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + ex.getMessage());
            Utility.showAlertDialog(Alert.AlertType.ERROR, "ConnectionError\nCheck connection and refresh");
            return new ArrayList<Transaction>();
        }

    }

    private static ArrayList<Transaction> parseTransactionsList(ArrayList<Transaction> transactions, JsonArray content) {
        for(JsonElement transaction: content){
            JsonObject transactionObj = transaction.getAsJsonObject();
            Long id = transactionObj.get("id").getAsLong();
            String name = transactionObj.get("name").getAsString();
            Double amount = transactionObj.get("amount").getAsDouble();
            String type = transactionObj.get("type").getAsString();

            // Parse LocalDate
            String dateStr = transactionObj.get("date").getAsString();
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);

            // Parse Category
            JsonObject categoryObj = transactionObj.get("category").getAsJsonObject();
            TransactionCategory category = new TransactionCategory(
                    categoryObj.get("id").getAsLong(),
                    categoryObj.get("name").getAsString(),
                    categoryObj.get("color").getAsString()
            );

            // Create Transaction object
            transactions.add(new Transaction(id, name, amount, type, date, category));
        }
        return transactions;
    }

    public static boolean deleteTransaction(Long transactionId){

        try {
            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction/"+transactionId,
                    ApiUtil.RequestMethod.DELETE,
                    null
            );

            if (response.has("status") && response.get("status").getAsInt() != 200){
                String error = response.get("error").toString();
                System.out.println("Failed: "+ error);
                Utility.showAlertDialog(Alert.AlertType.ERROR, "Something went wrong:\n"+error);
                return false;
            }

            if(response.has("success") && response.get("success").getAsBoolean())
            System.out.println("Success: " + response.get("message").toString());
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("⚠️ Network or connection error: " + ex.getMessage());
            Utility.showAlertDialog(Alert.AlertType.ERROR, "ConnectionError\nCheck connection and refresh");
            return false;
        }
    }
}
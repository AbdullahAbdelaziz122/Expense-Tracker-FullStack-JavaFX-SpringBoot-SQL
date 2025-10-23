package org.example.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.models.TransactionCategory;
import org.example.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {

    public static User getUserByEmail(String email){
        try {


            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/user?email=" + email, ApiUtil.RequestMethod.GET, null
            );

            int status = response.get("status").getAsInt();

            if(status != 200){
                System.out.println("Can't find user with email: "+ email );
                return null;
            }

            JsonObject userResponse = response.get("user").getAsJsonObject();
            System.out.println("User found: "+ response.get("user").toString());
            return Utility.responseToUserMapper(userResponse);

        }catch (IOException ex){
            ex.getMessage();
            return null;
        }
    }


    public static JsonObject postTransactionCategory(Long userId, String categoryName, String categoryColor){
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

            boolean status = response.get("success").getAsBoolean();

            if(!status){
                System.out.println("Create new category failed:" + response.get("message").toString());
                return null;
            }

            System.out.println("Create new category successful" + response.get("message").toString());
            return response;
        }catch (IOException exception){
            exception.getMessage();
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

            boolean success = response.has("success") && response.get("success").getAsBoolean();
            String message = response.has("message") ? response.get("message").getAsString() : "No message provided.";

            if (!success) {
                System.out.println("Failed to fetch transaction categories: " + message);
                return transactionCategories;
            }

            System.out.println("Success: " + message);

            // Extract "data"
            if (!response.has("data") || response.get("data").isJsonNull()) {
                System.out.println("No 'data' field found in response.");
                return transactionCategories;
            }

            JsonObject data = response.get("data").getAsJsonObject();

            // Optional: extract user info (for logging or use)
            if (data.has("user")) {
                JsonObject user = data.get("user").getAsJsonObject();
                System.out.println("User: " + user.get("name").getAsString() + " (" + user.get("email").getAsString() + ")");
            }

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
            System.err.println("⚠Network or I/O error while fetching categories: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        return transactionCategories;
    }


}

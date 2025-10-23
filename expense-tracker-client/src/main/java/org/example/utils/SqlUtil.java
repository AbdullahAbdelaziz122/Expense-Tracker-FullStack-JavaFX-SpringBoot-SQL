package org.example.utils;

import com.google.gson.JsonObject;
import org.example.models.User;

import java.io.IOException;

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
            System.out.println("✅ User found: "+ response.get("user").toString());
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
                System.out.println("❌ Create new category failed:" + response.get("message").toString());
                return null;
            }

            System.out.println("✅ Create new category successful" + response.get("message").toString());
            return response;
        }catch (IOException exception){
            exception.getMessage();
            return null;
        }
    }

    public static JsonObject getAllTransactionCategoriesByUser(Long userId){

        try {

            JsonObject response = ApiUtil.fetchApi(
                    "/api/v1/transaction-category/user/" + userId,
                    ApiUtil.RequestMethod.GET,
                    null
            );

            boolean status = response.get("success").getAsBoolean();

            if(!status){
                System.out.println("❌ " + response.get("message").toString());
                return null;
            }

            System.out.println("✅ Fetched data" + response.get("message").toString());
            return response;

        }catch (IOException exception){
            exception.getMessage();
            return null;
        }

    }

}

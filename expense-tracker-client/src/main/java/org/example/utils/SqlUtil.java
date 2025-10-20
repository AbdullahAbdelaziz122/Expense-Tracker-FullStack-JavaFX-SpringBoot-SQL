package org.example.utils;

import com.google.gson.JsonObject;
import org.example.User;

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

            int status = response.get("status").getAsInt();

            if(status != 201){
                System.out.println("❌ Create new category failed:" + response.toString());
                return null;
            }
            System.out.println("✅ Create new category successful" + response.toString());

            return response;
        }catch (IOException exception){
            exception.getMessage();
            return null;
        }
    }
}

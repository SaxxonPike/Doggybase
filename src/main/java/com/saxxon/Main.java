package com.saxxon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        try {
            /* future improvement: move to external configuration */
            var apiUrl = new URL("https://dog.ceo/");
            var apiService = new DogCeoService(apiUrl);

            if (args.length < 1)
            {
                var doggy = apiService.getRandom();
                System.out.println(doggy);
            }
            else
            {
                var doggies = apiService.getRandomForBreed(args[0], 1);
                if (doggies.isEmpty())
                {
                    System.out.println("No images found for the specified breed.");
                    exit(ExitCodes.NoImagesFound);
                }
                System.out.println(doggies.get(0));
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid API URL.");
            exit(ExitCodes.InvalidApiUrl);
        } catch (DogCeoException e) {
            System.out.print("An API exception has occurred: ");
            System.out.println(e.getMessage());
            exit(ExitCodes.ApiException);
        } catch (IOException e) {
            System.out.print("An IO exception has occurred: ");
            System.out.println(e.getMessage());
            exit(ExitCodes.IoException);
        }

        /* ordinarily we could just exit the function normally, but Retrofit would
        *  occupy threads far longer than the lifetime of this program as described
        *  in the Github issue here: https://github.com/square/retrofit/issues/3144 */
        exit(0);
    }
}

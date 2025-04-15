package me.yleoft.zHomes;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpdateChecker {

    public String getVersion() {
        try {
            String jsonString = readUrl("https://api.github.com/repos/yL3oft/zHomes/tags");
            JsonArray json = (JsonArray) new JsonParser().parse(jsonString);
            JsonObject item = (JsonObject) json.get(0);
            return item.get("name").toString().replace("\"", "");
        }catch (Exception e) {
            e.printStackTrace();
        }

        return Main.getInstance().getDescription().getVersion();
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public String update(String pluginName, String version) {
        try {
            File dir = Main.getInstance().getDataFolder().getParentFile();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir.toPath(), pluginName+"-*.jar")) {
                for (Path file : stream) {
                    try {
                        Files.delete(file);
                    } catch (IOException ignored) {
                    }
                }
            } catch (IOException ignored) {
            }

            String path = dir+"/"+pluginName+"-"+version+".jar";
            File newF = new File(path);
            downloadFile(newF, getPluginDownloadURL(123141));
            return path;
        } catch (Exception e) {}
        return "ERROR";
    }

    public static void downloadFile(File destination, String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.connect();
            FileOutputStream outputStream = new FileOutputStream(destination);
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int readBytes = 0;
            while ((readBytes = inputStream.read(buffer)) > 0)
                outputStream.write(buffer, 0, readBytes);
            outputStream.close();
            inputStream.close();
            connection.disconnect();
        } catch (Exception exception) {}
    }

    public static String getPluginDownloadURL(int id) {
        return "https://api.spiget.org/v2/resources/" + id + "/download";
    }

}

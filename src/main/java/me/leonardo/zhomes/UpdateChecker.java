package me.leonardo.zhomes;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UpdateChecker {

    public String getVersion() {
        try {
            String jsonString = readUrl("https://api.github.com/repos/yL3oft/zHomes/tags");
            JSONArray json = (JSONArray) new JSONParser().parse(jsonString);
            JSONObject item = (JSONObject) json.get(0);
            String name = (String) item.get("name");

            return name;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return Main.main.getDescription().getVersion();
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

    public void update() {
        JavaPlugin plugin = (JavaPlugin) Main.main.getServer().getPluginManager().getPlugin("zHomes");
        Method getFileMethod;
        try {
            getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
            getFileMethod.setAccessible(true);
            File f = (File) getFileMethod.invoke(plugin);

            f.delete();

            downloadFile(f, getPluginDownloadURL(104825));
        } catch (Exception e) {}
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

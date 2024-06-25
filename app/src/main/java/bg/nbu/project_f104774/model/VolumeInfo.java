package bg.nbu.project_f104774.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolumeInfo {
    private String title;
    private List<String> authors;
    private String description;
    private List<String> categories;

    public static VolumeInfo fromJson(JSONObject jsonObject) throws JSONException {
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.title = jsonObject.optString("title");

        JSONArray authorsArray = jsonObject.optJSONArray("authors");
        if (authorsArray != null) {
            volumeInfo.authors = new ArrayList<>();
            for (int i = 0; i < authorsArray.length(); i++) {
                volumeInfo.authors.add(authorsArray.optString(i));
            }
        }

        volumeInfo.description = jsonObject.optString("description");

        JSONArray categoriesArray = jsonObject.optJSONArray("categories");
        if (categoriesArray != null) {
            volumeInfo.categories = new ArrayList<>();
            for (int i = 0; i < categoriesArray.length(); i++) {
                volumeInfo.categories.add(categoriesArray.optString(i));
            }
        }

        return volumeInfo;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("authors", new JSONArray(authors));
            jsonObject.put("description", description);
            jsonObject.put("categories", new JSONArray(categories));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
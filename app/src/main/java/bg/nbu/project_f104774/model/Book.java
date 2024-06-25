package bg.nbu.project_f104774.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Book {
    private String id;
    private VolumeInfo volumeInfo;

    public static Book fromJson(JSONObject jsonObject) throws JSONException {
        Book book = new Book();
        book.id = jsonObject.optString("id");
        book.volumeInfo = VolumeInfo.fromJson(jsonObject.optJSONObject("volumeInfo"));
        return book;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("volumeInfo", new JSONObject(volumeInfo.toJson()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}

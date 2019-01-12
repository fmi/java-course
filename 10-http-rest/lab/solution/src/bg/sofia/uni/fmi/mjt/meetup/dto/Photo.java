package bg.sofia.uni.fmi.mjt.meetup.dto;

import com.google.gson.annotations.SerializedName;

public class Photo {

	private long id;

	@SerializedName("photo_link")
	private String photoLink;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	@Override
	public String toString() {
		return "Photo [photoLink=" + photoLink + "]";
	}
}

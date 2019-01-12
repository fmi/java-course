package bg.sofia.uni.fmi.mjt.meetup.dto;

import com.google.gson.annotations.SerializedName;

public enum Status {
	
	@SerializedName("cancelled") CANCELLED,
	@SerializedName("draft") DRAFT,
	@SerializedName("past") PAST,
	@SerializedName("proposed") PROPOSED,
	@SerializedName("suggested") SUGGESTED,
	@SerializedName("upcoming") UPCOMING
	
}

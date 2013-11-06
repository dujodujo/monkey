package si.android.toy.content;

import java.util.ArrayList;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
	
	public static final Creator CREATOR = new Creator();
	
	private ArrayList<Location> locations = new ArrayList<Location>();	
	private int points;
	private long id;
	private String name;
	private String description;
	private String mapId;
	private String tableId;
	private long startId;
	private long stopId;
	private String category;
	
	public static class Creator implements Parcelable.Creator<Track> {

		public Track createFromParcel(Parcel source) {
			ClassLoader classLoader = getClass().getClassLoader();
			Track track = new Track();
			
			track.id = source.readLong();
			track.name = source.readString();
			track.description = source.readString();
			track.mapId = source.readString();
			track.tableId = source.readString();
			track.startId = source.readLong();
			track.stopId = source.readLong();
			track.category = source.readString();
			track.points =source.readInt();
			
			for(int i = 0; i < track.points; i++) {
				Location location = source.readParcelable(classLoader);
				track.locations.add(location);
			}			
			return track;
		}

		public Track[] newArray(int size) {
			return new Track[size];
		}
	}
	
	private Track() {}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(mapId);
		dest.writeString(tableId);
		dest.writeString(category);
		dest.writeLong(startId);
		dest.writeLong(stopId);
		dest.writeInt(points);
		
		for(int i = 0; i < points; i++) {
			dest.writeParcelable(locations.get(i), 0);
		}
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public long getStartId() {
		return startId;
	}

	public void setStartId(long startId) {
		this.startId = startId;
	}

	public long getStopId() {
		return stopId;
	}

	public void setStopId(long stopId) {
		this.stopId = stopId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}

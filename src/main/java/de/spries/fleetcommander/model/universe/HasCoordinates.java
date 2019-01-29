package de.spries.fleetcommander.model.universe;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface HasCoordinates {

	int getX();

	int getY();

	default double distanceTo(HasCoordinates other) {
		int distanceX = getX() - other.getX();
		int distanceY = getY() - other.getY();
		return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
	}

	static <T extends HasCoordinates> List<T> sortByDistanceAsc(Collection<T> objects, T referenceObject) {
		Comparator<T> CLOSE_OBJECTS_FIRST = Comparator.comparingDouble(o -> o.distanceTo(referenceObject));
		return objects.stream().sorted(CLOSE_OBJECTS_FIRST).collect(Collectors.toList());
	}

}

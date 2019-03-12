
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import domain.Path;
import domain.Segment;

@Service
public class SegmentService {

	public List<Segment> getAllSegments(final Path path) {
		List<Segment> fullPath = null;
		Segment next = null;
		if (path != null) {
			next = path.getOrigin();
			fullPath = new ArrayList<>();
		}
		while (next != null) {
			fullPath.add(next);
			next = next.getDestination();
		}
		return fullPath;
	}
}

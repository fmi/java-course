public class SocialDistanceMaximizer {

    public static int maxDistance(int[] seats) {
        int numberOfSeats = seats.length;
        int maxDistance = 0;
        int currentDistance = 0;

        boolean freeSeatsAtStart = true;

        for (int i = 0; i < numberOfSeats; i++) {
            if (seats[i] == 0) {
                ++currentDistance;
            } else {
                if (freeSeatsAtStart) {
                    if (currentDistance > maxDistance) {
                        maxDistance = currentDistance;
                    }
                    freeSeatsAtStart = false;
                } else {
                    int halfCurrentDistance = currentDistance % 2 == 0 ? currentDistance / 2 : currentDistance / 2 + 1;
                    if (halfCurrentDistance > maxDistance) {
                        maxDistance = halfCurrentDistance;
                    }
                }

                currentDistance = 0;
            }
        }

        if (currentDistance > maxDistance) {
            maxDistance = currentDistance;
        }

        return maxDistance;
    }

}

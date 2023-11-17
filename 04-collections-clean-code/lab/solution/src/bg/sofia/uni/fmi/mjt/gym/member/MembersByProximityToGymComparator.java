package bg.sofia.uni.fmi.mjt.gym.member;

import java.util.Comparator;

public class MembersByProximityToGymComparator implements Comparator<GymMember> {
    private final Address gymAddress;

    public MembersByProximityToGymComparator(Address address) {
        this.gymAddress = address;
    }

    @Override
    public int compare(GymMember o1, GymMember o2) {
        return Double.compare(o1.getAddress().getDistanceTo(gymAddress), o2.getAddress().getDistanceTo(gymAddress));
    }
}

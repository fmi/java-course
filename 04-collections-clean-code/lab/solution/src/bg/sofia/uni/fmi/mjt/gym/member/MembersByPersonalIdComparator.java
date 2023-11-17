package bg.sofia.uni.fmi.mjt.gym.member;

import java.util.Comparator;

public class MembersByPersonalIdComparator implements Comparator<GymMember> {
    @Override
    public int compare(GymMember o1, GymMember o2) {
        return o1.getPersonalIdNumber().compareTo(o2.getPersonalIdNumber());
    }
}
